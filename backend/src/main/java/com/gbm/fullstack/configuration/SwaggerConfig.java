package com.gbm.fullstack.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.models.parameters.Parameter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2 //enables SpringFox support for Swagger 2
public class SwaggerConfig {
	@Bean
	public Docket api(){
    	//Adding Header
        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder.name("X-GBM-TOKEN")
        .description("token value")
        .modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        List<springfox.documentation.service.Parameter> aParameters = new ArrayList<>();
        aParameters.add(aParameterBuilder.build());
        return new Docket(DocumentationType.SWAGGER_2)
        			.select()
                    .apis(RequestHandlerSelectors.basePackage("com.gbm.fullstack.controller"))
        			.paths(PathSelectors.regex("/v1/.*"))
        			.build()
        			.apiInfo(apiInfo())
        			.pathMapping("")
        			.globalOperationParameters(aParameters);
   }
	
	private ApiInfo apiInfo() {
		
		return new ApiInfoBuilder()
				.title("Welcome to the GBM Full-stack Programmer Information Center")
	            .description("Topics in this Information Center provide information to program Products Warehouse ")
	            .version("1.0.0")
	            .build();
	   }

}
