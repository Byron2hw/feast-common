package com.feast.common.autoconfigure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author Byron
 * @date 2022/12/13 2:59 下午
 */
@Configuration(proxyBeanMethods = false)
@EnableOpenApi
public class SwaggerAutoconfigure {
    @Value("${spring.profiles.active:NA}")
    private String active;
    @Value("${feast.swagger.title:Restful APIS}")
    private String title;
    @Value("${feast.swagger.description:Restful APIS}")
    private String description;
    @Value("${feast.swagger.contact.name:feast}")
    private String contactName;
    @Value("${feast.swagger.contact.url:}")
    private String contactUrl;
    @Value("${feast.swagger.contact.email:}")
    private String contactEmail;
    @Value("${feast.swagger.version:1.0}")
    private String version;

    @Bean
    @ConditionalOnMissingBean(Docket.class)
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .enable("dev".equals(active))
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return (new ApiInfoBuilder()).title(title)
                .description(description)
                .version(version)
                .contact(new Contact(contactName, contactUrl, contactEmail))
                .build();
    }
}
