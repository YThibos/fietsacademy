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

import be.vdab.entities.Docent;
import be.vdab.enums.Geslacht;
import be.vdab.exceptions.DocentAlreadyExistsException;
import be.vdab.services.CampusService;
import be.vdab.services.DocentService;

/**
 * Servlet implementation class ToevoegenServlet
 */
@WebServlet("/docenten/toevoegen.htm")
public class ToevoegenServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/docenten/toevoegen.jsp";
	private static final String REDIRECT_URL = "%s/docenten/zoeken.htm?id=%d";
	
	private final transient DocentService docentService = new DocentService();
	private final transient CampusService campusService  = new CampusService(); 

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("campussen", campusService.findAll());
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Map<String, String> fouten = new HashMap<>();
		
		// ----PARAMETERS CHECK----
		// check voornaam
		String voornaam = request.getParameter("voornaam");
		if (!Docent.isStringValid(voornaam)) {
			fouten.put("voornaam", "verplicht");
		}
		// check familienaam
		String familienaam = request.getParameter("familienaam");
		if (!Docent.isStringValid(familienaam)) {
			fouten.put("familienaam", "verplicht");
		}
		// check wedde
		BigDecimal wedde = null;
		try {
			wedde = new BigDecimal(request.getParameter("wedde"));
			if (!Docent.isWeddeValid(wedde)) {
				fouten.put("wedde", "tik een positief getal of 0");
			}
		} catch (NumberFormatException ex) {
			fouten.put("wedde", "tik een positief getal of 0");
		}
		// check geslacht
		String geslacht = request.getParameter("geslacht");
		if (geslacht == null) {
			fouten.put("geslacht", "verplicht");
		}
		// check rijksregisternummer
		long rijksregisternr = 0;
		try {
			rijksregisternr = Long.parseLong(request.getParameter("rijksregisternr"));
			if (!Docent.isRijksregisternrValid(rijksregisternr)) {
				fouten.put("rijksregisternr", "verkeerde cijfers");
			}
		} catch (NumberFormatException ex) {
			fouten.put("rijksregisternr", "verkeerde cijfers");
		}
		
		// check campus
		String campusId = request.getParameter("campussen");
		if (campusId == null) {
			fouten.put("campussen", "verplicht campus te kiezen");
		}
		
		// Als alles in orde, maak docent aan via docentService
		if (fouten.isEmpty()) {
			
			Docent docent = new Docent(voornaam, familienaam, Geslacht.valueOf(geslacht), wedde, rijksregisternr);
			docent.setCampus(campusService.read(Long.parseLong(campusId)));
			
			try {
				docentService.create(docent);
				response.sendRedirect(
					response.encodeRedirectURL(String.format(REDIRECT_URL, request.getContextPath(), docent.getId())));
			}
			catch (DocentAlreadyExistsException ex) {
				fouten.put("rijksregisternr", "Rijksregisternr bestaat al in database");
			}
		}  
		if (!fouten.isEmpty()) {
			request.setAttribute("fouten", fouten);
			request.setAttribute("campussen", campusService.findAll());
			request.getRequestDispatcher(VIEW).forward(request, response);
		}
	}

}
