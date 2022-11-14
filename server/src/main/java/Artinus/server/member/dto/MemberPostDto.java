package Artinus.server.member.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MemberPostDto {

    public MemberPostDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @NotBlank
    @Email
    @ApiModelProperty(value = "필드 값", example = "test@gmail.com", required = true)
    private String email;

    @NotBlank
    @ApiModelProperty(value = "필드 값", example = "password", required = true)
    private String password;

    @ApiModelProperty(value = "필드 값", example = " ")
    private String roles;

}