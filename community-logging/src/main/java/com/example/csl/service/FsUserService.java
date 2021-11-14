package com.example.csl.service;


import com.example.csl.bean.FsUser;

import java.io.IOException;

public interface FsUserService {
    void createUser(FsUser user) throws IOException;
    Long findEmail(String email);
    Long loginUser(String phone,String password);
    void update(Long userId);
}
