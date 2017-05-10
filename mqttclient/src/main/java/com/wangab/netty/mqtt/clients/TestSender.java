package com.wangab.netty.mqtt.clients;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;

/**
 * TestSender
 *
 * @author Anbang Wang
 * @date 2017/5/5
 */
public class TestSender {
    private final static String CLIENT_ID = "Sender";
    private final static String CONNECTION_STRING = "tcp://127.0.0.1:1883";
    private final static boolean CLEAN_START = true;
    private final static short KEEP_ALIVE = 30;// 低耗网络，但是又需要及时获取数据，心跳30s
    private final static long RECONNECTION_ATTEMPT_MAX = 6;
    private final static long RECONNECTION_DELAY = 2000;
    private final static int SEND_BUFFER_SIZE = 2 * 1024 * 1024;//发送最大缓冲为2M
    private final static String USERNAME = "wab1";
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
        //发送消息
        int n = 0;
        while (true) {
            n++;
            blockingConnection.publish("mqtt/top2", ("Hello MQTT Publish Message No:" + n).getBytes(), QoS.AT_LEAST_ONCE, true);
            System.out.println("发送第" + n + "条消息");
            Thread.sleep(2000);
            if (n == 10) {
                break;
            }
        }
        blockingConnection.kill();
    }
}
