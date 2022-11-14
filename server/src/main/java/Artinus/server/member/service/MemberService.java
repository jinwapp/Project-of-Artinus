package Artinus.server.member.service;

import Artinus.server.config.aes256.AES256;
import Artinus.server.exception.CustomException;
import Artinus.server.member.dto.MemberPostDto;
import Artinus.server.member.dto.MemberResponseDto;
import Artinus.server.member.entity.Member;
import Artinus.server.member.mapper.MemberMapper;
import Artinus.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper mapper;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AES256 aes256;

    public MemberResponseDto createMember(MemberPostDto memberPostDto) throws Exception {

        Member member = mapper.memberPostDtoToMember(memberPostDto);
        checkExistsEmail(aes256.encrypt(member.getEmail()));

        member.setEmail(aes256.encrypt(member.getEmail()));
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setRoles("ROLE_USER");

        Member response = memberRepository.save(member);
        return mapper.memberToMemberResponseDto(response);
    }

    private void checkExistsEmail(String email) {
        Member member = memberRepository.findByEmail(email);

        if (member != null) {
            throw new CustomException("Member already Exists", HttpStatus.CONFLICT);
        }
    }

}
