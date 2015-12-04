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
import db.QuestionHelper;

/**
 * Servlet implementation class FBServlet
 */
@WebServlet("/FBServlet")
public class FBServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FBServlet() {
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
		String textBefore = request.getParameter("textBefore");
		String answer = request.getParameter("answer");
		String textAfter = request.getParameter("textAfter");
		int questionID = Integer.parseInt(request.getParameter("questionID"));
		ServletContext context = getServletContext();
		DBConnection conn = (DBConnection) context.getAttribute("Database Connection");
        QuestionHelper.setFBAttributes(conn, questionID, textBefore, textAfter, answer);
        RequestDispatcher dispatch = request.getRequestDispatcher("createquiz.jsp");
		dispatch.forward(request, response);
	}

}
