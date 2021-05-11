package ir.aliakbarmoeini.support.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.tags.Tags
import ir.aliakbarmoeini.support.dto.query.CreateReplyDto
import ir.aliakbarmoeini.support.dto.query.ReplyDetailsDto
import ir.aliakbarmoeini.support.service.query.ReplyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/users/{userId}/queries/{queryId}/replies")
class ReplyController(val replyService: ReplyService) {

    @PostMapping
    @Operation(summary = "Submit Query Reply")
    @Tags(Tag(name = "User Endpoints"), Tag(name = "Supporter Endpoints"))
    fun submitReply(
        @PathVariable userId: Long,
        @PathVariable queryId: Long,
        @RequestBody createReplyDto: CreateReplyDto
    ): ResponseEntity<ReplyDetailsDto> {
        val replyDetailsDto = replyService.submitReply(userId, queryId, createReplyDto)
        return ResponseEntity.created(URI.create("/api/users/$userId/queries/$queryId/replies/${replyDetailsDto.id}"))
            .body(replyDetailsDto)
    }

    @GetMapping
    @Operation(summary = "Get All Replies of Query")
    @Tags(Tag(name = "User Endpoints"), Tag(name = "Supporter Endpoints"), Tag(name = "Admin Endpoints"))
    fun getAllReplies(@PathVariable userId: Long, @PathVariable queryId: Long): List<ReplyDetailsDto> =
        replyService.getAll(userId, queryId)


}