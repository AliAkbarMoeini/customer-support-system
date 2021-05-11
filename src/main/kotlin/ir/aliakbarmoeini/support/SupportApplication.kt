package ir.aliakbarmoeini.support

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootApplication
class SupportApplication {
    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

}


fun main(args: Array<String>) {
    runApplication<SupportApplication>(*args)
}

