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
public class CommentTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private AES256 aes256;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("댓글 생성 테스트")
    @Order(1)
    public void postCommentTest() throws Exception {

        //given
        Member member = new Member(1L, aes256.encrypt("test@gmail.com"), aes256.encrypt("password"), "ROLE_USER");
        memberRepository.save(member);
        PrincipalDetails principalDetails = new PrincipalDetails(memberRepository.findById(1L).get());

        Board board = new Board(1L, "test : title", "test : description", member);
        boardRepository.save(board);

        CommentPostDto commentPostDto = new CommentPostDto(1L, "test : comment");

        String content = gson.toJson(commentPostDto);

        //when
        ResultActions actions = mockMvc.perform(

                MockMvcRequestBuilders.post("/comment/post")
                        .with(SecurityMockMvcRequestPostProcessors.user(principalDetails)) // @AuthenticationPrincipal 해결.
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        MvcResult result = actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.comment").value(commentPostDto.getComment()))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }


    @Test
    @DisplayName("댓글 조회 테스트")
    @Order(2)
    public void getCommentTest() throws Exception {

        //given
        Member member = new Member(1L, aes256.encrypt( "test@gmail.com"), aes256.encrypt("password"),"ROLE_USER");
        memberRepository.save(member);
        PrincipalDetails principalDetails = new PrincipalDetails(memberRepository.findById(1L).get());

        Board board = new Board(1L, "test : title", "test : description", member);
        boardRepository.save(board);

        Comment comment1 = new Comment(1L, "test : comment", board, member);
        commentRepository.save(comment1);

        Comment comment2 = new Comment(2L, "test : comment", board, member);
        commentRepository.save(comment2);

        //when
        ResultActions actions = mockMvc.perform(

                MockMvcRequestBuilders.get("/comment/{boardId}",1)
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
