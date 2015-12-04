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
 * Servlet implementation class AddQuestionServlet
 */
@WebServlet("/AddQuestionServlet")
public class AddQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddQuestionServlet() {
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
		String qType = request.getParameter("qtype");
		int type = -1;
		if (qType.equals("MULTIPLE_CHOICE")) type = QuestionHelper.MULTIPLE_CHOICE;
		else if (qType.equals("QUESTION_RESPONSE")) type = QuestionHelper.QUESTION_RESPONSE;
		else if (qType.equals("FILL_IN_BLANK")) type = QuestionHelper.FILL_IN_BLANK;
		else if (qType.equals("PICTURE_RESPONSE")) type = QuestionHelper.PICTURE_RESPONSE;
		HttpSession session = request.getSession();
		ServletContext context = getServletContext();
		DBConnection conn = (DBConnection) context.getAttribute("Database Connection");
        int questionID = QuestionHelper.addQuestion(conn, type);
        Integer quizID = (Integer)session.getAttribute("quizID");
        QuizHelper.addQuizQuestion(conn, quizID, questionID);
        RequestDispatcher dispatch = request.getRequestDispatcher("createquiz.jsp");
		dispatch.forward(request, response);
	}

}
