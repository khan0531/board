package com.example.board.service;

import com.example.board.model.LoginType;
import com.example.board.model.User;
import com.example.board.model.UserRole;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Transactional
  public void signUp(User user) {
    String rawPassword = user.getPassword();
    String encPassword = bCryptPasswordEncoder.encode(rawPassword);
    user.setPassword(encPassword);
    user.setLoginType(LoginType.GENERAL);
    user.setRole(UserRole.USER);
    userRepository.save(user);
  }

  @Transactional(readOnly = true)
  public User findUser(String username) {
    return userRepository.findByUsername(username).orElseGet(User::new);
  }

  @Transactional
  public void updateUserInfo(User user) {
    User currUser = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

    if (currUser.getLoginType() == LoginType.GENERAL) {
      String rawPassword = user.getPassword();
      String encPassword = bCryptPasswordEncoder.encode(rawPassword);
      currUser.setPassword(encPassword);
      currUser.setEmail(user.getEmail());
    }
  }

}
