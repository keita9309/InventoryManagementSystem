package com.example.its.web.inventory;

import com.example.its.domain.inventory.InventoryService;
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
        model.addAttribute("page", inventoryService.selectAll(pageable, user.getUsername()));
        model.addAttribute("inventoryCount", inventoryService.inventoryTotalCount(user.getUsername()));
        return "inventories/list";
    }

    @GetMapping({"/creationForm"})
    public String showCreationForm(@ModelAttribute InventoryForm form) {
        return "inventories/creationForm";
    }

    @PostMapping({"/creationForm"})
    public String create(@AuthenticationPrincipal UserDetails user, @Validated InventoryForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return showCreationForm(form);
        } else {
            inventoryService.create(form.getInventoryname(), user.getUsername(), form.getStock(), form.getRemarks());

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
            if (inventoryname != "") {
                inventoryService.editInventoryname(id, inventoryname);
            }
            Integer stock = form.getStock();
            if (stock != null) {
                inventoryService.editStock(id, stock);
            }
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
        int deleteCount = 0;
        String[] records = selectedRecords;
        int recordsLength = selectedRecords.length;

        for(int i = 0; i < recordsLength; i++) {
            String id = records[i];
            this.inventoryService.delete(Integer.valueOf(id));
            deleteCount++;
        }
        if (deleteCount > 0) {
            redirectAttributes.addFlashAttribute("message", "" + deleteCount + "件のデータが正常に削除されました！");
        }
        return "redirect:/inventories";
    }

    @GetMapping("/{inventoryId}")
    public String showDetail(@PathVariable("inventoryId") Integer inventoryId, Model model) {
        model.addAttribute("inventory", inventoryService.findById(inventoryId));
        return "inventories/detail";
    }

    @PostMapping(path = "back", params = "back")
    public String back() {
        return "redirect:/";
    }

}
