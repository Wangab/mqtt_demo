package com.wangab.netty.mqtt.clients;

import org.fusesource.mqtt.client.*;

import java.net.URISyntaxException;

/**
 * TestResaver
 *
 * @author Anbang Wang
 * @date 2017/5/5
 */
public class TestResaver {
    private final static String CLIENT_ID = "Receiver";
    private final static String CONNECTION_STRING = "tcp://127.0.0.1:1883";
    private final static boolean CLEAN_START = true;
    private final static short KEEP_ALIVE = 30;// 低耗网络，但是又需要及时获取数据，心跳30s
    private final static long RECONNECTION_ATTEMPT_MAX = 6;
    private final static long RECONNECTION_DELAY = 2000;
    private final static int SEND_BUFFER_SIZE = 2 * 1024 * 1024;//发送最大缓冲为2M
    private final static String USERNAME = "wab";
    private final static String PASSWORD = "wab123";

    public static void main(String[] args) throws Exception {
        MQTT mqtt = new MQTT();
        mqtt.setClientId(CLIENT_ID);
        mqtt.setUserName(USERNAME);
        mqtt.setPassword(PASSWORD);
        mqtt.setHost(CONNECTION_STRING);
        mqtt.setCleanSession(CLEAN_START);
        mqtt.setReconnectAttemptsMax(RECONNECTION_ATTEMPT_MAX);
        mqtt.setReconnectDelay(RECONNECTION_DELAY);
        mqtt.setKeepAlive(KEEP_ALIVE);
        mqtt.setSendBufferSize(SEND_BUFFER_SIZE);
        //创建阻塞式链接，他会一直等着不断线
        BlockingConnection blockingConnection = mqtt.blockingConnection();
        blockingConnection.connect();

        //指定订阅主题
        Topic[] topics = {new Topic("mqtt/top1", QoS.AT_LEAST_ONCE), new Topic("mqtt/top2", QoS.AT_LEAST_ONCE)};
        blockingConnection.subscribe(topics);
        //接收消息
        while (true) {
            //接收订阅的消息内容
            Message message = blockingConnection.receive();
            //获取订阅的消息内容
            byte[] payload = message.getPayload();
            System.out.println("received Message  Topic="+message.getTopic()+" Content :"+new String(payload));
            //签收消息的回执
            message.ack();
            Thread.sleep(2000);
        }
    }
}

