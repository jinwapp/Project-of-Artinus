package Artinus.server.reply.dto;

import Artinus.server.member.dto.GetMemberResponseDto;
import Artinus.server.member.dto.MemberResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReplyListResponseDto {

    private Long replyId;

    private String reply;

    private GetMemberResponseDto member;

    private LocalDateTime creationDate;

    private LocalDateTime lastEditDate;
}
