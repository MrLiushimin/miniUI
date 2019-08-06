--创建表空间
create tablespace miniUI
logging
datafile '/u01/app/oracle/oradata/XE/miniUI.dbf'
size 50m
autoextend on
next 50m maxsize 20480m
extent management local;

--创建用户
create user miniUI identified by miniUI
default tablespace miniUI
temporary tablespace temp;


--创建表
CREATE TABLE plus_file (
  "id" NUMBER(11) NOT NULL,
  "name" VARCHAR2(500) DEFAULT NULL,
  "type" VARCHAR2(50) DEFAULT NULL,
  "size" VARCHAR2(20) DEFAULT NULL, 
  "url" VARCHAR2(300) DEFAULT NULL,
  "pid" VARCHAR2(50) DEFAULT NULL,
  "createdate" DATE DEFAULT NULL,
  "updatedate" DATE DEFAULT NULL,
  "folder" NUMBER(11) DEFAULT NULL,
  "num" NUMBER(11) DEFAULT NULL,
  PRIMARY KEY ("id")
);
--plus_file表的自增主键
create sequence seq_file increment by 1 start with 1 nomaxvalue nocycle nocache;
--plus_file表插入数据时对id自增，并且自己插入id时不能比seq_file当前值大于超过200
create or replace trigger tri_file_id
  before insert on plus_file
  for each row
declare
  nextid number;
  curid number;
  currval number;
  ex_1 exception;
  errorCode number;
begin
  IF :new."id" IS NULL or :new."id"=0 THEN
  	select max("id") into curid from plus_file;
  	select seq_file.currval into currval from dual;
  	IF curid > currval THEN
  		WHILE currval < curid LOOP
  			select seq_file.nextval into currval from dual;
  		END LOOP;
  	END IF;
    select seq_file.nextval
    into nextid
    from dual;
    :new."id":=nextid;
  ELSE
   	select seq_file.currval into currval from dual;
   	IF :new."id" - currval > 200 THEN
   		raise ex_1;
   	END IF;
   	IF :new."id" > currval THEN
   		WHILE currval < :new."id" LOOP
  			select seq_file.nextval into currval from dual;
  		END LOOP;
   	END IF;
  END IF;
  exception
  	when ex_1 then
  		RAISE_APPLICATION_ERROR(-20001,'插入id比当前seq超过200');
  	WHEN OTHERS THEN
  		errorCode := SQLCODE;
  		IF errorCode = -8002 THEN
  			IF :new."id" > 200 THEN
		   		RAISE_APPLICATION_ERROR(-20001,'插入id比当前seq超过200');
		   	END IF;
		   	IF :new."id" IS NULL or :new."id"=0 THEN
			   	select seq_file.nextval into nextid from dual;
			   	IF :new."id" > nextid THEN
			   		WHILE nextid < :new."id" LOOP
			  			select seq_file.nextval into nextid from dual;
			  		END LOOP;
			   	END IF;
		    	:new."id":=nextid;
		    END IF;
  		END IF;
END tri_file_id;


--主键是部门首字母
CREATE TABLE t_department (
	"id" VARCHAR2(20) NOT NULL PRIMARY KEY,
	"name" VARCHAR2(50) DEFAULT NULL,
	"manager" VARCHAR2(300) DEFAULT NULL,
	"manager_name" VARCHAR2(200) DEFAULT NULL
);


CREATE TABLE t_educational (
	"id" VARCHAR2(20) NOT NULL PRIMARY KEY,
	"name" VARCHAR2(20) DEFAULT NULL
);
--t_educational表的自增主键
create sequence seq_edu increment by 1 start with 1 nomaxvalue nocycle nocache;
--t_educational表插入数据时对id自增，并且自己插入id时不能比seq_file当前值大于超过200
create or replace trigger tri_edu_id
  before insert on t_educational
  for each row
declare
  nextid number;
  curid number;
  currval number;
  ex_1 exception;
  errorCode number;
begin
  IF :new."id" IS NULL or :new."id"=0 THEN
  	select max("id") into curid from t_educational;
  	select seq_edu.currval into currval from dual;
  	IF curid > currval THEN
  		WHILE currval < curid LOOP
  			select seq_edu.nextval into currval from dual;
  		END LOOP;
  	END IF;
    select seq_edu.nextval
    into nextid
    from dual;
    :new."id":=nextid;
  ELSE
   	select seq_edu.currval into currval from dual;
   	IF :new."id" - currval > 200 THEN
   		raise ex_1;
   	END IF;
   	IF :new."id" > currval THEN
   		WHILE currval < :new."id" LOOP
  			select seq_edu.nextval into currval from dual;
  		END LOOP;
   	END IF;
  END IF;
  exception
  	when ex_1 then
  		RAISE_APPLICATION_ERROR(-20001,'插入id比当前seq超过200');
  	WHEN OTHERS THEN
  		errorCode := SQLCODE;
  		IF errorCode = -8002 THEN
  			IF :new."id" > 200 THEN
		   		RAISE_APPLICATION_ERROR(-20001,'插入id比当前seq超过200');
		   	END IF;
		   	IF :new."id" IS NULL or :new."id"=0 THEN
			   	select seq_edu.nextval into nextid from dual;
			   	IF :new."id" > nextid THEN
			   		WHILE nextid < :new."id" LOOP
			  			select seq_edu.nextval into nextid from dual;
			  		END LOOP;
			   	END IF;
		    	:new."id":=nextid;
		    END IF;
  		END IF;
END tri_edu_id;


--主键是uuid
CREATE TABLE t_employee (
	"id" VARCHAR2(50) NOT NULL PRIMARY KEY,
	"loginname" VARCHAR2(50) DEFAULT NULL,
	"name" VARCHAR2(50) DEFAULT NULL,
	"age" NUMBER(11) DEFAULT NULL,
	"birthday" DATE DEFAULT NULL,
	"dept_id" VARCHAR2(50) DEFAULT NULL,
	"position" VARCHAR2(50) DEFAULT NULL,
	"gender" NUMBER(11) DEFAULT NULL,
	"married" NUMBER(11) DEFAULT NULL,
	"salary" VARCHAR2(20) DEFAULT NULL,
	"educational" VARCHAR2(20) DEFAULT NULL,
	"country" VARCHAR2(20) DEFAULT NULL,
	"city" VARCHAR2(20) DEFAULT NULL,
	"remarks" VARCHAR2(2000) DEFAULT NULL,
	"school" VARCHAR2(50) DEFAULT NULL,
	"createtime" DATE DEFAULT NULL,
	"email" VARCHAR2(100) DEFAULT NULL
);
COMMENT on column t_employee."id" is '员工编号';
COMMENT on column t_employee."loginname" is '帐号';
COMMENT on column t_employee."name" is '姓名';
COMMENT on column t_employee."age" is '年龄(number)';
COMMENT on column t_employee."birthday" is '生日(datetime)';
COMMENT on column t_employee."dept_id" is '部门(combobox)';
COMMENT on column t_employee."position" is '职位(combobox)';
COMMENT on column t_employee."gender" is '性别：1男；2女';
COMMENT on column t_employee."married" is '0未婚；1已婚';
COMMENT on column t_employee."salary" is '薪资';
COMMENT on column t_employee."educational" is '学历';
COMMENT on column t_employee."country" is '国家';
COMMENT on column t_employee."city" is '城市';
COMMENT on column t_employee."remarks" is '备注';
COMMENT on column t_employee."school" is '毕业学校';


--主键是地区首字母
CREATE TABLE t_position (
	"id" VARCHAR2(50) NOT NULL PRIMARY KEY,
	"name" VARCHAR2(50) DEFAULT NULL,
	"dept_id" VARCHAR2(50) DEFAULT NULL
);

--插入数据
INSERT ALL INTO plus_file VALUES ('1','Files',NULL,'0',NULL,'-1',NULL,NULL,1,NULL)
  INTO plus_file VALUES ('10','Product icons',NULL,NULL,NULL,'3',NULL,NULL,1,NULL)
  INTO plus_file VALUES ('11','Description.rtf','rtf',NULL,NULL,'1',NULL,NULL,0,NULL)
  INTO plus_file VALUES ('12','n.txt','txt',NULL,NULL,'1',NULL,NULL,0,NULL)
  INTO plus_file VALUES ('13','o.txt','txt',NULL,NULL,'1',NULL,NULL,0,NULL)
  INTO plus_file VALUES ('14','file1.txt','txt',NULL,NULL,'2',NULL,NULL,0,NULL)
  INTO plus_file VALUES ('15','file2.txt','txt',NULL,NULL,'2',NULL,NULL,0,NULL)
  INTO plus_file VALUES ('16','file3.txt','txt',NULL,NULL,'3',NULL,NULL,0,NULL)
  INTO plus_file VALUES ('17','file4.txt','txt',NULL,NULL,'3',NULL,NULL,0,NULL)
  INTO plus_file VALUES ('18','file5.txt','txt',NULL,NULL,'4',NULL,NULL,0,NULL)
  INTO plus_file VALUES ('19','file6.txt','txt',NULL,NULL,'5',NULL,NULL,0,NULL)
  INTO plus_file VALUES ('2','Documents',NULL,NULL,NULL,'1',NULL,NULL,1,NULL)
  INTO plus_file VALUES ('20','file7.txt','txt',NULL,NULL,'6',NULL,NULL,0,NULL)
  INTO plus_file VALUES ('21','file8','txt',NULL,NULL,'7',NULL,NULL,0,NULL)
  INTO plus_file VALUES ('22','file9','txt',NULL,NULL,'8',NULL,NULL,0,NULL)
  INTO plus_file VALUES ('3','Images',NULL,NULL,NULL,'1',NULL,NULL,1,NULL)
  INTO plus_file VALUES ('4','Music',NULL,NULL,NULL,'1',NULL,NULL,1,NULL)
  INTO plus_file VALUES ('5','System',NULL,NULL,NULL,'1',NULL,NULL,1,NULL)
  INTO plus_file VALUES ('6','Video',NULL,NULL,NULL,'1',NULL,NULL,1,NULL)
  INTO plus_file VALUES ('7','Projects',NULL,NULL,NULL,'2',NULL,NULL,1,NULL)
  INTO plus_file VALUES ('8','Reports',NULL,NULL,NULL,'2',NULL,NULL,1,NULL)
  INTO plus_file VALUES ('9','Employees',NULL,NULL,NULL,'3',NULL,NULL,1,NULL)
SELECT 1 FROM DUAL;

INSERT ALL INTO t_department VALUES ('sc','市场销售部',NULL,NULL)
  INTO t_department VALUES ('rs','人事部',NULL,NULL)
  INTO t_department VALUES ('js','技术部',NULL,NULL)
  INTO t_department VALUES ('cw','财务部',NULL,NULL)
SELECT 1 FROM DUAL;

INSERT ALL INTO t_educational VALUES (1,'大专')
	INTO t_educational VALUES (2,'本科')
	INTO t_educational VALUES (3,'研究生')
	INTO t_educational VALUES (4,'博士')
SELECT 1 FROM DUAL;

INSERT ALL INTO t_employee VALUES ('8197fb45-2b60-4309-820c-e70824fc9b33','zm@163.com','张明',27,to_date('1983-01-13 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'cw','cw2',1,0,'3839','2','','','爱好足球','贵州财经大学',to_date('2012-01-31 10:50:12','yyyy-MM-dd hh24:mi:ss'),'zm@163.com')
  INTO t_employee VALUES ('b3d3d2b2-460a-470e-a33c-aff1c8d8a652','sww@163.com','宋蔚伟',25,to_date('1987-01-21 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'sc','sc3',1,0,'3681','2','','','爱好篮球','天津科技大学',to_date('2012-01-31 10:53:35','yyyy-MM-dd hh24:mi:ss'),'sww@163.com')
  INTO t_employee VALUES ('410c0185-3c9c-44ec-b9fb-f5a856013157','cyj@163.com','陈英杰',25,to_date('1987-01-21 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'sc','sc3',1,0,'3681','2','','','','南京审计学院',to_date('2012-01-31 10:53:55','yyyy-MM-dd hh24:mi:ss'),'cyj@qq.com')
  INTO t_employee VALUES ('70b7f3d2-b5d3-413f-b3ae-d56e07fbe5fe','zw@hotmail.com','张伟',31,to_date('1981-01-14 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'rs','rs2',1,1,'4760','2','','','','南京工业大学',to_date('2012-01-31 11:04:18','yyyy-MM-dd hh24:mi:ss'),'zw@hotmail.com')
  INTO t_employee VALUES ('ceb1ab63-c0c6-40fb-a481-65ed70b9e2a2','huang8373268@qq.com','黄磊',37,NULL,'sc','sc2',2,1,'7398','2','','','','金陵科技学院',to_date('2012-01-31 11:10:36','yyyy-MM-dd hh24:mi:ss'),'huang8373268@qq.com') 
  INTO t_employee VALUES ('20ac24f7-a34c-4f12-89f0-91d65b2ddfa9','xucc198712@qq.com','徐承承',27,to_date('1985-07-17 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'js','js2',2,0,'4373','2','','','','南京邮电学院',to_date('2012-01-31 13:28:51','yyyy-MM-dd hh24:mi:ss'),'xucc198712@qq.com')
  INTO t_employee VALUES ('d6fb5410-adde-46a9-9d2b-dee99c64a83d','xzz@163.com','谢忠哲',25,to_date('1987-10-07 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'js','js3',2,0,'3860','2','','','','宁波工程大学',to_date('2012-01-31 13:28:34','yyyy-MM-dd hh24:mi:ss'),'xzz@163.com')
  INTO t_employee VALUES ('ac3c3166-e677-45a5-9d1b-da4f09d6fc2a','zhoulan2010@hotmail.com','周兰',34,to_date('1978-12-15 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'rs','rs1',2,1,'6580','3','','','','南京理工大学',to_date('2012-01-31 13:28:33','yyyy-MM-dd hh24:mi:ss'),'zhoulan20@hotmail.com')
  INTO t_employee VALUES ('61a6943f-990e-4169-b6a0-bed9bdc42a5f','xiaoqian18392342@qq.com','陈晓倩',24,to_date('1988-11-18 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'sc','sc3',2,0,'3000','2','','','','南京信息工程大学',to_date('2012-01-31 13:28:31','yyyy-MM-dd hh24:mi:ss'),'xiaoqian1@qq.com')
  INTO t_employee VALUES ('54b12a07-1f7d-4616-b3e9-9dcc465a5f33','13625147852@163.com','张鹏楠',25,to_date('1987-06-12 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'js','js3',1,0,'3230','2','','','','南京邮电学院',to_date('2012-01-31 14:04:24','yyyy-MM-dd hh24:mi:ss'),NULL)
  INTO t_employee VALUES ('b0171c0f-5064-4975-9f54-ebf28666aca6','jan3844@gmail.com','沈臻妍',25,to_date('1987-03-17 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'rs','rs2',2,0,'3400','2','','','','浙江理工大学',to_date('2012-01-31 13:28:31','yyyy-MM-dd hh24:mi:ss'),'jan3844@gmail.com')
  INTO t_employee VALUES ('9252b4a2-8000-4b3e-b29a-ac7849163ca2','djf19830326@qq.com','杜鉴锋',28,to_date('1984-11-08 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'js','js2',1,1,'5300','2','','','','南京理工大学',to_date('2012-01-31 13:28:29','yyyy-MM-dd hh24:mi:ss'),'djf19830326@qq.com')
  INTO t_employee VALUES ('8cb7e27c-ebab-48b5-bf83-67f8294e8797','shcheng@163.com','施华成',30,to_date('1982-05-04 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'sc','sc2',2,1,'5600','2','','','','南京晓庄学院',to_date('2012-01-31 13:28:28','yyyy-MM-dd hh24:mi:ss'),'shcheng@163.com')
  INTO t_employee VALUES ('7ef2fc41-27b2-4991-9134-189f95d76c42','hk2006@qq.com','黄凯',24,to_date('1988-01-28 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'sc','sc3',1,0,'2900','2','','','','湖南科技大学',to_date('2012-01-31 13:28:27','yyyy-MM-dd hh24:mi:ss'),'hk2006@qq.com')
  INTO t_employee VALUES ('a817ca24-ede0-4932-8455-b1e86eb7772d','wl19861219@163.com','王岚',25,to_date('1987-08-19 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'sc','sc3',2,0,'3000','2','','','','南京审计学院',to_date('2012-01-31 13:28:26','yyyy-MM-dd hh24:mi:ss'),'wl19861219@163.com')
  INTO t_employee VALUES ('b4366fb3-fb9c-4244-b3b6-c56de0c70cde','tangyue@gmail.com','唐岳',35,to_date('1977-06-21 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'sc','sc1',1,1,'6300','2','','','','金陵科技大学',to_date('2012-01-31 13:28:26','yyyy-MM-dd hh24:mi:ss'),'tangyue@gmail.com')
  INTO t_employee VALUES ('7cc225c6-2673-4993-ab02-288e15fc351b','wss2005@qq.com','王珊珊',27,to_date('1985-03-12 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'js','js4',2,1,'3700','2','','','','南京审计学院',to_date('2012-01-31 11:10:47','yyyy-MM-dd hh24:mi:ss'),'wss2005@qq.com')
  INTO t_employee VALUES ('a46d28db-c484-4e58-9076-166cb957a152','19880141x@qq.com','薛乃馨',25,to_date('1987-01-20 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'cw','cw3',2,0,'3100','2','','','','合肥学院',to_date('2012-01-31 11:10:47','yyyy-MM-dd hh24:mi:ss'),'19880141x@qq.com')
  INTO t_employee VALUES ('beb61bb1-9c6c-4c93-8d82-ab784cc00b27','wwy@yahoo.com','吴蔚一',28,to_date('1984-06-12 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'js','js4',1,1,'4320','2','','','','铜陵学院',to_date('2012-01-31 11:10:47','yyyy-MM-dd hh24:mi:ss'),'wwy@yahoo.com')
  INTO t_employee VALUES ('c95c8c42-9fa9-4188-9a6e-0cb676d824e5','jinyi1987@qq.com','金怡',26,to_date('1986-04-14 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'js','js2',2,0,'3987','2','','','','江苏大学',to_date('2012-01-31 11:10:41','yyyy-MM-dd hh24:mi:ss'),'jinyi1987@qq.com')
  INTO t_employee VALUES ('6f1da849-7fea-4fe1-ba33-71f7d4a6412d','ywq2000@163.com','袁维琦',27,to_date('1985-01-09 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'js','js3',1,1,'4632','2','','','','三江学院',to_date('2012-01-31 11:10:40','yyyy-MM-dd hh24:mi:ss'),'ywq2000@163.com')
  INTO t_employee VALUES ('626c9bd1-c99b-4969-9af8-58d216c2a49b','hongweishi@hotmail.com','施宏伟',37,to_date('1975-10-16 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'js','js1',1,1,'6350','2','','','','江苏科技大学',to_date('2012-01-31 11:10:39','yyyy-MM-dd hh24:mi:ss'),'hongweishi@hotmail.com')
  INTO t_employee VALUES ('17ac00b0-ea98-4865-b006-38a462ad201a','wj1983417@qq.com','王军',28,to_date('1984-01-10 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'js','js2',1,1,'4850','2','','','','扬州大学',to_date('2012-01-31 11:10:38','yyyy-MM-dd hh24:mi:ss'),'wj1983417@qq.com')
  INTO t_employee VALUES ('d4d7b386-5030-4ef8-882f-0aa869217121','chenjie1866@qq.com','陈杰',24,to_date('1988-10-12 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'cw','cw3',1,0,'3000','2','','','','徐州工程大学',to_date('2012-01-31 11:10:38','yyyy-MM-dd hh24:mi:ss'),'chenjie1866@qq.com')
  INTO t_employee VALUES ('27eb622c-6b1b-431e-9cf5-c82a71fea2d5','wkliang123@163.com','王克良',29,to_date('1983-01-13 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'sc','sc2',1,1,'5300','2','','','','常熟理工大学',to_date('2012-01-31 11:10:37','yyyy-MM-dd hh24:mi:ss'),'wkliang123@163.com')
  INTO t_employee VALUES ('77b8dd59-30a5-49cd-aaa2-095926770438','qinli3849087@qq.com','秦立',25,to_date('1987-10-30 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'sc','sc3',2,1,'3000','2','','','','盐城师范学院',to_date('2012-01-31 11:10:37','yyyy-MM-dd hh24:mi:ss'),'qinli384@qq.com')
  INTO t_employee VALUES ('c7643283-f8f8-4329-9f06-276168ffc192','zhangliang@163.com','张亮',25,to_date('1987-05-21 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'sc','sc3',2,0,'3500','2','','','','宿州学院',to_date('2012-01-31 11:10:36','yyyy-MM-dd hh24:mi:ss'),'zhangliang@163.com')
  INTO t_employee VALUES ('10a9f701-e0e0-4506-8937-e7c0ae8c4ae6','glq@qq.com','顾乐琴',24,to_date('1988-01-13 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'js','js5',2,0,'2000','2','','','','上海交通大学',to_date('2012-01-31 11:10:33','yyyy-MM-dd hh24:mi:ss'),'glq@qq.com')
  INTO t_employee VALUES ('5b68ce26-90d5-4df5-9fe3-cdbd2a325f23','gyj@qq.com','高英杰',26,to_date('1986-10-15 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'cw','cw3',1,0,'3232','2','','','','上海外贸大学',to_date('2012-01-31 11:04:51','yyyy-MM-dd hh24:mi:ss'),'gyj@qq.com')
  INTO t_employee VALUES ('7804c632-5959-4c8e-b7e7-633926efa8d9','zhou123@163.com','周捷',26,to_date('1986-01-14 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'js','js3',1,0,'4600','2','','','','安徽农业大学',to_date('2012-01-31 11:04:46','yyyy-MM-dd hh24:mi:ss'),'zhou123@163.com')
  INTO t_employee VALUES ('14e8841e-9b41-485c-9cd4-de77f8ba4cfa','zhuming@163.com','朱敏',24,to_date('1988-01-13 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'js','js5',1,0,'2000','2','','','','汕头大学',to_date('2012-01-31 11:04:41','yyyy-MM-dd hh24:mi:ss'),'zhumin@163.com')
  INTO t_employee VALUES ('ad62a74d-82b0-4ece-9d01-59965d60f86e','miaochun@qq.com','苗春',30,to_date('1982-06-16 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'rs','rs1',2,1,'6000','2','','','','苏州大学',to_date('2012-01-31 11:04:35','yyyy-MM-dd hh24:mi:ss'),'miaochun@qq.com')
  INTO t_employee VALUES ('12dfbe12-8a80-4bd6-9d88-f36b505408f8','wjm@qq.com','王佳美',27,to_date('1985-07-25 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'sc','sc3',2,1,'3681','2','','','','河北科技大学',to_date('2012-01-31 10:53:40','yyyy-MM-dd hh24:mi:ss'),'wjm@qq.com')
  INTO t_employee VALUES ('0ffa83ef-c0d2-4c9b-9c31-aa46b657c700','lili@163.com','李丽',25,to_date('1987-06-25 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'js','js3',2,0,'3681','2','','','','上海工商学院',to_date('2012-01-31 10:53:52','yyyy-MM-dd hh24:mi:ss'),'lili@163.com')
  INTO t_employee VALUES ('92c2a9fb-5da1-4de5-a516-0c4c96d82ffd','wq@163.com','王强',25,to_date('1987-12-11 00:00:00', 'yyyy-MM-dd hh24:mi:ss'),'js','js2',1,0,'3201','2','','','','天津商业大学',to_date('2012-01-31 10:53:39','yyyy-MM-dd hh24:mi:ss'),'wq@163.com')
SELECT 1 FROM DUAL;

INSERT ALL INTO t_position VALUES ('cw1','财务总监','cw')
  INTO t_position VALUES ('cw2','会计','cw')
  INTO t_position VALUES ('cw3','出纳','cw')
  INTO t_position VALUES ('sc1','市场总监','sc')
  INTO t_position VALUES ('sc2','销售经理','sc')
  INTO t_position VALUES ('sc3','销售员','sc')
  INTO t_position VALUES ('js1','技术总监','js')
  INTO t_position VALUES ('js2','后台工程师','js')
  INTO t_position VALUES ('js3','前端工程师','js')
  INTO t_position VALUES ('js4','美工','js')
  INTO t_position VALUES ('js5','实习生','js')
  INTO t_position VALUES ('rs1','人事部经理','rs')
  INTO t_position VALUES ('rs2','人事助理','rs')
SELECT 1 FROM DUAL;

SELECT * FROM USER_TAB_COMMENTS;
SELECT * FROM T_DEPARTMENT;
SELECT * FROM PLUS_FILE;
SELECT * FROM T_EDUCATIONAL;
SELECT * FROM T_EMPLOYEE;
SELECT * FROM T_POSITION;


