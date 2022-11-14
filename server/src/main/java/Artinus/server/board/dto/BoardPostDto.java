package Artinus.server.board.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class BoardPostDto {

    @ApiModelProperty(value = "필드 값", example = "test : title", required = true)
    private final String title;

    @ApiModelProperty(value = "필드 값", example = "test : description", required = true)
    private final String description;
}
