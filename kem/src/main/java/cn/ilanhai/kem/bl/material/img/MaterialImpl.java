package cn.ilanhai.kem.bl.material.img;

import java.util.Date;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.ilanhai.framework.InterfaceUtil.InterfaceDocAnnotation;
import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.bl.customer.Customer;
import cn.ilanhai.kem.bl.customer.impl.CustomerImpl;
import cn.ilanhai.kem.bl.material.Material;
import cn.ilanhai.kem.bl.material.MaterialManager;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.material.MaterialDao;
import cn.ilanhai.kem.domain.CountDto;
import cn.ilanhai.kem.domain.enums.EnableState;
import cn.ilanhai.kem.domain.enums.MaterialInfoType;
import cn.ilanhai.kem.domain.material.MaterialEntity;
import cn.ilanhai.kem.domain.material.MaterialImgEntity;
import cn.ilanhai.kem.domain.material.MaterialInfoEntity;
import cn.ilanhai.kem.domain.material.MaterialMainEntity;
import cn.ilanhai.kem.domain.material.SearchAllMaterialResponse;
import cn.ilanhai.kem.domain.material.SearchMaterailRequest;
import cn.ilanhai.kem.domain.material.SearchMaterialEntity;
import cn.ilanhai.kem.domain.material.classification.ClassificationRequest;
import cn.ilanhai.kem.domain.material.dto.DeleteMeterialDto;
import cn.ilanhai.kem.domain.material.dto.DownloadMaterialDto;
import cn.ilanhai.kem.domain.material.dto.GetMaterialDto;
import cn.ilanhai.kem.domain.material.dto.GetMaterialResponseDto;
import cn.ilanhai.kem.domain.material.dto.MaterialDto;
import cn.ilanhai.kem.domain.material.dto.SaveMeterialDto;
import cn.ilanhai.kem.domain.material.dto.SearchMateralResponseDto;
import cn.ilanhai.kem.domain.material.dto.SearchMaterialDto;
import cn.ilanhai.kem.domain.material.dto.ShelfMaterialDto;
import cn.ilanhai.kem.domain.material.dto.UserSVGUploadDto;
import cn.ilanhai.kem.domain.material.keyword.ModifyKeyWordRequest;
import cn.ilanhai.kem.domain.material.keyword.SetKeyWordRequest;
import cn.ilanhai.kem.domain.material.remrek.RemrekRequest;
import cn.ilanhai.kem.keyfac.KeyFactory;

@Component("material")
public class MaterialImpl extends BaseBl implements Material {
	private Logger logger = Logger.getLogger(MaterialImpl.class);

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	public Entity addMaterialType(RequestContext context) throws BlAppException, DaoAppException {

		CodeTable ct;
		try {
			this.checkBackUserLogined(context);
			// 获取请求数据
			MaterialDto materialDto = context.getDomain(MaterialDto.class);
			this.valiDomainIsNull(materialDto, "添加分类管理");
			valiParaItemIntegerNull(materialDto.getTerminalType(), "请选择素材终端类型", false);
			valiParaItemNumBetween(1, 2, materialDto.getTerminalType(), "素材终端类型错误", false);
			this.valiName(materialDto.getMaterialName(), "素材类型名称", 5);
			valiParaItemIntegerNull(materialDto.getMaterialState(), "请选择素材是否启用", false);
			valiParaItemNumBetween(0, 1, materialDto.getMaterialState(), "素材状态错误", false);
			MaterialEntity materialEntity = new MaterialEntity();

			SearchMaterialEntity searchMaterialEntity = new SearchMaterialEntity();
			searchMaterialEntity.setMaterialName(materialDto.getMaterialName());
			searchMaterialEntity.setTerminalType(materialDto.getTerminalType());
			searchMaterialEntity.setTrueSearch(true);
			Dao dao = this.daoProxyFactory.getDao(context, MaterialDao.class);

			this.valiDaoIsNull(dao, "分类管理查询");
			if (((CountDto) dao.query(searchMaterialEntity, false)).getCount() > 0) {
				ct = CodeTable.BL_ADDMATERIALTYPE_ERROR;
				throw new BlAppException(ct.getValue(), ct.getDesc());
			}

			materialEntity.setCreatetime(new Date());
			materialEntity.setMaterial_name(materialDto.getMaterialName());
			materialEntity.setMaterial_state(materialDto.getMaterialState());
			materialEntity.setTerminal_type(materialDto.getTerminalType());
			materialEntity.setUserId(this.getSessionUserId(context));
			materialEntity.setMaterial_id(KeyFactory.newKey(KeyFactory.KEY_MATERIAL));
			return MaterialManager.saveMaterialType(context, materialEntity);
		} catch (BlAppException e) {
			throw e;
		} catch (DaoAppException e) {
			ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	public void shelfMaterialType(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		try {
			this.checkBackUserLogined(context);
			// 获取请求数据
			ShelfMaterialDto shelfMaterialDto = context.getDomain(ShelfMaterialDto.class);
			this.valiDomainIsNull(shelfMaterialDto, "分类管理操作");
			valiParaItemIntegerNull(shelfMaterialDto.getMaterialState(), "请指定素材状态", false);
			valiParaItemNumBetween(0, 2, shelfMaterialDto.getMaterialState(), "素材状态错误", false);
			valiParaItemStrNullOrEmpty(shelfMaterialDto.getMaterialId(), "请选择分类素材", false);

			MaterialEntity materialEntity = MaterialManager.queryMaterialType(context,
					shelfMaterialDto.getMaterialId());
			this.valiDomainIsNull(materialEntity, "素材分类编号错误", false);
			materialEntity.setMaterial_state(shelfMaterialDto.getMaterialState());
			MaterialManager.saveMaterialType(context, materialEntity);
		} catch (BlAppException e) {
			throw e;
		} catch (DaoAppException e) {
			ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	public Entity searchMaterialType(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		try {
			// 获取请求数据
			SearchMaterialDto searchMaterialDto = context.getDomain(SearchMaterialDto.class);
			this.valiDomainIsNull(searchMaterialDto, "分类管理查询");
			valiParaItemIntegerNull(searchMaterialDto.getTerminalType(), "请选择素材终端类型", false);
			valiParaItemNumBetween(1, 2, searchMaterialDto.getTerminalType(), "素材类型终端类型错误", false);
			if (searchMaterialDto.getMaterialState() != null) {
				valiParaItemNumBetween(0, 2, searchMaterialDto.getMaterialState(), "素材状态错误", false);
			}
			this.checkLimit(searchMaterialDto.getStartCount(), searchMaterialDto.getPageSize());
			SearchMaterialEntity searchMaterialEntity = new SearchMaterialEntity();
			searchMaterialEntity.setMaterialName(searchMaterialDto.getMaterialName());
			searchMaterialEntity.setPageSize(searchMaterialDto.getPageSize());
			searchMaterialEntity.setStartCount(searchMaterialDto.getStartCount());
			searchMaterialEntity.setTerminalType(searchMaterialDto.getTerminalType());
			searchMaterialEntity.setMaterialType(searchMaterialDto.getMaterialType());
			searchMaterialEntity.setMaterialState(searchMaterialDto.getMaterialState());
			Dao dao = this.daoProxyFactory.getDao(context, MaterialDao.class);
			this.valiDaoIsNull(dao, "分类管理查询");
			SearchMateralResponseDto searchMateralResponseDto = new SearchMateralResponseDto();
			searchMateralResponseDto.setList(dao.query(searchMaterialEntity));
			searchMateralResponseDto.setStartCount(searchMaterialDto.getStartCount());
			searchMateralResponseDto.setPageSize(searchMaterialDto.getPageSize());
			searchMateralResponseDto.setTotalCount(((CountDto) dao.query(searchMaterialEntity, false)).getCount());
			return searchMateralResponseDto;
		} catch (BlAppException e) {
			throw e;
		} catch (DaoAppException e) {
			ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.1.1.0")
	public Entity search(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		Dao dao = null;
		try {
			SearchMaterailRequest request = context.getDomain(SearchMaterailRequest.class);
			BLContextUtil.valiDomainIsNull(request, "请求参数不能为空");
			BLContextUtil.valiParaItemIntegerNull(request.getStartCount(), "开始数量不能为空");
			BLContextUtil.valiParaItemIntegerNull(request.getPageSize(), "查询数量不能为空");
			request.setServiceName(this.getValue(context, "serviceName"));
			dao = this.daoProxyFactory.getDao(context, MaterialDao.class);
			SearchAllMaterialResponse response = new SearchAllMaterialResponse();
			response.setList(transformation(dao.query(request)));
			response.setPageSize(request.getPageSize());
			response.setStartCount(request.getStartCount());
			response.setTotalCount(request.getCount());
			return response;
		} catch (BlAppException e) {
			throw e;
		} catch (DaoAppException e) {
			ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.1.1.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity uploadsvg(RequestContext context) throws BlAppException, DaoAppException {
		logger.info("进入SVG\\upload方法");
		CodeTable ct;
		SaveMeterialDto response = new SaveMeterialDto();
		String svgId = null;
		String materialTypeId = null;
		String manuscriptId = null;
		try {
			BLContextUtil.checkBackUserLogined(context);
			// 获取入参
			UserSVGUploadDto request = context.getDomain(UserSVGUploadDto.class);
			valiPara(request);
			logger.info("获取入参:" + request.toString());
			this.valiParaNotNull(request.getType(), "SVG类型错误", false);
			this.valiParaItemNumBetween(0, 1, request.getType(), "SVG类型错误", false);
			String materialId = request.getMaterialId();
			manuscriptId = request.getManuscriptId();
			materialTypeId = request.getMaterialTypeId();
			// 如果带有 素材id 则添加 源文件或模版 根据入参类型来保存svg
			if (!Str.isNullOrEmpty(materialId)) {
				logger.info("添加素材[" + materialId + "]的源文件或模版");
				MaterialInfoType materialInfoType = MaterialInfoType.getEnum(request.getType());
				for (int i = 0; i < request.getImgName().size(); i++) {
					// 封装数据
					logger.info("SVG路径:" + request.getImgPath().get(i));
					MaterialManager.saveMaterialInfo(context, materialInfoType, request.getImgPath().get(i),
							materialId);
				}
				response.setId(materialId);
				return response;
			}

			// 如果不带元素id则走普通元素上传流程
			for (int i = 0; i < request.getImgName().size(); i++) {
				svgId = MaterialManager.createMaterialMain(context);
				logger.info("添加SVG素材[" + svgId + "]");
				// 封装数据
				logger.info("SVG名称:" + request.getImgName().get(i));
				MaterialManager.saveMaterialInfo(context, MaterialInfoType.materialname, request.getImgName().get(i),
						svgId);
				logger.info("SVG路径:" + request.getImgPath().get(i));
				MaterialManager.saveMaterialInfo(context, MaterialInfoType.materialurl, request.getImgPath().get(i),
						svgId);
				if (!Str.isNullOrEmpty(materialTypeId)) {
					logger.info("添加SVG素材到素材类型[" + materialTypeId + "]");
					MaterialImgEntity materialImgEntity = new MaterialImgEntity();
					materialImgEntity.setImg_id(svgId);
					materialImgEntity.setMaterial_id(materialTypeId);
					MaterialManager.saveMaterialImg(context, materialImgEntity);
				}

				if (!Str.isNullOrEmpty(manuscriptId)) {
					logger.info("添加SVG素材到模板[" + manuscriptId + "]");
					MaterialManager.saveMaterialInfo(context, MaterialInfoType.manuscriptid, manuscriptId, svgId);
				}
				if (request.getType() != null) {
					MaterialInfoType materialInfoType = MaterialInfoType.getEnum(request.getType());
					// 封装数据
					logger.info("SVG路径:" + request.getImgPath().get(i));
					MaterialManager.saveMaterialInfo(context, materialInfoType, request.getImgPath().get(i), svgId);
				}
				response.setId(svgId);

			}
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
		return response;
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.1.1.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public void savesvg(RequestContext context) throws BlAppException, DaoAppException {
		logger.info("进入SVG\\savesvg方法");
		CodeTable ct;
		String svgId = null;
		boolean state = true;
		// 用于判断是否 改变启用状态
		int size = 0;
		try {
			BLContextUtil.checkBackUserLogined(context);
			svgId = context.getDomain(String.class);
			BLContextUtil.valiParaItemStrNullOrEmpty(svgId, CodeTable.BL_COMMON_MATERIAL_ERROR.getDesc(), false);
			logger.info("获取素材SVG:" + svgId + "的源SVG 和 模版");
			MaterialInfoEntity materialresouse = MaterialManager.getMaterialInfoById(context,
					MaterialInfoType.materialresouse, svgId);
			logger.info("源:" + materialresouse);
			MaterialInfoEntity materialtemplate = MaterialManager.getMaterialInfoById(context,
					MaterialInfoType.materialtemplate, svgId);
			logger.info("模版:" + materialtemplate);
			// 如果 源数据不为空 则状态为待修改
			if (materialresouse != null) {
				if (!Str.isNullOrEmpty(materialresouse.getInfoValue())) {
					state = false;
					size++;
				}
			}
			// 如果 模版数据不为空 则状态为正常
			if (materialtemplate != null) {
				if (!Str.isNullOrEmpty(materialtemplate.getInfoValue())) {
					state = true;
					size++;
				}
			}
			MaterialMainEntity mainEntity = MaterialManager.getMaterialMain(context, svgId);
			if (mainEntity == null) {
				ct = CodeTable.BL_COMMON_MATERIAL_ERROR;
				throw new BlAppException(ct.getValue(), "您要保存的素材不存在");
			}
			// 保存模版状态
			logger.info("SVG状态修改:" + state);
			MaterialManager.saveMaterialInfo(context, MaterialInfoType.materialstate,
					state ? EnableState.enable.getValue() + "" : EnableState.unenable.getValue() + "", svgId);
			// 新增保存
			if (EnableState.unenable.getValue().equals(mainEntity.getEnable())) {
				if (size == 2) {
					// 包含了源和模版 则改为启用状态
					MaterialManager.saveMaterialMain_state(context, EnableState.enable, svgId);
					// 保存模板和源文件 清空临时存储的模板和源文件
					buildInfo(context, svgId, materialresouse, materialtemplate);
				} else {
					ct = CodeTable.BL_COMMON_MATERIAL_ERROR;
					throw new BlAppException(ct.getValue(), ct.getDesc());
				}
			} else {// 编辑
				if (size == 2) {
					// 保存模板和源文件 清空临时存储的模板和源文件
					buildInfo(context, svgId, materialresouse, materialtemplate);
				}
			}
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	/**
	 * 保存模板和源文件 清空临时存储的模板和源文件
	 * 
	 * @param context
	 * @param svgId
	 * @param materialresouse
	 * @param materialtemplate
	 * @throws BlAppException
	 */
	private void buildInfo(RequestContext context, String svgId, MaterialInfoEntity materialresouse,
			MaterialInfoEntity materialtemplate) throws BlAppException {
		// 如果已保存 模版 则将模版保存在 SVG 素材中
		MaterialManager.saveMaterialInfo(context, MaterialInfoType.materialtemplate, "", svgId);
		// 清空 源 和 模版
		MaterialManager.saveMaterialInfo(context, MaterialInfoType.materialtemplateurl, materialtemplate.getInfoValue(),
				svgId);

		// 如果已保存 模版 则将模版保存在 SVG 素材中
		MaterialManager.saveMaterialInfo(context, MaterialInfoType.materialurl, materialresouse.getInfoValue(), svgId);
		// 清空 源 和 模版
		MaterialManager.saveMaterialInfo(context, MaterialInfoType.materialresouse, "", svgId);
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.1.1.0")
	public boolean setmaterialskeyword(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		try {
			SetKeyWordRequest request = context.getDomain(SetKeyWordRequest.class);
			BLContextUtil.valiParaItemStrNullOrEmpty(request.getKeywords(), "关键字不能为空");
			if (request.getMaterialIds().length <= 0) {
				throw new BlAppException(-100, "素材id不能为空");
			}
			for (int i = 0; i < request.getMaterialIds().length; i++) {
				MaterialManager.saveMaterialInfo_keyword(context, request.getKeywords(), request.getMaterialIds()[i]);
			}
			return true;
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.1.1.0")
	public boolean setkeyword(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		String infoKey = null;
		try {
			ModifyKeyWordRequest request = context.getDomain(ModifyKeyWordRequest.class);
			BLContextUtil.valiParaItemStrNullOrEmpty(request.getMaterialId(), "素材id不能为空");
			if (request.getKeywords() == null) {
				infoKey = MaterialManager.removalDuplicateKey("");
			} else {
				infoKey = MaterialManager.removalDuplicateKey(request.getKeywords());
			}

			MaterialManager.saveMaterialInfo(context, MaterialInfoType.materialkeyword, infoKey,
					request.getMaterialId());
			return true;
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.1.1.0")
	public boolean setmaterialtype(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		Dao dao = null;
		try {
			ClassificationRequest request = context.getDomain(ClassificationRequest.class);
			logger.info("参数：" + request);
			dao = this.daoProxyFactory.getDao(context, MaterialDao.class);
			if (request.getCategoryIds().length <= 0) {
				throw new BlAppException(-1, "请选择分类");
			}
			if (request.getMaterialIds().length > 0) {
				dao.save(request);
			} else {
				throw new BlAppException(-1, "素材id不能为空");
			}
			return true;
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), e.getMessage());
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.1.1.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public void deletematerial(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		try {
			this.checkBackUserLogined(context);
			DeleteMeterialDto request = context.getDomain(DeleteMeterialDto.class);
			int val = MaterialManager.deleteMaterial(context, request);
			BLContextUtil.valiSaveDomain(val, "删除素材");
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.1.1.0")
	public boolean setremark(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		try {
			RemrekRequest request = context.getDomain(RemrekRequest.class);
			BLContextUtil.valiParaItemStrNullOrEmpty(request.getMaterialId(), "素材id不能为空");
			MaterialManager.saveMaterialInfo(context, MaterialInfoType.materialremark, request.getRemark(),
					request.getMaterialId());
			return true;
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.1.1.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity downloadmaterial(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		Entity entity = null;
		String materialId = null;
		try {
			logger.info("进入SVG\\downloadmaterial方法");
			DownloadMaterialDto request = context.getDomain(DownloadMaterialDto.class);
			BLContextUtil.valiPara(request);
			materialId = request.getMaterialId();
			BLContextUtil.valiParaItemStrNullOrEmpty(materialId, "素材id不能为空");
			String type = request.getType() + "";
			// 普通素材下载流程
			if (Str.isNullOrEmpty(type)) {
				GetMaterialDto searchMaterailRequest = new GetMaterialDto();
				searchMaterailRequest.setMaterialId(materialId);
				logger.info("素材id:" + materialId);
				Dao dao = BLContextUtil.getDao(context, MaterialDao.class);
				entity = dao.query(searchMaterailRequest, false);
				logger.info("素材实例:" + entity);
				return entity;
			} else {// SVG下载流程
				BLContextUtil.valiParaItemNumBetween(0, 1, request.getType(), "SVG类型错误", false);
				MaterialInfoType materialInfoType = MaterialInfoType.getEnum(request.getType());
				MaterialInfoEntity InfoEntity = null;
				GetMaterialResponseDto getMaterialResponseDto = new GetMaterialResponseDto();
				logger.info("素材类型:" + materialInfoType);
				switch (materialInfoType) {
				case materialresouse:// 源
					// 获取源文件
					InfoEntity = MaterialManager.getMaterialInfoById(context, materialInfoType, materialId);
					logger.info("materialresouse:" + InfoEntity);
					// 如果源文件路径为空 则取保存后的源文件
					if (InfoEntity == null || Str.isNullOrEmpty(InfoEntity.getInfoValue())) {
						InfoEntity = MaterialManager.getMaterialInfoById(context, MaterialInfoType.materialurl,
								materialId);
						logger.info("materialurl:" + InfoEntity);
						if (InfoEntity != null) {
							getMaterialResponseDto.setThumbnail(InfoEntity.getInfoValue());
							getMaterialResponseDto.setMaterialType("2");
						}
					} else {
						getMaterialResponseDto.setThumbnail(InfoEntity.getInfoValue());
						getMaterialResponseDto.setMaterialType("2");
					}
					logger.info("素材:" + getMaterialResponseDto);
					return getMaterialResponseDto;
				case materialtemplate:// 模版
					// 获取模版文件
					InfoEntity = MaterialManager.getMaterialInfoById(context, materialInfoType, materialId);
					logger.info("materialtemplate:" + InfoEntity);
					// 如果模版文件路径为空 则取保存后的模版文件
					if (InfoEntity == null || Str.isNullOrEmpty(InfoEntity.getInfoValue())) {
						InfoEntity = MaterialManager.getMaterialInfoById(context, MaterialInfoType.materialtemplateurl,
								materialId);
						logger.info("materialtemplateurl:" + InfoEntity);
						if (InfoEntity != null) {
							getMaterialResponseDto.setThumbnail(InfoEntity.getInfoValue());
							getMaterialResponseDto.setMaterialType("2");
						}
					} else {
						getMaterialResponseDto.setThumbnail(InfoEntity.getInfoValue());
						getMaterialResponseDto.setMaterialType("2");
					}
					logger.info("素材:" + getMaterialResponseDto);
					return getMaterialResponseDto;
				default:
					return getMaterialResponseDto;
				}
			}
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}
}
