package com.parsel.mqtt_client.boards.drycontact

import com.parsel.mqtt_client.client.Client

abstract class Board(id: Long, plataName: String, DEV_TYPE: String) {

    protected var topic : String
    protected  var onOffState =false
    protected var client = Client.getClient()

    init {
        topic = "/"+id.toString()+"/"+plataName+"/"+DEV_TYPE
        System.out.println("Plata name: "+plataName+" topic: "+topic)
    }






    abstract  fun start() : Boolean

    abstract  fun stop() : Boolean
}