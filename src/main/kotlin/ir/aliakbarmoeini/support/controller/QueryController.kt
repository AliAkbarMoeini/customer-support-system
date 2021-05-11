package ir.aliakbarmoeini.support.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.tags.Tags
import ir.aliakbarmoeini.support.dto.query.CreateQueryDto
import ir.aliakbarmoeini.support.dto.query.QueryDetailsDto
import ir.aliakbarmoeini.support.dto.query.UpdateQueryDto
import ir.aliakbarmoeini.support.exception.RequestBodyException
import ir.aliakbarmoeini.support.service.query.QueryService
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class QueryController(val queryService: QueryService) {

    @PostMapping("/users/{userId}/queries")
    @Operation(summary = "Create Query")
    @Tag(name = "User Endpoints")
    fun submitQuery(
        @PathVariable userId: Long,
        @Valid @RequestBody createQueryDto: CreateQueryDto,
        binding: BindingResult
    ): ResponseEntity<QueryDetailsDto> {
        if (binding.hasErrors())
            throw RequestBodyException(binding)
        val queryDetailsDto = queryService.save(userId, createQueryDto)
        return ResponseEntity.created(URI.create("/api/users/$userId/queries/${queryDetailsDto.id}"))
            .body(queryDetailsDto)
    }

    @GetMapping("/users/{userId}/queries/{queryId}")
    @Operation(summary = "Get Single Query")
    @Tags(Tag(name = "Admin Endpoints"), Tag(name = "Supporter Endpoints"), Tag(name = "User Endpoints"))
    fun getQuery(@PathVariable userId: Long, @PathVariable queryId: Long): QueryDetailsDto =
        queryService.get(userId, queryId)

    @GetMapping("/users/{userId}/queries")
    @Operation(summary = "Get All", description = "Get All Queries of User")
    @Tags(Tag(name = "Admin Endpoints"), Tag(name = "Supporter Endpoints"), Tag(name = "User Endpoints"))
    fun getAllByUser(@PathVariable userId: Long): List<QueryDetailsDto> =
        queryService.getAllByUserId(userId)

    @PutMapping("/admin/queries/{queryId}")
    @Operation(summary = "Update Query")
    @Tags(Tag(name = "Admin Endpoints"), Tag(name = "Supporter Endpoints"))
    fun updateQuery(
        @PathVariable queryId: Long,
        @RequestBody updateQueryDto: UpdateQueryDto
    ): QueryDetailsDto = queryService.update(queryId, updateQueryDto)

    @DeleteMapping("/admin/queries/{queryId}")
    @Tags(Tag(name = "Admin Endpoints"))
    @Operation(summary = "Delete Query")
    fun deleteQuery(@PathVariable queryId: Long): ResponseEntity<Unit> {
        queryService.deleteById(queryId)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/admin/queries")
    @Operation(summary = "Get All Queries")
    @Tags(Tag(name = "Admin Endpoints"), Tag(name = "Supporter Endpoints"))
    fun getAll(): List<QueryDetailsDto> =
        queryService.getAll()

}


