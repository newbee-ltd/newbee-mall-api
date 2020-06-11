![](static-files/newbee-mall.png)

![Build Status](https://img.shields.io/badge/build-passing-green.svg)
![Version 2.0.0](https://img.shields.io/badge/version-2.0.0-yellow.svg)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/newbee-ltd/newbee-mall-api/blob/master/LICENSE)

newbee-mall 项目是一套电商系统，包括 newbee-mall 商城系统及 newbee-mall-admin 商城后台管理系统，基于 Spring Boot 2.X 及相关技术栈开发。 前台商城系统包含首页门户、商品分类、新品上线、首页轮播、商品推荐、商品搜索、商品展示、购物车、订单结算、订单流程、个人订单管理、会员中心、帮助中心等模块。 后台管理系统包含数据面板、轮播图管理、商品管理、订单管理、会员管理、分类管理、设置等模块。

本仓库中的源码为新蜂商城前后端分离版本的后端 API 项目，技术栈为 Spring Boot，主要面向服务端开发人员，前端 Vue 页面源码在另外一个仓库 [newbee-mall-vue-app](https://github.com/newbee-ltd/newbee-mall-vue-app)。

前后端分离版本包括三个仓库：

- [新蜂商城 V2 后端接口 newbee-mall-api](https://github.com/newbee-ltd/newbee-mall-api)
- [新蜂商城 V2 前端页面 newbee-mall-vue-app](https://github.com/newbee-ltd/newbee-mall-vue-app)
- [新蜂商城 V2 后台管理系统 newbee-mall-manage](https://github.com/newbee-ltd/newbee-mall-manage)

>与新蜂商城第一个版有所区别，希望大家不要混淆。

**坚持不易，如果觉得项目还不错的话可以给项目一个 Star 吧，也是对我一直更新代码的一种鼓励啦，谢谢各位的支持。**

![newbee-mall-info](https://newbee-mall.oss-cn-beijing.aliyuncs.com/poster/store/newbee-mall-star.png)

> 更多 Spring Boot 实战项目可以关注十三的另一个代码仓库 [spring-boot-projects](https://github.com/ZHENFENG13/spring-boot-projects)，该仓库中主要是 Spring Boot 的入门学习教程以及一些常用的 Spring Boot 实战项目教程，包括 Spring Boot 使用的各种示例代码，同时也包括一些实战项目的项目源码和效果展示，实战项目包括基本的 web 开发以及目前大家普遍使用的前后端分离实践项目等，后续会根据大家的反馈继续增加一些实战项目源码，摆脱各种 hello world 入门案例的束缚，真正的掌握 Spring Boot 开发。

关注公众号：**程序员的小故事**，回复"勾搭"进群交流。

![wx-gzh](https://newbee-mall.oss-cn-beijing.aliyuncs.com/wx-gzh/%E6%89%AB%E7%A0%81%E5%85%B3%E6%B3%A8.png)

## 开发及部署文档

- [**Spring Boot 大型线上商城项目实战教程**](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [技术选型之 Spring Boot](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [前期准备工作及基础环境搭建](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [Spring Boot 项目初体验--项目搭建及启动](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [Spring Boot 核心详解及源码分析](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [Spring Boot 之 DispatchServlet 自动配置源码解读](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [Spring Boot 之 Web 开发及 MVC 自动配置分析](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [Thymeleaf 模板引擎技术介绍及整合](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [Thymeleaf 语法详解及编码实践](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [Spring Boot 实践之数据源自动配置及数据库操作](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [Spring Boot 实践之整合 Mybatis 操作数据库](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [项目初体验：启动和使用新蜂商城](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [新蜂商城功能模块和流程设计详解](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [前端页面设计及技术选型](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [页面布局制作及跳转逻辑实现](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [Spring Boot 整合 kaptcha 实现验证码功能](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [新蜂商城后台管理系统登录功能实现](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [登陆拦截器设置并完善身份验证](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [通用分页功能设计与开发实践](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [JqGrid 插件整合制作分页效果](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [Spring Boot 实践之文件上传处理及路径回显](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [新蜂商城轮播图管理模块开发](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [新蜂商城分类管理模块开发-1](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [新蜂商城分类管理模块开发-2](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [富文本编辑器 KindEditor 介绍及整合详解](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [新蜂商城商品类目三级联动功能实现](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [新蜂商城商品编辑功能实现](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [新蜂商城商品管理模块功能实现](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [新蜂商城首页制作-1](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [新蜂商城首页制作-2](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [新蜂商城首页模块配置及功能完善](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [新蜂商城会员的注册/登录功能实现](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [新蜂商城搜索商品功能实现](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [新蜂商城购物车功能实现](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [Spring Boot中的事务处理](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [新蜂商城订单确认页和订单生成功能实践](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [新蜂商城个人订单列表和订单详情页制作](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [新蜂商城订单流程功能完善](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [新蜂商城错误页面制作](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)
- [小册总结](https://juejin.im/book/5da2f9d4f265da5b81794d48?referrer=59199e22a22b9d0058279886)

## 联系作者

> 大家有任何问题或者建议都可以在 [issues](https://github.com/newbee-ltd/newbee-mall/issues) 中反馈给我，我会慢慢完善这个项目。

- 我的邮箱：2449207463@qq.com
- QQ技术交流群：719099151 796794009

## 软件著作权

>本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！

![](https://newbee-mall.oss-cn-beijing.aliyuncs.com/poster/store/newbee-mall-copyright-02.png)

## 接口文档

![](static-files/newbee-mall-api-swagger.png)

## 页面展示

以下为新蜂商城 Vue 版本的页面预览：

- 登录页

![](static-files/登录.png)

- 首页

![](static-files/首页.png)

- 商品搜索

![](static-files/商品搜索.png)

- 商品详情页

![](static-files/详情页.png)

- 购物车

![](static-files/购物车.png)

- 生成订单

![](static-files/生成订单.png)

- 地址管理

![](static-files/地址管理.png)

- 订单列表

![](static-files/订单列表.png)

- 订单详情

![](static-files/订单详情.png)

## 感谢

- [spring-projects](https://github.com/spring-projects/spring-boot)
- [mybatis](https://github.com/mybatis/mybatis-3)
- [swagger](https://github.com/swagger-api)
- [Lombok](https://github.com/rzwitserloot)