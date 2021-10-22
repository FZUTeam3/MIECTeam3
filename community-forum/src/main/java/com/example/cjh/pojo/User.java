package com.example.cjh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("fs_user")
public class User {
    /** 用户id */
    @TableId(type = IdType.AUTO)
    private int userId ;
    /** 账号 */
    private String account ;
    /** 密码 */
    private String password ;
    /** 账号昵称 */
    private String nickname ;
    /** 绑定电话 */
    private String phoneNumber ;
    /** 邮箱地址 */
    private String email ;
    /** 创建账号时间 */
    private String createDate ;
    /** 最后登录时间 */
    private String lastLogin ;
    /** 当前状态 */
    private String status ;
    /** 密码加密盐（后台随机生成） */

}
