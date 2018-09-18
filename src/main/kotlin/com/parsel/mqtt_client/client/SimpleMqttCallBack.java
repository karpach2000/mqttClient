package com.parsel.mqtt_client.client;
import com.parsel.mqtt_client.boards.Rele.NewMessageGetHandler;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.List;

public class SimpleMqttCallBack implements MqttCallback  {

    public SimpleMqttCallBack()
    {
        System.out.println("publick SimpleMqttCallBack");
    }

    private List<NewMessageGetHandler> messageGetHandlers = new ArrayList();

    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("Connection to MQTT broker lost!!!");
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        handleStateChangeEvent(topic, new String(mqttMessage.getPayload()));
        System.out.println("Message received:\nmessage: "+ new String(mqttMessage.getPayload())+"\ntopic: " + topic);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        System.out.println("deliveryComplete");
    }


    public void addReleStateChangeHandlers(NewMessageGetHandler handler) {
        this.messageGetHandlers.add(handler);
    }


    private  void handleStateChangeEvent(String topic, String data)
    {
        for(NewMessageGetHandler h: messageGetHandlers)
        {
            h.newRelePosition(topic, data);
        }
    }
}
