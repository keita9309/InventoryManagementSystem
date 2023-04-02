package com.example.its.web.user;

import com.example.its.domain.auth.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String showList(Model model, Pageable pageable) {
        System.out.println(pageable);
        model.addAttribute("page", userService.findAll(pageable));
        return "users/list";
    }

    @GetMapping("/creationForm")
    // model.addAttributeと同じような効果が得られる ↓
    public String showCreationForm(@ModelAttribute UserForm from) {
        return "users/creationForm";
    }

    @GetMapping("/successCreateUser")
    public String showSuccessCreateUser() {
        return "users/successCreateUser";
    }

    // POST /users
    @PostMapping("/creationForm")
    public String createUser(@AuthenticationPrincipal UserDetails user, @Validated UserForm form, BindingResult bindingResult) {
        // バリデーションエラーが発生した時の処理
        if (bindingResult.hasErrors()) {
            // showCreationFormメソッドを呼び出して、"users/creationForm"にredirect
            return showCreationForm(form);
        } else {
            if (form.getAuthority() == null) {
                form.setAuthority("USER");
            }
            // ユーザ名・パスワード・権限情報を元にDBを更新(新規登録)
            userService.create(form.getUsername(), form.getPassword(), form.getAuthority());

            if (user != null) {
                // 管理者の場合：ユーザーの新規登録が終わったあとは、一覧画面に戻る(redirect)
                return "redirect:/users";
            } else {
                // 一般ユーザーの場合：ユーザーの新規登録が終わったあとは、新規作成完了画面に遷移
                return "users/successCreateUser";
            }
        }

    }

    @PostMapping(path = "deleteForm", params = "deleteForm")
//    public String showDeleteForm(@ModelAttribute DeleteUserForm from) {
    public String deleteForm(@ModelAttribute DeleteUserForm from) {
        return "users/deleteForm";
    }

    @PostMapping("/delete")
    public String delete(@Validated DeleteUserForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return deleteForm(form);
        } else {
            userService.delete(form.getUsername());
            redirectAttributes.addFlashAttribute("message", "\"ユーザー情報：" + form.getUsername() + " \" が正常に削除されました！");
            return "redirect:/users";
        }
    }

    @PostMapping(path = "editForm")
    public String editForm(EditUserForm form) {
        return "users/editAuthority";
    }

    @PostMapping(path = "edit")
    public String editAuthority(EditUserForm form, RedirectAttributes redirectAttributes) {
        System.out.println("username : " + form.getUsername());
        System.out.println("authority : " + form.getAuthority());
        // ユーザー名と権限情報を元にDBを更新
        userService.editAuthority(form.getUsername(), form.getAuthority());
        // 更新成功後のメッセージ
        redirectAttributes.addFlashAttribute("message", "\" ユーザー情報：" + form.getUsername() + " \" の権限が正常に更新されました！");
        return "redirect:/users";
    }

}
