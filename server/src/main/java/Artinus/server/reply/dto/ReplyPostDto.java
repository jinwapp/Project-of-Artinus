package Artinus.server.reply.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReplyPostDto {

    @ApiModelProperty(value = "필드 값", example = "1", required = true)
    private Long commentId;

    @ApiModelProperty(value = "필드 값", example = "test : reply", required = true)
    private String reply;
}
