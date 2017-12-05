package com.codeReading.busi.service.user.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeReading.busi.dal.iface.user.IUserContactDao;
import com.codeReading.busi.dal.model.User;
import com.codeReading.busi.dal.model.UserContact;
import com.codeReading.busi.dpn.enums.UserContactType;
import com.codeReading.busi.dpn.exception.user.UserPermissionDeniedException;
import com.codeReading.busi.po.UserContactPO;
import com.codeReading.busi.service.user.IUserContactService;
import com.codeReading.busi.service.user.IUserService;
import com.codeReading.core.framework.web.BaseService;
import com.codeReading.core.framework.web.InnerResultData;
import com.codeReading.core.framework.web.ResultData;
import com.codeReading.core.util.DateUtil;
import com.codeReading.core.util.SeqUtil;

@Service
public class UserContactService  extends BaseService implements IUserContactService {
	private Logger log = LoggerFactory.getLogger(UserContactService.class);
	
	@Autowired
	private IUserContactDao contactDao;
	@Autowired
	private IUserService userService;
	
	@Override
	public ResultData get(String userid) throws Exception {
		log.debug("#[服务] 开始获取用户联系方式 userid=[{}]", userid);
		ResultData result = ResultData.init();
		
		//组织数据
		result.setData("contact",getFieldValueMap(getByUserid(userid).getData()));
		
		//返回结果
		log.info("#[服务] 完成获取用户联系方式 result=[{}]", result);
		return result;
	}
	
	@Override
	public ResultData save(UserContactPO contactpo, String userid) throws Exception {
		log.debug("#[服务] 开始保存用户联系方式 userid=[{}]", userid);
		ResultData result = ResultData.init();
		
		//组织数据
		UserContact uct = new UserContact();
		uct.setUserid(userid);
		uct.setContact(contactpo.getContact());
		UserContact usercontact = contactDao.getByUserid(userid);
		if(null != usercontact){ //更新联系方式
			uct.setUcid(usercontact.getUcid());
			uct.setModtime(DateUtil.currentTimestamp());
			contactDao.update(uct);
		}else{ //添加联系方式
			uct.setUcid(SeqUtil.produceUcid()); //创建一个新的用户联系方式编号
			uct.setType(contactpo.getType());
			contactDao.insert(uct);
		}
		
		//返回结果
		result.setData(getFieldValueMap(uct));
		log.info("#[服务] 完成保存专家联系方式 result=[{}]", result);
		return result;
	}
	
	@Override
	public ResultData delete(String contactid, String currentUserid) throws Exception {
		log.debug("#[服务] 开始删除专家联系方式 expert=[{}]", currentUserid);
		ResultData result = ResultData.init();
		
		//删除数据
		UserContact contact = contactDao.get(contactid);
		if(null != contact){
			if(contact.getUserid().equals(currentUserid)){
				contactDao.delete(contactid);
				log.info("#[服务] 完成删除专家联系方式 result=[{}]", result);
			}else{ //非本人操作
				throw new UserPermissionDeniedException("非本人操作");
			}
		}else{
			log.info("#[服务] 该联系方式不存在 result=[{}]", result);
		}
		
		//返回结果
		return result;
	}

	@Override
	public InnerResultData<UserContact> getByUserid(String userid) throws Exception {
		InnerResultData<UserContact> result = new InnerResultData<UserContact>();
		
		//组织数据
		UserContact contact = new UserContact();
		contact = contactDao.getByUserid(userid);
		if(null != contact){
			if(contact.getUserid().equals(userid)){
				result.setData(contact);
			}else{
				throw new UserPermissionDeniedException("非本人操作");
			}
		}else{ //如果没有联系方式，则默认使用用户注册时的手机号
			User user = userService.getUser(userid).getData();
			if(null != user){
				UserContact contactTemp = new UserContact();
				if(null!=user.getPhone() && !"".equals(user.getPhone())){
					contactTemp.setContact(user.getPhone());
					contactTemp.setType(UserContactType.PHONE);
				}else if(null!=user.getEmail() && !"".equals(user.getEmail())){
					contactTemp.setContact(user.getEmail());
					contactTemp.setType(UserContactType.EMAIL);
				}
				result.setData(contactTemp);
			}
		}
		
		//返回结果
		return result;
	}
}
