package com.example.its.web.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotExistUsernameValidator.class)
public @interface NotExistUsername {
    String message() default "入力されたユーザー名は存在しません。別のユーザー名を入力して下さい。";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
