package com.example.ims.web.validation;

import com.example.ims.domain.auth.UserRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 返ってきた値が空(重複なし)であればtrue
        return userRepository.findByUsername(value).isEmpty();
    }
}
