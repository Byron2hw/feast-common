package com.feast.common.autoconfigure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author Byron
 * @date 2022/12/13 2:59 下午
 */
@Configuration
@EnableOpenApi
public class SwaggerAutoconfigure {
    @Value("${spring.profiles.active:NA}")
    private String active;

    @Bean
    @ConditionalOnMissingBean(Docket.class)
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .enable("dev".equals(active))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
