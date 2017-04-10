package cn.ilanhai.kem.dao.contacts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.CountDto;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.contacts.ContactsInfoEntity;
import cn.ilanhai.kem.domain.contacts.ContactsMainEntity;
import cn.ilanhai.kem.domain.contacts.SearchContactsById;
import cn.ilanhai.kem.domain.contacts.SynchronizeContractsRequest;
import cn.ilanhai.kem.domain.contacts.dto.DeleteContactsDto;
import cn.ilanhai.kem.domain.contacts.dto.QueryContactGroupsDto;
import cn.ilanhai.kem.domain.contacts.dto.QueryContactsDto;
import cn.ilanhai.kem.domain.contacts.group.AddGroupContactsRequest;
import cn.ilanhai.kem.domain.contacts.group.ContactsGroupEntity;
import cn.ilanhai.kem.domain.contacts.group.ContactsInfo;
import cn.ilanhai.kem.domain.contacts.group.DeleteGroupRequest;
import cn.ilanhai.kem.domain.contacts.group.QueryContactsGroupIsExits;
import cn.ilanhai.kem.domain.contacts.group.QueryGroupContactsByContact;
import cn.ilanhai.kem.domain.contacts.group.SearchContactGroupRequest;
import cn.ilanhai.kem.domain.contacts.group.SearchGroupContactsRequest;
import cn.ilanhai.kem.mail.protocol.MailInfo;
import cn.ilanhai.kem.mail.protocol.sohu.address.DeleteMemberRequest;
import cn.ilanhai.kem.queue.QueueManager;
import cn.ilanhai.kem.queue.msg.MailMsg;
import cn.ilanhai.kem.domain.contacts.dto.QueryGroupContactsDto;
import cn.ilanhai.kem.domain.contacts.dto.QueryGroupPhoneNumberDto;
import cn.ilanhai.kem.domain.contacts.dto.QueryGroupPhoneNumberResponseDto;

/**
 * 联系人dao
 * 
 * @author hy
 *
 */
@Component("contactDao")
public class ContactsDao extends MybatisBaseDao {
	private SqlSession sqlSession = this.getSqlSession();

	public ContactsDao() throws DaoAppException {
		super();
	}

	@Override
	public int save(Entity entity) throws DaoAppException {
		if (entity instanceof ContactsMainEntity) {
			return saveContactMainEntity((ContactsMainEntity) entity);
		}

		if (entity instanceof ContactsInfoEntity) {
			return saveContactInfoEntity((ContactsInfoEntity) entity);
		}

		if (entity instanceof ContactsGroupEntity) {
			return saveContactGroup((ContactsGroupEntity) entity);
		}

		if (entity instanceof AddGroupContactsRequest) {
			return saveGroupContacts((AddGroupContactsRequest) entity);
		}

		if (entity instanceof DeleteGroupRequest) {
			return deleteGroup((DeleteGroupRequest) entity);
		}
		return super.save(entity);
	}

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity instanceof QueryContactsDto) {
			return queryContacts((QueryContactsDto) entity).iterator();
		}
		if (entity instanceof QueryGroupContactsDto) {
			return queryGroupContacts((QueryGroupContactsDto) entity).iterator();
		}
		if (entity instanceof SearchContactGroupRequest) {
			return queryGroupContacts((SearchContactGroupRequest) entity).iterator();
		}
		if (entity instanceof SearchGroupContactsRequest) {
			return queryGroupContacts((SearchGroupContactsRequest) entity).iterator();
		}
		if (entity instanceof QueryContactGroupsDto) {
			return queryContactsGroup((QueryContactGroupsDto) entity).iterator();
		} else if (entity instanceof QueryGroupPhoneNumberDto) {
			return queryGroupContacts((QueryGroupPhoneNumberDto) entity).iterator();
		} else if (entity instanceof IdEntity) {
			return queryGroupByContacts((IdEntity<String>) entity).iterator();
		} else if (entity instanceof SynchronizeContractsRequest) {
			return querySynchronizeContacts((SynchronizeContractsRequest) entity).iterator(); 
		}
		return super.query(entity);
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity instanceof QueryContactsDto) {
			return queryContactsCount((QueryContactsDto) entity);
		} else if (entity instanceof QueryContactsGroupIsExits) {
			return queryContactsGroup((QueryContactsGroupIsExits) entity);
		} else if (entity instanceof ContactsGroupEntity) {
			return queryContactsGroup((ContactsGroupEntity) entity);
		} else if (entity instanceof SearchContactGroupRequest) {
			return queryContactsGroupCount((SearchContactGroupRequest) entity);
		} else if (entity instanceof SearchGroupContactsRequest) {
			return queryGroupContactsCount((SearchGroupContactsRequest) entity);
		} else if (entity instanceof SearchContactsById) {
			return queryGroupByContacts((SearchContactsById) entity);
		} else if (entity instanceof IdEntity) {
			return queryGroupById((IdEntity<String>) entity);
		} else if (entity instanceof SynchronizeContractsRequest) {
			return querySynchronizeContactsCount((SynchronizeContractsRequest) entity);
		}
		return super.query(entity, flag);
	}

	@Override
	public int delete(Entity entity) throws DaoAppException {
		if (entity instanceof DeleteContactsDto) {
			return deleteContacts((DeleteContactsDto) entity);
		} else if (entity instanceof QueryGroupContactsByContact) {
			return deleteGroupContacts((QueryGroupContactsByContact) entity);
		}
		return super.delete(entity);
	}

	private int saveContactMainEntity(ContactsMainEntity entity) {
		if (entity == null) {
			return -1;
		}
		return addContactMainEntity(entity);
	}

	private int saveContactInfoEntity(ContactsInfoEntity entity) throws DaoAppException {
		if (entity == null) {
			return -1;
		}

		if (!isExitsContactInfo(entity)) {
			return addContactInfoEntity(entity);
		}
		return updateContactInfoEntity(entity);
	}

	private int addContactMainEntity(ContactsMainEntity entity) {
		if (entity == null) {
			return -1;
		}
		return sqlSession.insert("contacts.addContactMain", entity);
	}

	private int addContactInfoEntity(ContactsInfoEntity entity) {
		if (entity == null) {
			return -1;
		}
		return sqlSession.insert("contacts.addContactInfo", entity);
	}

	private int updateContactMainEntity(ContactsMainEntity entity) {
		if (entity == null) {
			return -1;
		}
		return sqlSession.update("contacts.updateContactMain", entity);
	}

	/**
	 * 判断是否存在
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	private boolean isExitsContactInfo(ContactsInfoEntity entity) throws DaoAppException {
		if (entity == null) {
			return false;
		}
		if (!Str.isNullOrEmpty(entity.getInfoValue())) {
			ContactsInfoEntity contactsInfoEntity = sqlSession.selectOne("contacts.getContactInfo", entity);
			if (contactsInfoEntity == null || !entity.getInfoValue().equals(contactsInfoEntity.getInfoValue())) {
				Integer count = sqlSession.selectOne("contacts.getContactInfoValue", entity);
				if (count > 0) {
					throw new DaoAppException(-1, "重复联系人");
				}
			}
		}
		Integer count2 = sqlSession.selectOne("contacts.getContactInfoCount", entity);
		if (count2 != null) {
			return count2 > 0;
		}
		return false;
	}

	private int updateContactInfoEntity(ContactsInfoEntity entity) {
		if (entity == null) {
			return -1;
		}
		return sqlSession.update("contacts.updateContactInfo", entity);
	}

	private int deleteContacts(DeleteContactsDto dto) {
		if (dto == null) {
			return -1;
		}
		return sqlSession.update("contacts.deleteContacts", dto);
	}

	private List<Entity> queryContacts(QueryContactsDto dto) {
		if (dto == null) {
			return null;
		}
		return sqlSession.selectList("contacts.queryContacts", dto);
	}

	private List<Entity> queryGroupContacts(QueryGroupContactsDto dto) {
		if (dto == null) {
			return null;
		}
		return sqlSession.selectList("contacts.queryGroupContacts", dto);
	}

	private List<Entity> queryGroupContacts(QueryGroupPhoneNumberDto dto) {
		if (dto == null) {
			return null;
		}
		return sqlSession.selectList("contacts.queryGroupPhoneNumber", dto);
	}

	private List<Entity> queryContactsGroup(QueryContactGroupsDto dto) {
		if (dto == null) {
			return null;
		}
		return sqlSession.selectList("contacts.querycontactsgroupbyids", dto);
	}

	private Entity queryContactsCount(QueryContactsDto dto) {
		if (dto == null) {
			return new CountDto();
		}
		return sqlSession.selectOne("contacts.queryContactsCount", dto);
	}

	// 群组相关

	/**
	 * 通过name查询群组
	 * 
	 * @param entity
	 * @return
	 */
	private Entity queryContactsGroup(QueryContactsGroupIsExits entity) {
		if (entity == null) {
			return null;
		}
		return sqlSession.selectOne("contacts.querycontactsgroupbyname", entity);
	}

	/**
	 * 通过groupid查询群组
	 * 
	 * @param entity
	 * @return
	 */
	private Entity queryContactsGroup(ContactsGroupEntity entity) {
		if (entity == null) {
			return null;
		}
		return sqlSession.selectOne("contacts.querycontactsgroupbyid", entity);
	}

	/**
	 * 保存群组
	 * 
	 * @param entity
	 * @return
	 */
	private int saveContactGroup(ContactsGroupEntity entity) {
		if (entity == null) {
			return -1;
		}
		if (!isExites((ContactsGroupEntity) entity)) {
			return add((ContactsGroupEntity) entity);
		} else {
			return update((ContactsGroupEntity) entity);
		}
	}

	private int update(ContactsGroupEntity entity) {

		return sqlSession.update("contacts.updatecontactgroup", entity);
	}

	private int add(ContactsGroupEntity entity) {

		return sqlSession.insert("contacts.insertcontactgroup", entity);
	}

	private boolean isExites(ContactsGroupEntity entity) {
		ContactsGroupEntity contactsGroupEntity = (ContactsGroupEntity) this.queryContactsGroup(entity);
		if (contactsGroupEntity == null) {
			return false;
		}
		return true;
	}

	// 查询群组
	private List<Entity> queryGroupContacts(SearchContactGroupRequest entity) {
		if (entity == null) {
			return null;
		}
		logger.info("查询群组");
		return sqlSession.selectList("contacts.querycontactsgroups", entity);
	}

	// 查询群组总数
	private Entity queryContactsGroupCount(SearchContactGroupRequest entity) {
		if (entity == null) {
			return null;
		}
		logger.info("查询群组总数");
		Integer count = sqlSession.selectOne("contacts.querycontactsgroupscount", entity);
		IdEntity<Integer> counts = new IdEntity<Integer>();
		counts.setId(count);
		return counts;
	}

	// 保存群组联系人
	private int saveGroupContacts(AddGroupContactsRequest entity) {
		if (entity == null) {
			return -1;
		}
		this.deleteGroupInfo((AddGroupContactsRequest) entity);
		if (entity.getList().size() > 0) {
			return this.saveGroupInfo((AddGroupContactsRequest) entity);
		}
		return 1;
	}

	private int saveGroupInfo(AddGroupContactsRequest entity) {
		// TODO Auto-generated method stub
		return sqlSession.insert("contacts.insertgropupcontacts", entity);
	}

	private int deleteGroupInfo(AddGroupContactsRequest entity) {

		return sqlSession.delete("contacts.deletegropupcontacts", entity);
	}

	// 查询群组联系人
	private List<Entity> queryGroupContacts(SearchGroupContactsRequest entity) {
		if (entity == null) {
			return null;
		}
		return sqlSession.selectList("contacts.querygroupcontactss", entity);
	}

	// 联系人总数
	private Entity queryGroupContactsCount(SearchGroupContactsRequest entity) {
		if (entity == null) {
			return null;
		}
		logger.info("查询群组联系人总数");

		IdEntity<Integer> count = new IdEntity<Integer>();
		Integer number = sqlSession.selectOne("contacts.querygroupcontactscount", entity);
		logger.info("查询到数量为：" + number);
		count.setId(number);
		return count;
	}

	// 删除群组
	private int deleteGroup(DeleteGroupRequest entity) {
		if (entity == null) {
			return -1;
		}
		logger.info("删除群组");
		sqlSession.delete("contacts.deletegroup", entity);
		sqlSession.delete("contacts.deletegroupcontacts", entity);

		return 1;
	}

	// 通过联系人查询群组
	private List<Entity> queryGroupByContacts(IdEntity<String> entity) {
		if (entity == null) {
			return null;
		}
		return sqlSession.selectList("contacts.selectcontactgroupid", entity.getId());

	}

	// 删除群组中的联系人
	private int deleteGroupContacts(QueryGroupContactsByContact entity) {
		if (entity == null) {
			return -1;
		}
		return sqlSession.delete("contacts.deletegroupcontactsbycontact", entity);
	}

	// 查询联系人信息
	private Entity queryGroupByContacts(SearchContactsById entity) {
		if (entity == null) {
			return null;
		}
		logger.info("查询联系人信息，id：" + entity.getContactsId());
		return sqlSession.selectOne("contacts.searchcontactsinfo", entity);
	}
	
	//查询群组信息
	private Entity queryGroupById(IdEntity<String> entity) {
		if (entity == null) {
			return null;
		}
		logger.info("查询群组信息，id：" + entity.getId());
		return sqlSession.selectOne("contacts.searchgroupinfo", entity.getId());
	}
	
	//查询同步的联系人
	private List<Entity> querySynchronizeContacts(SynchronizeContractsRequest entity) {
		if (entity == null) {
			return null;
		}
		logger.info("查询用户，id：" + entity.getUserId() + "可以用不的联系人");
		return sqlSession.selectList("contacts.searchsysnchronizecontacts", entity);
	}
	
	//查询同步联系人时去重的数量
	private Entity querySynchronizeContactsCount(SynchronizeContractsRequest entity) {
		if (entity == null) {
			return null;
		}
		logger.info("查询用户，id：" + entity.getUserId() + "同步联系人时去重的数量");
		List<Entity> list = sqlSession.selectList("contacts.searchcommonnumber", entity);
		IdEntity<Integer> count = new IdEntity<Integer>();
		count.setId(list.size());
		logger.info("去重的数量为：" + count.getId());
		return count;
	}
	
}
