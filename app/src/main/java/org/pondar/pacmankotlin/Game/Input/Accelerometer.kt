package org.pondar.pacmankotlin.Game.Input

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import org.pondar.pacmankotlin.GameController
import org.pondar.pacmankotlin.MainActivity

class Accelerometer(var gameController: GameController?,var main: MainActivity) :  SensorEventListener {

    var SensorInput = ArrayList<Float>()

    lateinit var sensorManager: SensorManager

    fun setListener(){
        sensorManager = main.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME
        )
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {


        SensorInput.add(event?.values?.get(0)!!)
        if(SensorInput.count() > 30){
            var LastInput = SensorInput.subList(SensorInput.count() - 20, SensorInput.count())
            var result = LastInput.average() *2
            gameController?.ShipPos = result.toFloat()
        }else{
            gameController?.ShipPos = 0.0F
        }
    }
}