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

                System.out.println("New value $data")
                var relePosition = getBoolArrayFromString(data)
                var i=0
                for(p in relePosition)
                {

                        this.commutator.relayWrite(i, p)

                    i++
                }

            }
        }

        private fun getBoolArrayFromString( data: String): BooleanArray {
            var array: BooleanArray = BooleanArray(data.length)
            var i = 0
            for (d in data)
            {
                array[i] = d != '0'
                i++
            }
            return array
        }


    }
    var comutator = Commutator(plataName)

    public override fun  start() : Boolean
    {
        if(!onOffState) {
            if (comutator.findPort() != "ERROR") {
                System.out.println("Порт найден")

                var relePositionGetEvent = NewRelePositionGetEvent(topic, comutator)
                client.addNewMessageGetHandlers(relePositionGetEvent)
                client.subscribe(topic);

                onOffState = true
                return true

            } else {
                System.out.println("Не удалось найти порт.")
                onOffState = false;
                return false
            }
        }
        else {
            System.out.println("Не Возможно запустить коммутатор, т.к он уже запущен.")
            return false
        }
    }

    public override fun  stop() : Boolean{
        if(onOffState)
            onOffState=false;
        return true
    }
}