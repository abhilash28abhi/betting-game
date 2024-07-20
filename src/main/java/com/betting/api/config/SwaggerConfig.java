package com.betting.api.config;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;

public class SwaggerConfig {

    public static final String API_TITLE = "betting-service";
    public static final String API_DESCRIPTION = "Microservice containing the business logic for placing bets for players";
    public static final String API_DESCRIPTION_DEPRECATED = API_DESCRIPTION + " This API version has been deprecated.";

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("v1")
                .packagesToScan("com.betting.api.controller", "com.betting.api.dto")
                .addOpenApiCustomiser(versionApiCustomizer("1.0", true))
                .build();
    }

    private OpenApiCustomiser versionApiCustomizer(String version, boolean deprecated) {
        return openApi -> {
            Info v1Info = new Info()
                    .title(API_TITLE)
                    .description(deprecated ? API_DESCRIPTION_DEPRECATED : API_DESCRIPTION)
                    .version(version);
            openApi.setInfo(v1Info);
        };
    }
}
