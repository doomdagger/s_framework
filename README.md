s_framework
===========

> a blank project can be used as a boilerplate, utterly based on Spring (Spring MVC, Spring Context, Spring JDBC)

Project Need to be bootstrapped before running on server.

Tomcat 7 recommended.

核心包介绍
------
s_framework使用了标准的三层架构规范，开发者的二次开发，会一直围绕下面三个包展开:
```
com.hg.ecommerce.action    //Spring MVC Controller 
com.hg.ecommerce.dao.impl    //Data Access Object Implementation
com.hg.ecommerce.service.impl    //Service Implementation
```
* `com.hg.ecommerce.action`主要存放Spring MVC的控制器，在控制器中开发者规定路由规则与响应形式，并调用适当的服务类完成主要业务逻辑。
* `com.hg.ecommerce.dao.impl`主要存放DAO类，DAO类一般无需二次开发，使用框架提供的API来进行数据库访问操作。
* `com.hg.ecommerce.service.impl`主要存放Service类，即服务类。服务类通过DAO类进行数据库访问操作，并在原始数据的基础上，进行加工处理，使得数据适合返回前端框架，服务类将主要被控制器类使用。

快速开始
------

###简单配置

使用s_framework之前，必须要进行适当的配置工作，由于使用了[Maven](http://maven.apache.org/)管理项目依赖，Source Folder的命名和组织也采用了Maven标准。一切的配置文件，均在`src/main/resources`这个源码包下，二次开发使用的，仅是`project-custom.properties`这个配置文件。在这个配置文件中，我们可以覆盖框架的默认配置属性，也可以自定义新的配置信息。

>project-custom.properties

```
#database info
db.name=mysql   #使用的数据库名称
db.driverClassName=com.mysql.jdbc.Driver #jdbc驱动类名称
db.url=jdbc:mysql://localhost:3306/demo?characterEncoding=utf8  #数据库url
db.username=root   #数据库用户
db.password=911119   #数据库用户密码

#log path config - default to be your home directory -- see project.properties
log.path=D:\\Developer\\log    #系统日志目录

#ext js root
ext.url=http://localhost:8080/extjs/

```
框架的详细配置信息，参考[Configuration]()一节的内容。

###快速生成实体类

运行`com.hg.ecommerce.util.Bootstrap`类的main方法，框架将根据数据库配置信息，访问数据库，抓取表信息，生成单表单实体。

>生成的实体均已EntityObject为父类，继承此类中的具备RESTFul特性的便捷方法。


>运行完毕后，新生成的类并不会直接显示在Eclipse左侧的Project Explorer视图中，在项目名称上点击右键，选择Refresh，刷新项目文件结构，这是Model类可见，并存放于`com.hg.ecommerce.model`包中。


###运行项目

在Eclipse的server视图中，确保存在tomcat7.0.52以上版本，在tomcat服务器上点击右键，选择`add and remove`，在弹出的对话框中，将s_framework项目add到右侧一栏中。然后启动tomcat即可，访问:
`http://localhost:*your_port*/ecommerce/app/index`