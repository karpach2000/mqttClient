package com.parsel.mqtt_client.boards.drycontact

import com.parcel.harddrivers.SensorReader
import com.parcel.harddrivers.inteface.BoardStateChangeHandler
import com.parsel.mqtt_client.client.Client


public  class DryContactBoard( id: Long, plataName: String) : Board(id, plataName, "digitalIn") {



    /**
     * Класс обработки изменения знаений входов.
     */
    private class BoardStateChangeEvent(var client: Client, var  topic: String): BoardStateChangeHandler
    {


        @Override
        override fun onBoardStateChange(pinValues: BooleanArray)
        {

            var value = getLineFromByteArray(pinValues)
            var a :Long =System.currentTimeMillis()
            this.client.sendMessage(value, topic)
            var d: Long= System.currentTimeMillis()-a
            d= System.currentTimeMillis()-a
            System.out.print("Время передачи на сервер $d ms")
        }

        private fun getLineFromByteArray(array: BooleanArray) : String
        {
            var line =""
            for(b in array)
            {
                line += if(b)"1" else "0"
            }
            return line;
        }


    }



    private var client = Client.getClient()
    /**
     * Плата
     */
    private var sensorRead: SensorReader = SensorReader(plataName)




    /**
     * Запустить.
     */
    public override fun start() {
        var portName=sensorRead.findPort()
        if(!portName.equals("ERROR"))
        {
            System.out.print("Порт найден "+portName)
            sensorRead.addBoardStateChangeHandler(BoardStateChangeEvent(client, topic))
            sensorRead.startRead()
        }
        else
        {
            System.out.print("Порт  не найден "+portName)

        }

    }

    /**
     * Остановить.
     */
    public override fun stop() {

    }






}


