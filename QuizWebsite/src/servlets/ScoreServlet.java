package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import db.UserHelper;
import entities.QuestionAbstract;
import entities.Quiz;
import entities.User;

/**
 * Servlet implementation class ScoreServlet
 */
@WebServlet("/ScoreServlet")
public class ScoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScoreServlet() {
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
		HttpSession session = request.getSession();
		ServletContext context = getServletContext();
		DBConnection conn = (DBConnection) context.getAttribute("Database Connection");
		Integer quizID = (Integer)session.getAttribute("quizID");
		String username = (String)session.getAttribute("name");
		Quiz quiz = QuizHelper.getQuizByID(conn, quizID);
		QuizHelper.addEndTime(conn, quiz, username);
		List<QuestionAbstract> questions = quiz.getQuestions();
		int numCorrect = 0;
		int numQuestions = questions.size();
		for (int i = 0; i <= numQuestions; ++i) {
			String param = "question" + i;
			String userAnswer = request.getParameter(param);
			String realAnswer = questions.get(i).getAnswer();
			if (userAnswer.equalsIgnoreCase(realAnswer)) numCorrect++;
		}
		double score = (double) numCorrect / numQuestions;
		User user = UserHelper.getUserByID(conn, username);
		user.addQuizTaken(quiz, conn, score); // this handles achievements and also calls QuizHelper.addQuizTaken
        RequestDispatcher dispatch = request.getRequestDispatcher("quizresults.jsp?id=" + quizID);
 
		dispatch.forward(request, response);
	}

}
