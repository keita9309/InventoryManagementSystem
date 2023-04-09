package com.example.ims.web.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class InventoryForm {
    private Integer id;
    private @NotBlank @Size(max = 50)
    String inventoryname;
    private @Size(max = 50)
    String username;
    private Integer stock;
    private @NotBlank @Size(max = 256)
    String remarks;

}
