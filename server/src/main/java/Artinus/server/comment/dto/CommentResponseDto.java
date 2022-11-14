package Artinus.server.comment.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDto {

    private Long commentId;

    private String comment;

    private LocalDateTime creationDate;

    private LocalDateTime lastEditDate;

}
