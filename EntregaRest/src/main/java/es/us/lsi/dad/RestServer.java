package es.us.lsi.dad;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.Tuple;

public class RestServer extends AbstractVerticle {

	MySQLPool mySqlClient;
	private Gson gson;

	@Override
	public void start(Promise<Void> startFuture) {
		// Creating some synthetic data
		//		createSomeDataPlacas(3);
		//		createSomeDataSensores(20);
		//		createSomeDataActuadores(15);

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
		router.get("/api/placas").handler(this::getAllPlacas);
		router.get("/api/placas/one").handler(this::getOnePlaca);
		router.get("/api/placas/:id").handler(this::getPlacaById);
		router.post("/api/placas").handler(this::addOnePlaca);
		router.get("/api/placas/:id/sensores").handler(this::getSensoresPlaca);
		router.get("/api/placas/:id/actuadores").handler(this::getActuadoresPlaca);

		router.route("/api/sensores*").handler(BodyHandler.create());
		router.get("/api/sensores").handler(this::getAllSensores);
		router.get("/api/sensores/one").handler(this::getOneSensor);
		router.get("/api/sensores/:id").handler(this::getSensorById);
		router.post("/api/sensores").handler(this::addOneSensor);
		router.get("/api/sensores/:id/values").handler(this::getValuesSensor);
		router.get("/api/sensores/:id/last").handler(this::getLastValueSensor);
		router.get("/api/sensores/:id/three").handler(this::getLastThreeValueSensor);

		router.route("/api/actuadores*").handler(BodyHandler.create());
		router.get("/api/actuadores").handler(this::getAllActuadores);
		router.get("/api/actuadores/one").handler(this::getOneActuador);
		router.get("/api/actuadores/:id").handler(this::getActuadorById);
		router.post("/api/actuadores").handler(this::addOneActuador);

		MySQLConnectOptions rootOptions = new MySQLConnectOptions()
				.setPort(3306)
				.setHost("localhost")
				.setDatabase("RestServerDAD")
				.setUser("root")
				.setPassword("dadroot");

		PoolOptions poolOptions = new PoolOptions().setMaxSize(5);

		mySqlClient = MySQLPool.pool(vertx, rootOptions, poolOptions);
	}

	@Override
	public void stop(Promise<Void> stopPromise) throws Exception {
		try {
			stopPromise.complete();
		} catch (Exception e) {
			stopPromise.fail(e);
		}
		super.stop(stopPromise);
	}

	private void getAllPlacas(RoutingContext routingContext) {
		mySqlClient.getConnection(connection -> {
			if (connection.succeeded()) {
				connection.result().preparedQuery(
						"SELECT * FROM Placas;",
						res -> {
							if (res.succeeded()) {
								// Get the result set
								RowSet<Row> resultSet = res.result();
								JsonArray result = new JsonArray();
								for (Row elem : resultSet) {
									result.add(JsonObject.mapFrom(new Placa(
											elem.getInteger("idValue"),
											elem.getInteger("idPlaca"),
											elem.getInteger("idGroup"))));
								}
								if (result.isEmpty()) {
									routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
									.setStatusCode(404).end("No se encontraron placas");
								} else {
									routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
									.setStatusCode(200).end(result.toString());
								}
							} else {
								routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
								.setStatusCode(500).end("Error: " + res.cause().getLocalizedMessage());
							}
							connection.result().close();
						});
			} else {
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
				.setStatusCode(500).end("Error al conectar: " + connection.cause().toString());
			}
		});
	}
	private void getAllSensores(RoutingContext routingContext) {
		mySqlClient.getConnection(connection -> {
			if (connection.succeeded()) {
				connection.result().preparedQuery(
						"SELECT * FROM Sensores;",
						res -> {
							if (res.succeeded()) {
								// Get the result set
								RowSet<Row> resultSet = res.result();
								JsonArray result = new JsonArray();
								for (Row elem : resultSet) {
									result.add(JsonObject.mapFrom(new Sensor(
											elem.getInteger("idValue"),
											elem.getInteger("idSensor"),
											elem.getInteger("idPlaca"),
											elem.getDouble("valor1"),
											elem.getDouble("valor2"),
											elem.getInteger("tipoSensor"),
											elem.getLong("tiempo"),
											elem.getInteger("idGroup"))));
								}
								if (result.isEmpty()) {
									routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
									.setStatusCode(404).end("No se encontraron sensores");
								} else {
									routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
									.setStatusCode(200).end(result.toString());
								}
							} else {
								routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
								.setStatusCode(500).end("Error: " + res.cause().getLocalizedMessage());
							}
							connection.result().close();
						});
			} else {
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
				.setStatusCode(500).end("Error al conectar: " + connection.cause().toString());
			}
		});
	}
	private void getAllActuadores(RoutingContext routingContext) {
		mySqlClient.getConnection(connection -> {
			if (connection.succeeded()) {
				connection.result().preparedQuery(
						"SELECT * FROM Actuadores;",
						res -> {
							if (res.succeeded()) {
								// Get the result set
								RowSet<Row> resultSet = res.result();
								JsonArray result = new JsonArray();
								for (Row elem : resultSet) {
									result.add(JsonObject.mapFrom(new Actuador(
											elem.getInteger("idValue"),
											elem.getInteger("idActuador"),
											elem.getInteger("idPlaca"),
											elem.getDouble("valor"),
											elem.getInteger("tipoActuador"),
											elem.getLong("tiempo"),
											elem.getInteger("idGroup"))));
								}
								if (result.isEmpty()) {
									routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
									.setStatusCode(404).end("No se encontraron actuadores");
								} else {
									routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
									.setStatusCode(200).end(result.toString());
								}
							} else {
								routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
								.setStatusCode(500).end("Error: " + res.cause().getLocalizedMessage());
							}
							connection.result().close();
						});
			} else {
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
				.setStatusCode(500).end("Error al conectar: " + connection.cause().toString());
			}
		});
	}

	private void getOnePlaca(RoutingContext routingContext) {
		mySqlClient.getConnection(connection -> {
			if (connection.succeeded()) {
				connection.result().preparedQuery(
						"SELECT * FROM Placas LIMIT 1;",
						res -> {
							if (res.succeeded()) {
								// Get the result set
								RowSet<Row> resultSet = res.result();
								JsonArray result = new JsonArray();
								for (Row elem : resultSet) {
									result.add(JsonObject.mapFrom(new Placa(
											elem.getInteger("idValue"),
											elem.getInteger("idPlaca"),
											elem.getInteger("idGroup"))));
								}
								if (result.isEmpty()) {
									routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
									.setStatusCode(404).end("No se encontraron placas");
								} else {
									routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
									.setStatusCode(200).end(result.toString());
								}
							} else {
								routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
								.setStatusCode(500).end("Error: " + res.cause().getLocalizedMessage());
							}
							connection.result().close();
						});
			} else {
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
				.setStatusCode(500).end("Error al conectar: " + connection.cause().toString());
			}
		});
	}
	private void getOneSensor(RoutingContext routingContext) {
		mySqlClient.getConnection(connection -> {
			if (connection.succeeded()) {
				connection.result().preparedQuery(
						"SELECT * FROM Sensores LIMIT 1;",
						res -> {
							if (res.succeeded()) {
								// Get the result set
								RowSet<Row> resultSet = res.result();
								JsonArray result = new JsonArray();
								for (Row elem : resultSet) {
									result.add(JsonObject.mapFrom(new Sensor(
											elem.getInteger("idValue"),
											elem.getInteger("idSensor"),
											elem.getInteger("idPlaca"),
											elem.getDouble("valor1"),
											elem.getDouble("valor2"),
											elem.getInteger("tipoSensor"),
											elem.getLong("tiempo"),
											elem.getInteger("idGroup"))));
								}
								if (result.isEmpty()) {
									routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
									.setStatusCode(404).end("No se encontraron sensores");
								} else {
									routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
									.setStatusCode(200).end(result.toString());
								}
							} else {
								routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
								.setStatusCode(500).end("Error: " + res.cause().getLocalizedMessage());
							}
							connection.result().close();
						});
			} else {
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
				.setStatusCode(500).end("Error al conectar: " + connection.cause().toString());
			}
		});
	}
	private void getOneActuador(RoutingContext routingContext) {
		mySqlClient.getConnection(connection -> {
			if (connection.succeeded()) {
				connection.result().preparedQuery(
						"SELECT * FROM Actuadores LIMIT 1;",
						res -> {
							if (res.succeeded()) {
								// Get the result set
								RowSet<Row> resultSet = res.result();
								JsonArray result = new JsonArray();
								for (Row elem : resultSet) {
									result.add(JsonObject.mapFrom(new Actuador(
											elem.getInteger("idValue"),
											elem.getInteger("idActuador"),
											elem.getInteger("idPlaca"),
											elem.getDouble("valor"),
											elem.getInteger("tipoActuador"),
											elem.getLong("tiempo"),
											elem.getInteger("idGroup"))));
								}
								if (result.isEmpty()) {
									routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
									.setStatusCode(404).end("No se encontraron actuadores");
								} else {
									routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
									.setStatusCode(200).end(result.toString());
								}
							} else {
								routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
								.setStatusCode(500).end("Error: " + res.cause().getLocalizedMessage());
							}
							connection.result().close();
						});
			} else {
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
				.setStatusCode(500).end("Error al conectar: " + connection.cause().toString());
			}
		});
	}

	private void getPlacaById(RoutingContext routingContext) {
		int id = 0;
		try {
			id = Integer.parseInt(routingContext.request().getParam("id"));
			final int comp = id;
			mySqlClient.getConnection(connection -> {
				if (connection.succeeded()) {
					connection.result().preparedQuery(
							"SELECT * FROM Placas WHERE idPlaca = ?;",
							Tuple.of(comp),
							res -> {
								if (res.succeeded()) {
									// Get the result set
									RowSet<Row> resultSet = res.result();
									JsonArray result = new JsonArray();
									for (Row elem : resultSet) {
										result.add(JsonObject.mapFrom(new Placa(
												elem.getInteger("idValue"),
												elem.getInteger("idPlaca"),
												elem.getInteger("idGroup"))));
									}
									if (result.isEmpty()) {
										routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
										.setStatusCode(404).end("No se encontraron placas");
									} else {
										routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
										.setStatusCode(200).end(result.toString());
									}
								} else {
									routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
									.setStatusCode(500).end("Error: " + res.cause().getLocalizedMessage());
								}
								connection.result().close();
							});
				} else {
					routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
					.setStatusCode(500).end("Error al conectar: " + connection.cause().toString());
				}
			});
		} catch (NumberFormatException e) {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(400)
			.end("Error: " + e.getLocalizedMessage());
		}
	}
	private void getSensorById(RoutingContext routingContext) {
		int id = 0;
		try {
			id = Integer.parseInt(routingContext.request().getParam("id"));
			final int comp = id;
			mySqlClient.getConnection(connection -> {
				if (connection.succeeded()) {
					connection.result().preparedQuery(
							"SELECT * FROM Sensores WHERE idSensor = ? LIMIT 1;",
							Tuple.of(comp),
							res -> {
								if (res.succeeded()) {
									// Get the result set
									RowSet<Row> resultSet = res.result();
									JsonArray result = new JsonArray();
									for (Row elem : resultSet) {
										result.add(JsonObject.mapFrom(new Sensor(
												elem.getInteger("idValue"),
												elem.getInteger("idSensor"),
												elem.getInteger("idPlaca"),
												elem.getDouble("valor1"),
												elem.getDouble("valor2"),
												elem.getInteger("tipoSensor"),
												elem.getLong("tiempo"),
												elem.getInteger("idGroup"))));
									}
									if (result.isEmpty()) {
										routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
										.setStatusCode(404).end("No se encontraron sensores");
									} else {
										routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
										.setStatusCode(200).end(result.toString());
									}
								} else {
									routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
									.setStatusCode(500).end("Error: " + res.cause().getLocalizedMessage());
								}
								connection.result().close();
							});
				} else {
					routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
					.setStatusCode(500).end("Error al conectar: " + connection.cause().toString());
				}
			});
		} catch (NumberFormatException e) {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(400)
			.end("Error: " + e.getLocalizedMessage());
		}
	}
	private void getActuadorById(RoutingContext routingContext) {
		int id = 0;
		try {
			id = Integer.parseInt(routingContext.request().getParam("id"));
			final int comp = id;
			mySqlClient.getConnection(connection -> {
				if (connection.succeeded()) {
					connection.result().preparedQuery(
							"SELECT * FROM Actuadores WHERE idActuador = ?;",
							Tuple.of(comp),
							res -> {
								if (res.succeeded()) {
									// Get the result set
									RowSet<Row> resultSet = res.result();
									JsonArray result = new JsonArray();
									for (Row elem : resultSet) {
										result.add(JsonObject.mapFrom(new Actuador(
												elem.getInteger("idValue"),
												elem.getInteger("idActuador"),
												elem.getInteger("idPlaca"),
												elem.getDouble("valor"),
												elem.getInteger("tipoActuador"),
												elem.getLong("tiempo"),
												elem.getInteger("idGroup"))));
									}
									if (result.isEmpty()) {
										routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
										.setStatusCode(404).end("No se encontraron actuadores");
									} else {
										routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
										.setStatusCode(200).end(result.toString());
									}
								} else {
									routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
									.setStatusCode(500).end("Error: " + res.cause().getLocalizedMessage());
								}
								connection.result().close();
							});
				} else {
					routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
					.setStatusCode(500).end("Error al conectar: " + connection.cause().toString());
				}
			});
		} catch (NumberFormatException e) {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(400)
			.end("Error: " + e.getLocalizedMessage());
		}
	}

	private void getSensoresPlaca(RoutingContext routingContext) {
		int id = 0;
		try {
			id = Integer.parseInt(routingContext.request().getParam("id"));
			final int comp = id;
			mySqlClient.getConnection(connection -> {
				if (connection.succeeded()) {
					connection.result().preparedQuery(
							"SELECT * FROM Sensores WHERE idPlaca = ?;",
							Tuple.of(comp),
							res -> {
								if (res.succeeded()) {
									// Get the result set
									RowSet<Row> resultSet = res.result();
									JsonArray result = new JsonArray();
									for (Row elem : resultSet) {
										result.add(JsonObject.mapFrom(new Sensor(
												elem.getInteger("idValue"),
												elem.getInteger("idSensor"),
												elem.getInteger("idPlaca"),
												elem.getDouble("valor1"),
												elem.getDouble("valor2"),
												elem.getInteger("tipoSensor"),
												elem.getLong("tiempo"),
												elem.getInteger("idGroup"))));
									}
									if (result.isEmpty()) {
										routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
										.setStatusCode(404).end("No se encontraron sensores");
									} else {
										routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
										.setStatusCode(200).end(result.toString());
									}
								} else {
									routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
									.setStatusCode(500).end("Error: " + res.cause().getLocalizedMessage());
								}
								connection.result().close();
							});
				} else {
					routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
					.setStatusCode(500).end("Error al conectar: " + connection.cause().toString());
				}
			});
		} catch (NumberFormatException e) {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(400)
			.end("Error: " + e.getLocalizedMessage());
		}
	}
	private void getActuadoresPlaca(RoutingContext routingContext) {
		int id = 0;
		try {
			id = Integer.parseInt(routingContext.request().getParam("id"));
			final int comp = id;
			mySqlClient.getConnection(connection -> {
				if (connection.succeeded()) {
					connection.result().preparedQuery(
							"SELECT * FROM Actuadores WHERE idPlaca = ?;",
							Tuple.of(comp),
							res -> {
								if (res.succeeded()) {
									// Get the result set
									RowSet<Row> resultSet = res.result();
									JsonArray result = new JsonArray();
									for (Row elem : resultSet) {
										result.add(JsonObject.mapFrom(new Actuador(
												elem.getInteger("idValue"),
												elem.getInteger("idActuador"),
												elem.getInteger("idPlaca"),
												elem.getDouble("valor"),
												elem.getInteger("tipoActuador"),
												elem.getLong("tiempo"),
												elem.getInteger("idGroup"))));
									}
									if (result.isEmpty()) {
										routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
										.setStatusCode(404).end("No se encontraron actuadores");
									} else {
										routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
										.setStatusCode(200).end(result.toString());
									}
								} else {
									routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
									.setStatusCode(500).end("Error: " + res.cause().getLocalizedMessage());
								}
								connection.result().close();
							});
				} else {
					routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
					.setStatusCode(500).end("Error al conectar: " + connection.cause().toString());
				}
			});
		} catch (NumberFormatException e) {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(400)
			.end("Error: " + e.getLocalizedMessage());
		}
	}

	private void getValuesSensor(RoutingContext routingContext) {
		int id = 0;
		try {
			id = Integer.parseInt(routingContext.request().getParam("id"));
			final int comp = id;
			mySqlClient.getConnection(connection -> {
				if (connection.succeeded()) {
					connection.result().preparedQuery(
							"SELECT * FROM Sensores WHERE idSensor = ? ORDER BY idValue DESC;",
							Tuple.of(comp),
							res -> {
								if (res.succeeded()) {
									// Get the result set
									RowSet<Row> resultSet = res.result();
									JsonArray result = new JsonArray();
									for (Row elem : resultSet) {
										result.add(JsonObject.mapFrom(new Sensor(
												elem.getInteger("idValue"),
												elem.getInteger("idSensor"),
												elem.getInteger("idPlaca"),
												elem.getDouble("valor1"),
												elem.getDouble("valor2"),
												elem.getInteger("tipoSensor"),
												elem.getLong("tiempo"),
												elem.getInteger("idGroup"))));
									}
									if (result.isEmpty()) {
										routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
										.setStatusCode(404).end("No se encontraron sensores");
									} else {
										routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
										.setStatusCode(200).end(result.toString());
									}
								} else {
									routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
									.setStatusCode(500).end("Error: " + res.cause().getLocalizedMessage());
								}
								connection.result().close();
							});
				} else {
					routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
					.setStatusCode(500).end("Error al conectar: " + connection.cause().toString());
				}
			});
		} catch (NumberFormatException e) {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(400)
			.end("Error: " + e.getLocalizedMessage());
		}
	}
	private void getLastValueSensor(RoutingContext routingContext) {
		int id = 0;
		try {
			id = Integer.parseInt(routingContext.request().getParam("id"));
			final int comp = id;
			mySqlClient.getConnection(connection -> {
				if (connection.succeeded()) {
					connection.result().preparedQuery(
							"SELECT * FROM Sensores WHERE idSensor = ? ORDER BY idValue DESC LIMIT 1;",
							Tuple.of(comp),
							res -> {
								if (res.succeeded()) {
									// Get the result set
									RowSet<Row> resultSet = res.result();
									JsonArray result = new JsonArray();
									for (Row elem : resultSet) {
										result.add(JsonObject.mapFrom(new Sensor(
												elem.getInteger("idValue"),
												elem.getInteger("idSensor"),
												elem.getInteger("idPlaca"),
												elem.getDouble("valor1"),
												elem.getDouble("valor2"),
												elem.getInteger("tipoSensor"),
												elem.getLong("tiempo"),
												elem.getInteger("idGroup"))));
									}
									if (result.isEmpty()) {
										routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
										.setStatusCode(404).end("No se encontraron sensores");
									} else {
										routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
										.setStatusCode(200).end(result.toString());
									}
								} else {
									routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
									.setStatusCode(500).end("Error: " + res.cause().getLocalizedMessage());
								}
								connection.result().close();
							});
				} else {
					routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
					.setStatusCode(500).end("Error al conectar: " + connection.cause().toString());
				}
			});
		} catch (NumberFormatException e) {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(400)
			.end("Error: " + e.getLocalizedMessage());
		}
	}
	private void getLastThreeValueSensor(RoutingContext routingContext) {
		int id = 0;
		try {
			id = Integer.parseInt(routingContext.request().getParam("id"));
			final int comp = id;
			mySqlClient.getConnection(connection -> {
				if (connection.succeeded()) {
					connection.result().preparedQuery(
							"SELECT * FROM Sensores WHERE idSensor = ? ORDER BY idValue DESC LIMIT 3;",
							Tuple.of(comp),
							res -> {
								if (res.succeeded()) {
									// Get the result set
									RowSet<Row> resultSet = res.result();
									JsonArray result = new JsonArray();
									for (Row elem : resultSet) {
										result.add(JsonObject.mapFrom(new Sensor(
												elem.getInteger("idValue"),
												elem.getInteger("idSensor"),
												elem.getInteger("idPlaca"),
												elem.getDouble("valor1"),
												elem.getDouble("valor2"),
												elem.getInteger("tipoSensor"),
												elem.getLong("tiempo"),
												elem.getInteger("idGroup"))));
									}
									if (result.isEmpty()) {
										routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
										.setStatusCode(404).end("No se encontraron sensores");
									} else {
										routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
										.setStatusCode(200).end(result.toString());
									}
								} else {
									routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
									.setStatusCode(500).end("Error: " + res.cause().getLocalizedMessage());
								}
								connection.result().close();
							});
				} else {
					routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
					.setStatusCode(500).end("Error al conectar: " + connection.cause().toString());
				}
			});
		} catch (NumberFormatException e) {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(400)
			.end("Error: " + e.getLocalizedMessage());
		}
	}

	private void addOnePlaca(RoutingContext routingContext) {
		final Placa placa = gson.fromJson(routingContext.getBodyAsString(), Placa.class);
		Integer idPlaca = placa.getIdPlaca();
		Integer idGroup = placa.getIdGroup();

		if (idPlaca == null) {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
			.setStatusCode(400).end("Campos requeridos (NOT NULL):\n\tidPlaca");
			return;
		}

		mySqlClient.getConnection(connection -> {
			if (connection.succeeded()) {
				connection.result().preparedQuery(
						"INSERT INTO Placas (idPlaca, idGroup) VALUES (?, ?)",
						Tuple.of(idPlaca, idGroup),
						res -> {
							if (res.succeeded()) {
								routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
								.setStatusCode(201).end("Placa añadida");
							} else {
								routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
								.setStatusCode(500).end("Error al añadir: " + res.cause().getLocalizedMessage());
							}
							connection.result().close();
						});
			} else {
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
				.setStatusCode(500).end("Error al conectar: " + connection.cause().toString());
			}
		});
	}
	private void addOneSensor(RoutingContext routingContext) {
		final Sensor aux = gson.fromJson(routingContext.getBodyAsString(), Sensor.class);
		Integer idSensor = aux.getIdSensor();
		Integer idPlaca = aux.getIdPlaca();
		Double valor1 = aux.getValor1();
		Double valor2 = aux.getValor2();
		Integer tipoSensor = aux.getTipoSensor();
		Long tiempo = aux.getTiempo();
		Integer idGroup = aux.getIdGroup();

		if (idSensor == null || idPlaca == null || valor1 == null || tipoSensor == null || tiempo == null) {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
			.setStatusCode(400).end("Campos requeridos (NOT NULL):\n\tidSensor\n\tidPlaca\n\tvalor1\n\ttipoSensor\n\ttiempo");
			return;
		}

		mySqlClient.getConnection(connection -> {
			if (connection.succeeded()) {
				connection.result().preparedQuery(
						"INSERT INTO Sensores(idSensor, idPlaca, valor1, valor2, tipoSensor, tiempo, idGroup) VALUES (?, ?, ?, ?, ?, ?, ?)",
						Tuple.of(idSensor, idPlaca, valor1, valor2, tipoSensor, tiempo, idGroup),
						res -> {
							if (res.succeeded()) {
								routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
								.setStatusCode(201).end("Sensor añadido");
							} else {
								routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
								.setStatusCode(500).end("Error al añadir: " + res.cause().getLocalizedMessage());
							}
							connection.result().close();
						});
			} else {
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
				.setStatusCode(500).end("Error al conectar: " + connection.cause().toString());
			}
		});
	}
	private void addOneActuador(RoutingContext routingContext) {
		final Actuador aux = gson.fromJson(routingContext.getBodyAsString(), Actuador.class);
		Integer idActuador = aux.getIdActuador();
		Integer idPlaca = aux.getIdPlaca();
		Double valor = aux.getValor();
		Integer tipoActuador = aux.getTipoActuador();
		Long tiempo = aux.getTiempo();
		Integer idGroup = aux.getIdGroup();

		if (idActuador == null || idPlaca == null || valor == null || tipoActuador == null || tiempo == null) {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
			.setStatusCode(400).end("Campos requeridos (NOT NULL):\n\tidActuador\n\tidPlaca\n\tvalor\n\ttipoActuador\n\ttiempo");
			return;
		}

		mySqlClient.getConnection(connection -> {
			if (connection.succeeded()) {
				connection.result().preparedQuery(
						"INSERT INTO Actuadores(idActuador, idPlaca, valor, tipoActuador, tiempo, idGroup) VALUES (?, ?, ?, ?, ?, ?)",
						Tuple.of(idActuador, idPlaca, valor, tipoActuador, tiempo, idGroup),
						res -> {
							if (res.succeeded()) {
								routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
								.setStatusCode(201).end("Actuador añadido");
							} else {
								routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
								.setStatusCode(500).end("Error al añadir: " + res.cause().getLocalizedMessage());
							}
							connection.result().close();
						});
			} else {
				routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
				.setStatusCode(500).end("Error al conectar: " + connection.cause().toString());
			}
		});
	}
	
}