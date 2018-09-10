package com.parsel.mqtt_client.boards.Rele;

public interface NewMessageGetHandler {
    void newRelePosition(String topic, String data);
}
