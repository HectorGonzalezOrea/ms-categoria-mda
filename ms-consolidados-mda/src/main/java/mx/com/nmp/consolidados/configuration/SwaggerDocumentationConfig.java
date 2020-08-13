package mx.com.nmp.consolidados.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-22T02:47:50.165Z")

@Configuration
public class SwaggerDocumentationConfig {

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Ajuste de Precios Consolidados.")
            .description("### Descripción   API que permite realizar el ajuste de precios consolidados por medio de un archivo generado por el área de gestión de portafolios con productos y su respectivo precio a actualizar.     Antes de usar este recurso, el consumidor deberá ser autorizado. Para ello el consumidor enviará una llave en el encabezado HTTP. Ejemplo:   * `X-API-KEY: eyJ4NXQjUzI1NiI6IkFTS1ESG42`    ")
            .license("Nacional Monte de Piedad 2019")
            .licenseUrl("https://www.montepiedad.com.mx/portal/storage/Aviso_de_Privacidad_MAR19.pdf")
            .termsOfServiceUrl("")
            .version("1.0.0")
            .contact(new Contact("","", "sgonzalez@spsolutions.com.mx"))
            .build();
    }

    @Bean
    public Docket customImplementation(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    .apis(RequestHandlerSelectors.basePackage("mx.com.nmp.consolidados.api"))
                    .build()
                .directModelSubstitute(org.threeten.bp.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(org.threeten.bp.OffsetDateTime.class, java.util.Date.class)
                .apiInfo(apiInfo());
    }

}
