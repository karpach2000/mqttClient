package com.parsel.mqtt_client

import com.parsel.mqtt_client.boards.Rele.ReleBoard
import com.parsel.mqtt_client.boards.drycontact.DryContactBoard
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class MqttClientApplication

fun main(args: Array<String>) {
    //var subscriber = ClientSubscriber()
   // subscriber.start("/1/kabinSR/digitalIn/")
    //var board = DryContactBoard(1, "kabinSR")
    //board.start()
    var boardRele = ReleBoard(1, "rele")
    boardRele.start()
    //runApplication<MqttClientApplication>(*args)
}
