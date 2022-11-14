package Artinus.server.board.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardResponseDto {

    private Long boardId;

    private String title;

    private String description;

    private LocalDateTime creationDate;

    private LocalDateTime lastEditDate;

}
