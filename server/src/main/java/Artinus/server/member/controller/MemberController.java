package Artinus.server.member.controller;

import Artinus.server.member.dto.MemberPostDto;
import Artinus.server.member.dto.MemberResponseDto;
import Artinus.server.member.entity.Member;
import Artinus.server.member.mapper.MemberMapper;
import Artinus.server.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper mapper;

    /*
        회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity signupMember(@RequestBody MemberPostDto memberPostDto) {

        Member member = memberService.createMember(mapper.memberPostDtoToMember(memberPostDto));
        MemberResponseDto memberResponseDto = mapper.memberToMemberResponseDto(member);

        return new ResponseEntity(memberResponseDto, HttpStatus.CREATED);
    }
}
