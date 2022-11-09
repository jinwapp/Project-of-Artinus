package Artinus.server.member.service;

import Artinus.server.exception.CustomException;
import Artinus.server.member.entity.Member;
import Artinus.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Member createMember(Member member) {
        checkExistEmail(member.getEmail());

        member.setEmail(member.getEmail());
        member.setPassword((member.getPassword()));
//        member.setEmail(bCryptPasswordEncoder.encode(member.getEmail()));
//        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        return memberRepository.save(member);
    }

    private void checkExistEmail(String email) {
        Member member = memberRepository.findByEmail(email);

        if (member != null) {
            throw new CustomException("Member Exists", HttpStatus.CONFLICT);
        }
    }
}
