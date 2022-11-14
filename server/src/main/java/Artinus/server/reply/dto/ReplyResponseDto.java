package Artinus.server.reply.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReplyResponseDto {

    private Long replyId;

    private String reply;

    private LocalDateTime creationDate;

    private LocalDateTime lastEditDate;

}
