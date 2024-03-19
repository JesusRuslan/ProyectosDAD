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

	private Map<Integer,Placa1> placa1 = new HashMap<Integer, Placa1>();
	private Map<Integer,Placa2> placa2 = new HashMap<Integer, Placa2>();
	
	private Gson gson;

	public void start(Promise<Void> startFuture) {
		// Creating some synthetic data
		createSomeData1(5);
		createSomeData2(5);

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
		router.route("/api/placa1*").handler(BodyHandler.create());
		router.route("/api/placa2*").handler(BodyHandler.create());
		router.get("/api/placa1/:id").handler(this::getOneP1);
		router.get("/api/placa2/:id").handler(this::getOneP2);
		router.post("/api/placa1").handler(this::addOneP1);
		router.post("/api/placa2").handler(this::addOneP2);
		/*
		router.route("/api/users*").handler(BodyHandler.create());
		router.get("/api/users").handler(this::getAllWithParams);
		router.get("/api/users/:userid").handler(this::getOne);
		router.post("/api/users").handler(this::addOne);
		router.delete("/api/users/:userid").handler(this::deleteOne);
		router.put("/api/users/:userid").handler(this::putOne);
		*/
	}


	
	
	//GET SENSORES PLACA1
	private void getOneP1(RoutingContext routingContext) {
		
		int id = Integer.parseInt(routingContext.request().getParam("id"));
		
		if (placa1.containsKey(id)) {
			
			Placa1 ds = placa1.get(id);
			
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
					.end(gson.toJson(ds));
		} else {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(204)
					.end();
		}
	}
	
	//GET SENSORES PLACA2
	private void getOneP2(RoutingContext routingContext) {
		
		int id = Integer.parseInt(routingContext.request().getParam("id"));
		
		if (placa2.containsKey(id)) {
			
			Placa2 ds = placa2.get(id);
			
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
					.end(gson.toJson(ds));
		} else {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(204)
					.end();
		}
	}
	
	
	//POST PLACA1
	private void addOneP1(RoutingContext routingContext) {
		
		final Placa1 placa = gson.fromJson(routingContext.getBodyAsString(), Placa1.class);
		placa1.put(placa.getId(),placa);
		routingContext.response().setStatusCode(201).putHeader("content-type", "application/json; charset=utf-8")
				.end(gson.toJson(placa));
	}
	
	//POST PLACA2
	private void addOneP2(RoutingContext routingContext) {
		
		final Placa2 placal = gson.fromJson(routingContext.getBodyAsString(), Placa2.class);
		placa2.put(placal.getId(),placal);
		routingContext.response().setStatusCode(201).putHeader("content-type", "application/json; charset=utf-8")
				.end(gson.toJson(placal));
	}
	
	
	//CREAR DATOS RANDOM
	private void createSomeData1(int number) {
		Random rnd = new Random();
		IntStream.range(0, number).forEach(elem -> {
			int id = rnd.nextInt();
			placa1.put(id, new Placa1(id));
		});
	}
	private void createSomeData2(int number) {
		Random rnd = new Random();
		IntStream.range(0, number).forEach(elem -> {
			int id = rnd.nextInt();
			placa2.put(id, new Placa2(id));
		});
	}
	
	/*
	private void createSomeData(int number) {
		Random rnd = new Random();
		IntStream.range(0, number).forEach(elem -> {
			int id = rnd.nextInt();
			users.put(id, new UserEntity(id, "Nombre_" + id, "Apellido_" + id,
					new Date(Calendar.getInstance().getTimeInMillis() + id), "Username_" + id, "Password_" + id));
		});
	}
	*/
	
}
