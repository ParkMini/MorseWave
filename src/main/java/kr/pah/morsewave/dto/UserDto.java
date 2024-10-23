package kr.pah.morsewave.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String email;
    private String nickname;
    private String password;
    private boolean admin;
    private String adminToken;
}