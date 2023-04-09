package com.example.ims.domain.auth;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
// 自動でコンストラクタ(lombok)を生成してくれるアノテーション
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<User> findAll(Pageable pageable) {
        RowBounds rowBounds = new RowBounds((int)pageable.getOffset(), pageable.getPageSize());
        List<User> userList = userRepository.findAll(rowBounds);
        int total = userRepository.count();
        return new PageImpl(userList, pageable, total);
    }

    public Optional<User> findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    @PreAuthorize("permitAll()")
    public void create(String username, String password, String authority) {
        String encodedPassword = passwordEncoder.encode(password);
        userRepository.insert(username, encodedPassword, authority);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(String username) {
        userRepository.delete(username);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void editAuthority(String username, String authority) {
        userRepository.editAuthority(username, authority);
    }

}
