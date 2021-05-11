package ir.aliakbarmoeini.support.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import ir.aliakbarmoeini.support.config.OpenApiConfig
import ir.aliakbarmoeini.support.dto.user.AuthenticationDto
import ir.aliakbarmoeini.support.dto.user.RegisterUserDto
import ir.aliakbarmoeini.support.dto.user.TokenDto
import ir.aliakbarmoeini.support.dto.user.UserDetailsDto
import ir.aliakbarmoeini.support.exception.AuthenticationException
import ir.aliakbarmoeini.support.exception.RequestBodyException
import ir.aliakbarmoeini.support.facade.JwtFacade
import ir.aliakbarmoeini.support.service.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/api")
@Tag(name = "Sign-up/Sign-in")
class AuthenticationController(
    val userService: UserService,
    val jwtFacade: JwtFacade,
    val authenticationManager: AuthenticationManager,
) {

    @PostMapping("getToken")
    @Tag(name = OpenApiConfig.UNSECURED)
    @Operation(summary = "Create Token", description = "create Jwt with username and password")
    fun createAuthenticationToken(
        @Valid @RequestBody authenticationDto: AuthenticationDto,
        binding: BindingResult
    ): TokenDto {
        if (binding.hasErrors())
            throw RequestBodyException(binding)
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    authenticationDto.username,
                    authenticationDto.password
                )
            )
        } catch (ex: DisabledException) {
            throw AuthenticationException("USER_DISABLED", ex)
        } catch (ex: BadCredentialsException) {
            throw AuthenticationException("INVALID_CREDENTIALS", ex)
        }
        val user = userService.loadUserByUsername(authenticationDto.username)
        val token = jwtFacade.generateToken(user)
        return TokenDto(token)
    }

    @GetMapping("refreshToken")
    @Operation(summary = "Refresh Token", description = "get new token in response body with new expiration date")
    fun refreshToken(request: HttpServletRequest): ResponseEntity<TokenDto> {
        val oldToken = request.getHeader("Authorization").substring(7)
        return if (jwtFacade.canTokenBeRefreshed(oldToken))
            ResponseEntity.ok(TokenDto(jwtFacade.refreshToken(oldToken)))
        else
            ResponseEntity.badRequest().body(null)
    }

    @PostMapping("register")
    @Tag(name = OpenApiConfig.UNSECURED)
    @Operation(summary = "Sign-up", description = "register new user")
    fun registerUser(
        @Valid @RequestBody registerUserDto: RegisterUserDto,
        binding: BindingResult
    ): ResponseEntity<UserDetailsDto> {
        if (binding.hasErrors())
            throw RequestBodyException(binding)
        val saved = userService.save(registerUserDto)
        return ResponseEntity.created(URI.create("/api/users/${saved.id}")).body(saved)
    }
}