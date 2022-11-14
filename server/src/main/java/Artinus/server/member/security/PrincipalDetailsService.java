package Artinus.server.member.security;

import Artinus.server.config.aes256.AES256;
import Artinus.server.member.entity.Member;
import Artinus.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService { //Spring Security 에서 유저의 정보를 가져오는 인터페이스이다

    private final MemberRepository memberRepository;
    private final AES256 aes256;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(aes256.encrypt(email));
        return new PrincipalDetails(member);

    }
}