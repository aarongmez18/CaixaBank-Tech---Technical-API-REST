package api.caixaTech.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI caixaTechOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CaixaTech Technical API REST.")
                        .description("Simple REST API to manage personal loan requests.")
                        .version("1.0.0")
                        .license(new License().name("MIT")));
    }
}
