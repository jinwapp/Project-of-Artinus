package Artinus.server.comment.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentPostDto {

    @ApiModelProperty(value = "필드 값", example = "1", required = true)
    private Long boardId;

    @ApiModelProperty(value = "필드 값", example = "test : comment", required = true)
    private String comment;
}
