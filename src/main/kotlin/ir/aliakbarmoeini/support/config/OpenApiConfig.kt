package ir.aliakbarmoeini.support.config

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import org.springdoc.core.customizers.OperationCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.HandlerMethod

@Configuration
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
class OpenApiConfig {

    companion object {
        const val UNSECURED = "security.open"
    }

    @Bean
    fun customOpenAPI(): OpenAPI = OpenAPI()
        .addSecurityItem(SecurityRequirement().addList("bearerAuth"))
        .components(Components())
        .info(
            Info()
                .title("Customer Support System")
                .description("Customer Support System RESTFul Api")
                .contact(
                    Contact()
                        .name("Ali Akbar Moeini")
                        .email("hi@aliakbarmoeini")
                )
                .version("1.0")
        )


    @Bean
    fun customizer(): OperationCustomizer =
        OperationCustomizer { operation: Operation, _: HandlerMethod? ->
            val tags = operation.tags
            if (tags != null && tags.contains(UNSECURED)) {
                operation.security = emptyList()
                operation.tags = tags
                    .filter { it != UNSECURED }
                    .toList()
            }
            operation
        }
}