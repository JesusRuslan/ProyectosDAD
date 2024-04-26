package es.us.lsi.dad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ServletSimpleTYH extends HttpServlet {

	private static final long serialVersionUID = 861415467074580048L;

	private Map<Integer,SensorTempHumedad> map;
	private String message;
	private int number;
	private SensorTempHumedad sensor;

	public void init() throws ServletException {
		message = "Medición Sensor Temperatura y Humedad";
		number = 0;
		map = new HashMap<Integer, SensorTempHumedad>();
		map.put(1, new SensorTempHumedad(5, 2, 12f, 12f));
		//throw new ServletException();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		out.println("<h1>Medición del sensor Temperatura y Humedad</h1>");
		String message2 = Calendar.getInstance().getTime().toString();
		out.println("<body>");
		out.println("<div>");
		out.println("<h2>PlacaId:</h2>");
		out.println("<p>PlacaId:"+ request.getParameter("placaId") +  "</p>");
		out.println("<h2>SensorId:</h2>");
		out.println("<p>SensorId:"+ request.getParameter("sensorId") +  "</p>");
		out.println("<h2>Tiempo:</h2>");
		out.println("<p>Tiempo:"+ message2 + number+ "</p>");
		out.println("</div>");
		out.println("<div>");
		out.println("<h2>Temperatura:</h2>");
		out.println("<p>Temperatura:" + request.getParameter("temperatura")+"</p>");
		out.println("</div>");
		out.println("<div>");
		out.println("<h2>Humedad:</h2>");
		out.println("<p>Humedad:"+ request.getParameter("humedad")+"</p>");
		out.println("</div>");
		out.println("</body>");
		number++;

	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    BufferedReader reader = req.getReader();
	    
	    Gson gson = new Gson();
		SensorTempHumedad sensor = gson.fromJson(reader, SensorTempHumedad.class);
		if (!sensor.getPlacaId().equals("") && !sensor.getSensorId().equals("")) {
			map.put(2, new SensorTempHumedad(sensor.getSensorId(), sensor.getPlacaId(), sensor.getTemperatura(), sensor.getHumedad()));
			resp.getWriter().println(gson.toJson(sensor));
			resp.setStatus(201);
		}else{
			resp.setStatus(300);
			response(resp, "Wrong user and password");
		}
	   
	}
	

	private void response(HttpServletResponse resp, String msg) throws IOException {
		// TODO Auto-generated method stub
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<t1>" + msg + "</t1>");
		out.println("</body>");
		out.println("</html>");
	}

	public void destroy() {
		// do nothing.
	}
}