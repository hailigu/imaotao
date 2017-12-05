package com.codeReading.busi.dpn.search.repository;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.codeReading.busi.dal.model.Badge;
import com.codeReading.busi.dal.model.User;
import com.codeReading.busi.dal.model.UserService;
import com.codeReading.core.search.IndexRepository;
import com.codeReading.core.search.UpdateRepository;

/**
 * 用户搜索实例对象
 * @author Rofly
 */
public class UserRepository implements IndexRepository, UpdateRepository, Serializable {
	private Logger log = LoggerFactory.getLogger(UserRepository.class);
	
	private static final long serialVersionUID = 1L;
	
	// ~ properties ~
	
	//user
	private String userid;
	private String nickname;
	private String phone;
	private String email;
	private String state;
	private String avatar;
	
	//user service
	private Boolean isrealname;
	private Integer logincount;
	private Integer onlinetimecount;
	private Long logintime;
	
	private Long modtime; //使用user service 的数据
	private Long intime;
	
	//徽章
	private Collection<Map<String, Object>> badges;
	
	// ~ constructs ~
	
	public UserRepository(User user, UserService us, Collection<Badge> _badges) {
		Assert.notNull(user, "用户对象不能为null");
		Assert.notNull(user.getUserid(), "用户编号不能为null");
		
		this.userid = user.getUserid();
		this.email = user.getEmail();
		this.phone = user.getPhone();
		this.nickname = user.getNickname();
		this.avatar = user.getAvatar();
		this.state = user.getState();
		
		if(null != us){
			this.isrealname = us.getIsrealname();
			this.logincount = us.getLogincount();
			this.onlinetimecount = us.getOnlinetimecount();
			if(null != us.getModtime())this.logintime = us.getLogintime().getTime();
			if(null != us.getModtime())this.modtime = us.getModtime().getTime();
			if(null != us.getIntime())this.intime = us.getIntime().getTime();
		}
		
		if(null != _badges){
			badges = new ArrayList<Map<String, Object>>();
			for(Badge badge : _badges){
				Map<String, Object> item = new HashMap<String, Object>();
				item.put("badgeid", badge.getBadgeid());
				item.put("badgename", badge.getBadgename());
				item.put("description", badge.getDescription());
				item.put("icon", badge.getIcon());
				badges.add(item);
			}
		}
	}
	
	// ~ methods ~
	@Override
	public boolean canIndex(){
		if(
			StringUtils.isEmpty(userid)
			|| (StringUtils.isEmpty(email) && StringUtils.isEmpty(phone))
			|| StringUtils.isEmpty(nickname)
			) {
			return false;
		}else{
			return true;
		}
	}
	@Override
	public boolean canUpdate(){
		return !StringUtils.isEmpty(userid);
	}
	
	
	@Override
	public XContentBuilder toSourceBuilder(){
		try {
			XContentBuilder builder =  XContentFactory.jsonBuilder().prettyPrint();
			
			builder.startObject();
			
			if(null != userid)builder.field("userid", userid);
			if(null != nickname)builder.field("nickname", nickname);
			if(null != phone)builder.field("phone", phone);
			if(null != email)builder.field("email", email);
			if(null != state)builder.field("state", state);
			if(null != avatar)builder.field("avatar", avatar);
			
			if(null != isrealname)builder.field("isrealname", isrealname);
			if(null != logincount)builder.field("logincount", logincount);
			if(null != onlinetimecount)builder.field("onlinetimecount", onlinetimecount);
			if(null != logintime)builder.field("logintime", logintime);
			if(null != modtime)builder.field("modtime", modtime);
			if(null != intime)builder.field("intime", intime);
			
			if(null != badges){
				builder.startArray("badges");
				for (Map<String, Object> t : badges) {
					builder.startObject();
					builder.field("badgeid", t.get("badgeid"));
					builder.field("badgename", t.get("badgename"));
					builder.field("description", t.get("description"));
					builder.field("icon", t.get("icon"));
					builder.endObject();
				}
				builder.endArray();
				
			}
			
			builder.endObject();
			if(log.isTraceEnabled())log.debug("User repository parse to json: {}", builder.string());
			return builder;
		} catch (IOException e) {
			return null;
		}
	}

	// ~ getter and setter ~
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public Boolean getIsrealname() {
		return isrealname;
	}
	public void setIsrealname(Boolean isrealname) {
		this.isrealname = isrealname;
	}
	public Integer getLogincount() {
		return logincount;
	}
	public void setLogincount(Integer logincount) {
		this.logincount = logincount;
	}
	public Integer getOnlinetimecount() {
		return onlinetimecount;
	}
	public void setOnlinetimecount(Integer onlinetimecount) {
		this.onlinetimecount = onlinetimecount;
	}
	public Long getLogintime() {
		return logintime;
	}
	public void setLogintime(Long logintime) {
		this.logintime = logintime;
	}
	public Long getModtime() {
		return modtime;
	}
	public void setModtime(Long modtime) {
		this.modtime = modtime;
	}
	public Long getIntime() {
		return intime;
	}
	public void setIntime(Long intime) {
		this.intime = intime;
	}
}
