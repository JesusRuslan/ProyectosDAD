package es.us.lsi.dad;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ServletSimpleTYH extends HttpServlet {

	private static final long serialVersionUID = 861415467074580048L;

	private String message;
	private int number;
	private SensorTempHumedad sensor;

	public void init() throws ServletException {
		message = "Medición Sensor Temperatura y Humedad";
		number = 0;
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

	public void destroy() {
		// do nothing.
	}
}