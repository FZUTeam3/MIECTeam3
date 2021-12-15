package com.example.csl.service;


import com.example.csl.bean.FsUser;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public interface FsUserService {
    void createUser(FsUser user) throws IOException;
    Long findEmail(String email);
    Long loginUser(String phone,String password);
    void update(Long userId);
    FsUser findMessage(Long userId);
    void delete(Long userId);
    Integer statisticsOnline();
    ArrayList<Integer> statisticsLogin();

}
