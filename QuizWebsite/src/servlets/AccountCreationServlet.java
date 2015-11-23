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

import entities.AccountManager;

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
		AccountManager manager = (AccountManager) context.getAttribute("Account Manager");
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");
		if (manager.accountExists(name)) {
			RequestDispatcher dispatch = request.getRequestDispatcher("accountexists.jsp");
			dispatch.forward(request, response);
		} else {
			manager.createAccount(name, pass);
			HttpSession session = request.getSession();
	        session.setAttribute("name", name);
	        // TODO update database
			RequestDispatcher dispatch = request.getRequestDispatcher("success.jsp");
			dispatch.forward(request, response);
		}	
	}

}