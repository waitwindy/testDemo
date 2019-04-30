package com.gbicc.kafka;
import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.security.JaasUtils;

import kafka.admin.AdminUtils;
import kafka.admin.RackAwareMode;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.consumer.Whitelist;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import kafka.server.ConfigType;
import kafka.utils.ZkUtils;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;


public class kafkaTest {

    private final static String URL = "192.168.1.61:2181";
//    private final static String BORKERURL = "192.168.172.100:9092";
    private final static String BORKERURL = "192.168.1.61:6667";
    private final static String NAME = "test";
    // 创建主题
    private static void createTopic() {
        ZkUtils zkUtils = ZkUtils.apply(URL, 30000, 30000, JaasUtils.isZkSecurityEnabled());
        // 创建一个单分区单副本名为t1的topic
        AdminUtils.createTopic(zkUtils, NAME, 3, 1, new Properties(), RackAwareMode.Enforced$.MODULE$);
        zkUtils.close();
        System.out.println("创建成功!");
    }

    // 删除主题(未彻底删除)
    private static void deleteTopic() {
        ZkUtils zkUtils = ZkUtils.apply(URL, 30000, 30000, JaasUtils.isZkSecurityEnabled());
        // 删除topic 't1'
        AdminUtils.deleteTopic(zkUtils, NAME);
        zkUtils.close();
        System.out.println("删除成功!");
    }

    // 修改主题
    private static void editTopic() {
        ZkUtils zkUtils = ZkUtils.apply(URL, 30000, 30000, JaasUtils.isZkSecurityEnabled());
        Properties props = AdminUtils.fetchEntityConfig(zkUtils, ConfigType.Topic(), NAME);
        // 增加topic级别属性
        props.put("min.cleanable.dirty.ratio", "0.3");
        // 删除topic级别属性
        props.remove("max.message.bytes");
        // 修改topic 'test'的属性
        AdminUtils.changeTopicConfig(zkUtils, NAME, props);
        zkUtils.close();
    }

    // 主题读取
    private static void queryTopic() {
        ZkUtils zkUtils = ZkUtils.apply(URL, 30000, 30000, JaasUtils.isZkSecurityEnabled());
        // 获取topic 'test'的topic属性属性
        Properties props = AdminUtils.fetchEntityConfig(zkUtils, ConfigType.Topic(), NAME);
        // 查询topic-level属性
        Iterator it = props.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            System.out.println(key + " = " + value);
        }
        zkUtils.close();
    }

    /**
     * @Description: 生产者
     */
    private static void producer() {
        Properties properties = new Properties();
//        properties.put("zookeeper.connect", "192.168.1.63:2181");//声明zk
//        properties.put("serializer.class", StringEncoder.class.getName());
        properties.put("bootstrap.servers", BORKERURL);// 声明kaf    ka broker
        properties.put("acks", "all");
        properties.put("advertised.host.name", "192.168.1.61");
        properties.put("advertised.port", "6667");
        properties.put("retries", 0);
        properties.put("batch.size", 16384);
        properties.put("linger.ms", 1);
        properties.put("buffer.memory", 33554432);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        for(int i = 0; i < 100; i++)
            producer.send(new ProducerRecord<String, String>(NAME, "message from client: ", Integer.toString(i)));

        producer.flush() ;
        producer.close() ;
    }

    /**
     * @Description: 消费者
     */
    private static void customer() {
        try {
//            System.setProperty("java.security.auth.login.config", "D:\\Temp\\kafka_client_jaas.conf");
            Properties props = new Properties();
            props.put("bootstrap.servers",BORKERURL );
            props.put("group.id", "test-1-group");
            props.put("enable.auto.commit", "true");
            props.put("auto.offset.reset", "earliest");

            props.put("auto.commit.interval.ms", "1000");
            props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
            consumer.subscribe(Arrays.asList(NAME));

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records){
                PrintStream printf = System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static void main(String[] args) {
//        createTopic();
////		 deleteTopic();
//        // editTopic();
//        // queryTopic();
//
//        producer();

		customer();
    }
}

