package Artinus.server.board.mapper;

import Artinus.server.board.dto.BoardPostDto;
import Artinus.server.board.dto.BoardResponseDto;
import Artinus.server.board.entity.Board;
import Artinus.server.comment.entity.Comment;
import Artinus.server.member.entity.Member;
import Artinus.server.member.security.PrincipalDetails;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BoardMapper {
    default Board boardPostDtoToBoard(BoardPostDto boardPostDto, PrincipalDetails principalDetails) {
        Board board = new Board();
        Member member = new Member();

        member.setMemberId(principalDetails.getMember().getMemberId());

        board.setTitle(boardPostDto.getTitle());
        board.setDescription(boardPostDto.getDescription());
        board.setMember(member);

        return board;
    }

    BoardResponseDto boardToBoardResponseDto(Board board);

}