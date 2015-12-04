package servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.DBConnection;
import db.UserHelper;

import entities.AccountManager;
import entities.User;

/**
 * Servlet implementation class AccountCreationServlet
 */
@WebServlet("/AccountCreationServlet")
public class AccountCreationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountCreationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");
		boolean admin = Boolean.parseBoolean(request.getParameter("admin"));
		DBConnection conn = (DBConnection) context.getAttribute("Database Connection");
		
		User user = UserHelper.getUserByID(conn, name);
		if (user != null)
		{
			RequestDispatcher dispatch = request.getRequestDispatcher("accountexists.jsp");
			dispatch.forward(request, response);
		} else {
			HttpSession session = request.getSession();
	        session.setAttribute("name", name);
	        User newUser = new User(name, pass, admin);
	        int status = UserHelper.addUser(conn, newUser);
	        if (status == -1) // TODO kind of weird that we go to accountexists if there was a DB error but okay --> we can make a general error page like they do with twitter and stuff instead, this was jsut place holder
	        {
	        	RequestDispatcher dispatch = request.getRequestDispatcher("error.jsp");
	        	dispatch.forward(request, response);
	        }
			RequestDispatcher dispatch = request.getRequestDispatcher("index.jsp");
			dispatch.forward(request, response);
		}	
	}

}
