package Artinus.server.board.service;

import Artinus.server.board.dto.BoardDetailResponseDto;
import Artinus.server.board.dto.BoardPostDto;
import Artinus.server.board.dto.BoardResponseDto;
import Artinus.server.board.entity.Board;
import Artinus.server.board.mapper.BoardMapper;
import Artinus.server.board.repository.BoardRepository;
import Artinus.server.comment.entity.Comment;
import Artinus.server.comment.repository.CommentRepository;
import Artinus.server.exception.CustomException;
import Artinus.server.member.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper mapper;
    private final BoardRepository boardRepository;

    public BoardResponseDto createBoard(BoardPostDto boardPostDto,
                                        @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Board board = mapper.boardPostDtoToBoard(boardPostDto, principalDetails);
        Board response = boardRepository.save(board);
        return mapper.boardToBoardResponseDto(response);
    }

    public BoardResponseDto findBoard(long boardId) {

        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board response = optionalBoard.orElseThrow(() -> new CustomException("Board Not Exists", HttpStatus.NOT_FOUND));

        return mapper.boardToBoardResponseDto(response);
    }
}