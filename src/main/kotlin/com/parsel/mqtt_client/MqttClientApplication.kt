package com.parsel.mqtt_client

import com.parsel.mqtt_client.client.Client
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MqttClientApplication

fun main(args: Array<String>) {
    var client = Client.getClient()
    client.sendMessage("Hay")

    runApplication<MqttClientApplication>(*args)
}
