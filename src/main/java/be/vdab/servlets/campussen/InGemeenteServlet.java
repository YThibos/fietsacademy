package be.vdab.servlets.campussen;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.vdab.services.CampusService;

/**
 * Servlet implementation class InGemeenteServlet
 */
@WebServlet("/campussen/ingemeente.htm")
public class InGemeenteServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/campussen/ingemeente.jsp";
	private final transient CampusService campusService = new CampusService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String gemeente = request.getParameter("gemeente");
		if (gemeente != null) {
			if (gemeente.isEmpty()) {
				request.setAttribute("fouten", Collections.singletonMap("gemeente", "Verplicht in te vullen"));
			}
			else {
				request.setAttribute("campussen", campusService.findByGemeente(gemeente));
			}
		}
		
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

}
