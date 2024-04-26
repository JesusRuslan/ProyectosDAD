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
/*
		getAllWithConnection();

		for (int i = 0; i < 100; i++) {
			getByName("lsoriamo");	
		}
		*/
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
							elem.getInteger("sensoresId"),
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
								elem.getInteger("actuadoresId"),
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
		
}
