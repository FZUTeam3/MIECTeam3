package com.example.csl.Service;


import com.example.csl.bean.fsUser;

import java.io.IOException;

public interface fsUserService {
    void createUser(fsUser user) throws IOException;
    Long findEmail(String email);
    Long loginUser(String phone,String password);
    void update(Long userId);
}
