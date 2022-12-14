package Artinus.server.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MemberResponseDto {

    private long memberId;

    private String email;

    private LocalDateTime creationDate;

    private LocalDateTime lastEditDate;

}
