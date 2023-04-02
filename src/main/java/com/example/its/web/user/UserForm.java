package com.example.its.web.user;

import com.example.its.web.validation.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class UserForm {

    @NotBlank
    @UniqueUsername
    @Size(max = 30)
    private String username;

    @NotBlank
    @Size(min = 8, max = 50)
    private String password;

    // 未ログイン時の新規登録にて非表示でエラーになるためコメントアウト
    // @NotBlank
    private String authority;

}
