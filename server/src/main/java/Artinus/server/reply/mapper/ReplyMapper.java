package Artinus.server.reply.mapper;

import Artinus.server.board.entity.Board;
import Artinus.server.comment.dto.CommentListResponseDto;
import Artinus.server.comment.entity.Comment;
import Artinus.server.member.entity.Member;
import Artinus.server.member.security.PrincipalDetails;
import Artinus.server.reply.dto.ReplyListResponseDto;
import Artinus.server.reply.dto.ReplyPostDto;
import Artinus.server.reply.dto.ReplyResponseDto;
import Artinus.server.reply.entity.Reply;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReplyMapper {
    default Reply replyPostDtoToReply(ReplyPostDto replyPostDto, PrincipalDetails principalDetails) {
        Reply reply = new Reply();
        Comment comment = new Comment();
        Member member = new Member();

        comment.setCommentId(replyPostDto.getCommentId());
        member.setMemberId(principalDetails.getMember().getMemberId());

        reply.setReply(replyPostDto.getReply());
        reply.setComment(comment);
        reply.setMember(member);

        return reply;
    }

    ReplyResponseDto replyToReplyResponseDto(Reply comment);

    List<ReplyListResponseDto> replyListToReplyListResponseDto(List<Reply> replyList);

}