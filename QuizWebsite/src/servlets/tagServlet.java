package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.DBConnection;
import db.QuizHelper;
import db.UserHelper;
import entities.User;

/**
 * Servlet implementation class tagServlet
 */
@WebServlet("/tagServlet")
public class tagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public tagServlet() {
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
		DBConnection conn = (DBConnection) context.getAttribute("Database Connection");
		String tag = request.getParameter("tag");
		String id = request.getParameter("id");
		int quizid = 0;
		if (id != null)
		{
			quizid = Integer.parseInt(id);
		}
		int status = QuizHelper.addTag(conn, quizid, tag);
		if (status != -1)
		{
	        RequestDispatcher dispatch = request.getRequestDispatcher("quizsummary.jsp?id=" + quizid);
			dispatch.forward(request, response);
		}else {
			String message = "Error occurred when adding tag.";
			RequestDispatcher dispatch = request.getRequestDispatcher("quizsummary.jsp?id=" + quizid +"&message=" + message);
			dispatch.forward(request, response);
		}
	}

}
