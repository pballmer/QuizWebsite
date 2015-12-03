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
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		DBConnection conn = (DBConnection) context.getAttribute("Database Connection");
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");

		User user = UserHelper.getUserByID(conn, name);
		if (user != null && user.passMatches(pass))
		{
			HttpSession session = request.getSession();
	        session.setAttribute("name", name);
	        RequestDispatcher dispatch = request.getRequestDispatcher("index.jsp");
			dispatch.forward(request, response);
		}else {
			RequestDispatcher dispatch = request.getRequestDispatcher("loginfailure.jsp");
			dispatch.forward(request, response);
		}
	}

}
