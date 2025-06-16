package com.KHH.novelsite.user.repository;

import com.KHH.novelsite.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 이메일로 사용자 찾기 (로그인, 중복체크 등에 자주 씀)
    Optional<User> findByEmail(String email);

    // 닉네임 중복체크 (회원가입 등에서 쓸 수 있음)
    Optional<User> findByNickname(String nickname);

    // 필요하다면 추가 메서드 여기서 정의
}
