package com.example.cjh.uitls;

public class ThumbUtils {
    public static final String Key_Thumb = "Key_Thumb";


    public static String getThumbKey(int articleId, int userId) {
        StringBuilder sb = new StringBuilder();
        sb.append(articleId);
        sb.append("::");
        sb.append(userId);
        return sb.toString();
    }
}
