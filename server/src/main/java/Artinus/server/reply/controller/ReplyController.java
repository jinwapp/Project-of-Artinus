package Artinus.server.reply.controller;

import Artinus.server.member.security.PrincipalDetails;
import Artinus.server.reply.dto.ReplyListResponseDto;
import Artinus.server.reply.dto.ReplyPostDto;
import Artinus.server.reply.dto.ReplyResponseDto;
import Artinus.server.reply.service.ReplyService;
import Artinus.server.pagination.MultiResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@Api(tags = "대댓글 : 생성, 조회")
@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @Operation(summary = "대댓글 생성", description = "토큰 필요함")
    @ApiResponse(code = 201, message = "created")
    @PostMapping("post")
    public ResponseEntity postReply(@RequestBody ReplyPostDto replyPostDto,
                                    @AuthenticationPrincipal PrincipalDetails principalDetails) {

        ReplyResponseDto replyResponseDto = replyService.createReply(replyPostDto,principalDetails);

        return new ResponseEntity<>(replyResponseDto,HttpStatus.CREATED);
    }

    /*
    CommentId로 Reply 조회
    */
    @Operation(summary = "대댓글 조회", description = "해당없음")
    @ApiResponse(code = 200, message = "Ok")
    @GetMapping("/{comment-id}")
    public ResponseEntity getReply(@PathVariable("comment-id") long commentId,
                                     @Positive @RequestParam(defaultValue = "1") int page,
                                     @Positive @RequestParam(defaultValue = "10") int size) {

        MultiResponseDto<ReplyListResponseDto> multiResponseDto = replyService.findBoard(commentId, page - 1, size);

        return new ResponseEntity<>(multiResponseDto,HttpStatus.OK);
    }
}