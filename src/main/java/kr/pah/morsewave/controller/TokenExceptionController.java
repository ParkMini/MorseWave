package kr.pah.morsewave.controller;

import kr.pah.morsewave.exception.CustomException;
import kr.pah.morsewave.exception.ErrorCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenExceptionController {

    @GetMapping("/exception/entrypoint")
    public void entryPoint() {
        throw new CustomException(ErrorCode.NO_LOGIN);
    }

    @GetMapping("/exception/tokenexpire")
    public void tokenExpire() {
        throw new CustomException(ErrorCode.TOKEN_EXPIRED);
    }

    @GetMapping("/exception/access")
    public void denied() {
        throw new CustomException(ErrorCode.NO_ADMIN);
    }
}