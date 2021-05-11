package ir.aliakbarmoeini.support.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.tags.Tags
import ir.aliakbarmoeini.support.dto.user.UserDetailsDto
import ir.aliakbarmoeini.support.service.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class UserController(val userService: UserService) {

    @GetMapping("/users/{userId}")
    @Operation(summary = "User Detail", description = "get user details")
    @Tags(Tag(name = "User Endpoints"), Tag(name = "Admin Endpoints"))
    fun getById(@PathVariable userId: Long): UserDetailsDto = userService.getById(userId)


    @GetMapping("/admin/users")
    @Operation(summary = "Get All Users")
    @Tag(name = "Admin Endpoints")
    fun getAll(): List<UserDetailsDto> = userService.getAll()


    @PutMapping("/admin/users/{userId}")
    @Operation(summary = "Change User Roles")
    @Tag(name = "Admin Endpoints")
    fun changeRole(@PathVariable userId: Long, roles: Array<String>): ResponseEntity<Unit> {
        userService.updateUserRoles(userId, roles)
        return ResponseEntity.ok().build()
    }
}