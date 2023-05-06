package com.example.ims.domain.auth;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;
import java.util.Optional;

/**
 * Mybatisのマッパー
 */
@Mapper
public interface UserRepository {

    @Select("select * from invmng_users where username = #{username}")
    Optional<User> findByUsername(String username);

    @Select("select count(*) from invmng_users")
    int count();

    @Select("select * from invmng_users")
    List<User> findAll(RowBounds rowBounds);

    @Insert("insert into invmng_users (username, password, authority) values (#{username}, #{password}, #{authority})")
    void insert(String username, String password, String authority);

    @PreAuthorize("hasAuthority('ADMIN')")
    @Delete({"delete from invmng_users where username = #{username}"})
    void delete(String username);

    @Update("update invmng_users set authority = #{authority} where username = #{username}")
    void editAuthority(String username, String authority);

}
