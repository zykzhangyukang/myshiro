###  章鱼权限系统

![https://img.shields.io/badge/license-Apache%202.0-blue.svg?longCache=true&style=flat-square](https://img.shields.io/badge/license-Apache%202.0-blue.svg?longCache=true&style=flat-square)
![https://img.shields.io/badge/springboot-2.1.8-yellow.svg?style=flat-square](https://img.shields.io/badge/springboot-2.1.8-yellow.svg?style=flat-square)
![https://img.shields.io/badge/shiro-1.4.0-orange.svg?longCache=true&style=flat-square](https://img.shields.io/badge/shiro-1.4.0-orange.svg?longCache=true&style=flat-square)
![https://img.shields.io/badge/layui-2.5.5-brightgreen.svg?longCache=true&style=flat-square](https://img.shields.io/badge/layui-2.5.5-brightgreen.svg?longCache=true&style=flat-square)

![https://img.shields.io/badge/license-Apache%202.0-blue.svg?longCache=true&style=flat-square](https://img.shields.io/badge/license-Apache%202.0-blue.svg?longCache=true&style=flat-square)
![https://img.shields.io/badge/springboot-2.0.4-yellow.svg?longCache=true&style=popout-square](https://img.shields.io/badge/springboot-2.0.4-yellow.svg?longCache=true&style=popout-square)

章鱼权限系统是一个简单高效的后台权限管理系统。项目基础框架采用全新的Java Web开发框架 —— Spring Boot2.0，消除了繁杂的XML配置，使得二次开发更为简单；数据访问层采用tk_Mybatis，同时引入了通用Mapper和PageHelper插件，可快速高效的对单表进行增删改查操作，消除了大量传统XML配置SQL的代码；安全框架采用Shiro，可实现对按钮级别的权限控制,前端页面使用LayUI构建，主题风格小清新简洁，
,使用activiti实现业务流程的管理。

 - 系统模块（已完成）
 - 请假模块 （已完成）

演示环境账号密码：

账号 | 密码| 权限
---|---|---
zhangyukang | 123456 | 系统的最高权限


本地部署账号密码：

账号 | 密码| 权限
---|---|---
测试用户 | zhangyukang |普通网站的测试用户
测试组长 | zhangyukang | 测试部的管理者，审批测试用户的请假单。
第三方用户（github） | 无 |Github用户登入
 
### 快速开始
 - 导入sql文件夹的sql脚本。
 - 修改配置文件中的数据库名以及数据库密码。
 - 浏览器访问页面。localhost:8080


#### 1. 2019年10月24日: 完成开发环境搭建，完成菜单树的刷取。
#### 2. 2019年10月24日 22:57:02: 完成登入日志管理，系统用户管理。
####  3. 2019年11月10日 添加数据源监控(阿里)。
####  4. 2019年11月11日 完成角色的CRUD，以及角色的权限分配。
####  5. 2019年11月12日 完成菜单管理.（菜单和按钮）
####  6. 2019年11月13日 根据角色获取菜单动态生成菜单。
####  7. 2019年11月15日 使用AOP切面实现系统操作日志。
####  8. 2019年11月16日 解决ip2region，在maven打成jar包路径错误的问题。
####  9. 2019年11月23日 添加部门领导，留言功能（图片上传OSS）等。
####  10. 2019年12月4日 完成请假模块。
####  11. 2019年 12月5日 添加sql文件


### 页面展示


![登入统计](/images/1.PNG)

![系统日志](/images/2.PNG)

![流程管理](/images/3.PNG)

### 目录结构

```
 -- com
     -- coderman
         -- rbac
            |-- base (基础模块)
            |   |-- bean   
            |   |   `-- Message.java
            |   |-- controller
            |   |   |-- BaseController.java
            |   |   |-- FileController.java
            |   |   `-- MessageController.java
            |   |-- mapper
            |   |   `-- MessageMapper.java
            |   |-- service
            |   |   |-- impl
            |   |   |   `-- MessageServiceImpl.java
            |   |   `-- MessageService.java
            |   |-- utils
            |   |   `-- OssUploadImgProvider.java
            |   `-- vo
            |       |-- MessageVo.java
            |       `-- ResultFileVo.java
            |-- CarApplication.java
            `-- sys (系统模块)
                |-- annotation
                |   `-- ControllerEndpoint.java
                |-- aspect
                |   |-- AspectSupport.java
                |   `-- ControllerEndpointAspect.java
                |-- bean
                |   |-- ActiveUser.java
                |   |-- Department.java
                |   |-- DeptDTreeJson.java
                |   |-- LoginLog.java
                |   |-- Log.java
                |   |-- MenuDTreeJson.java
                |   |-- Menu.java
                |   |-- MenuNode.java
                |   |-- Role.java
                |   |-- RoleMenu.java
                |   |-- User.java
                |   `-- UserRole.java
                |-- config
                |   `-- ShiroAutoConfiguration.java
                |-- contast
                |   `-- MyConstant.java
                |-- controller
                |   |-- DepartmentController.java
                |   |-- LogController.java
                |   |-- LoginController.java
                |   |-- MenuController.java
                |   |-- RoleController.java
                |   |-- SessionController.java
                |   |-- SystemController.java
                |   `-- UserController.java
                |-- converter
                |   `-- TimeConverter.java
                |-- dto
                |   |-- HotMovieDTO.java
                |   |-- Movie.java
                |   `-- UserDTO.java
                |-- enums
                |   |-- ResultEnum.java
                |   `-- UserTypeEnum.java
                |-- exception
                |   |-- ExceptionHandle.java
                |   `-- SysException.java
                |-- mapper
                |   |-- DepartmentMapper.java
                |   |-- LoginLogMapper.java
                |   |-- LogMapper.java
                |   |-- MenuMapper.java
                |   |-- RoleExtMapper.java
                |   |-- RoleMapper.java
                |   |-- RoleMenuMapper.java
                |   |-- UserExtMapper.java
                |   |-- UserMapper.java
                |   `-- UserRoleMapper.java
                |-- realm
                |   `-- UserRealm.java
                |-- service
                |   |-- DepartmentService.java
                |   |-- impl
                |   |   |-- DepartmentServiceImpl.java
                |   |   |-- LoginLogServiceImpl.java
                |   |   |-- LogServiceImpl.java
                |   |   |-- MenuServiceImpl.java
                |   |   |-- RoleServiceImpl.java
                |   |   |-- SessionServiceImpl.java
                |   |   `-- UserServiceImpl.java
                |   |-- LoginLogService.java
                |   |-- LogService.java
                |   |-- MenuService.java
                |   |-- RoleService.java
                |   |-- SessionService.java
                |   `-- UserService.java
                |-- test
                |   |-- TestDate.java
                |   |-- TestInteger.java
                |   |-- TestLog.java
                |   |-- TestMbg.java
                |   |-- TestMD5.java
                |   |-- TestOKHttp.java
                |   |-- TestRoleExtMapper.java
                |   `-- TestUserMapper.java
                |-- utils
                |   |-- AddressUtil.java
                |   |-- BrowserUtil.java
                |   |-- DateUtil.java
                |   |-- IPUtil.java
                |   |-- JVMInfoUtils.java
                |   |-- MD5Util.java
                |   |-- MenuTreeBuilder.java
                |   |-- MySessionDAO.java
                |   `-- WebUtil.java
                `-- vo
                    |-- DepartmentVo.java
                    |-- LoginLogVo.java
                    |-- LogVo.java
                    |-- MenuVo.java
                    |-- PageVo.java
                    |-- ResultVo.java
                    |-- RoleVo.java
                    |-- TestResultVo.java
                    `-- UserVo.java
```





