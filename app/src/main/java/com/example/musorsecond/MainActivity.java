package com.example.musorsecond;

import androidx.appcompat.app.AppCompatActivity;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //https://www.hivemq.com/blog/mqtt-client-library-encyclopedia-eclipse-paho-java/

        MqttClient client = null;

        try {
             client = new MqttClient(
                    "tcp://broker.mqttdashboard.com:1883", //URI
                    MqttClient.generateClientId(), //ClientId
                    new MemoryPersistence()); //Persistence
             client.connect();
             if (client.isConnected())
                 Toast.makeText(this, "Соединение прошло успешно", Toast.LENGTH_SHORT).show();

        } catch (MqttException e) {
            e.printStackTrace();
            Toast.makeText(this,e.toString(), Toast.LENGTH_LONG).show();
        }



        // публикация
        String topic = "/SONDA/SCHOOL/1";
        String payload = "Privet15";
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(topic, message);

        } catch (MqttException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }



        // подписка не работает!


        /*String topic2 = "/SONDA/SCHOOL/2";
        client.setCallback(new MqttCallback() {

            @Override
            public void connectionLost(Throwable cause) { //Called when the client lost the connection to the broker
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Toast.makeText(MainActivity.this, topic + ": " + Arrays.toString(message.getPayload()), Toast.LENGTH_LONG).show();
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {//Called when a outgoing publish is complete
            }
        });

        try {
            client.connect();
            client.subscribe(topic2, 1);
        } catch (MqttException e) {
            e.printStackTrace();
        }*/


    }
}

