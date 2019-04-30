package com.gbicc.test;

import java.util.UUID;

public class creatUui {

    public static void main(String[] args) {
        String name ="aaaabbbb";
        String name2 ="aaaabbbb ";
        String s = UUID.nameUUIDFromBytes(name.trim().getBytes()).toString();
        String s2 = UUID.nameUUIDFromBytes(name2.trim().getBytes()).toString();
        if (s.equals(s2)){
            System.out.println("两个 uuid 相同");
            System.out.println(s);
            System.out.println(s2);
        }else {
            System.out.println("两个uuid不同");
            System.out.println(s);
            System.out.println(s2);
        }
    }
}
