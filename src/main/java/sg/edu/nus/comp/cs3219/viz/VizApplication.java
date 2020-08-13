package sg.edu.nus.comp.cs3219.viz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//This are the things needed for swagger ui.
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@SpringBootApplication
@EnableSwagger2
public class VizApplication {

    public static void main(String[] args) {
        SpringApplication.run(VizApplication.class, args);
    }
    @Bean
    public Docket productApi() {
       return new Docket(DocumentationType.SWAGGER_2).select()
          .apis(RequestHandlerSelectors.basePackage("sg.edu.nus.comp.cs3219.viz")).build();
    }
}
