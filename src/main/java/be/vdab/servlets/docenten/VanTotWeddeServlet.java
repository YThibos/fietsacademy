package be.vdab.servlets.docenten;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.vdab.services.DocentService;

/**
 * Servlet implementation class VanTotWeddeServlet
 */
@WebServlet("/docenten/vantotwedde.htm")
public class VanTotWeddeServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final String VIEW = "/WEB-INF/JSP/docenten/vantotwedde.jsp";
	private final transient DocentService docentService = new DocentService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setAttribute("docenten", docentService.findByWeddeBetween(null, null));
		
		request.getRequestDispatcher(VIEW).forward(request, response);
		
	}

}
