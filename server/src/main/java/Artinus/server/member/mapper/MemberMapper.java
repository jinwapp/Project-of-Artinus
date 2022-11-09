package Artinus.server.member.mapper;

import Artinus.server.member.dto.MemberPostDto;
import Artinus.server.member.dto.MemberResponseDto;
import Artinus.server.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    Member memberPostDtoToMember(MemberPostDto memberPostDto);

    MemberResponseDto memberToMemberResponseDto(Member member);

}
