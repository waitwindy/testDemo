package com.gbicc.hive;

import org.apache.hadoop.hive.ql.exec.UDF;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class JugeDate extends UDF {

    public Integer evaluate(String orgStr){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            simpleDateFormat.setLenient(false);
            simpleDateFormat.parse(orgStr);
        } catch (ParseException e) {
            return 0;
        }

        return 1;
    }

    public static void main(String[] args) {
        JugeDate udfDemo = new JugeDate();
        Integer evaluate = udfDemo.evaluate("20181419");
        System.out.println(evaluate);
    }


}
