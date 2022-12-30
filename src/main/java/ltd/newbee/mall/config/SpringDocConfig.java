package ltd.newbee.mall.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    // 注意事项：可以在Swagger UI页面中的搜索框中输入/v3/api-docs、/v3/api-docs/mallGroup、/v3/api-docs/adminGroup查看所有接口、商城端接口和后台管理端接口。

    // 扫描路径
    private static final String adminBasePackage = "ltd.newbee.mall.api.admin";
    private static final String mallBasePackage = "ltd.newbee.mall.api.mall";
    // 请求头名称
    private static final String headerName = "token";

    @Bean
    public GroupedOpenApi adminGroup() {
        return GroupedOpenApi.builder()
                .group("adminGroup")
                .addOperationCustomizer((operation, handlerMethod) -> {
                    operation.addSecurityItem(new SecurityRequirement().addList(headerName));
                    return operation;
                })
                .packagesToScan(adminBasePackage)
                .build();
    }

    @Bean
    public GroupedOpenApi mallGroup() {
        return GroupedOpenApi.builder()
                .group("mallGroup")
                .addOperationCustomizer((operation, handlerMethod) -> {
                    operation.addSecurityItem(new SecurityRequirement().addList(headerName));
                    return operation;
                })
                .packagesToScan(mallBasePackage)
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        Components components = new Components();
        // 登录认证token
        components.addSecuritySchemes(headerName,
                new SecurityScheme()
                        .type(SecurityScheme.Type.APIKEY)
                        .scheme("basic")
                        .name(headerName)
                        .in(SecurityScheme.In.HEADER)
                        .description("请求头")
        );
        return new OpenAPI()
                .components(components)
                .info(apiInfo());
    }

    private Info apiInfo() {
        Contact contact = new Contact();
        contact.setEmail("2449207463@qq.com");
        contact.setName("程序员十三");
        contact.setUrl("https://juejin.cn/user/3808363978174302");
        return new Info()
                .title("新蜂商城接口文档")
                .description("swagger接口文档")
                .version("3.0")
                .contact(contact);
    }
}
