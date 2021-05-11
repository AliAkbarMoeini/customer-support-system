package ir.aliakbarmoeini.support.config

import ir.aliakbarmoeini.support.config.jwt.JwtAuthorizationFilter
import ir.aliakbarmoeini.support.config.jwt.JwtUnauthorizedResponseAuthenticationEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component

@Component
class SecurityConfig(
    val jwtAuthorizationFilter: JwtAuthorizationFilter,
    val unauthorizedEntryPoint: JwtUnauthorizedResponseAuthenticationEntryPoint
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.cors().and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint)
            .and()
            .authorizeRequests()
            .antMatchers("/api/register", "/api/getToken").permitAll()
            .antMatchers("/api/refreshToken", "/api/users/**").authenticated()
            .antMatchers(HttpMethod.PUT, "/api/admin/queries/**").hasAnyRole("SUPPORTER", "ADMIN")
            .antMatchers(HttpMethod.GET, "/api/admin/queries").hasAnyRole("SUPPORTER", "ADMIN")
            .antMatchers("/api/admin/**").hasAnyRole("ADMIN")

    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager? {
        return super.authenticationManagerBean()
    }
}