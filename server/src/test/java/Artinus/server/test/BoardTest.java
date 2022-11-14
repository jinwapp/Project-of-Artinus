package Artinus.server.test;

import Artinus.server.config.aes256.AES256;
import Artinus.server.board.dto.BoardPostDto;
import Artinus.server.board.entity.Board;
import Artinus.server.board.repository.BoardRepository;
import Artinus.server.member.entity.Member;
import Artinus.server.member.repository.MemberRepository;
import Artinus.server.member.security.PrincipalDetails;
import com.google.gson.Gson;
import org.junit.jupiter.api.*;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BoardTest {

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


    @Test
    @Order(1)
    @DisplayName("게시글 생성 테스트")
    public void postBoardTest() throws Exception {

        //given
        Member member = new Member(1L, aes256.encrypt( "test@gmail.com"), aes256.encrypt("password"),"ROLE_USER");
        memberRepository.save(member);
        PrincipalDetails principalDetails = new PrincipalDetails(memberRepository.findById(1L).get());

        BoardPostDto boardPostDto = new BoardPostDto("test : title", "test : description");

        String content = gson.toJson(boardPostDto);

        //when
        ResultActions actions = mockMvc.perform(

                MockMvcRequestBuilders.post("/board/post")
                        .with(SecurityMockMvcRequestPostProcessors.user(principalDetails)) // @AuthenticationPrincipal 해결.
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        MvcResult result = actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(boardPostDto.getTitle()))
                .andExpect(jsonPath("$.description").value(boardPostDto.getDescription()))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }


    @Test
    @Order(2)
    @DisplayName("게시글 1개 조회 테스트")
    public void getBoardTest() throws Exception {

        //given
        Member member = new Member(1L, aes256.encrypt( "test@gmail.com"), aes256.encrypt("password"),"ROLE_USER");
        memberRepository.save(member);
        PrincipalDetails principalDetails = new PrincipalDetails(memberRepository.findById(1L).get());

        Board board = new Board(1L, "test : title", "test : description", member);
        boardRepository.save(board);

        //when
        ResultActions actions = mockMvc.perform(

                MockMvcRequestBuilders.get("/board/{boardId}",1)
                        .with(SecurityMockMvcRequestPostProcessors.user(principalDetails)) // @AuthenticationPrincipal 해결.
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        MvcResult result = actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(board.getTitle()))
                .andExpect(jsonPath("$.description").value(board.getDescription()))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }
}
