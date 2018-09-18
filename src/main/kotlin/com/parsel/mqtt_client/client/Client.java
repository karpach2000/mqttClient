package com.parsel.mqtt_client.client;

import com.parsel.mqtt_client.boards.Rele.NewMessageGetHandler;
import org.eclipse.paho.client.mqttv3.*;

import java.util.ArrayList;


public class Client {

    protected ArrayList<String> brokerUrlList =new ArrayList<>();
    protected String mqttId="dev0";
    protected int qos =2;
    protected MqttClient mqttClient;

    private  SimpleMqttCallBack simpleMqttCallBack = new SimpleMqttCallBack();


    /******************СИНГЕЛТОН********************/
    private static Client client;

    private Client() throws MqttException {
        brokerUrlList.add("tcp://5.200.53.139:1883");
        connect();
    }

    public  static Client getClient() throws MqttException {
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
            //disconnect();
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

    public void subscribe(String topic)
    {
        try {
            mqttClient.subscribe(topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void addNewMessageGetHandlers(NewMessageGetHandler handler) {
        simpleMqttCallBack.addReleStateChangeHandlers(handler);
    }

    /******************ПРИВАТЫ********************/



    private void connect() throws MqttException {
        String brokerUrl= brokerUrlList.get(0);
        //MemoryPersistence persistence = new MemoryPersistence();
        mqttClient = new MqttClient(brokerUrl, mqttId);
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        connOpts.setUserName("parcel");
        connOpts.setPassword("parcel".toCharArray());
        System.out.println("Connecting to broker: "+ brokerUrl);
        mqttClient.connect(connOpts);
        System.out.println("Connected");


        mqttClient.setCallback(simpleMqttCallBack);

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
        //System.exit(0);
    }

}
