package Artinus.server.member.controller;

import Artinus.server.member.dto.MemberPostDto;
import Artinus.server.member.dto.MemberResponseDto;
import Artinus.server.member.mapper.MemberMapper;
import Artinus.server.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "회원 : 회원가입")
@RestController
@RequestMapping("/members")
@Validated
@RequiredArgsConstructor
        public class MemberController {

            private final MemberService memberService;
            private final MemberMapper mapper;

            @Operation(summary = "회원가입", description = "해당없음")
            @ApiResponse(code = 201, message = "created")
            @PostMapping("/signup")
            public ResponseEntity postMember(@Valid @RequestBody MemberPostDto memberPostDto) throws Exception {

                MemberResponseDto memberResponseDto = memberService.createMember(memberPostDto);

                return new ResponseEntity(memberResponseDto, HttpStatus.CREATED);
    }
}