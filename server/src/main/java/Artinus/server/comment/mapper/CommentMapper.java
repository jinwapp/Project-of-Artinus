package Artinus.server.comment.mapper;

import Artinus.server.board.entity.Board;
import Artinus.server.comment.dto.CommentListResponseDto;
import Artinus.server.comment.dto.CommentPostDto;
import Artinus.server.comment.dto.CommentResponseDto;
import Artinus.server.comment.entity.Comment;
import Artinus.server.member.entity.Member;
import Artinus.server.member.security.PrincipalDetails;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    default Comment commentPostDtoToComment(CommentPostDto commentPostDto, PrincipalDetails principalDetails) {
        Comment comment = new Comment();
        Board board = new Board();
        Member member = new Member();

        board.setBoardId(commentPostDto.getBoardId());
        member.setMemberId(principalDetails.getMember().getMemberId());

        comment.setComment(commentPostDto.getComment());
        comment.setBoard(board);
        comment.setMember(member);

        return comment;
    }

    CommentResponseDto commentToCommentResponseDto(Comment comment);

    List<CommentListResponseDto> commentListToCommentListResponseDto(List<Comment> commentList);

}





















