package com.parsel.mqtt_client.client;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.ArrayList;

public class Client {

    private ArrayList<String> brokerUrlList =new ArrayList<>();
    private String mqttId="dev0";
    private int qos =2;
    private MqttClient mqttClient;


    /******************СИНГЕЛТОН********************/
    private static Client client;

    private Client()
    {
        brokerUrlList.add("tcp://5.200.53.139:1883");
    }

    public  static Client getClient()
    {
        if(client==null)
        {
            client = new Client();
        }
       return  client;
    }

    /******************ИНТЕРФЕЙСЫ********************/
    public  void sendMessage(String messageText, String topic)
    {
        try {
            connect();
            publishMessage(messageText, topic );
            disconnect();
        }
        catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }

    /******************ПРИВАТЫ********************/

    private void connect() throws MqttException {
        String brokerUrl= brokerUrlList.get(0);
        MemoryPersistence persistence = new MemoryPersistence();
        mqttClient = new MqttClient(brokerUrl, mqttId, persistence);
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        System.out.println("Connecting to broker: "+ brokerUrl);
        mqttClient.connect(connOpts);
        System.out.println("Connected");
    }


    private void publishMessage(String messageText, String topic) throws MqttException {
        System.out.println("Publishing message: "+messageText);
        MqttMessage message = new MqttMessage(messageText.getBytes());
        message.setQos(qos);
        mqttClient.publish(topic, message);
        System.out.println("Message published");
    }

    private void disconnect() throws MqttException {
        mqttClient.disconnect();
        System.out.println("Disconnected");
        System.exit(0);
    }

}
