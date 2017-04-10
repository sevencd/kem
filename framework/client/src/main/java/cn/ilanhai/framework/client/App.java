package cn.ilanhai.framework.client;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.app.test.domain.UEntity;
import cn.ilanhai.framework.client.consumer.Consumer;
import cn.ilanhai.framework.common.exception.ContainerException;
import cn.ilanhai.framework.uitl.FastJson;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		Consumer consumer = null;
		consumer = new Consumer();
		URI location = null;
		UEntity ue = null;
		try {

			// location = new URI("http://apptest/user/add");
			// ue = new UEntity();
			// ue.setId(111);
			// ue.setName("add");
			// System.out.println(consumer.getAppService().post(location, ue));
			//
			//
			// location = new URI("http://apptest/user/update");
			// ue = new UEntity();
			// ue.setId(222);
			// ue.setName("update");
			// System.out.println(consumer.getAppService().post(location, ue));
			//
			location = new URI("http://apptest/user/delete");
			ue = new UEntity();
			ue.setId(333);
			ue.setName("delete");
			// System.out.println(consumer.getAppService().post(location, ue));
			// System.out.println(consumer.getAppService().postJSON(location,
			// "{\"id\":777,\"name\":\"he\"}"));

			location = new URI("http://apptest/user/getUser");
			// ue = new UEntity();
			// ue.setId(444);
			// ue.setName("getUser");
			 System.out.println(consumer.getAppService()
			 .get(location, ue, false));
			System.out.println(consumer.getAppService().getJSON(location,
					"{\"id\":888,\"name\":\"he\"}"));

			location = new URI("http://apptest/user/getUsers");
			System.out.println(consumer.getAppService().getJSON(location,
					"{\"id\":999,\"name\":\"he\"}"));

			location = new URI("http://apptest/user/getExc");
			System.out.println(consumer.getAppService().getJSON(location,
					"{\"id\":999,\"name\":\"he\"}"));
			// ue = new UEntity();
			// ue.setId(555);
			// ue.setName("getUsers");
			// consumer.getAppService().get(location, ue);

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}
}
