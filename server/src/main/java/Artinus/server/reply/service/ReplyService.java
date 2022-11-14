package Artinus.server.reply.service;

import Artinus.server.member.security.PrincipalDetails;
import Artinus.server.reply.dto.ReplyListResponseDto;
import Artinus.server.reply.dto.ReplyPostDto;
import Artinus.server.reply.dto.ReplyResponseDto;
import Artinus.server.reply.entity.Reply;
import Artinus.server.reply.mapper.ReplyMapper;
import Artinus.server.reply.repository.ReplyRepository;
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
public class ReplyService {

    private final ReplyMapper mapper;
    private final ReplyRepository replyRepository;

    public ReplyResponseDto createReply(ReplyPostDto replyPostDto,
                                        @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Reply reply = mapper.replyPostDtoToReply(replyPostDto, principalDetails);
        Reply response = replyRepository.save(reply);
        return mapper.replyToReplyResponseDto(response);
    }

    public MultiResponseDto<ReplyListResponseDto> findBoard(long commentId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("REPLY_ID").descending());
        Page<Reply> replyPage = replyRepository.findByCommentId(commentId, pageRequest);
        List<Reply> replyList = replyPage.getContent();
        List<ReplyListResponseDto> replyListResponseDtoList = mapper.replyListToReplyListResponseDto(replyList);

        return new MultiResponseDto<>(replyListResponseDtoList, replyPage);
    }
}
