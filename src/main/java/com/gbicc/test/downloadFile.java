package com.gbicc.test;

public class downloadFile implements Runnable {

    private String urlPath;
    private String outputPathName;
    public downloadFile(String urlPath,String outputPathName) {
        this.urlPath=urlPath;
        this.outputPathName=outputPathName;
    }

    @Override
    public void run() {
        test.download(urlPath,outputPathName,300);
    }
}
