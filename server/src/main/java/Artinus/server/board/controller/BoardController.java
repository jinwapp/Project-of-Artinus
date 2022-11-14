package Artinus.server.board.controller;

import Artinus.server.board.dto.BoardDetailResponseDto;
import Artinus.server.board.dto.BoardPostDto;
import Artinus.server.board.dto.BoardResponseDto;
import Artinus.server.board.entity.Board;
import Artinus.server.board.mapper.BoardMapper;
import Artinus.server.board.service.BoardService;
import Artinus.server.member.security.PrincipalDetails;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@Api(tags = "게시물 : 생성, 조회")
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**
     * 게시글 생성 // 토큰이 필요함.
     */
    @Operation(summary = "게시물 생성", description = "토큰이 필요하고 ROLE_USER 인가")
    @ApiResponse(code = 201, message = "created")
    @PostMapping("/post")
    public ResponseEntity postBoard(@RequestBody BoardPostDto boardPostDto,
                                    @AuthenticationPrincipal PrincipalDetails principalDetails) {

        BoardResponseDto boardResponseDto = boardService.createBoard(boardPostDto,principalDetails);

        return new ResponseEntity(boardResponseDto, HttpStatus.CREATED);
    }

    /**
     * 게시글 1개 조회 // 토큰이 필요함.
     */
    @Operation(summary = "게시물 1개 조회", description = "토큰이 필요하고 ROLE_USER 인가")
    @ApiResponse(code = 200, message = "Ok")
    @GetMapping("/{board-id}")
    public ResponseEntity getBoard(@PathVariable("board-id") @Positive long boardId) {

        BoardResponseDto boardResponseDto = boardService.findBoard(boardId);

        return new ResponseEntity<>(boardResponseDto, HttpStatus.OK);
    }

}