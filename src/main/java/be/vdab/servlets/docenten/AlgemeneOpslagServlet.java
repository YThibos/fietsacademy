package be.vdab.servlets.docenten;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.vdab.services.DocentService;

/**
 * Servlet implementation class AlgemeneOpslagServlet
 */
@WebServlet("/docenten/algemeneopslag.htm")
public class AlgemeneOpslagServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/docenten/algemeneopslag.jsp";
	private final transient DocentService docentService = new DocentService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.getRequestDispatcher(VIEW).forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map<String, String> fouten = new HashMap<>();
		
		try {
			
			BigDecimal percentage = new BigDecimal(request.getParameter("percentage"));
			
			if (percentage.compareTo(BigDecimal.ZERO) < 0) {
				fouten.put("percentage", "Geef een positief getal in");
			}
			else {
				docentService.algemeneOpslag(percentage);
			}
			
		} catch (NumberFormatException ex) {
			fouten.put("percentage", "Geef een positief getal in");
		}
		
		if (fouten.isEmpty()) {
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath()));
		}
		else {
			request.setAttribute("fouten", fouten);
			request.getRequestDispatcher(VIEW).forward(request, response);
		}
		
	}

}
