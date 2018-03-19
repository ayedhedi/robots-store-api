package lu.rbc.robotsstoreapi.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by Hedi Ayed on 27/02/2018.
 *
 *
 * --------------------
 *
 * @author Hedi Ayed
 */
@Configuration
@EnableSwagger2
public class SwaggerConfigs {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("lu.rbc.robotsstoreapi.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "ROBOT SHOP STORE API",
                "This API has been developed in order to answer to the RBC programming assignment.",
                "API Final Round",
                "N.A.",
                new Contact("Hedi Ayed", "https://www.rbcits.com", "ayed.hedi@gmail.com"),
                "Apache 2.0", "https://www.apache.org/licenses/LICENSE-2.0", Collections.emptyList());
    }
}
