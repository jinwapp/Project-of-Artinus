package Artinus.server.board.dto;

import Artinus.server.comment.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoardDetailResponseDto {
    private List<Comment> commentList;
}
