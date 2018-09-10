package com.parsel.mqtt_client.boards.Rele

import com.parcel.harddrivers.Commutator
import com.parsel.mqtt_client.boards.drycontact.Board

public  class ReleBoard( id: Long, plataName: String) : Board(id, plataName, "digitalOut") {



    /**
     * Класс обработки изменения знаений входов.
     */
    private class NewRelePositionGetEvent(var topicWant: String, var commutator: Commutator): NewMessageGetHandler
    {
        override fun newRelePosition(topicHave: String, data: String) {
            if(topicWant.contains(topicHave))
            {
                var relePosition = getBoolArrayFromString(data)
                this.commutator.setAllRelays(relePosition)
            }
        }

        private fun getBoolArrayFromString( data: String): BooleanArray {
            var array: BooleanArray = BooleanArray(data.length)
            var i = 0
            for (d in data)
            {
                array[i] = d != '0'
            }
            return array
        }

    }
    var comutator = Commutator(plataName)

    public override fun  start() : Boolean
    {
        if(comutator.findPort()!="ERROR") {
            System.out.println("Порт найден")
            return true

        }
        else {
            System.out.println("Не удалось найти порт.")
            return false
        }
    }

    public override fun  stop() {

    }
}