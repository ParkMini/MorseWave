package kr.pah.morsewave.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@ToString
public class LoginUserDto {
    private String email;
    private String password;
}