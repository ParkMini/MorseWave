package kr.pah.morsewave.service;

import kr.pah.morsewave.dto.LoginUserDto;
import kr.pah.morsewave.dto.UserDto;
import kr.pah.morsewave.exception.CustomException;
import kr.pah.morsewave.exception.ErrorCode;
import kr.pah.morsewave.model.User;
import kr.pah.morsewave.model.UserRoleEnum;
import kr.pah.morsewave.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private static final String ADMIN_PW = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";


    public User signup(UserDto userDto) {
        String email = userDto.getEmail();

        // 회원 ID 중복 확인
        Optional<User> found = userRepository.findByEmail(email);
        if (found.isPresent()) {
            throw new CustomException(ErrorCode.SAME_EMAIL);
        }

        String nickname = userDto.getNickname();

        // 패스워드 암호화
        String password = passwordEncoder.encode(userDto.getPassword());

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (userDto.isAdmin()) {
            if (!userDto.getAdminToken().equals(ADMIN_PW)) {
                throw new CustomException(ErrorCode.ADMIN_TOKEN);
            }
            role = UserRoleEnum.ADMIN;
        }

        // 빌더 패턴을 사용하여 User 객체 생성
        User user = User.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .role(role)
                .build();

        userRepository.save(user);
        return user;
    }

    //로그인
    public User login(LoginUserDto loginUserDto) {
        User user = userRepository.findByEmail(loginUserDto.getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.NO_USER)
        );
        if (!passwordEncoder.matches(loginUserDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.NO_USER);
        }
        return user;
    }
}