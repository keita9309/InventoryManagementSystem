package com.example.its.web.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditUserForm {

    private String username;

    private String authority;

}
