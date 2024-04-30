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

public class MainVerticle extends AbstractVerticle{
	
	MySQLPool mySqlClient;
	
	public void start(Promise<Void> startFuture) {
		MySQLConnectOptions connectOptions = new MySQLConnectOptions().setPort(3306).setHost("localhost")
				.setDatabase("proyectodad").setUser("root").setPassword("root");

		PoolOptions poolOptions = new PoolOptions().setMaxSize(5);

		mySqlClient = MySQLPool.pool(vertx, connectOptions, poolOptions);

		getAllSensores();
		getAllActuadores();
		getAllPlacas();
		getTresUltimosSensores();
		getValuesSensor(2);
		getValuesActuador(1);
		
		
	
	}
	
		private void getAllSensores() {
			mySqlClient.query("SELECT * FROM proyectodad.sensores;" , res -> {
			if (res.succeeded()) {
				// Get the result set
				RowSet<Row> resultSet = res.result();
				System.out.println(resultSet.size());
				JsonArray result = new JsonArray();
				for (Row elem : resultSet) {
					result.add(JsonObject.mapFrom(new Sensores(
							elem.getInteger("valueId"),
							elem.getInteger("placaId"),
							elem.getInteger("groupId"),
							elem.getInteger("sensorId"),
							elem.getDouble("temperatura"),
							elem.getDouble("humedad"),
							elem.getLong("tiempo"))));
				}
				System.out.println(result.toString());
			} else {
				System.out.println("Error: " + res.cause().getLocalizedMessage());
			}
		});
		}
			
			
			private void getAllActuadores() {
				mySqlClient.query("SELECT * FROM proyectodad.actuadores;" , res -> {
				if (res.succeeded()) {
					// Get the result set
					RowSet<Row> resultSet = res.result();
					System.out.println(resultSet.size());
					JsonArray result = new JsonArray();
					for (Row elem : resultSet) {
						result.add(JsonObject.mapFrom(new Actuadores(
								elem.getInteger("valueId"),
								elem.getInteger("placaId"),
								elem.getInteger("groupId"),
								elem.getInteger("actuadorId"),
								elem.getDouble("velocidad"),
								elem.getBoolean("sentido"),
								elem.getLong("tiempo"))));
					}
					System.out.println(result.toString());
				} else {
					System.out.println("Error: " + res.cause().getLocalizedMessage());
				}
			});
		}
			private void getAllPlacas() {
				mySqlClient.query("SELECT * FROM proyectodad.placas;" , res -> {
				if (res.succeeded()) {
					// Get the result set
					RowSet<Row> resultSet = res.result();
					System.out.println(resultSet.size());
					JsonArray result = new JsonArray();
					for (Row elem : resultSet) {
						result.add(JsonObject.mapFrom(new Placas(
								elem.getInteger("valueId"),
								elem.getInteger("placaId"))));
					}
					System.out.println(result.toString());
				} else {
					System.out.println("Error: " + res.cause().getLocalizedMessage());
				}
			});
			}
				
				private void getTresUltimosSensores() {
					mySqlClient.query("SELECT * FROM proyectodad.sensores ORDER BY valueId ASC LIMIT 3 ;" , res -> {
					if (res.succeeded()) {
						// Get the result set
						RowSet<Row> resultSet = res.result();
						System.out.println(resultSet.size());
						JsonArray result = new JsonArray();
						for (Row elem : resultSet) {
							result.add(JsonObject.mapFrom(JsonObject.mapFrom(new Sensores(
									elem.getInteger("valueId"),
									elem.getInteger("placaId"),
									elem.getInteger("groupId"),
									elem.getInteger("sensorId"),
									elem.getDouble("temperatura"),
									elem.getDouble("humedad"),
									elem.getLong("tiempo")))));
						}
						System.out.println(result.toString());
					} else {
						System.out.println("Error: " + res.cause().getLocalizedMessage());
					}
				});	
				
		}
				private void getValuesSensor(Integer sensorId) {
						mySqlClient.getConnection(connection -> {
							if (connection.succeeded()) {
								connection.result().preparedQuery("SELECT * FROM proyectodad.sensores WHERE sensorId = ?" ,
									Tuple.of(sensorId), res -> {
										if (res.succeeded()) {
											// Get the result set
											RowSet<Row> resultSet = res.result();
											System.out.println(resultSet.size());
											JsonArray result = new JsonArray();
											for (Row elem : resultSet) {
								result.add(JsonObject.mapFrom(JsonObject.mapFrom(new Sensores(
										elem.getInteger("valueId"),
										elem.getInteger("placaId"),
										elem.getInteger("groupId"),
										elem.getInteger("sensorId"),
										elem.getDouble("temperatura"),
										elem.getDouble("humedad"),
										elem.getLong("tiempo")))));
											}
											System.out.println(result.toString());
										} else {
											System.out.println("Error: " + res.cause().getLocalizedMessage());
										}
										connection.result().close();
									});
						} else {
							System.out.println(connection.cause().toString());
						}
					});
				}
				
				
				private void getValuesActuador(Integer actuadorId) {
					mySqlClient.getConnection(connection -> {
						if (connection.succeeded()) {
							connection.result().preparedQuery("SELECT * FROM proyectodad.actuadores WHERE actuadorId = ?" ,
								Tuple.of(actuadorId), res -> {
									if (res.succeeded()) {
										// Get the result set
										RowSet<Row> resultSet = res.result();
										System.out.println(resultSet.size());
										JsonArray result = new JsonArray();
										for (Row elem : resultSet) {
							result.add(JsonObject.mapFrom(JsonObject.mapFrom(new Actuadores(
									elem.getInteger("valueId"),
									elem.getInteger("placaId"),
									elem.getInteger("groupId"),
									elem.getInteger("actuadorId"),
									elem.getDouble("velocidad"),
									elem.getBoolean("sentido"),
									elem.getLong("tiempo")))));
										}
										System.out.println(result.toString());
									} else {
										System.out.println("Error: " + res.cause().getLocalizedMessage());
									}
									connection.result().close();
								});
					} else {
						System.out.println(connection.cause().toString());
					}
				});
			}
				
}
