package es.us.lsi.dad;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.Tuple;

public class FuncionesBBDD extends AbstractVerticle {

	MySQLPool mySqlClient;

	@Override
	public void start(Promise<Void> startFuture) {
		MySQLConnectOptions rootOptions = new MySQLConnectOptions()
				.setPort(3306)
				.setHost("localhost")
				.setDatabase("RestServerDAD")
				.setUser("root")
				.setPassword("dadroot");
		/*
		MySQLConnectOptions userOptions = new MySQLConnectOptions()
				.setPort(3306)
				.setHost("localhost")
				.setDatabase("restserverdad")
				.setUser("daduser")
				.setPassword("daduser");
		 */

		PoolOptions poolOptions = new PoolOptions().setMaxSize(5);

		mySqlClient = MySQLPool.pool(vertx, rootOptions, poolOptions);

		getAllPlacas();
		getAllSensores();
		getAllActuadores();
/*
		getOnePlaca();
		getOneSensor();
		getOneActuador();

		getSensoresPlaca(1);
		getActuadoresPlaca(1);

		getValuesSensor(1);
		getLastValueSensor(1);
		getLastThreeValueSensor(1);

		addOnePlaca();
		addOneSensor();
		addOneActuador();
*/
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

	private void getAllPlacas() {
		mySqlClient.query("SELECT * FROM Placas;", res -> {
			if (res.succeeded()) {
				// Get the result set
				RowSet<Row> resultSet = res.result();
				System.out.println("PLACAS");
				System.out.println(resultSet.size());
				JsonArray result = new JsonArray();
				for (Row elem : resultSet) {
					result.add(JsonObject.mapFrom(new Placa(
							elem.getInteger("idValue"),
							elem.getInteger("idPlaca"),
							elem.getInteger("idGroup"))));
				}
				System.out.println(result.toString());
			} else {
				System.out.println("Error: " + res.cause().getLocalizedMessage());
			}
			System.out.println();
		});
	}
	private void getAllSensores() {
		mySqlClient.query("SELECT * FROM Sensores;", res -> {
			if (res.succeeded()) {
				// Get the result set
				RowSet<Row> resultSet = res.result();
				System.out.println("SENSORES");
				System.out.println(resultSet.size());
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
				System.out.println(result.toString());
			} else {
				System.out.println("Error: " + res.cause().getLocalizedMessage());
			}
			System.out.println();
		});
	}
	private void getAllActuadores() {
		mySqlClient.query("SELECT * FROM Actuadores;", res -> {
			if (res.succeeded()) {
				// Get the result set
				RowSet<Row> resultSet = res.result();
				System.out.println("ACTUADORES");
				System.out.println(resultSet.size());
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
				System.out.println(result.toString());
			} else {
				System.out.println("Error: " + res.cause().getLocalizedMessage());
			}
			System.out.println();
		});
	}

	private void getOnePlaca() {
		mySqlClient.getConnection(connection -> {
			if (connection.succeeded()) {
				connection.result().preparedQuery("SELECT * FROM Placas LIMIT 1;", res -> {
					if (res.succeeded()) {
						// Get the result set
						RowSet<Row> resultSet = res.result();
						System.out.println("1 PLACA");
						System.out.println(resultSet.size());
						JsonArray result = new JsonArray();
						for (Row elem : resultSet) {
							result.add(JsonObject.mapFrom(new Placa(
									elem.getInteger("idValue"),
									elem.getInteger("idPlaca"),
									elem.getInteger("idGroup"))));
						}
						System.out.println(result.toString());
					} else {
						System.out.println("Error: " + res.cause().getLocalizedMessage());
					}
					System.out.println();
				});
			}
		});
	}
	private void getOneSensor() {
		mySqlClient.getConnection(connection -> {
			if (connection.succeeded()) {
				connection.result().preparedQuery("SELECT * FROM Sensores LIMIT 1;", res -> {
					if (res.succeeded()) {
						// Get the result set
						RowSet<Row> resultSet = res.result();
						System.out.println("1 SENSOR");
						System.out.println(resultSet.size());
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
						System.out.println(result.toString());
					} else {
						System.out.println("Error: " + res.cause().getLocalizedMessage());
					}
					System.out.println();
				});
			}
		});
	}
	private void getOneActuador() {
		mySqlClient.getConnection(connection -> {
			if (connection.succeeded()) {
				connection.result().preparedQuery("SELECT * FROM Actuadores LIMIT 1;", res -> {
					if (res.succeeded()) {
						// Get the result set
						RowSet<Row> resultSet = res.result();
						System.out.println("1 ACTUADOR");
						System.out.println(resultSet.size());
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
						System.out.println(result.toString());
					} else {
						System.out.println("Error: " + res.cause().getLocalizedMessage());
					}
					System.out.println();
				});
			}
		});
	}

	private void getSensoresPlaca(Integer idPlaca) {
		mySqlClient.getConnection(connection -> {
			if (connection.succeeded()) {
				connection.result().preparedQuery("SELECT * FROM Sensores WHERE idPlaca = ?;", Tuple.of(idPlaca), res -> {
					if (res.succeeded()) {
						// Get the result set
						RowSet<Row> resultSet = res.result();
						System.out.println("SENSORES DE PLACA");
						System.out.println(resultSet.size());
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
						System.out.println(result.toString());
					} else {
						System.out.println("Error: " + res.cause().getLocalizedMessage());
					}
					System.out.println();
				});
			}
		});
	}
	private void getActuadoresPlaca(Integer idPlaca) {
		mySqlClient.getConnection(connection -> {
			if (connection.succeeded()) {
				connection.result().preparedQuery("SELECT * FROM Actuadores WHERE idPlaca = ?;", Tuple.of(idPlaca), res -> {
					if (res.succeeded()) {
						// Get the result set
						RowSet<Row> resultSet = res.result();
						System.out.println("ACTUADORES DE PLACA");
						System.out.println(resultSet.size());
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
						System.out.println(result.toString());
					} else {
						System.out.println("Error: " + res.cause().getLocalizedMessage());
					}
					System.out.println();
				});
			}
		});
	}

	private void getValuesSensor(Integer idSensor) {
		mySqlClient.getConnection(connection -> {
			if (connection.succeeded()) {
				connection.result().preparedQuery("SELECT * FROM Sensores WHERE idSensor = ? ORDER BY idValue DESC;", Tuple.of(idSensor), res -> {
					if (res.succeeded()) {
						// Get the result set
						RowSet<Row> resultSet = res.result();
						System.out.println("VALORES DE SENSOR");
						System.out.println(resultSet.size());
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
						System.out.println(result.toString());
					} else {
						System.out.println("Error: " + res.cause().getLocalizedMessage());
					}
					System.out.println();
				});
			}
		});
	}
	private void getLastValueSensor(Integer idSensor) {
		mySqlClient.getConnection(connection -> {
			if (connection.succeeded()) {
				connection.result().preparedQuery("SELECT * FROM Sensores WHERE idSensor = ? ORDER BY idValue DESC LIMIT 1;", Tuple.of(idSensor), res -> {
					if (res.succeeded()) {
						// Get the result set
						RowSet<Row> resultSet = res.result();
						System.out.println("ÚLTIMO VALOR DE SENSOR");
						System.out.println(resultSet.size());
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
						System.out.println(result.toString());
					} else {
						System.out.println("Error: " + res.cause().getLocalizedMessage());
					}
					System.out.println();
				});
			}
		});
	}
	private void getLastThreeValueSensor(Integer idSensor) {
		mySqlClient.getConnection(connection -> {
			if (connection.succeeded()) {
				connection.result().preparedQuery("SELECT * FROM Sensores WHERE idSensor = ? ORDER BY idValue DESC LIMIT 3;", Tuple.of(idSensor), res -> {
					if (res.succeeded()) {
						// Get the result set
						RowSet<Row> resultSet = res.result();
						System.out.println("ÚLTIMO VALOR DE SENSOR");
						System.out.println(resultSet.size());
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
						System.out.println(result.toString());
					} else {
						System.out.println("Error: " + res.cause().getLocalizedMessage());
					}
					System.out.println();
				});
			}
		});
	}
	
	private void addOnePlaca() {
		
	}
	private void addOneSensor() {
		// TODO Auto-generated method stub
		
	}
	private void addOneActuador() {
		// TODO Auto-generated method stub
		
	}

}
