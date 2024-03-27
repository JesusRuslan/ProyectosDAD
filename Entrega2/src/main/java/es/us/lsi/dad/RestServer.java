package es.us.lsi.dad;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
		createSomeData1(5);
		createSomeData2(5);
		createSomeData3(5);

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
		router.get("/api/placas/:id").handler(this::getOnePlaca);
		router.post("/api/placas").handler(this::addOnePlaca);
		
		router.route("/api/sensores*").handler(BodyHandler.create());
		router.get("/api/sensores/:id").handler(this::getOneSensor);
		router.post("/api/sensores").handler(this::addOneSensor);
		
		router.route("/api/actuadores*").handler(BodyHandler.create());
		router.get("/api/actuadores/:id").handler(this::getOneActuador);
		router.post("/api/actuadores").handler(this::addOneActuador);

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
	
	//POST PLACAs
	private void addOnePlaca(RoutingContext routingContext) {
		
		final Placas placas = gson.fromJson(routingContext.getBodyAsString(), Placas.class);
		placas.getid();
		routingContext.response().setStatusCode(201).putHeader("content-type", "application/json; charset=utf-8")
				.end(gson.toJson(placas));
	}
	
	//POST SENSOR
		private void addOneSensor(RoutingContext routingContext) {
			
			final Sensores sensores = gson.fromJson(routingContext.getBodyAsString(), Sensores.class);
			sensores.getSensorId();
			routingContext.response().setStatusCode(201).putHeader("content-type", "application/json; charset=utf-8")
					.end(gson.toJson(sensores));
		}
		
	//POST ACTUADOR
		private void addOneActuador(RoutingContext routingContext) {
					
			final Actuadores actuadores = gson.fromJson(routingContext.getBodyAsString(), Actuadores.class);
			actuadores.getActuadoresId();
			routingContext.response().setStatusCode(201).putHeader("content-type", "application/json; charset=utf-8")
				.end(gson.toJson(sensores));
				}	
	
	//CREAR DATOS RANDOM
	private void createSomeData1(int number) {
		Random rnd = new Random();
		IntStream.range(0, number).forEach(elem -> {
			int id = rnd.nextInt();
			placas.put(id, new Placas(id));
		});
	}
	private void createSomeData2(int number) {
		Random rnd = new Random();
		IntStream.range(0, number).forEach(elem -> {
			int id = rnd.nextInt();
			sensores.put(id, new Sensores(id));
		});
	}
	private void createSomeData3(int number) {
		Random rnd = new Random();
		IntStream.range(0, number).forEach(elem -> {
			int id = rnd.nextInt();
			actuadores.put(id, new Actuadores(id));
		});
	}
	
	
}
