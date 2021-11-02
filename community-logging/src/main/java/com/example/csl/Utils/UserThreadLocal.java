package com.example.csl.Utils;



import com.example.csl.bean.FsUser;
import org.springframework.stereotype.Component;

@Component
public class UserThreadLocal {

    private UserThreadLocal(){}

    private static final ThreadLocal<FsUser> LOCAL = new ThreadLocal<>();

    public static   void put(FsUser user){
        LOCAL.set(user);
    }
    public static FsUser get(){
        return LOCAL.get();
    }
    public  static void remove(){
        LOCAL.remove();
    }
}
