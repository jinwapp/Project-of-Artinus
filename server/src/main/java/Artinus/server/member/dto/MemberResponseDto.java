package Artinus.server.member.dto;

import Artinus.server.audit.Auditable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberResponseDto {
    private long memberId;
    private String email;
    private LocalDateTime creationDate;
    private LocalDateTime lastEditDate;
}
