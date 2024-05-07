package es.us.lsi.dad;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.mqtt.MqttClient;
import io.vertx.mqtt.MqttClientOptions;

public class ClienteMQTT extends AbstractVerticle {
	
	Gson gson;
	
	public void start(Promise<Void> startFuture) {
		gson = new Gson();
		MqttClient mqttClient = MqttClient.create(vertx, new MqttClientOptions().setAutoKeepAlive(true));
		mqttClient.connect(1883, "localhost", s -> {
			
			mqttClient.subscribe("topic_2", MqttQoS.AT_LEAST_ONCE.value(), handler -> {
				if(handler.succeeded()) {
					System.out.println("Suscripción " + mqttClient.clientId());
				}
			});
			
			mqttClient.publishHandler(handler -> {
				System.out.println("Mensaje recibido:");
				System.out.println("\tTopic: " + handler.topicName().toString());
				System.out.println("\tId del mensaje: " + handler.messageId());
				System.out.println("\tContenido: " + handler.payload().toString());
				/*
				try {
					SimpleClass sc = gson.fromJson(handler.payload().toString(), SimpleClass.Class);
					System.out.println("\tSimpleClass: " + sc.toString());
				} catch(JsonSyntaxException e) {
					System.out.println("\tNo es una SimpleClass. ");
				}
				*/
			});
			
			mqttClient.publish("topic_1", Buffer.buffer("Ejemplo"), MqttQoS.AT_LEAST_ONCE, false, false);
			
		});
	}
	
}
