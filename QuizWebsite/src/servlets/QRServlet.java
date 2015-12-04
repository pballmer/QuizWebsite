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
import db.QuestionHelper;
import db.QuizHelper;

/**
 * Servlet implementation class QRServlet
 */
@WebServlet("/QRServlet")
public class QRServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QRServlet() {
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
		String text = request.getParameter("text");
		String answer = request.getParameter("answer");
		int questionID = Integer.parseInt(request.getParameter("questionID"));
		ServletContext context = getServletContext();
		DBConnection conn = (DBConnection) context.getAttribute("Database Connection");
        QuestionHelper.setQRAttributes(conn, questionID, text, answer);
        RequestDispatcher dispatch = request.getRequestDispatcher("createquiz.jsp");
		dispatch.forward(request, response);
	}

}
