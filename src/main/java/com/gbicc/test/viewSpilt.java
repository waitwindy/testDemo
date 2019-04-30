package com.gbicc.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class viewSpilt {
    public static void main(String[] args) throws IOException {
        splitFile();
    }
    public static void splitFile() throws IOException {
        FileInputStream fis =
                new FileInputStream("D:\\combined 2.mp4");
        FileOutputStream fos=null;
        byte buf[]=new byte[1024*1024*5];//定义一次写入 5M 数据
        int len=0;
        int count=1;
        fos=new FileOutputStream("d:\\"+(count++)+".mp4");
        int num = 1;//用来记录程序运行中实时的每个 output 写入次数
        while ((len=fis.read(buf))!=-1){
            fos.write(buf,0,len);
            if(num>600) {
            //如果写入次数大于 20(单个文件超过 100M),则此流停止,创建新的输出流写入新的片段
                fos.close();
                fos=
                        new FileOutputStream("d:\\"+(count++)+".mp4");
                num = 1;//重新计数
            }
            else{
                num++;
            }
        }
        fis.close();
        fos.close();
        System.out.println("切割完毕!");
    }



}
