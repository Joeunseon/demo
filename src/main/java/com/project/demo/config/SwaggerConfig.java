package com.project.demo.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Demo API", version = "1.0", description = "Demo 프로젝트 API 문서"))
public class SwaggerConfig {

}
