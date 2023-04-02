package com.example.its.domain.inventory;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// 自動でコンストラクタ(lombok)を生成してくれるアノテーション
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public int inventoryTotalCount(String username) {
        int total = inventoryRepository.count(username);
        return total;
    }

    public Page<InventoryEntity> selectAll(Pageable pageable, String username) {
        RowBounds rowBounds = new RowBounds((int)pageable.getOffset(), pageable.getPageSize());
        List<InventoryEntity> inventoryEntityList = inventoryRepository.selectAll(rowBounds, username);
        int total = inventoryRepository.count(username);
        return new PageImpl(inventoryEntityList, pageable, total);
    }

    @Transactional
    public void create(String inventoryname, String username, Integer stock, String remarks) {
        inventoryRepository.insert(inventoryname, username, stock, remarks);
    }

    public InventoryEntity findById(Integer inventoryId) {
        return inventoryRepository.findById((long)inventoryId);
    }

    public void delete(Integer inventoryId) {
        inventoryRepository.delete((long)inventoryId);
    }

    public void editInventoryname(Integer inventoryId, String inventoryname) {
        inventoryRepository.editInventoryname((long)inventoryId, inventoryname);
    }

    public void editStock(Integer inventoryId, Integer stock) {
        inventoryRepository.editStock((long)inventoryId, stock);
    }

    public void editRemarks(Integer inventoryId, String remarks) {
        inventoryRepository.editRemarks((long)inventoryId, remarks);
    }

}
