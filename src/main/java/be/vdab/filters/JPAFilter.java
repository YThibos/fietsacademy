package be.vdab.filters;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Servlet Filter implementation class JPAFilter
 */
@WebFilter("*.htm")
public class JPAFilter implements Filter {

	private static final EntityManagerFactory entityManagerFactory = 
			Persistence.createEntityManagerFactory("fietsacademy");
	private static final ThreadLocal<EntityManager> entityManagers = new ThreadLocal<>();
	
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}
	

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		entityManagers.set(entityManagerFactory.createEntityManager());

		try {
			request.setCharacterEncoding("UTF-8");
			chain.doFilter(request, response);
		} finally {
			EntityManager entityManager = entityManagers.get();
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			entityManager.close();
			entityManagers.remove();
		}
		
	}
	
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		entityManagerFactory.close();
	}
	
	public static EntityManager getEntityManager() {
		return entityManagers.get();
	}

}
