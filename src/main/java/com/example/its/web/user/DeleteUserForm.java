package com.example.its.web.user;

import com.example.its.web.validation.NotExistUsername;
import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class DeleteUserForm {
    @NotExistUsername
    @NotBlank @Size(min = 1, max = 20)
    private String username;

    private String authority;

}
