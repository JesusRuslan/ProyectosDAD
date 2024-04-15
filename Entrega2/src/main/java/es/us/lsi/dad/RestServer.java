package es.us.lsi.dad;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class RestServer extends AbstractVerticle {

	private Map<Integer,Placas> placas = new HashMap<Integer, Placas>();
	private Map<Integer,Sensores> sensores = new HashMap<Integer, Sensores>();
	private Map<Integer,Actuadores> actuadores = new HashMap<Integer, Actuadores>();
	private Gson gson;

	public void start(Promise<Void> startFuture) {
		// Creating some synthetic data
		createSomeDataPlacas(5);
		createSomeDataSensores(5);
		createSomeDataActuadores(5);
		

		// Instantiating a Gson serialize object using specific date format
		gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

		// Defining the router object
		Router router = Router.router(vertx);

		// Handling any server startup result
		vertx.createHttpServer().requestHandler(router::handle).listen(8080, result -> {
			if (result.succeeded()) {
				startFuture.complete();
			} else {
				startFuture.fail(result.cause());
			}
		});

		// Defining URI paths for each method in RESTful interface, including body
		// handling by /api/users* or /api/users/*
		
		//todo los post y put ->que tengan cuerpo
		router.route("/api/placas*").handler(BodyHandler.create());
		router.get("/api/placas").handler(this::getAllPlaca);
		router.get("/api/placas/:id").handler(this::getOnePlaca);
		router.post("/api/placas").handler(this::addOnePlaca);
		
		//Youyousie
		router.get("/api/sensores/registros").handler(this::getTresUltimosSensores);
		
		router.route("/api/sensores*").handler(BodyHandler.create());
		router.get("/api/sensores").handler(this::getAllSensor);
		router.get("/api/sensores/:id").handler(this::getOneSensor);
		router.post("/api/sensores").handler(this::addOneSensor);
		router.get("/api/sensores/:id/valores").handler(this::getListadoValoresSensor);
		router.get("/api/placas/:id/valoresSensores").handler(this::getListaValoresSensoresIdPlaca);
		
		router.route("/api/actuadores*").handler(BodyHandler.create());
		router.get("/api/actuadores").handler(this::getAllActuador);
		router.get("/api/actuadores/:id").handler(this::getOneActuador);
		router.post("/api/actuadores").handler(this::addOneActuador);
	//	router.get("/api/placas/:id/actuadores").handler(this::getValorActuadoresIdPlaca);

	}


	//GET ALL ID PLACAs
	private void getAllPlaca(RoutingContext routingContext) {
		
		
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
					.end(gson.toJson(placas.values()));
		
	}
	//GET ALL ID ACTUADORES
		private void getAllActuador(RoutingContext routingContext) {
			
			
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
						.end(gson.toJson(actuadores.values()));
			
		}
	//GET ALL ID SENSORES
	private void getAllSensor(RoutingContext routingContext) {
			
			
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
					.end(gson.toJson(sensores.values()));
			
		}
	
	//GET ID PLACAs
	private void getOnePlaca(RoutingContext routingContext) {
		
		int id = Integer.parseInt(routingContext.request().getParam("id"));
		
		if (placas.containsKey(id)) {
			
			Placas ds = placas.get(id);
			
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
					.end(gson.toJson(ds));
		} else {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(500)
					.end();
		}
	}
	
	//GET ID SENSORES
	private void getOneSensor(RoutingContext routingContext) {
			
			int id = Integer.parseInt(routingContext.request().getParam("id"));
			
			if (sensores.containsKey(id)) {
				
				Sensores ds = sensores.get(id);
				
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
						.end(gson.toJson(ds));
			} else {
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(500)
						.end();
			}
		}
	
	//GET ID ACTUADORES
	
	private void getOneActuador(RoutingContext routingContext) {
		
		int id = Integer.parseInt(routingContext.request().getParam("id"));
		
		if (actuadores.containsKey(id)) {
			
			Actuadores ds = actuadores.get(id);
			
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
					.end(gson.toJson(ds));
		} else {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(500)
					.end();
		}
	}
	
	/* 
	 ///////me falta compararlo por time
	 
	private void getTresUltimosSensores(RoutingContext routingContext) {
        // Obtener todas las entradas del mapa de actuadores
        List<Map.Entry<Integer, Sensores>> entries = new ArrayList<>(sensores.entrySet());
        
        //Siendo [idValue:sensores]
        
        // Verificar si hay menos de tres valores
        if (entries.size() <= 3) {
            // Si hay tres o menos elementos, devolver todas las entradas
            routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
            .end(gson.toJson(entries));
        } else {
            // Si hay más de tres valores, obtener los últimos tres 
            List<Map.Entry<Integer, Sensores>> lastThreeEntries = entries.subList(entries.size() - 3, entries.size());

            // Convertir las entradas de mapa a una lista de valores (en este caso, los ActuadorEntity)
            List<Sensores> lastThreeActuadores = lastThreeEntries.stream()
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());

            // Devolver los últimos tres actuadores como respuesta
            routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
            .end(gson.toJson(lastThreeActuadores));
        }
    }
	
	/////esto adecuarlo
	 * 
	private void LastSensorValue(RoutingContext routingContext) {
	    int sensorId = Integer.parseInt(routingContext.request().getParam("sensorId"));
	    List<Sensores> sen = sensores.values()
	            .stream()
	            .sorted(Comparator.comparing(Sensores::getTiempo))
	            .filter(x -> x.getSensorId().equals(sensorId))
	            .collect(Collectors.toList());

	    List<Double> values = sen.stream()
	            .map(Sensores::getValor)
	            .map(Double::doubleValue)
	            .collect(Collectors.toList());

	    routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
	            .end(gson.toJson(values));
	}
	
	*/
	
	
	
	
	
	
	//GET LISTA VALORES DADO LA ID DE UN SENSOR
	
	private void getListadoValoresSensor(RoutingContext routingContext) {
		int id = Integer.parseInt(routingContext.request().getParam("id"));
		Sensores sensor = sensores.get(id);
		if (sensor != null) {
		    List<Double> ds = sensor.getValor();
		    routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
		            .end(gson.toJson(ds));
		} else {
		    routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(500)
		            .end();
		}
	}
	
	
	
		//GET CON ID PLACA NOS DE VALORES SENSORES 
	private void getListaValoresSensoresIdPlaca(RoutingContext routingContext) {
			
			int id = Integer.parseInt(routingContext.request().getParam("id"));
			Map<Integer,List<Double>> idSensorValores= new HashMap<Integer, List<Double>>();
			
			if (placas.containsKey(id)) {
				
				List<Sensores> acc = sensores.values().stream().filter(e->e.getPlacaId()==id).toList(); //.actuador.get(valor);
					for(int i=0;i<acc.size();i++) {
						Sensores sensor=acc.get(i);
						idSensorValores.put(sensor.getSensorId(), sensor.getValor()) ;
					}
			
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
						.end(gson.toJson(idSensorValores.toString()));
			} else {
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(500)
						.end();
			}
		}
	
	
	
		
	
	
	//GET ÚLTIMO VALOR DE UN SENSOR

	private void getÚltimoValorSensor(RoutingContext routingContext) {
		int id = Integer.parseInt(routingContext.request().getParam("id"));
		Sensores sensor = sensores.get(id);
		if (sensor != null) {
			
		    List<Double> ds = sensor.getValor();
		    routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
		            .end(gson.toJson(ds));
		} else {
		    routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(500)
		            .end();
		}
	}
	
	
	
	//POST PLACAs
	private void addOnePlaca(RoutingContext routingContext) {
		
		final Placas placa = gson.fromJson(routingContext.getBodyAsString(), Placas.class);
		placas.put(placa.getid(), placa);
		routingContext.response().setStatusCode(201).putHeader("content-type", "application/json; charset=utf-8")
				.end(gson.toJson(placa));
	}
	
	//POST SENSOR
		private void addOneSensor(RoutingContext routingContext) {
			
			final Sensores sensore = gson.fromJson(routingContext.getBodyAsString(), Sensores.class);
			sensores.put(sensore.getSensorId(), sensore);
			routingContext.response().setStatusCode(201).putHeader("content-type", "application/json; charset=utf-8")
					.end(gson.toJson(sensore));
		}
		
	//POST ACTUADOR
		private void addOneActuador(RoutingContext routingContext) {
					
			final Actuadores actuadore = gson.fromJson(routingContext.getBodyAsString(), Actuadores.class);
			actuadores.put(actuadore.getActuadoresId(), actuadore);
			routingContext.response().setStatusCode(201).putHeader("content-type", "application/json; charset=utf-8")
				.end(gson.toJson(actuadore));
				}	
	
	//CREAR DATOS RANDOM

		private void createSomeDataPlacas(int number) {
			Random rnd = new Random();
			IntStream.range(0, number).forEach(elem -> {
				int id = rnd.nextInt();
				placas.put(id, new Placas(id));
			});
		}
		private void createSomeDataSensores(int number) {
			Random rnd = new Random();
			IntStream.range(0, number).forEach(elem -> {
				int id = rnd.nextInt();
				sensores.put(id, new Sensores(id, getRandomPlaca()));
			});
		}
		private void createSomeDataActuadores(int number) {
			Random rnd = new Random();
			IntStream.range(0, number).forEach(elem -> {
				int id = rnd.nextInt();
				actuadores.put(id,new Actuadores(id, getRandomPlaca()));
			});
		}

		private Integer getRandomPlaca() {
			List<Integer> PlacasId = new ArrayList<>(placas.keySet());
			Collections.shuffle(PlacasId);
			return PlacasId.get(0);
		}

	
	
}
