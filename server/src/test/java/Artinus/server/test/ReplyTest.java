package Artinus.server.test;

import Artinus.server.board.entity.Board;
import Artinus.server.board.repository.BoardRepository;
import Artinus.server.comment.dto.CommentPostDto;
import Artinus.server.comment.entity.Comment;
import Artinus.server.comment.repository.CommentRepository;
import Artinus.server.config.aes256.AES256;
import Artinus.server.member.entity.Member;
import Artinus.server.member.repository.MemberRepository;
import Artinus.server.member.security.PrincipalDetails;
import Artinus.server.reply.dto.ReplyPostDto;
import Artinus.server.reply.entity.Reply;
import Artinus.server.reply.repository.ReplyRepository;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReplyTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private AES256 aes256;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @DisplayName("대댓글 생성 테스트")
    @Order(1)
    public void postReplyTest() throws Exception {

        //given
        Member member = new Member(1L, aes256.encrypt( "test@gmail.com"), aes256.encrypt("password"),"ROLE_USER");
        memberRepository.save(member);
        PrincipalDetails principalDetails = new PrincipalDetails(memberRepository.findById(1L).get());

        Comment comment = new Comment();
        commentRepository.save(comment);

        ReplyPostDto replyPostDto = new ReplyPostDto(1L, "test : reply");

        String content = gson.toJson(replyPostDto);

        //when
        ResultActions actions = mockMvc.perform(

                MockMvcRequestBuilders.post("/reply/post")
                        .with(SecurityMockMvcRequestPostProcessors.user(principalDetails)) // @AuthenticationPrincipal 해결.
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        MvcResult result = actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.reply").value(replyPostDto.getReply()))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }


    @Test
    @DisplayName("대댓글 조회 테스트")
    @Order(2)
    public void getCommentTest() throws Exception {

        //given
        Member member = new Member(1L, aes256.encrypt( "test@gmail.com"), aes256.encrypt("password"),"ROLE_USER");
        memberRepository.save(member);
        PrincipalDetails principalDetails = new PrincipalDetails(memberRepository.findById(1L).get());

        Board board = new Board(1L, "test : title", "test : description", member);
        boardRepository.save(board);

        Comment comment = new Comment(1L,"test : comment", board,member);
        commentRepository.save(comment);

        Reply reply1 = new Reply(1L,"test2 : reply1",comment,member);
        replyRepository.save(reply1);

        Reply reply2 = new Reply(2L,"test2 : reply2",comment,member);
        replyRepository.save(reply2);

        //when
        ResultActions actions = mockMvc.perform(

                MockMvcRequestBuilders.get("/reply/{commentId}",1)
                        .with(SecurityMockMvcRequestPostProcessors.user(principalDetails)) // @AuthenticationPrincipal 해결.
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        MvcResult result = actions
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }
}