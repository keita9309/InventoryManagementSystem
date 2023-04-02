package com.example.its.domain.inventory;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Mybatisのマッパー
 */
@Mapper
public interface InventoryRepository {

    @Select("select count(*) from inventories where username = #{username}")
    int count(String username);

    @Select("select * from inventories where username = #{username}")
    List<InventoryEntity> selectAll(RowBounds rowBounds, String username);

    @Select("select * from inventories")
    List<InventoryEntity> findAll();

    @Insert("insert into inventories (inventoryname, username, stock, remarks) values (#{inventoryname}, #{username}, #{stock}, #{remarks})")
    void insert(String inventoryname, String username, int stock, String remarks);

    @Select("select * from inventories where id = #{inventoryId}")
    InventoryEntity findById(long inventoryId);

    @Delete("delete from inventories where id = #{inventoryId}")
    void delete(long inventoryId);

    @Update("update inventories set inventoryname = #{inventoryname} where id = #{inventoryId}")
    void editInventoryname(long inventoryId, String inventoryname);

    @Update("update inventories set stock = #{stock} where id = #{inventoryId}")
    void editStock(long inventoryId, int stock);

    @Update("update inventories set remarks = #{remarks} where id = #{inventoryId}")
    void editRemarks(long inventoryId, String remarks);

}
