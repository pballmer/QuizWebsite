package listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import db.DBConnection;

import entities.AccountManager;

/**
 * Application Lifecycle Listener implementation class SiteListener
 *
 */
@WebListener
public class SiteListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public SiteListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        AccountManager manager = new AccountManager();
        DBConnection db = new DBConnection();
        ServletContext context = event.getServletContext();
        context.setAttribute("Account Manager", manager);
        context.setAttribute("Database Connection", db);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
    	ServletContext context = event.getServletContext();
        DBConnection db = (DBConnection) context.getAttribute("Database Connection");
        db.close();
    }
	
}
