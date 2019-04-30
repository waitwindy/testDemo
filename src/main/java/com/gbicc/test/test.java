package com.gbicc.test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {
    public static void main(String[] args) throws Exception {
        int i=0;
        int j =0;
        if((++i>0)||(++j>0)){
            System.out.println(i);
            System.out.println(j);
        }
//        CombineFile("D:\\gbicc\\downView","D:\\gbicc\\combined.ts");
//        download("http://jingchangkan.tv/push.jingchangkan.tv+122749969   _TVU+1543107143471.ts","view.tx",200);
    }

//  下载视频文件到本地
    public static boolean download(String urlString, String filename,int timeout){
        boolean ret = false;
        File file = new File(filename);
        try {
            if(file.exists()){
                ret = true;
                System.out.println("filename :"+filename+" is exit");
            }else{
                // 构造URL
                URL url = new URL(urlString);
                // 打开连接
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setConnectTimeout(timeout);
                con.setReadTimeout(timeout);
                con.connect();
                int contentLength = con.getContentLength();
                // 输入流
                InputStream is = con.getInputStream();
                // 1K的数据缓冲
                byte[] bs = new byte[1024];
                // 读取到的数据长度
                int len;
                // 输出的文件流

                File file2=new File(file.getParent());
                file2.mkdirs();
                if(file.isDirectory()){

                }else{
                    file.createNewFile();//创建文件
                }
                OutputStream os = new FileOutputStream(file);
                // 开始读取
                while ((len = is.read(bs)) != -1) {
                    os.write(bs, 0, len);
                }
                // 完毕，关闭所有链接
                os.close();
                is.close();
                if(contentLength != file.length()){
                    file.delete();
                    ret = false;
                }else{
                    ret = true;
                }
            }
        } catch (IOException e) {
            file.delete();
            ret = false;
            System.out.println("[VideoUtil:download]:\n" + " VIDEO URL：" + urlString + " \n NEW FILENAME:" + filename + " DOWNLOAD FAILED!! ");
        }finally {
            return ret;
        }
    }

    /**
     * 断点续传
     * @param urlString
     * @param filename
     * @param timeout
     * @return
     */
    public static boolean resumeDownload(String urlString, String filename,int timeout) throws Exception{
        boolean ret = false;
        File fileFinal = new File(filename);
        String tmpFileName = filename+".tmp";
        File file = new File(tmpFileName);

        try {
            if(fileFinal.exists()){
                ret = true;
            }else{
                long contentStart = 0;
                File file2=new File(file.getParent());

                if(file.exists()){
                    contentStart = file.length();
                }else{
                    file2.mkdirs();
                }
                // 构造URL
                URL url = new URL(urlString);
                // 打开连接
                HttpURLConnection con = (HttpURLConnection )url.openConnection();
                con.setConnectTimeout(timeout);
                con.setReadTimeout(timeout);
                //设置续传的点
                if(contentStart>0){
                    con.setRequestProperty("RANGE","bytes="+contentStart+"-");
                }
                con.connect();
                int contentLength = con.getContentLength();
                // 输入流
                InputStream is = con.getInputStream();
                // 100Kb的数据缓冲
                byte[] bs = new byte[100*1024];
                // 读取到的数据长度
                int len;
                RandomAccessFile oSavedFile = new RandomAccessFile(tmpFileName,"rw");
                oSavedFile.seek(contentStart);
                // 开始读取
                while ((len = is.read(bs)) != -1) {
                    oSavedFile.write(bs, 0, len);
                }
                // 完毕，关闭所有链接
                oSavedFile.close();
                is.close();
                file.renameTo(fileFinal);
                ret = true;
            }
        } catch (IOException e) {
            file.delete();
            ret = false;
            System.out.println("[VideoUtil:download]:\n" + " VIDEO URL：" + urlString + " \n NEW FILENAME:" + filename + " DOWNLOAD FAILED!! ");
            throw new Exception(e);
        }finally {
            return ret;
        }
    }

//    多视频文件合并
    public static  void CombineFile(String path,String tar) throws Exception {
        try {
            File dirFile = new File(path);
            FileInputStream fis;
            FileOutputStream fos = new FileOutputStream(tar);
            byte buffer[] = new byte[1024 * 1024 * 2];//一次读取2M数据，将读取到的数据保存到byte字节数组中
            int len;
            if(dirFile.isDirectory()) { //判断file是否为目录
                String[] fileNames = dirFile.list();
                Arrays.sort(fileNames, new StringComparator());//实现目录自定义排序
                for (int i = 0;i<fileNames.length ;i++){
                    System.out.println(path+"\\"+fileNames[i]);
                    fis = new FileInputStream(path+"\\"+fileNames[i]);
                    len = 0;
                    while ((len = fis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);//buffer从指定字节数组写入。buffer:数据中的起始偏移量,len:写入的字数。
                    }
                    fis.close();
//                    File file = new File(path + "\\" + fileNames[i]);
//                    file.delete();
                }
            }
            fos.flush();
            fos.close();
        }catch (IOException e){
            e.printStackTrace();
        } finally {
            System.out.println("合并完成！");
        }
    }

}
