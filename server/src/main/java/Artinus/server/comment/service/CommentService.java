package Artinus.server.comment.service;

import Artinus.server.comment.dto.CommentListResponseDto;
import Artinus.server.comment.dto.CommentPostDto;
import Artinus.server.comment.dto.CommentResponseDto;
import Artinus.server.comment.entity.Comment;
import Artinus.server.comment.mapper.CommentMapper;
import Artinus.server.comment.repository.CommentRepository;
import Artinus.server.member.security.PrincipalDetails;
import Artinus.server.pagination.MultiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper mapper;
    private final CommentRepository commentRepository;

    public CommentResponseDto createComment(CommentPostDto commentPostDto,
                                            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Comment comment = mapper.commentPostDtoToComment(commentPostDto, principalDetails);
        Comment response = commentRepository.save(comment);
        return mapper.commentToCommentResponseDto(response);
    }

    public MultiResponseDto<CommentListResponseDto> findBoard(long boardId, int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("COMMENT_ID").descending());
        Page<Comment> commentPage = commentRepository.findByBoardId(boardId, pageRequest);
        List<Comment> commentList = commentPage.getContent();
        List<CommentListResponseDto> commentListResponseDtoList = mapper.commentListToCommentListResponseDto(commentList);

        return new MultiResponseDto<>(commentListResponseDtoList, commentPage);
    }
}
