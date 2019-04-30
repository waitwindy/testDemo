package com.gbicc.test;

import java.util.Comparator;

public class StringComparator implements Comparator<String> {
    @Override
    public int compare(String  o1, String o2) {
        String[] split1 = o1.split("\\+");
        String[] split2 = o2.split("\\+");
        String timeStamp1 = split1[2].split("\\.")[0];
        String timeStamp2 = split2[2].split("\\.")[0];
        long l1 = Long.parseLong(timeStamp1);
        long l2 = Long.parseLong(timeStamp2);
        if (l1>l2){
            return 1;
        }else if (l1<l2){
            return -1;
        }
        return 0;
    }

    public static void main(String[] args) {
        String filename1 ="push.jingchangkan.tv+122749969_TVU+1543107230137.ts";
        String filename2 ="push.jingchangkan.tv+122749969_TVU+1543107241908.ts";

        String[] split1 = filename1.split("\\+");
        String[] split2 = filename2.split("\\+");
        String timeStamp1 = split1[2].split("\\.")[0];
        String timeStamp2 = split2[2].split("\\.")[0];
        long l1 = Long.parseLong(timeStamp1);
        long l2 = Long.parseLong(timeStamp2);
        System.out.println(l1 +"======"+l2);

    }
}
