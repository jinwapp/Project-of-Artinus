package Artinus.server.member.mapper;

import Artinus.server.member.dto.MemberPostDto;
import Artinus.server.member.dto.MemberResponseDto;
import Artinus.server.member.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") //spring의 bean으로 등록된다.
public interface MemberMapper {

    Member memberPostDtoToMember(MemberPostDto memberPostDto);
    MemberResponseDto memberToMemberResponseDto(Member member);

}
