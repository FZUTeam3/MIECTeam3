package com.example.csl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.csl.bean.FsUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;

@Mapper
public interface FsUserMapper extends BaseMapper<FsUser> {

    @Select("select user_id from fs_user where email=#{email}")
    Long findEmail(String email);

    @Select("select user_id from fs_user where email=#{email} and password = #{password}")
    Long loginUser(String email, String password);

    @Update("UPDATE fs_user SET status = #{status} and last_login=#{lastLogin} where user_id = #{userId} ")
    void updateLogin(@Param("status") String status, @Param("lastLogin") LocalDate lastLogin, @Param("userId") Long userId);

}
