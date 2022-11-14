package Artinus.server.comment.controller;

import Artinus.server.comment.dto.CommentListResponseDto;
import Artinus.server.comment.dto.CommentPostDto;
import Artinus.server.comment.dto.CommentResponseDto;
import Artinus.server.comment.service.CommentService;
import Artinus.server.member.security.PrincipalDetails;
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

@Api(tags = "댓글 : 생성, 조회")
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 생성", description = "토큰 필요함")
    @ApiResponse(code = 201, message = "created")
    @PostMapping("post")
    public ResponseEntity postComment(@RequestBody CommentPostDto commentPostDto,
                                      @AuthenticationPrincipal PrincipalDetails principalDetails) {

        CommentResponseDto commentResponseDto = commentService.createComment(commentPostDto, principalDetails);

        return new ResponseEntity<>(commentResponseDto,HttpStatus.CREATED);

    }
    /*
        BoardId로 Comment 조회
     */
    @Operation(summary = "댓글 조회", description = "해당없음")
    @ApiResponse(code = 200, message = "Ok")
    @GetMapping("/{board-id}")
    public ResponseEntity getComment(@PathVariable("board-id") long boardId,
                                     @Positive @RequestParam(defaultValue = "1") int page,
                                     @Positive @RequestParam(defaultValue = "10") int size) {

        MultiResponseDto<CommentListResponseDto> multiResponseDto = commentService.findBoard(boardId, page - 1, size);

        return new ResponseEntity<>(multiResponseDto,HttpStatus.OK);
    }
}