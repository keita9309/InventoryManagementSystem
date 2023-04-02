package com.example.its.domain.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InventoryEntity {

    private Integer id;
    private String inventoryname;
    private String username;
    private Integer stock;
    private String remarks;


}
