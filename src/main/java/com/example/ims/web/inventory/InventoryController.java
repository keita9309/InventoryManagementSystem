package com.example.ims.web.inventory;

import com.example.ims.domain.inventory.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public String showList(@AuthenticationPrincipal UserDetails user, Model model, Pageable pageable) {
        int inventoryCount = 0;
        // ページング化した在庫リスト(DBから取得)のキーを"page"に指定
        model.addAttribute("page", inventoryService.selectAll(pageable, user.getUsername()));
        // 在庫数が0の時の画面制御のために、ユーザーごとの在庫数を取得
        model.addAttribute("inventoryCount", inventoryService.inventoryTotalCount(user.getUsername()));
        return "inventories/list";
    }

    @GetMapping("/creationForm")
    public String showCreationForm(@ModelAttribute InventoryForm form) {
        return "inventories/creationForm";
    }

    @PostMapping({"/creationForm"})
    public String createInventory(@AuthenticationPrincipal UserDetails user, @Validated InventoryForm form,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return showCreationForm(form);
        } else {
            // 在庫名・ユーザー名・在庫数・補足事項の情報を元にDBを更新
            inventoryService.create(form.getInventoryname(), user.getUsername(), form.getStock(), form.getRemarks());
            // "message"のvalueとして在庫名を動的に表示
            redirectAttributes.addFlashAttribute("message", "\" " + form.getInventoryname() + " \" の情報が正常に作成されました！");
            return "redirect:/inventories";
        }
    }

    @PostMapping(path = "editForm", params = "editForm")
    public String editForm(@RequestParam Integer id, @ModelAttribute InventoryForm form, Model model) {
        System.out.println("id : " + id);
        model.addAttribute("id", id);
        model.addAttribute("inventoryname", form.getInventoryname());
        model.addAttribute("stock", form.getStock());
        model.addAttribute("remarks", form.getRemarks());
        return "inventories/edit";
    }

    @PostMapping(path = "edit", params = "edit")
    public String edit(@RequestParam Integer id, @ModelAttribute InventoryForm form, RedirectAttributes redirectAttributes) {
        try {
            String inventoryname = form.getInventoryname();
            // ユーザー名(インプットタグ)に値が入っていたら更新
            if (inventoryname != "") {
                inventoryService.editInventoryname(id, inventoryname);
            }
            // 在庫数に値が入っていたら更新
            Integer stock = form.getStock();
            if (stock != null) {
                inventoryService.editStock(id, stock);
            }
            // 補足事項に値が入っていたら更新
            String remarks = form.getRemarks();
            if (remarks != "") {
                inventoryService.editRemarks(id, remarks);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        redirectAttributes.addFlashAttribute("message", "データが正常に更新されました！");
        return "redirect:/inventories";
    }

    @PostMapping(path = "/deleteForm", params = "delete")
    public String delete(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
        inventoryService.delete(id);
        redirectAttributes.addFlashAttribute("message", "データが正常に削除されました！");
        return "redirect:/inventories";
    }

    @PostMapping(path = "/multiDeleteForm")
    public String deleteRecords(@RequestParam(value = "selectedRecords",defaultValue = "")
                                    String[] selectedRecords, RedirectAttributes redirectAttributes) {
        try {

        // 削除件数を数える変数
        int deleteCount = 0;
        // 在庫リスト画面で、チェックボックスにチェックを入れたレコードのidが入った配列
        String[] records = selectedRecords;
        // チェックしたレコードのidが入った、配列の要素数を取得
        int recordsLength = selectedRecords.length;

        // idの数分ループ
        for(int i = 0; i < recordsLength; i++) {
            String id = records[i];
            // idをStringからIntegerに変換し、それをもとにDBを更新(選択した在庫レコードの削除)
            inventoryService.delete(Integer.valueOf(id));
            // 削除した分カウント
            deleteCount++;
        }
        if (deleteCount > 0) {
            // 削除件数が0より大きければ、動的なメッセージを表示(エラーの切り分けのためのif文)
            redirectAttributes.addFlashAttribute("message", "" + deleteCount + "件のデータが正常に削除されました！");
        }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return "redirect:/inventories";
    }

    @GetMapping("/inventoryId")
    public String showDetail(@PathVariable("inventoryId") Integer inventoryId, Model model) {
        // 在庫のidを元に詳細画面を表示
        model.addAttribute("inventory", inventoryService.findById(inventoryId));
        return "inventories/detail";
    }

    @PostMapping(path = "back", params = "back")
    public String back() {
        return "redirect:/";
    }

}
