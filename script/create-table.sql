-------------------------------用户体系----------------------------------------------------
/*==============================================================*/
/* Table: t_user  用户核心信息表                                */
/*==============================================================*/
drop table if exists scr.t_user;
create table scr.t_user (
   userid               char(16)                                        not null,
   phone                varchar(16)     default ''                      not null,
   email                varchar(64)     default ''                      not null,
   password             varchar(256)    default ''                      not null,
   nickname             varchar(32)     default ''                      not null, 
   state                char(1)         default '1'                     not null,
   avatar               text            default ''                      not null,
   modtime              timestamp       default current_timestamp       not null,
   intime               timestamp       default current_timestamp       not null,
   primary key (userid)
);
comment on table scr.t_user is '用户核心信息表';
comment on column scr.t_user.userid is '用户号';
comment on column scr.t_user.phone is '常用电话';
comment on column scr.t_user.email is '邮箱';
comment on column scr.t_user.password is '登录密码';
comment on column scr.t_user.nickname is '用户昵称';
comment on column scr.t_user.state is '用户状态：1.未激活 2.正常 4.注销 5.用户冻结 6.系统锁定';
comment on column scr.t_user.avatar is '头像';
comment on column scr.t_user.modtime is '修改时间';
comment on column scr.t_user.intime is '入库时间';

/*==============================================================*/
/* Table: t_user_extend    用户扩展信息表                       */
/*==============================================================*/
drop table if exists scr.t_user_extend;
create table scr.t_user_extend (
   userid               char(16)                                     not null,
   birthday             varchar(10)       default '1900-1-1'         not null,
   introduction         varchar(256)      default ''                 not null,
   gender               char(1)           default '0'                not null,
   job                  varchar(64)       default ''                 not null,
   home                 varchar(128)      default ''                 not null,
   address              varchar(128)      default ''                 not null,
   education            char(1)           default '0'                not null,
   modtime              timestamp         default current_timestamp  not null,
   intime               timestamp         default current_timestamp  not null,
   primary key (userid),
   foreign key(userid) references scr.t_user(userid)
);
comment on table scr.t_user_extend is '用户扩展信息表';
comment on column scr.t_user_extend.userid is '用户号';
comment on column scr.t_user_extend.birthday is '生日';
comment on column scr.t_user_extend.introduction is '个人说明';
comment on column scr.t_user_extend.gender is '性别：0.未知、1.男、2.女';
comment on column scr.t_user_extend.job is '职业';
comment on column scr.t_user_extend.home is '籍贯';
comment on column scr.t_user_extend.address is '住址';
comment on column scr.t_user_extend.education is '教育程度：0.未知 1.博士 2.硕士 3.本科 4.大专 5.中专 6.高中 7.初中';
comment on column scr.t_user_extend.modtime is '修改时间';
comment on column scr.t_user_extend.intime is '入库时间';

/*==============================================================*/
/* Table: t_user_service    用户服务信息表                      */
/*==============================================================*/
drop table if exists scr.t_user_service;
create table scr.t_user_service (
   userid               char(16)                                        not null,
   isrealname           bool           default false                    not null,
   logincount           integer        default 0                        not null,
   onlinetimecount      integer        default 0                        not null,
   logintime            timestamp      default current_timestamp        not null,
   modtime              timestamp      default current_timestamp        not null,
   intime               timestamp      default current_timestamp        not null,
   primary key (userid),
   foreign key(userid) references scr.t_user(userid)
);
comment on table scr.t_user_service is '用户统计信息表';
comment on column scr.t_user_service.userid is '用户号';
comment on column scr.t_user_service.isrealname is '是否实名';
comment on column scr.t_user_service.logincount is '用户访问次数统计';
comment on column scr.t_user_service.onlinetimecount is '用户在线时长统计(分)';
comment on column scr.t_user_service.logintime is '登录时间';
comment on column scr.t_user_service.modtime is '修改时间';
comment on column scr.t_user_service.intime is '入库时间';

/*==============================================================*/
/* Table: t_user_contact     用户联系人表                       */
/*==============================================================*/
drop table if exists scr.t_user_contact;
create table scr.t_user_contact (
   ucid                 char(16)                                    not null,
   userid               char(16)       default ''                   not null,
   contact              varchar(64)    default ''                   not null,
   type                 char(1)        default ''                   not null,
   commonused           bool           default false                not null,
   modtime              timestamp      default current_timestamp    not null,
   intime               timestamp      default current_timestamp    not null,
   primary key (ucid),
   foreign key(userid) references scr.t_user(userid)
);
comment on table scr.t_user_contact is '用户联系方式关系表';
comment on column scr.t_user_contact.userid is '用户号';
comment on column scr.t_user_contact.contact is '联系方式';
comment on column scr.t_user_contact.type is '类型:1.手机 2.邮箱 3.qq 4.微信';
comment on column scr.t_user_contact.commonused is '是否常用，记录最后一次确定使用情况';
comment on column scr.t_user_contact.modtime is '修改时间';
comment on column scr.t_user_contact.intime is '入库时间';



/*==============================================================*/
/* Table: t_user_oauth     OAuth信息表                       */
/*==============================================================*/
drop table if exists scr.t_user_oauth;
create table scr.t_user_oauth (
   oid              char(16)                                    not null,
   userid           char(16)       default ''                   not null,
   oauthtype        varchar(64)    default ''                   not null,
   oauthid          varchar(64)    default ''                   not null,
   modtime          timestamp      default current_timestamp    not null,
   intime           timestamp      default current_timestamp    not null,
   primary key (oid),
   foreign key(userid) references scr.t_user(userid)
);
comment on table scr.t_user_oauth is 'OAuth信息表';
comment on column scr.t_user_oauth.oid is '序列号';
comment on column scr.t_user_oauth.userid is '用户id';
comment on column scr.t_user_oauth.oauthtype is 'oauth类型';
comment on column scr.t_user_oauth.oauthid is 'oauthid';
comment on column scr.t_user_oauth.modtime is '修改时间';
comment on column scr.t_user_oauth.intime is '入库时间';

/*==============================================================*/
/* Table: t_badge 徽章表                                         */
/*==============================================================*/
drop table if exists scr.t_badge;
create table scr.t_badge(
	badgeid            char(16)        not null,
	badgename          varchar(256)    not null            default '',	
	description        varchar(256)    not null            default '',
    icon               text            not null            default '',
	state              char(1)         not null            default '',
	modtime            timestamp       not null            default current_timestamp,
	intime             timestamp       not null            default current_timestamp,
	primary key (badgeid)
);
comment on table scr.t_badge is '徽章表';
comment on column scr.t_badge.badgeid is '徽章号';
comment on column scr.t_badge.badgename is '徽章名';
comment on column scr.t_badge.description is '徽章描述';
comment on column scr.t_badge.icon is '徽章图标';
comment on column scr.t_badge.state is '状态  1：有效  2：无效';
comment on column scr.t_badge.modtime is '修改时间';
comment on column scr.t_badge.intime is '入库时间';

/*==============================================================*/
/* Table: t_user_badge    用户徽章表                          */
/*==============================================================*/
drop table if exists scr.t_user_badge;
create table scr.t_user_badge(
	userbadgeid        char(16)                                        not null,
	userid             char(16)            default ''                  not null,
	badgeid            char(16)            default ''                  not null,
    comment            text                default ''                  not null,
	modtime            timestamp           default current_timestamp   not null,
	intime             timestamp           default current_timestamp   not null,
	primary key (userbadgeid),
	foreign key(userid) references scr.t_user(userid),
    foreign key(badgeid) references scr.t_badge(badgeid)
);
comment on table scr.t_user_badge is '用户徽章表';
comment on column scr.t_user_badge.userbadgeid is '用户徽章号';
comment on column scr.t_user_badge.userid is '用户号';
comment on column scr.t_user_badge.badgeid is '徽章号';
comment on column scr.t_user_badge.comment is '评注';
comment on column scr.t_user_badge.modtime is '修改时间';
comment on column scr.t_user_badge.intime is '入库时间';

---------------------------------------系统通用信息-----------------------------------------------
/*==============================================================*/
/* Table: t_tag    标签表                                       */
/*==============================================================*/
drop table if exists scr.t_tag;
create table scr.t_tag (
   tagid                char(16)                         not null,
   tagname              varchar(64)    default ''        not null,
   count                integer        default 0         not null,
   description          text           default ''        not null,
   state                char(1)        default '1'       not null,
   inuser               char(16)       default ''        not null,
   modtime              timestamp      default now()     not null,
   intime               timestamp      default now()     not null,
   primary key (tagid),
   foreign key (inuser) references scr.t_user(userid)
);
comment on table scr.t_tag is '标签信息表';
comment on column scr.t_tag.tagid is '标签编号';
comment on column scr.t_tag.tagname is '标签名称';
comment on column scr.t_tag.count is '使用次数';
comment on column scr.t_tag.description is '标签描述';
comment on column scr.t_tag.state is '状态：1.待审核 2.正常 4.注销';
comment on column scr.t_tag.inuser is '创建人';
comment on column scr.t_tag.modtime is '修改时间';
comment on column scr.t_tag.intime is '创建时间';

/*==============================================================*/
/* Table: t_sms_msg           短消息表             */
/*==============================================================*/

create table scr.t_sms_msg (
	smsid 			char(16)                            	                 not null,
	msgid           text    				default ''                       not null,
	phone           text                    default ''                       not null,
	content         text                    default ''                       not null,
	send_state      integer                 default 1                        not null,
	callback_state  integer                 default 1                        not null,
	modtime         timestamp               default current_timestamp        not null,
	intime          timestamp               default current_timestamp        not null,
	primary key(smsid)
);
comment on table scr.t_sms_msg is '发送短消息表';
comment on column "scr"."t_sms_msg"."smsid" is '平台发送信息编号';
comment on column "scr"."t_sms_msg"."msgid" is '消息编号（运营商）';
comment on column "scr"."t_sms_msg"."phone" is '发送手机';
comment on column "scr"."t_sms_msg"."content" is '发送内容';
comment on column "scr"."t_sms_msg"."send_state" is '发送状态（运营商） 1.未反馈   2 成功 4 失败';
comment on column "scr"."t_sms_msg"."callback_state" is '回馈状态（运营商） 1.未反馈   2 成功 4 失败';
comment on column "scr"."t_sms_msg"."modtime" is '修改时间';
comment on column "scr"."t_sms_msg"."intime" is '入库时间';

/*==============================================================*/
/* Table: t_attach 附件信息表                                   */
/*==============================================================*/
drop table if exists scr.t_attach;
create table scr.t_attach (
	attachid 			char(16) 									not null,
	attachtype 			char(2)			default '00' 				not null,
	path 				varchar(128) 	default ''  				not null,
	attachname 			varchar(64) 	default '' 					not null,
	filetype 			varchar(32)  	default ''  				not null,
	mimetype 			varchar(32) 	default '' 					not null,
	size 				integer  		default 0  					not null,
	state 				char(1)  		default '2'  				not null,
	userid 				char(16) 		default '' 					not null,
	downloadcount 		integer  		default 0  					not null,
	expiretime 			timestamp  		default current_timestamp   not null,
	modtime 			timestamp  		default current_timestamp 	not null,
	intime 				timestamp   	default current_timestamp 	not null,
	primary key (attachid),
	foreign key(userid) references scr.t_user(userid)
);
comment on table scr.t_attach is '附件信息表';
comment on column scr.t_attach.attachid is '附件号';
comment on column scr.t_attach.attachtype is '附件类型[体系编号+具体类型]（如，用户证书01等，未知内容为00）';
comment on column scr.t_attach.path is '路径';
comment on column scr.t_attach.attachname is '原附件名';
comment on column scr.t_attach.filetype is '附件类型(后缀)';
comment on column scr.t_attach.mimetype is '附件多媒体类型';
comment on column scr.t_attach.size is '上传大小';
comment on column scr.t_attach.state is '附件状态：2.正常 4.异常 7.删除 8.过期';
comment on column scr.t_attach.userid is '上传用户号';
comment on column scr.t_attach.downloadcount is '下载次数';
comment on column scr.t_attach.expiretime is '过期时间';
comment on column scr.t_attach.modtime is '修改时间';
comment on column scr.t_attach.intime is '入库时间';

/*==============================================================*/
/* Table: t_suggest 投诉与建议表                                */
/*==============================================================*/
drop table if exists scr.t_suggest;
create table scr.t_suggest (
	suggestid 			char(16) 						not null,
	content 			text 			default '' 		not null,
	userid 				char(16) 		default '' 		not null,
	contact 			varchar(256) 	default '' 		not null,
	path 				varchar(128) 	default '' 		not null,
	state 				char(1) 		default '2'  	not null,
	modtime 			timestamp  		default current_timestamp	not null,
	intime 				timestamp  		default current_timestamp	not null,
	primary key (suggestid)
);
comment on table scr.t_suggest is '投诉与建议表';
comment on column scr.t_suggest.suggestid is '投诉与建议编号';
comment on column scr.t_suggest.content is '投诉建议内容';
comment on column scr.t_suggest.userid is '用户编号';
comment on column scr.t_suggest.contact is '联系方式';
comment on column scr.t_suggest.path is '页面路径';
comment on column scr.t_suggest.state is '状态:2.待处理 4.已处理';
comment on column scr.t_suggest.modtime is '修改时间';
comment on column scr.t_suggest.intime is '添加时间';


/*==============================================================*/
/* Table: t_config   系统配置表                                 */
/*==============================================================*/
drop table if exists scr.t_config;
create table scr.t_config (
   key            varchar(64)                                   not null,
   value          text            default '0'                   not null,
   modtime        timestamp       default current_timestamp     not null,
   primary key (key)
);
comment on table scr.t_config is '系统配置表';
comment on column scr.t_config.key is '配置主键';
comment on column scr.t_config.value is '配置值';
comment on column scr.t_config.modtime is '修改时间';

/*==============================================================*/
/* Table: t_notify 系统通知表                                   */
/*==============================================================*/
drop table if exists scr.t_notify;
create table scr.t_notify (
	notifyid		 char(16) 									not null,
	type 		     char(1)      default '0'					not null,
	userid 		     char(16)     default ''   					not null,
	objectid 		 char(16) 	  default ''	    			not null,
	state 		     char(1)      default '2'       			not null,
	content			 text 	 	  default ''        			not null,
	readtime 	     timestamp    default current_timestamp 	not null, 
	modtime 		 timestamp    default current_timestamp 	not null,
	intime 			 timestamp    default current_timestamp 	not null,
	primary key (notifyid),
	foreign key(userid) references scr.t_user(userid)
);
comment on table scr.t_notify is '系统通知表';
comment on column scr.t_notify.notifyid is '通知编号';
comment on column scr.t_notify.type is '类型：1.用户预约  2.账户变动 3.用户变动 4.信息审核 5.文章审核 6.系统通知';
comment on column scr.t_notify.userid is '用户编号';
comment on column scr.t_notify.objectid is '关联信息号';
comment on column scr.t_notify.state is '状态：2.未读 3.已读 4.删除';
comment on column scr.t_notify.content is '通知内容';
comment on column scr.t_notify.readtime is '阅读时间';
comment on column scr.t_notify.modtime is '修改时间';
comment on column scr.t_notify.intime is '入库时间';

------------------------------源码体系-----------------------------------

/*==============================================================*/
/* Table: t_source_project   源码表                                 */
/*==============================================================*/
drop table if exists scr.t_source_project;
create table scr.t_source_project(
    projectid           char(16)                                         not null,
    name                varchar(128)        default ''                      not null,
    description         text                default ''                      not null,
    uploader            char(16)            default ''                      not null,
    sourcepath          varchar(128)        default ''                      not null,
    datapath            varchar(128)        default ''                      not null,
    projectpath         varchar(128)        default ''                      not null,
    logo         		varchar(128)        default ''                      not null,
    state               char(1)             default '2'                     not null,
    modtime             timestamp           default current_timestamp       not null,
    intime              timestamp           default current_timestamp       not null, 
    primary key (projectid),
    foreign key (uploader) references scr.t_user(userid)
);
comment on table scr.t_source_project is '源码表';
comment on column scr.t_source_project.projectid is '源码编号';
comment on column scr.t_source_project.name is '源码名称';
comment on column scr.t_source_project.description is '源码描述';
comment on column scr.t_source_project.sourcepath is '源码根目录';
comment on column scr.t_source_project.datapath is '索引数据根目录';
comment on column scr.t_source_project.projectpath is '工程根目录';
comment on column scr.t_source_project.logo is 'logo图片路径';
comment on column scr.t_source_project.state is '状态:1.草稿 2.正常 3.关闭 4.删除 6.锁定';
comment on column scr.t_source_project.uploader is '上传者';
comment on column scr.t_source_project.modtime is '修改时间';
comment on column scr.t_source_project.intime is '入库时间';


/*==============================================================*/
/* Table: t_source_project_statistics   源码工程统计信息表      */
/*==============================================================*/
drop table if exists scr.t_source_project_statistics;
create table scr.t_source_project_statistics(
    projectid           char(16)                                            not null,
    annotationcount     integer             default 0                       not null,
    watchcount          integer             default 0                       not null,
    modtime             timestamp           default current_timestamp       not null,
    intime              timestamp           default current_timestamp       not null, 
    primary key (projectid),
	foreign key(projectid) references scr.t_source_project(projectid)
);
comment on table scr.t_source_project_statistics is '源码工程统计信息表';
comment on column scr.t_source_project_statistics.projectid is '源码编号';
comment on column scr.t_source_project_statistics.annotationcount is '源码注释数';
comment on column scr.t_source_project_statistics.watchcount is '源码关注数';
comment on column scr.t_source_project_statistics.modtime is '修改时间';
comment on column scr.t_source_project_statistics.intime is '入库时间';

/*==============================================================*/
/* Table: t_source_file   源码文件表表                            */
/*==============================================================*/
drop table if exists scr.t_source_file;
create table scr.t_source_file(
    fileid              char(16)                                            not null,
    filename            varchar(128)        default ''                      not null,
    path                varchar(1024)        default ''                      not null,
    owner               char(16)            default ''                      not null,
    superpath           char(16)            default ''                      not null,
    type                char(1)             default '1'                     not null,
    viewcount        	integer             default 0                       not null,
    state               char(1)             default '2'                     not null,
    modtime             timestamp           default current_timestamp       not null,
    intime              timestamp           default current_timestamp       not null, 
    primary key (fileid),
    foreign key (owner) references scr.t_source_project(projectid)
);
comment on table scr.t_source_file is '源码表';
comment on column scr.t_source_file.fileid is '源码编号';
comment on column scr.t_source_file.filename is '文件名';
comment on column scr.t_source_file.path is '相对路径';
comment on column scr.t_source_file.owner is '所属源码工程';
comment on column scr.t_source_file.superpath is '上级目录';
comment on column scr.t_source_file.type is '类型: 0.文件夹 1.文件';
comment on column scr.t_source_file.viewcount is '阅览数';
comment on column scr.t_source_file.state is '状态: 2.正常 4.删除';
comment on column scr.t_source_file.modtime is '修改时间';
comment on column scr.t_source_file.intime is '入库时间';


/*==============================================================*/
/* Table: t_annotation  注释表                          */
/*==============================================================*/
drop table if exists scr.t_annotation;
create table scr.t_annotation(
    annotationid            char(16)                                        not null,
    fileid                  char(16)        default ''                      not null,
    linenum                 integer         default 0                       not null,
    userid                  char(16)        default ''                      not null,
    content                 text            default ''                      not null,
    support        		    integer         default 0                    	not null,
    state                   char(1)         default '2'                     not null,
    modtime                 timestamp       default current_timestamp       not null,
    intime                  timestamp       default current_timestamp       not null,
    primary key (annotationid),
    foreign key (userid) references scr.t_user(userid),
    foreign key (fileid) references scr.t_source_file(fileid)
);
comment on table scr.t_annotation is '注释表';
comment on column scr.t_annotation.annotationid is '注释编号'; 
comment on column scr.t_annotation.fileid is '文件编号'; 
comment on column scr.t_annotation.linenum is '行号。如果行号是0，表示是对这个文件进行注释'; 
comment on column scr.t_annotation.userid is '用户编号'; 
comment on column scr.t_annotation.content is '注释内容'; 
comment on column scr.t_annotation.support is '赞数';  
comment on column scr.t_annotation.state is '状态:正常（2）、删除（4）、锁定（6）'; 
comment on column scr.t_annotation.modtime is '修改时间'; 
comment on column scr.t_annotation.intime is '入库时间'; 

/*==============================================================*/
/* Table: t_source_watch   源码工程关注表                            */
/*==============================================================*/
drop table if exists scr.t_source_watch;
create table scr.t_source_watch(
    watchid         char(16)                                            not null,
    userid          char(16)            default ''                      not null,
    projectid       char(16)            default ''                      not null,
    state           char(1)             default ''                      not null,
    modtime         timestamp           default current_timestamp       not null,
    intime          timestamp           default current_timestamp       not null,
    primary key (watchid),
    foreign key (projectid) references scr.t_source_project(projectid),
    foreign key (userid) references scr.t_user(userid)
);
comment on table scr.t_source_watch is '源码工程关注表';
comment on column scr.t_source_watch.watchid is '关注编号';
comment on column scr.t_source_watch.userid is '关注者用户编号';
comment on column scr.t_source_watch.projectid is '源码工程编号';
comment on column scr.t_source_watch.state is '状态: 2.正常 4.删除';
comment on column scr.t_source_watch.modtime is '修改时间';
comment on column scr.t_source_watch.intime is '入库时间';


/*==============================================================*/
/* Table: t_article        文章信息表                  			*/
/*==============================================================*/
drop table if exists scr.t_article;
create table scr.t_article (
   articleid            char(16)                                      not null,
   projectid            char(16)      default ''                      not null,
   userid             	char(16)      default ''                      not null,
   title                varchar(128)  default ''                      not null,
   summary              varchar(256)  default ''                      not null,
   content          	text          default ''                      not null,
   type		            char(1)       default '1'                     not null,
   busistate            char(1)       default '1'                     not null,
   state                char(1)       default '2'                     not null,
   weight	        	numeric(3,2)  DEFAULT 1.0 		      		  not null,
   pageview        		integer       default 0                       not null,
   support        		integer       default 0                    	  not null,   
   remark              	text    	  default ''                      not null,
   publishtime          timestamp     default current_timestamp       not null,
   modtime              timestamp     default current_timestamp       not null,
   intime               timestamp     default current_timestamp       not null,
   primary key (articleid),
   foreign key (projectid) references scr.t_source_project(projectid),
   foreign key (userid) references scr.t_user(userid)
);
comment on table scr.t_article is '文章信息表';
comment on column scr.t_article.articleid is '文章编号';
comment on column scr.t_article.projectid is '源码工程编号';
comment on column scr.t_article.userid is '用户编号';
comment on column scr.t_article.title is '文章标题';
comment on column scr.t_article.summary is '文章摘要';
comment on column scr.t_article.content is '文章内容';
comment on column scr.t_article.type is '文章类型：1.普通 2.部署脚本';
comment on column scr.t_article.busistate is '业务状态:1.草稿 2.审核通过 3.已发布（审核中） 4.审核不通过';
comment on column scr.t_article.state is '状态:正常（2）、关闭（3）、删除（4）、锁定（6）';
comment on column scr.t_article.weight is '排序权重（1.0-1.99）';
comment on column scr.t_article.pageview is '文章浏览数';
comment on column scr.t_article.support is '赞数';
comment on column scr.t_article.remark is '备注:例如审核不通过的原因';
comment on column scr.t_article.publishtime is '发布时间';
comment on column scr.t_article.modtime is '修改时间';
comment on column scr.t_article.intime is '入库时间';


/*==============================================================*/
/* Table: t_relation_article_tag     文章标签关系表             */
/*==============================================================*/
drop table if exists scr.t_relation_article_tag;
create table scr.t_relation_article_tag (
   atrelationid          char(16)                                       not null,
   articleid             char(16)       default ''                      not null,
   tagid                 char(16)       default ''                      not null,
   modtime               timestamp      default current_timestamp       not null,
   intime                timestamp      default current_timestamp       not null,
   primary key (atrelationid),
   foreign key (articleid) references scr.t_article(articleid),
   foreign key (tagid) references scr.t_tag(tagid)
);
comment on table scr.t_relation_article_tag is '文章标签关系表';
comment on column scr.t_relation_article_tag.atrelationid is '文章标签关系编号';
comment on column scr.t_relation_article_tag.articleid is '文章编号';
comment on column scr.t_relation_article_tag.tagid is '标签编号';
comment on column scr.t_relation_article_tag.modtime is '修改时间';
comment on column scr.t_relation_article_tag.intime is '入库时间';

/*==============================================================*/
/* Table: t_article_review  文章评价表                          */
/*==============================================================*/
drop table if exists scr.t_article_review;
create table scr.t_article_review (
   reviewid             char(16)                                        not null,
   articleid            char(16)       default ''                       not null,
   objectid             char(16)       default ''                       not null,
   content              text           default ''                       not null,
   attachments          varchar(256)   default ''                       not null,
   author               char(16)       default ''                       not null,
   busistate            char(1)        default '1'                      not null,
   state                char(1)        default '2'                      not null,
   modtime              timestamp      default current_timestamp        not null,
   intime               timestamp      default current_timestamp        not null,
   primary key (reviewid),
   foreign key (articleid) references scr.t_article(articleid),
   foreign key (author) references scr.t_user(userid)
);
comment on table scr.t_article_review is '文章评价表';
comment on column scr.t_article_review.reviewid is '评价编号';
comment on column scr.t_article_review.articleid is '文章编号';
comment on column scr.t_article_review.objectid is '文章编号或者评价编号';
comment on column scr.t_article_review.content is '评价内容';
comment on column scr.t_article_review.attachments is '评价附件';
comment on column scr.t_article_review.author is '评价作者';
comment on column scr.t_article_review.busistate is '业务状态:2.审核通过 3.已发布（审核中） 4.审核不通过';
comment on column scr.t_article_review.state is '状态:正常（2）、删除（4）、锁定（6）';
comment on column scr.t_article_review.modtime is '修改时间';
comment on column scr.t_article_review.intime is '入库时间';


/*==============================================================*/
/* Table: t_support  点赞记录表                          */
/*==============================================================*/
drop table if exists scr.t_support;
create table scr.t_support(
    supportid               char(16)                                        not null,
    userid                  char(16)        default ''                      not null,
    targetid                char(16)        default ''                      not null,  
    targettype              char(1)         default ''                      not null,    
    orientation             smallint        default 1                       not null,    
    intime                  timestamp       default current_timestamp       not null,
    primary key (supportid),
    foreign key (userid) references scr.t_user(userid)
);
comment on table scr.t_support is '点赞记录表';
comment on column scr.t_support.supportid is '点赞编号';
comment on column scr.t_support.userid is '用户编号';
comment on column scr.t_support.targetid is '点赞目标';
comment on column scr.t_support.targettype is '点赞目标类型：1.文章 2.文章评论 3.注释';
comment on column scr.t_support.orientation is '赞还是踩：1.赞  -1.踩';
comment on column scr.t_support.intime is '入库时间';


/*==============================================================*/
/* Table: t_template  模板表                          */
/*==============================================================*/
drop table if exists scr.t_template;
create table scr.t_template(
    templateid              char(16)                                        not null,
	name					varchar(256)    default ''                      not null,
	description				varchar(256)    default ''                      not null,
	default_version			varchar(256)    default ''                      not null,
	icon					varchar(256)    default ''                      not null,
	rancherid				varchar(256)    default ''                      not null,
    projectid               char(16)        default ''                      not null,
	state                	char(1)         default '1'                     not null,
    intime                  timestamp       default current_timestamp       not null,
	modtime                 timestamp       default current_timestamp       not null,
    primary key (templateid),
    foreign key (projectid) references scr.t_source_project(projectid)
);
comment on table scr.t_template is '模板表';
comment on column scr.t_template.templateid is '模板编号';
comment on column scr.t_template.name is '名字';
comment on column scr.t_template.description is '模板描述';
comment on column scr.t_template.default_version is '默认版本';
comment on column scr.t_template.icon is '图标';
comment on column scr.t_template.rancherid is 'rancher中模板的标识';
comment on column scr.t_template.projectid is '源码工程ID';
comment on column scr.t_template.state is '正常（2）、删除（4）';
comment on column scr.t_template.modtime is '修改时间';
comment on column scr.t_template.intime is '入库时间';


/*==============================================================*/
/* Table: t_template_version  模板版本表                          */
/*==============================================================*/
drop table if exists scr.t_template_version;
create table scr.t_template_version(
    tvid              		char(16)                                        not null,
	verno					varchar(256)    default ''                      not null,
	versionlink				varchar(256)    default ''                      not null,
    readme                  varchar(1024)   default ''                      not null,
	templateid              char(16)        default ''	                    not null,
	ranchercompose           varchar(1024)   default ''                      not null,
	dockercompose           varchar(1024)   default ''                      not null,
	questions	            varchar(1024)   default ''                      not null,
    intime                  timestamp       default current_timestamp       not null,
	modtime                 timestamp       default current_timestamp       not null,
    primary key (tvid),
    foreign key (templateid) references scr.t_template(templateid)
);
comment on table scr.t_template_version is '模板版本表';
comment on column scr.t_template_version.tvid is '模板版本号';
comment on column scr.t_template_version.verno is '模板版本';
comment on column scr.t_template_version.versionlink is '模板版本链接';
comment on column scr.t_template_version.readme is '自述描述';
comment on column scr.t_template_version.templateid is '模板号';
comment on column scr.t_template_version.ranchercompose is 'rancher的编排描述';
comment on column scr.t_template_version.dockercompose is 'docker编排描述';
comment on column scr.t_template_version.questions is '模板的参数，需要用户输入的参数。json格式';
comment on column scr.t_template_version.modtime is '修改时间';
comment on column scr.t_template_version.intime is '入库时间';



/*==============================================================*/
/* Table: t_appstack  应用实例表                          */
/*==============================================================*/
drop table if exists scr.t_app_instance;
create table scr.t_app_instance(
    appinstanceid           char(16)                                        not null,
	name					varchar(256)    default ''                      not null,
	rancherenvid			varchar(256)    default ''                      not null,
	environment				varchar(1024)   default ''                      not null,
	tvid					char(16)   		default ''                      not null,
    userid                  char(16)        default ''                      not null,
	state                	char(1)         default '1'                     not null,
    intime                  timestamp       default current_timestamp       not null,
	modtime                 timestamp       default current_timestamp       not null,
    primary key (appinstanceid),
    foreign key (userid) references scr.t_user(userid)
);
comment on table scr.t_app_instance is '应用实例表';
comment on column scr.t_app_instance.appinstanceid is '应用实例编号';
comment on column scr.t_app_instance.name is '名字';
comment on column scr.t_app_instance.rancherenvid is 'rancher环境ID';
comment on column scr.t_app_instance.environment is 'rancher实例参数';
comment on column scr.t_app_instance.tvid is '模板版本ID';
comment on column scr.t_app_instance.userid is '用户编号';
comment on column scr.t_app_instance.state is '状态  正常（2）、停止（4）  删除（6） ';
comment on column scr.t_app_instance.modtime is '修改时间';
comment on column scr.t_app_instance.intime is '入库时间';




/*==============================================================*/
/* Table: t_service_instance  应用服务实例表                          */
/*==============================================================*/
drop table if exists scr.t_service_instance;
create table scr.t_service_instance(
    serviceinstanceid       char(16)                                        not null,
	name					varchar(256)    default ''                      not null,
    appinstanceid           char(16)        default ''                      not null,
	state                	char(1)         default '1'                     not null,
    intime                  timestamp       default current_timestamp       not null,
	modtime                 timestamp       default current_timestamp       not null,
    primary key (serviceinstanceid),
    foreign key (appinstanceid) references scr.t_app_instance(appinstanceid)
);
comment on table scr.t_service_instance is '应用服务实例表';
comment on column scr.t_service_instance.serviceinstanceid is '应用服务实例号';
comment on column scr.t_service_instance.name is '名字';
comment on column scr.t_service_instance.appinstanceid is '应用实例编号';
comment on column scr.t_service_instance.state is '状态  正常（2）、停止（4）  删除（6）';
comment on column scr.t_service_instance.modtime is '修改时间';
comment on column scr.t_service_instance.intime is '入库时间';


/*==============================================================*/
/* Table: t_bonus_point  积分余额表                          */
/*==============================================================*/
drop table if exists scr.t_bonus_point;
create table scr.t_bonus_point(
    userid               	char(16)                                        not null,
	bonuspoint				integer    		default 0                       not null,
    intime                  timestamp       default current_timestamp       not null,
	modtime                 timestamp       default current_timestamp       not null,
    primary key (userid)
);
comment on table scr.t_bonus_point is '积分余额表';
comment on column scr.t_bonus_point.userid is '用户编号';
comment on column scr.t_bonus_point.bonuspoint is '积分余额';
comment on column scr.t_bonus_point.modtime is '修改时间';
comment on column scr.t_bonus_point.intime is '入库时间';



/*==============================================================*/
/* Table: t_bonus  积分活动表                          */
/*==============================================================*/
drop table if exists scr.t_bonus_activities;
create table scr.t_bonus_activities(
    activityid               char(16)                                        not null,
	userid					 char(16)                                        not null,
	value					integer    default 0                      not null,
	postvalue				integer    default 0                      not null,
	comment					varchar(256)	default ''						not null,
    intime                  timestamp       default current_timestamp       not null,
    primary key (activityid),
    foreign key (userid) references scr.t_bonus_point(userid)
);
comment on table scr.t_bonus_activities is '积分活动表';
comment on column scr.t_bonus_activities.activityid is '活动编号';
comment on column scr.t_bonus_activities.userid is '用户编号';
comment on column scr.t_bonus_activities.value is '变动值，增加为正，减少为负';
comment on column scr.t_bonus_activities.postvalue is '变动后的值';
comment on column scr.t_bonus_activities.comment is '活动的说明';
comment on column scr.t_bonus_activities.intime is '入库时间';




/*==============================================================*/
/* Table: t_bonus  积分审核表                          */
/*==============================================================*/
drop table if exists scr.t_bonus_review;
create table scr.t_bonus_review(
    reviewid               char(16)                                         not null,
	value					integer    default 0                      		not null,
	comment					varchar(256)	default ''						not null,
	state					char(1)         default '1'                     not null,
	modtime                 timestamp       default current_timestamp       not null,
    intime                  timestamp       default current_timestamp       not null,
    primary key (reviewid)
);
comment on table scr.t_bonus_review is '积分审核表';
comment on column scr.t_bonus_review.reviewid is '审核编号';
comment on column scr.t_bonus_review.value is '变动值';
comment on column scr.t_bonus_review.comment is '积分审核的说明';
comment on column scr.t_bonus_review.state is '状态  未审核（1）  已审核（2）';
comment on column scr.t_bonus_review.modtime is '修改时间';
comment on column scr.t_bonus_review.intime is '入库时间';

/***********************************
数据量大时数据库优化： 
    数据库建索引
    频繁修改的数据项（浏览数、赞数）先进缓存，再存库
    频繁修改的数据项（浏览数、赞数）从主表分离出来
    代码文件，注释放到NoSQL中，或者索引中
    点赞记录表按照类型拆分
*************************************/