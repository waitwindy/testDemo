package com.gbicc.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

//实现多线程下载文档能力
public class threadDown {

    public static void main(String[] args) throws Exception {
        downFile();
    }

    public static void threadDownFile() throws Exception {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(15, 30, 2000, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5));
        List<String> strings = readFile("C:\\Users\\Administrator\\Desktop\\问题收集.txt");
        String outPutDir="D:\\gbicc\\downView\\";
        int temp=0;
        for (int i = 0; i < strings.size(); i++) {
            String fileUrl = strings.get(i);
            String fileName = outPutDir+fileUrl.substring(fileUrl.lastIndexOf("/")+1);
            executor.execute(new downloadFile(fileUrl,fileName));
        }
        executor.shutdown();
    }

    public static void downFile() throws Exception {
        List<String> strings = readFile("C:\\Users\\Administrator\\Desktop\\新建文本文档.txt");
        String outPutDir="D:\\gbicc\\downView\\";
        for (int i = 0; i < strings.size(); i++) {
            String fileUrl = strings.get(i);
            String fileName = outPutDir+fileUrl.substring(fileUrl.lastIndexOf("/")+1);
           test.download(fileUrl,fileName,3000);
        }
    }
    public static List<String> readFile(String fileName) throws Exception {
        FileInputStream inputStream = null;
        Scanner sc = null;
        List<String> names = new ArrayList<>();
        try {
            inputStream = new FileInputStream(fileName);
            sc = new Scanner(inputStream, "UTF-8");
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                // System.out.println(line);
                names.add(line.trim());
            }
            // note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (sc != null) {
                sc.close();
            }
        }
        return names;
    }

//    public static void main(String[] args) throws Exception {
//        List<String> strings = readFile("C:\\Users\\Administrator\\Desktop\\问题收集.txt");
//        for (int i = 0; i < strings.size(); i++) {
//            String fileUrl = strings.get(i);
//            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/")+1);
//
//            System.out.println("fileUrl  "+fileUrl);
//            System.out.println("fileName "+fileName);
//        }
//    }
}
