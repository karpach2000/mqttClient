package com.parsel.mqtt_client.boards.drycontact

abstract class Board(id: Long, plataName: String, DEV_TYPE: String) {


    protected var topic = "/"+id.toString()+"/"+plataName+"/"+DEV_TYPE





    abstract  fun start(): Boolean

    abstract  fun stop()
}