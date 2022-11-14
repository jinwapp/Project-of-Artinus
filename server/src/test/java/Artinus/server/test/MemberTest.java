package Artinus.server.test;

import Artinus.server.config.aes256.AES256;
import Artinus.server.member.dto.MemberPostDto;
import Artinus.server.member.entity.Member;
import Artinus.server.member.repository.MemberRepository;
import Artinus.server.member.security.PrincipalDetails;
import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Autowired
    private AES256 aes256;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    private MemberRepository memberRepository;


    @Test
    @DisplayName("회원가입 테스트")
    @Order(1)
    void postMemberTest() throws Exception {

        //given
        MemberPostDto memberPostDto = new MemberPostDto("test@gmail.com", "password");

        String content = gson.toJson(memberPostDto);

        //when
        ResultActions actions = mockMvc.perform(

                post("/members/signup")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        MvcResult result = actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(aes256.encrypt(memberPostDto.getEmail())))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());

    }

    @Test
    @Order(2)
    @DisplayName("로그인 테스트")
    void loginTest() throws Exception {

        //given
        Member member = new Member(1L, aes256.encrypt("test@gmail.com"), bCryptPasswordEncoder.encode("test@gmail.com"), "ROLE_USER");
        memberRepository.save(member);

        MemberPostDto memberPostDto = new MemberPostDto("test@gmail.com", "test@gmail.com");
        String content = gson.toJson(memberPostDto);


        //when
        ResultActions actions = mockMvc.perform(

                post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        MvcResult result = actions
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());

    }

    @Test
    @Order(3)
    @DisplayName("로그아웃 테스트")
    void logOutTest() throws Exception {

        //given
        Member member = new Member(1L, aes256.encrypt("test@gmail.com"), bCryptPasswordEncoder.encode("test@gmail.com"), "ROLE_USER");
        memberRepository.save(member);

        PrincipalDetails principalDetails = new PrincipalDetails(member);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //when
        ResultActions actions = mockMvc.perform(
                post("/logout")
        );

        //then
        MvcResult result = actions
                .andExpect(status().isOk())
                .andReturn();

    }
}
