package com.parsel.mqtt_client.boards.drycontact

import com.parsel.mqtt_client.client.Client

abstract class Board(id: Long, plataName: String, DEV_TYPE: String) {

    protected var client: Client = Client.getClient()
    protected var topic = id.toString()+"/"+plataName+"/"+DEV_TYPE





    abstract  fun start()

    abstract  fun stop()
}