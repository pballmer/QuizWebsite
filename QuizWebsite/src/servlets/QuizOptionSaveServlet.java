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
import db.QuizHelper;

/**
 * Servlet implementation class QuizOptionSaveServlet
 */
@WebServlet("/QuizOptionSaveServlet")
public class QuizOptionSaveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizOptionSaveServlet() {
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
		Object random = request.getParameter("random");
		boolean isRandom = (random != null);
		Object page = request.getParameter("page");
		boolean onePage = (page != null);
		Object feedback = request.getParameter("feedback");
		boolean immediate = (feedback != null);
		HttpSession session = request.getSession();
		ServletContext context = getServletContext();
		DBConnection conn = (DBConnection) context.getAttribute("Database Connection");
        QuizHelper.setProperties(conn, (Integer)session.getAttribute("quizID"), isRandom, onePage, immediate);
        RequestDispatcher dispatch = request.getRequestDispatcher("createquiz.jsp");
		dispatch.forward(request, response);
	}

}
