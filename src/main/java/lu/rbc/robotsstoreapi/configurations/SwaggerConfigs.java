package lu.rbc.robotsstoreapi.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
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
                .build();
    }
}
