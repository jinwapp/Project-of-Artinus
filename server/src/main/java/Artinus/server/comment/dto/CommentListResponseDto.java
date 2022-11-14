package Artinus.server.comment.dto;

import Artinus.server.member.dto.GetMemberResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentListResponseDto {

    private Long commentId;

    private String comment;

    private GetMemberResponseDto member;

    private LocalDateTime creationDate;

    private LocalDateTime lastEditDate;
}
