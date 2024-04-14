package es.us.lsi.dad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class RestServer extends AbstractVerticle {

	private Map<Integer, Placa> placas = new HashMap<>();
	private Map<Integer, Sensor> sensores = new HashMap<>();
	private Map<Integer, Actuador> actuadores = new HashMap<>();
	private Gson gson;

	public void start(Promise<Void> startFuture) {
		// Creating some synthetic data
		createSomeDataPlacas(3);
		createSomeDataSensores(20);
		createSomeDataActuadores(15);

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
		router.route("/api/placas*").handler(BodyHandler.create());
		router.get("/api/placas").handler(this::getAllPlaca);
		router.get("/api/placas/:id").handler(this::getOnePlaca);
		router.post("/api/placas").handler(this::addOnePlaca);
		router.get("/api/placas/:id/sensores").handler(this::getSensoresPlaca);
		router.get("/api/placas/:id/actuadores").handler(this::getActuadoresPlaca);
		
		router.route("/api/sensores*").handler(BodyHandler.create());
		router.get("/api/sensores").handler(this::getAllSensor);
		router.get("/api/sensores/:id").handler(this::getOneSensor);
		router.post("/api/sensores").handler(this::addOneSensor);
		router.get("api/sensores/:id/last").handler(this::getLastValueSensor());
		
		router.route("/api/actuadores*").handler(BodyHandler.create());
		router.get("/api/actuadores").handler(this::getAllActuador);
		router.get("/api/actuadores/:id").handler(this::getOneActuador);
		router.post("/api/actuadores").handler(this::addOneActuador);
		router.get("api/actuadores/:id/last").handler(this::getLastValueActuador());
	}

	@Override
	public void stop(Promise<Void> stopPromise) throws Exception {
		try {
			placas.clear();
			sensores.clear();
			actuadores.clear();
			stopPromise.complete();
		} catch (Exception e) {
			stopPromise.fail(e);
		}
		super.stop(stopPromise);
	}

	@SuppressWarnings("unused")
	private void getAllPlaca(RoutingContext routingContext) {
		routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
			.setStatusCode(200).end(gson.toJson(placas.values()));
	}
	@SuppressWarnings("unused")
	private void getAllSensor(RoutingContext routingContext) {
		routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
			.setStatusCode(200).end(gson.toJson(sensores.values()));
	}
	@SuppressWarnings("unused")
	private void getAllActuador(RoutingContext routingContext) {
		routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
			.setStatusCode(200).end(gson.toJson(actuadores.values()));
	}
	

	/*
	private void getAllWithParams(RoutingContext routingContext) {
		final String name = routingContext.queryParams().contains("name") ? routingContext.queryParam("name").get(0)
				: null;
		final String surname = routingContext.queryParams().contains("surname")
				? routingContext.queryParam("surname").get(0)
				: null;
		final String username = routingContext.queryParams().contains("username")
				? routingContext.queryParam("username").get(0)
				: null;

		routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
				.end(gson.toJson(new UserEntityListWrapper(users.values().stream().filter(elem -> {
					boolean res = true;
					res = res && (name != null ? elem.getName().equals(name) : true);
					res = res && (surname != null ? elem.getSurname().equals(surname) : true);
					res = res && (username != null ? elem.getUsername().equals(username) : true);
					return res;
				}).collect(Collectors.toList()))));
	}
	*/

	private void getOnePlaca(RoutingContext routingContext) {
		int id = 0;
		try {
			id = Integer.parseInt(routingContext.request().getParam("id"));

			if (placas.containsKey(id)) {
				Placa ds = placas.get(id);
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
						.setStatusCode(200).end(ds != null ? gson.toJson(ds) : "");
			} else {
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
						.setStatusCode(204).end();
			}
		} catch (Exception e) {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(204)
					.end();
		}
	}
	private void getOneSensor(RoutingContext routingContext) {
		int id = 0;
		try {
			id = Integer.parseInt(routingContext.request().getParam("id"));

			if (sensores.containsKey(id)) {
				Sensor ds = sensores.get(id);
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
						.setStatusCode(200).end(ds != null ? gson.toJson(ds) : "");
			} else {
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
						.setStatusCode(204).end();
			}
		} catch (Exception e) {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(204)
					.end();
		}
	}
	private void getOneActuador(RoutingContext routingContext) {
		int id = 0;
		try {
			id = Integer.parseInt(routingContext.request().getParam("id"));

			if (actuadores.containsKey(id)) {
				Actuador ds = actuadores.get(id);
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
						.setStatusCode(200).end(ds != null ? gson.toJson(ds) : "");
			} else {
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
						.setStatusCode(204).end();
			}
		} catch (Exception e) {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(204)
					.end();
		}
	}

	private void addOnePlaca(RoutingContext routingContext) {
		final Placa placa = gson.fromJson(routingContext.getBodyAsString(), Placa.class);
		placas.put(placa.getId(), placa);
		routingContext.response().setStatusCode(201).putHeader("content-type", "application/json; charset=utf-8")
				.end(gson.toJson(placa));
	}
	private void addOneSensor(RoutingContext routingContext) {
		final Sensor sensor = gson.fromJson(routingContext.getBodyAsString(), Sensor.class);
		sensores.put(sensor.getSensorId(), sensor);
		routingContext.response().setStatusCode(201).putHeader("content-type", "application/json; charset=utf-8")
				.end(gson.toJson(sensor));
	}
	private void addOneActuador(RoutingContext routingContext) {
		final Actuador actuador = gson.fromJson(routingContext.getBodyAsString(), Actuador.class);
		actuadores.put(actuador.getActuadorId(), actuador);
		routingContext.response().setStatusCode(201).putHeader("content-type", "application/json; charset=utf-8")
				.end(gson.toJson(actuador));
	}
	
	private void getSensoresPlaca(RoutingContext routingContext) {
		int id = 0;
		try {
			id = Integer.parseInt(routingContext.request().getParam("id"));
			final int comp = id;
			if (placas.containsKey(id)) {
				List<Sensor> ds = sensores.values().stream()
						.filter(s -> s.getPlacaId() == comp)
						.toList();
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
						.setStatusCode(200).end(!ds.isEmpty() ? gson.toJson(ds) : "");
			} else {
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
						.setStatusCode(204).end();
			}
		} catch (Exception e) {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(204)
					.end();
		}
	}
	private void getActuadoresPlaca(RoutingContext routingContext) {
		int id = 0;
		try {
			id = Integer.parseInt(routingContext.request().getParam("id"));
			final int comp = id;
			if (placas.containsKey(id)) {
				List<Actuador> ds = actuadores.values().stream()
						.filter(a -> a.getPlacaId() == comp)
						.toList();
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
						.setStatusCode(200).end(!ds.isEmpty() ? gson.toJson(ds) : "");
			} else {
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
						.setStatusCode(204).end();
			}
		} catch (Exception e) {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(204)
					.end();
		}
	}
	
	private void getLastValueSensor(RoutingContext routingContext) {
		int id = 0;
		try {
			id = Integer.parseInt(routingContext.request().getParam("id"));
			final int comp = id;
			if (placas.containsKey(id)) {
				List<Actuador> ds = actuadores.values().stream()
						.filter(a -> a.getPlacaId() == comp)
						.toList();
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
						.setStatusCode(200).end(!ds.isEmpty() ? gson.toJson(ds) : "");
			} else {
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
						.setStatusCode(204).end();
			}
		} catch (Exception e) {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(204)
					.end();
		}
	}
	
	private void getLastValueActuador(RoutingContext routingContext) {
		
	}

	/*
	private void deleteOne(RoutingContext routingContext) {
		int id = Integer.parseInt(routingContext.request().getParam("userid"));
		if (users.containsKey(id)) {
			UserEntity user = users.get(id);
			users.remove(id);
			routingContext.response().setStatusCode(200).putHeader("content-type", "application/json; charset=utf-8")
					.end(gson.toJson(user));
		} else {
			routingContext.response().setStatusCode(204).putHeader("content-type", "application/json; charset=utf-8")
					.end();
		}
	}
	*/
	/*
	private void putOne(RoutingContext routingContext) {
		int id = Integer.parseInt(routingContext.request().getParam("userid"));
		UserEntity ds = users.get(id);
		final UserEntity element = gson.fromJson(routingContext.getBodyAsString(), UserEntity.class);
		ds.setName(element.getName());
		ds.setSurname(element.getSurname());
		ds.setBirthdate(element.getBirthdate());
		ds.setPassword(element.getPassword());
		ds.setUsername(element.getUsername());
		users.put(ds.getIdusers(), ds);
		routingContext.response().setStatusCode(201).putHeader("content-type", "application/json; charset=utf-8")
				.end(gson.toJson(element));
	}
	*/

	private void createSomeDataPlacas(int number) {
		Random rnd = new Random();
		IntStream.range(0, number).forEach(elem -> {
			int id = rnd.nextInt();
			placas.put(id, new Placa(id));
		});
	}
	private void createSomeDataSensores(int number) {
		Random rnd = new Random();
		IntStream.range(0, number).forEach(elem -> {
			int id = rnd.nextInt();
			sensores.put(id, new Sensor(id, getRandomPlaca()));
		});
	}
	private void createSomeDataActuadores(int number) {
		Random rnd = new Random();
		IntStream.range(0, number).forEach(elem -> {
			int id = rnd.nextInt();
			actuadores.put(id, new Actuador(id, getRandomPlaca()));
		});
	}

	private Integer getRandomPlaca() {
		List<Integer> PlacasId = new ArrayList<>(placas.keySet());
		Collections.shuffle(PlacasId);
		return PlacasId.get(0);
	}
}
