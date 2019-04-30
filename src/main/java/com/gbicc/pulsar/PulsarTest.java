package com.gbicc.pulsar;

import org.apache.pulsar.client.api.*;

import java.util.concurrent.TimeUnit;

public class PulsarTest {
  static   String localClusterUrl = "pulsar://192.168.172.138:6650";
    public static void main2(String[] args) {
        String localClusterUrl = "pulsar://192.168.172.100:6650";
        try {
            PulsarClient client = PulsarClient.builder().serviceUrl(localClusterUrl).build();
            Producer<byte[]> producer = client.newProducer().topic("my-topic").create();
                    producer.send("我开始使用了".getBytes());
            producer.closeAsync().thenRun(()-> System.out.println("producer closed"));
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        send(localClusterUrl,"chaxun");
      getMessage(localClusterUrl,"my-topic");
//        getEarlistMessage(localClusterUrl,"my-topic");

    }

    private static void send(String sendUrl, String message){
        PulsarClient client = null;
        try {
            client = PulsarClient.builder().serviceUrl(sendUrl).build();
            Producer<byte[]> producer = client.newProducer().topic("my-topic").create();
            producer.send(message.getBytes());
            producer.closeAsync().thenRun(()-> System.out.println("producer closed"));
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }

    private static void getMessage(String url,String topic ){
        try {
            PulsarClient client = PulsarClient.builder().serviceUrl(localClusterUrl).build();
            Consumer consumer = client.newConsumer().topic(topic)
                    .subscriptionName("my-subscription")
                    .ackTimeout(10, TimeUnit.SECONDS)
                    .subscriptionType(SubscriptionType.Failover)
                    .subscribe();

            do{

                Message msg = consumer.receive();
                System.out.println(new String (msg.getData()));
                consumer.acknowledge(msg);
            }while (true);

        } catch (PulsarClientException e) {


        }

    }

    private static void getEarlistMessage(String url,String topic ){
        try {
            PulsarClient client = PulsarClient.builder().serviceUrl(url).build();
            Reader<byte[]> reader = client.newReader().topic(topic).startMessageId(MessageId.earliest).create();
            while (true){
                System.out.println("get Message from earlist % "+ new String(reader.readNext().getData()));
            }
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }

    }





}
