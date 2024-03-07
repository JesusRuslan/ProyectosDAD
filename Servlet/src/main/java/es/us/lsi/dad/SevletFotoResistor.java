package es.us.lsi.dad;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SevletFotoResistor {
	private static final long serialVersionUID = 8614154670780048L;

	private String message;
	private int number;


	public void init() throws ServletException {
		message = "Medición Sensor Fotoresistor";
		number = 0;
		//throw new ServletException();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		out.println("<h1>Medición Sensor Fotoresistor</h1>");
		String message2 = Calendar.getInstance().getTime().toString();
		out.println("<body>");
		out.println("<div>");
		out.println("<h2>Tiempo:</h2>");
		out.println("<p>Tiempo:"+ message2 + number+ "</p>");
		out.println("</div>");
		out.println("<div>");
		out.println("<h2>Luz:</h2>");
		out.println("<p>Luz:" + request.getParameter("luz")+"</p>");
		out.println("</div>");
		out.println("</body>");
		number++;

	}

	public void destroy() {
		// do nothing.
	}
}
