package com.gbicc.hbaseTest;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

public class hbaseTemplete {
    public static void main(String[] args) {
        HbaseTemplate template = new HbaseTemplate();
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum","192.168.172.138");
        configuration.set("hbase.zookeeper.port","2181");
        template.setConfiguration(configuration);
        template.setAutoFlush(true);

        Boolean execute = template.execute("test", (hTableInterface) -> {
            boolean flag = false;
            try {
                Put put = new Put("11223344".getBytes());
                put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("value"), Bytes.toBytes("zheshishenmeshidao"));
                hTableInterface.put(put);
                flag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return flag;
        });
        System.out.println("执行结果 ："+execute);
    }
}
