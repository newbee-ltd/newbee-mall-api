![](static-files/newbee-mall.png)

![Build Status](https://img.shields.io/badge/build-passing-green.svg)
![Version 2.0.0](https://img.shields.io/badge/version-2.0.0-yellow.svg)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/newbee-ltd/newbee-mall-api/blob/master/LICENSE)

newbee-mall 项目是一套电商系统，基于 Spring Boot 和 Vue 以及相关技术栈开发。前台商城系统包含首页门户、商品分类、新品上线、首页轮播、商品推荐、商品搜索、商品展示、购物车、订单结算、订单流程、个人订单管理、会员中心、帮助中心等模块。 后台管理系统包含数据面板、轮播图管理、商品管理、订单管理、会员管理、分类管理、设置等模块。

当前分支的 Spring Boot 版本为 2.7.5，想要学习和使用其它版本可以直接点击下方的分支名称跳转至对应的仓库分支中。

| 分支名称                                                    | Spring Boot Version |
| ------------------------------------------------------------ | ------------------- |
| [spring-boot-2.3.7](https://github.com/newbee-ltd/newbee-mall-api/tree/spring-boot-2.3.7) | 2.3.7-RELEASE       |
| [spring-boot-2.6.x](https://github.com/newbee-ltd/newbee-mall-api/tree/spring-boot-2.6.x) | 2.6.3               |
| [main](https://github.com/newbee-ltd/nnewbee-mall-api)            | 2.7.5               |
| [spring-boot-3.x](https://github.com/newbee-ltd/newbee-mall-api/tree/spring-boot-3.x) | 3.0.1               |

**坚持不易，如果觉得项目还不错的话可以给项目一个 Star 吧，也是对我一直更新代码的一种鼓励啦，谢谢各位的支持。**

![newbee-mall-info](https://newbee-mall.oss-cn-beijing.aliyuncs.com/poster/store/newbee-mall-star.png)

## newbee-mall （新蜂商城）系列项目概览

![newbee-mall-course-2022](https://github.com/newbee-ltd/newbee-mall-cloud/raw/main/static-files/newbee-mall-course-2022.png)

| 项目名称             | 仓库地址                                                     | 备注                                                         |
| :------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| newbee-mall          | [newbee-mall in GitHub](https://github.com/newbee-ltd/newbee-mall)<br>[newbee-mall in Gitee](https://gitee.com/newbee-ltd/newbee-mall) | 初始版本、Spring Boot、Thymeleaf、MyBatis、MySQL             |
| newbee-mall-plus     | [newbee-mall-plus in GitHub](https://github.com/newbee-ltd/newbee-mall-plus)<br/>[newbee-mall-plus in Gitee](https://gitee.com/newbee-ltd/newbee-mall-plus) | 升级版本、优惠券、秒杀、支付、Spring Boot、Thymeleaf、MyBatis、MySQL、Redis |
| newbee-mall-cloud    | [newbee-mall-cloud in GitHub](https://github.com/newbee-ltd/newbee-mall-cloud)<br/>[newbee-mall-cloud in Gitee](https://gitee.com/newbee-ltd/newbee-mall-cloud) | 微服务版本、分布式事务、Spring Cloud Alibaba、Nacos、Sentinel、OpenFeign、Seata |
| newbee-mall-api      | [newbee-mall-api in GitHub](https://github.com/newbee-ltd/newbee-mall-api)<br/>[newbee-mall-api in Gitee](https://gitee.com/newbee-ltd/newbee-mall-api) | 前后端分离、Spring Boot、MyBatis、Swagger、MySQL             |
| newbee-mall-api-go   | [newbee-mall-api-go in GitHub](https://github.com/newbee-ltd/newbee-mall-api-go)<br/>[newbee-mall-api-go in Gitee](https://gitee.com/newbee-ltd/newbee-mall-api-go) | 前后端分离、Go、Gin、MySQL                                   |
| newbee-mall-vue-app  | [newbee-mall-vue-app in GitHub](https://github.com/newbee-ltd/newbee-mall-vue-app)<br/>[newbee-mall-vue-app in Gitee](https://gitee.com/newbee-ltd/newbee-mall-vue-app) | 前后端分离、Vue2、Vant                                    |
| newbee-mall-vue3-app | [newbee-mall-vue3-app in GitHub](https://github.com/newbee-ltd/newbee-mall-vue3-app)<br/>[newbee-mall-vue3-app in Gitee](https://gitee.com/newbee-ltd/newbee-mall-vue3-app) | 前后端分离、Vue3、Vue-Router4、Vuex4、Vant3      |
| vue3-admin           | [vue3-admin in GitHub](https://github.com/newbee-ltd/vue3-admin)<br/>[vue3-admin in Gitee](https://gitee.com/newbee-ltd/vue3-admin) | 前后端分离、Vue3、Element-Plus、Vue-Router4、Vite      |

> 更多 Spring Boot 实战项目可以关注十三的另一个代码仓库 [spring-boot-projects](https://github.com/ZHENFENG13/spring-boot-projects)，该仓库中主要是 Spring Boot 的入门学习教程以及一些常用的 Spring Boot 实战项目教程，包括 Spring Boot 使用的各种示例代码，同时也包括一些实战项目的项目源码和效果展示，实战项目包括基本的 web 开发以及目前大家普遍使用的前后端分离实践项目等，后续会根据大家的反馈继续增加一些实战项目源码，摆脱各种 hello world 入门案例的束缚，真正的掌握 Spring Boot 开发。

关注公众号：**程序员十三**，回复"勾搭"进群交流。

![wx-gzh](https://newbee-mall.oss-cn-beijing.aliyuncs.com/wx-gzh/%E7%A8%8B%E5%BA%8F%E5%91%98%E5%8D%81%E4%B8%89-%E5%85%AC%E4%BC%97%E5%8F%B7.png)

## 开发及部署文档

#### Vue3 + Spring Boot 版本

- [开篇词：通关 Vue3 企业级项目开发，升职加薪快人一步](https://juejin.cn/book/6933939264455442444)
- [项目须知和课程约定](https://juejin.cn/book/6933939264455442444)
- [大势所趋：“前后端分离”开发模式](https://juejin.cn/book/6933939264455442444)
- [Vue3 简介及开发环境搭建](https://juejin.cn/book/6933939264455442444)
- [Vue3 组合 API 入口 Setup 浅析](https://juejin.cn/book/6933939264455442444)
- [Vue3 之响应式系统 API](https://juejin.cn/book/6933939264455442444)
- [Vue3 之生命周期钩子函数、提供注入](https://juejin.cn/book/6933939264455442444)
- [Vue3 性能和业务层面上的提升](https://juejin.cn/book/6933939264455442444)
- [Vite2 原理分析及简单插件编写](https://juejin.cn/book/6933939264455442444)
- [Vue-Router4 使用方法及路由原理](https://juejin.cn/book/6933939264455442444)
- [Vue3 实战项目启动篇](https://juejin.cn/book/6933939264455442444)
- [后端 API 开发技术选型之 Spring Boot](https://juejin.cn/book/6933939264455442444)
- [后端基础运行环境和开发工具准备](https://juejin.cn/book/6933939264455442444)
- [Spring Boot 项目搭建及快速上手](https://juejin.cn/book/6933939264455442444)
- [Spring Boot 实践之 Web 功能开发](https://juejin.cn/book/6933939264455442444)
- [Spring Boot 实践之文件上传处理](https://juejin.cn/book/6933939264455442444)
- [Spring Boot 实践之整合 MyBatis 操作数据库](https://juejin.cn/book/6933939264455442444)
- [Spring Boot 实践之整合 Lombok](https://juejin.cn/book/6933939264455442444)
- [Spring Boot 实践之整合 Swagger 生成接口文档](https://juejin.cn/book/6933939264455442444)
- [后端 API 项目启动和运行注意事项](https://juejin.cn/book/6933939264455442444)
- [接口参数处理和统一响应结果](https://juejin.cn/book/6933939264455442444)
- [API 接口开发实战之用户登录接口开发](https://juejin.cn/book/6933939264455442444)
- [API 接口开发实战之用户身份认证详解](https://juejin.cn/book/6933939264455442444)
- [API 接口开发实战之轮播图管理模块接口开发](https://juejin.cn/book/6933939264455442444)
- [API 接口开发实战之商品分类管理模块接口开发](https://juejin.cn/book/6933939264455442444)
- [API 接口开发实战之商品管理模块接口开发](https://juejin.cn/book/6933939264455442444)
- [API 接口开发实战之商品配置管理模块接口开发](https://juejin.cn/book/6933939264455442444)
- [API 接口开发实战之订单管理模块接口开发](https://juejin.cn/book/6933939264455442444)
- [前后端鉴权的四种方式](https://juejin.cn/book/6933939264455442444)
- [Vite2 + Vue3 + Element-plus 搭建管理后台项目](https://juejin.cn/book/6933939264455442444)
- [Vue3 实战之管理后台左右栏目布局](https://juejin.cn/book/6933939264455442444)
- [Vue3 实战之登录鉴权](https://juejin.cn/book/6933939264455442444)
- [Vue3 实战之首页大盘数据](https://juejin.cn/book/6933939264455442444)
- [Vue3 实战之首页配置](https://juejin.cn/book/6933939264455442444)
- [Vue3 实战之分类管理](https://juejin.cn/book/6933939264455442444)
- [Vue3 实战之商品管理](https://juejin.cn/book/6933939264455442444)
- [Vue3 实战之订单管理](https://juejin.cn/book/6933939264455442444)
- [Vue3 实战之会员管理、账户修改](https://juejin.cn/book/6933939264455442444)
- [pm2 实现一键部署云端服务器](https://juejin.cn/book/6933939264455442444)
- [常见问题汇总讲解](https://juejin.cn/book/6933939264455442444)

#### Vue 2.6 + Spring Boot 版本

- [向“全栈”进发！大型线上商城实战项目，Spring Boot + Vue 前后端分离版本的商城来了！](https://juejin.im/book/6844733826191589390)
- [项目须知和课程约定](https://juejin.im/book/6844733826191589390)
- [全栈开发！你必须要知道的“前后端分离”](https://juejin.im/book/6844733826191589390)
- [前端模块化的发展历史](https://juejin.im/book/6844733826191589390)
- [传统页面和单页面的权衡与抉择](https://juejin.im/book/6844733826191589390)
- [准备工作及基础环境搭建（后端）](https://juejin.im/book/6844733826191589390)
- [Spring Boot 项目初体验--项目搭建及启动](https://juejin.im/book/6844733826191589390)
- [项目编码简化利器！Spring Boot 整合 Lombok](https://juejin.im/book/6844733826191589390)
- [Lombok 插件问题处理](https://juejin.im/book/6844733826191589390)
- [商城后端项目启动和运行注意事项](https://juejin.im/book/6844733826191589390)
- [VSCode 的相关配置及插件介绍](https://juejin.im/book/6844733826191589390)
- [基础篇：Vue 指令](https://juejin.im/book/6844733826191589390)
- [基础篇：Vue全局API及生命周期介绍](https://juejin.im/book/6844733826191589390)
- [基础篇: CSS 预处理工具Less的介绍及使用](https://juejin.im/book/6844733826191589390)
- [Vue 脚手架工具 Vue-Cli 配置介绍](https://juejin.im/book/6844733826191589390)
- [Vue-Router 浅析原理及使用](https://juejin.im/book/6844733826191589390)
- [全局状态管理插件 Vuex 介绍及使用](https://juejin.im/book/6844733826191589390)
- [商城前端 H5 开发环境搭建及项目启动](https://juejin.im/book/6844733826191589390)
- [前后端交互文档利器！Spring Boot 整合 Swagger](https://juejin.im/book/6844733826191589390)
- [接口参数处理和统一响应结果](https://juejin.im/book/6844733826191589390)
- [口设计规范及接口调用实践](https://juejin.im/book/6844733826191589390)
- [商城开发实战-用户登录接口开发](https://juejin.im/book/6844733826191589390)
- [商城开发实战-用户身份认证详解](https://juejin.im/book/6844733826191589390)
- [商城开发实战-首页模块接口开发](https://juejin.im/book/6844733826191589390)
- [商城开发实战-分类模块接口开发](https://juejin.im/book/6844733826191589390)
- [商城开发实战-商品搜索模块接口开发](https://juejin.im/book/6844733826191589390)
- [商城开发实战-购物车模块接口开发](https://juejin.im/book/6844733826191589390)
- [商城开发实战-个人信息及收货地址接口开发](https://juejin.im/book/6844733826191589390)
- [商城开发实战-下单流程接口开发](https://juejin.im/book/6844733826191589390)
- [商城开发实战-订单处理流程详解](https://juejin.im/book/6844733826191589390)
- [商城移动端开发实战-新蜂商城登录注册页(前端鉴权)](https://juejin.im/book/6844733826191589390)
- [商城移动端开发实战-商城首页制作(轮播图、首页商品列表)](https://juejin.im/book/6844733826191589390)
- [商城移动端开发实战-商品分类页面制作(better-scrol的介绍及使用)](https://juejin.im/book/6844733826191589390)
- [商城移动端开发实战-商品列表页面制作(无限滚动加载)](https://juejin.im/book/6844733826191589390)
- [商城移动端开发实战-商品详情页面制作(Vuex 购物车数量全局管理)](https://juejin.im/book/6844733826191589390)
- [商城移动端开发实战-商城购物车页面制作(购物车页)](https://juejin.im/book/6844733826191589390)
- [商城移动端开发实战-地址管理页面及生成订单页面制作](https://juejin.im/book/6844733826191589390)
- [商城移动端开发实战-我的订单页面制作](https://juejin.im/book/6844733826191589390)
- [商城 pm2 一键部署云服务器](https://juejin.im/book/6844733826191589390)
- [课程总结](https://juejin.im/book/6844733826191589390)

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
