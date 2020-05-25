/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.config;

import ltd.newbee.mall.entity.MallUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket api() {

        ParameterBuilder tokenParam = new ParameterBuilder();
        List<Parameter> swaggerParams = new ArrayList<Parameter>();
        tokenParam.name("token").description("用户认证信息")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build(); //header中的ticket参数非必填，传空也可以
        swaggerParams.add(tokenParam.build());    //根据每个方法名也知道当前方法在设置什么参数

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .ignoredParameterTypes(MallUser.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ltd.newbee.mall.api"))// 修改为自己的 controller 包路径
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(swaggerParams);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("新蜂商城接口文档")
                .description("swagger接口文档")
                .version("2.0")
                .build();
    }
}