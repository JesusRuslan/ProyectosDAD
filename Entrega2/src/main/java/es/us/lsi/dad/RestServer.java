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
		createSomeDataSensoresRepetidos(2);
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
		
		router.route("/api/placas*").handler(BodyHandler.create());
		router.get("/api/placas").handler(this::getAllPlaca);
		router.get("/api/placas/:id").handler(this::getOnePlaca);
		router.post("/api/placas").handler(this::addOnePlaca);
		
		//GETTERS ADICIONALES
		router.get("/api/sensores/registros").handler(this::getTresUltimosSensores);
		router.get("/api/placa/ultimo/:id").handler(this::getLastValueSensorFromPlaca);
		router.get("/api/sensores/ultimo/:id").handler(this::getLastValueSensorFromSensor);
		router.get("/api/actuadores/ultimo/:id").handler(this::getLastValueSensorFromActuador);
		router.get("/api/sensores/:id/valores").handler(this::getValuesSensor);
		router.get("/api/actuadores/:id/valores").handler(this::getValuesActuador);
		router.get("/api/placas/:id/valoresSensores").handler(this::getListaValoresSensoresIdPlaca);
		
		
		router.route("/api/sensores*").handler(BodyHandler.create());
		router.get("/api/sensores").handler(this::getAllSensor);
		router.get("/api/sensores/:id").handler(this::getOneSensor);
		router.post("/api/sensores").handler(this::addOneSensor);

		
		router.route("/api/actuadores*").handler(BodyHandler.create());
		router.get("/api/actuadores").handler(this::getAllActuador);
		router.get("/api/actuadores/:id").handler(this::getOneActuador);
		router.post("/api/actuadores").handler(this::addOneActuador);

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
		int id = 0;
		try {
			id = Integer.parseInt(routingContext.request().getParam("id"));
			final int comp = id;
			List<Sensores> ds = sensores.values().stream()
					.filter(s -> s.getSensorId()==comp)
					.toList();
			if (!ds.isEmpty()) {
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
						.setStatusCode(200).end(gson.toJson(ds));
			} else {
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
						.setStatusCode(204).end();
			}
		} catch (Exception e) {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(204)
					.end();
		}
	}
	//GET ID ACTUADORES
	private void getOneActuador(RoutingContext routingContext) {
		int id = 0;
		try {
			id = Integer.parseInt(routingContext.request().getParam("id"));
			final int comp = id;
			List<Actuadores> ds = actuadores.values().stream()
					.filter(s -> s.getActuadorId()==comp)
					.toList();
			if (!ds.isEmpty()) {
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
						.setStatusCode(200).end(gson.toJson(ds));
			} else {
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
						.setStatusCode(204).end();
			}
		} catch (Exception e) {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(204)
					.end();
		}
	}
	

	
// GET ULTIMOS 3 SENSORES
	 
	private void getTresUltimosSensores(RoutingContext routingContext) {

		
        // Obtener todas las entradas del mapa de actuadores
        List<Map.Entry<Integer, Sensores>> entradas = new ArrayList<>(sensores.entrySet());
        
		List<Sensores> sensoresLista = sensores.values().stream().sorted(Comparator.comparing(Sensores::getTiempo))
	            .collect(Collectors.toList());
		
        //Siendo [idValue:sensores]
        
        if (entradas.size() <= 3) {
            // devolver todas las entradas, si hay tres o menos elementos,
            routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
            .end(gson.toJson(entradas));
        } else {
            // Si hay más de tres valores, obtener los últimos tres 
            List<Map.Entry<Integer, Sensores>> ultimosTres = entradas.subList(entradas.size() - 3, entradas.size());
            List<Sensores> lastThreeActuadores = ultimosTres.stream().map(Map.Entry::getValue).collect(Collectors.toList());

            routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
            .end(gson.toJson(lastThreeActuadores));
        }
    }

	// GET HISTORIAL DE VALORES DE UN SENSOR
	private void getValuesSensor(RoutingContext routingContext) {
		 int id = Integer.parseInt(routingContext.request().getParam("id"));
		Map<Integer,List<Double>> idSensorValores= new HashMap<Integer, List<Double>>();
		try {

			
			List<Sensores> acc = sensores.values().stream().filter(e->e.getSensorId()==id).toList(); 
			for(int i=0;i<acc.size();i++) {
				Sensores sensor=acc.get(i);
				idSensorValores.put(sensor.getValueId(), sensor.getValor()) ;
			}
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
			.end(gson.toJson(idSensorValores.toString()));
		} catch (Exception e) {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(204)
			.end();
		}
	}
	// GET HISTORIAL DE VALORES DE UN ACTUADOR
	private void getValuesActuador(RoutingContext routingContext) {
		 int id = Integer.parseInt(routingContext.request().getParam("id"));
		Map<Integer,List<Double>> idSensorValores= new HashMap<Integer, List<Double>>();
		try {

			
			List<Actuadores> acc = actuadores.values().stream().filter(e->e.getActuadorId()==id).toList(); 
			for(int i=0;i<acc.size();i++) {
				Actuadores actuador=acc.get(i);
				idSensorValores.put(actuador.getValueId(), actuador.getValor()) ;
			}
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
			.end(gson.toJson(idSensorValores.toString()));
		} catch (Exception e) {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(204)
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
	
// GET ULTIMO VALOR RECOGIDO DE LA PLACA
	private void getLastValueSensorFromPlaca(RoutingContext routingContext) {
		int id = 0;
		try {
			id = Integer.parseInt(routingContext.request().getParam("id"));
			final int comp = id;
			Sensores ds = sensores.values().stream()
					.filter(a -> a.getPlacaId() == comp)
					.sorted(Comparator.comparing(Sensores::getValueId)
							.reversed())
					.findFirst()
					.orElse(null);
			if (ds != null) {
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
				.setStatusCode(200).end(gson.toJson(ds));
			} else {
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
				.setStatusCode(204).end();
			}
		} catch (Exception e) {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(204)
			.end();
		}
	}
	
	// GET ULTIMO VALOR RECOGIDO DE UN SENSOR
	
	private void getLastValueSensorFromSensor(RoutingContext routingContext) {
		int id = 0;
		try {
			id = Integer.parseInt(routingContext.request().getParam("id"));
			final int comp = id;
			Sensores ds = sensores.values().stream()
					.filter(a -> a.getSensorId() == comp)
					.sorted(Comparator.comparing(Sensores::getValueId)
							.reversed())
					.findFirst()
					.orElse(null);
			if (ds != null) {
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
				.setStatusCode(200).end(gson.toJson(ds));
			} else {
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
				.setStatusCode(204).end();
			}
		} catch (Exception e) {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(204)
			.end();
		}
	}
		// GET ULTIMO VALOR RECOGIDO DE UN ACTUADOR
		
		private void getLastValueSensorFromActuador(RoutingContext routingContext) {
			int id = 0;
			try {
				id = Integer.parseInt(routingContext.request().getParam("id"));
				final int comp = id;
				Actuadores ds = actuadores.values().stream()
						.filter(a -> a.getActuadorId()== comp)
						.sorted(Comparator.comparing(Actuadores::getValueId)
								.reversed())
						.findFirst()
						.orElse(null);
				if (ds != null) {
					routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
					.setStatusCode(200).end(gson.toJson(ds));
				} else {
					routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
					.setStatusCode(204).end();
				}
			} catch (Exception e) {
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(204)
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
			final Sensores aux = gson.fromJson(routingContext.getBodyAsString(), Sensores.class);
			final Sensores sensor = new Sensores(
					aux.getSensorId(),
					aux.getPlacaId(),
					aux.getValor());
			sensores.put(sensor.getValueId(), sensor);
			routingContext.response().setStatusCode(201).putHeader("content-type", "application/json; charset=utf-8")
					.end(gson.toJson(sensor));
		}
		//POST ACTUADOR
		private void addOneActuador(RoutingContext routingContext) {
			final Actuadores aux = gson.fromJson(routingContext.getBodyAsString(), Actuadores.class);
			final Actuadores actuador = new Actuadores(
					aux.getActuadorId(),
					aux.getPlacaId(),
					aux.getValor());
			actuadores.put(actuador.getValueId(), actuador);
			routingContext.response().setStatusCode(201).putHeader("content-type", "application/json; charset=utf-8")
					.end(gson.toJson(actuador));
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
				Sensores aux = new Sensores(id, getRandomPlaca());
				sensores.put(aux.getValueId(), aux);
			});
		}
		
		private void createSomeDataSensoresRepetidos(int number) {
			Random rnd = new Random();
			IntStream.range(0, number).forEach(elem -> {
				int id = rnd.nextInt();
				Sensores aux = new Sensores(getRandomSensores(), getRandomPlaca());
				sensores.put(aux.getValueId(), aux);
			});
		}
		private void createSomeDataActuadores(int number) {
			Random rnd = new Random();
			IntStream.range(0, number).forEach(elem -> {
				int id = rnd.nextInt();
				Actuadores aux = new Actuadores(id, getRandomPlaca());
				actuadores.put(aux.getValueId(), aux);
			});
		}

		private Integer getRandomPlaca() {
			List<Integer> PlacasId = new ArrayList<>(placas.keySet());
			Collections.shuffle(PlacasId);
			return PlacasId.get(0);
		}

		private Integer getRandomSensores() {
			List<Integer> SensorId = sensores.values().stream().map(Sensores:: getSensorId).collect(Collectors.toList());
			Collections.shuffle(SensorId);
			return SensorId.get(0);
		}
	
}
