package cn.ilanhai.framework.testNg;

import static com.jayway.restassured.RestAssured.given;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.alibaba.fastjson.JSON;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
public class HTTPReqGen {

	public enum HttpType {
		PUT, GET, DELETE, POST
	}

	protected static final Logger logger = LoggerFactory.getLogger(HTTPReqGen.class);

	private RequestSpecification reqSpec;
	private DataReader myInputData;
	private String call_host = "";
	private String call_suffix = "";
	private String call_string = "";
	private HttpType call_type = null;
	private String body = "";
	private Map<String, String> headers = new HashMap<String, String>();
	private HashMap<String, String> cookie_list = new HashMap<String, String>();
	private String template;
	private String id;
	private static Map<String, Map<String, Object>> responseMap = new HashMap<String, Map<String, Object>>();

	public Map<String, String> getHeaders() {
		return headers;
	}

	public String getCallString() {
		return call_string;
	}
	public HTTPReqGen(DataReader myInputData) {
		reqSpec = given().relaxedHTTPSValidation();
		this.myInputData = myInputData;
	}

	public HTTPReqGen(String proxy) {
		reqSpec = given().relaxedHTTPSValidation().proxy(proxy);
	}

	public HTTPReqGen generate_request(String template, String recordId) throws Exception {
		RecordHandler record = this.myInputData.get_record(recordId);
		this.id = recordId;
		return generate_request(template, (HashMap<String, String>) record.get_map());
	}

	public HTTPReqGen generate_request(String template, HashMap<String, String> record) throws Exception {
		this.template = template;
		String filled_template = "";
		Boolean found_replacement = true;
		headers.clear();

		try {

			String[] tokens = tokenize_template(template);

			while (found_replacement) {
				found_replacement = false;
				filled_template = "";

				for (String item : tokens) {

					if (item.startsWith("<<") && item.endsWith(">>")) {
						found_replacement = true;
						item = item.substring(2, item.length() - 2);

						if (!record.containsKey(item)) {
							logger.info(
									"Template contained replacement string whose value did not exist in input record:["
											+ item + "]");
						}

						item = record.get(item);
					}

					filled_template += item;
				}

				tokens = tokenize_template(filled_template);
			}

		} catch (Exception e) {
			logger.error("Problem performing replacements from template: ", e);
		}

		try {

			InputStream stream = IOUtils.toInputStream(filled_template, "UTF-8");
			BufferedReader in = new BufferedReader(new InputStreamReader(stream));
			String line = "";
			String[] line_tokens;

			line = in.readLine();
			line_tokens = line.split(" ");
			call_type = Enum.valueOf(HttpType.class, line_tokens[0]);
			call_suffix = line_tokens[1];

			line = in.readLine();
			line_tokens = line.split(" ");
			call_host = line_tokens[1];

			call_string = call_host + call_suffix;

			line = in.readLine();
			while (line != null && !line.equals("")) {

				String lineP1 = line.substring(0, line.indexOf(":")).trim();
				String lineP2 = line.substring(line.indexOf(" "), line.length()).trim();

				headers.put(lineP1, lineP2);

				line = in.readLine();
			}

			if (line != null && line.equals("")) {
				body = "";
				while ((line = in.readLine()) != null && !line.equals("")) {
					body += line;
				}
			}

		} catch (Exception e) {
			logger.error("Problem setting request values from template: ", e);
		}

		return this;
	}

	public Response perform_request() throws Exception {

		Response response = null;

		try {

			for (Map.Entry<String, String> entry : cookie_list.entrySet()) {
				reqSpec.cookie(entry.getKey(), entry.getValue());
			}
			analysisHeader();
			analysisBody();

			switch (call_type) {

			case GET: {
				response = reqSpec.get(call_string);
				break;
			}
			case POST: {
				response = reqSpec.body(body).post(call_string);
				break;
			}
			case PUT: {
				response = reqSpec.body(body).put(call_string);
				break;
			}
			case DELETE: {
				response = reqSpec.delete(call_string);
				break;
			}

			default: {
				logger.error("Unknown call type: [" + call_type + "]");
			}
			}

		} catch (Exception e) {
			logger.error("Problem performing request: ", e);
		}
		if (response.statusCode() == 200) {
			responseMap.put(this.id, JSON.parseObject(response.asString()));
		}
		return response;
	}

	private void analysisHeader() {
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			String header = null;
			if (entry.getValue().startsWith("@@")) {
				String key = entry.getValue().substring(2, entry.getValue().length());
				if (responseMap.get(key) != null) {
					header = (String) responseMap.get(key).get(entry.getKey());
					headers.put(entry.getKey(), header);
				} else {
					HTTPReqGen myReqGen = new HTTPReqGen(myInputData);
					try {
						myReqGen.generate_request(template, key);
						Response currentResponse = myReqGen.perform_request();
						if (currentResponse.statusCode() == 200) {
							header = getResponseMap(key, entry.getKey(), currentResponse);
							headers.put(entry.getKey(), header);
						}
					} catch (Exception e) {
						Assert.fail("Problem using HTTPRequestGenerator to generate response: " + e.getMessage());
					}
				}

			} else {
				header = entry.getValue();
			}
			reqSpec.header(entry.getKey(), header);
		}
	}

	private void analysisBody() {
		Map<String, Object> jsonBody = JSON.parseObject(body);
		for (Entry<String, Object> jsonMap : jsonBody.entrySet()) {
			String jsonId = ((String) jsonMap.getValue());
			if (jsonId.startsWith("@@")) {
				jsonId = jsonId.substring(2, jsonId.length());
				if (responseMap.get(jsonId) != null) {
					jsonBody.put(jsonMap.getKey(), responseMap.get(jsonId).get(jsonMap.getKey()));
				} else {
					HTTPReqGen myReqGen = new HTTPReqGen(myInputData);
					try {
						myReqGen.generate_request(template, jsonId);
						Response currentResponse = myReqGen.perform_request();
						if (currentResponse.statusCode() == 200) {
							String result = getResponseMap(jsonId, jsonMap.getKey(), currentResponse);
							jsonBody.put(jsonMap.getKey(), result);
						}
					} catch (Exception e) {
						Assert.fail("Problem using HTTPRequestGenerator to generate response: " + e.getMessage());
					}
				}
			}else if(jsonId.startsWith("##")){
				
			}
		}
		body = JSON.toJSONString(jsonBody);
	}

	private String getResponseMap(String jsonId, String bodyName, Response response) {
		Map<String, Object> jsonBody = JSON.parseObject(response.asString());
		responseMap.put(jsonId, jsonBody);
		return (String) jsonBody.get(bodyName);
	}

	private String[] tokenize_template(String template) {
		return template.split("(?=[<]{2})|(?<=[>]{2})");
	}

}