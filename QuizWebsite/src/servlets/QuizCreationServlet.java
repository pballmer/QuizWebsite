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
import db.UserHelper;
import entities.Quiz;

/**
 * Servlet implementation class QuizCreationServlet
 */
@WebServlet("/QuizCreationServlet")
public class QuizCreationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizCreationServlet() {
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
		Integer quizID = (Integer)session.getAttribute("quizID");
		String quizName = (String) session.getAttribute("quizName");
		String quizDesc = (String) session.getAttribute("quizDesc");
		String username = (String)session.getAttribute("name");
		Quiz quiz = new Quiz(quizID, quizName, quizDesc);
		ServletContext context = getServletContext();
		DBConnection conn = (DBConnection) context.getAttribute("Database Connection");
        // TODO set request's id parameter so that quizsummary can display right away
        QuizHelper.addQuizMade(conn, quiz, username);
        if(UserHelper.getNumQuizzesMade(conn, username) == 1){
    		UserHelper.addAchievement(conn, UserHelper.AMATEUR, username);
        } else if (UserHelper.getNumQuizzesMade(conn, username) == 5){
        	UserHelper.addAchievement(conn, UserHelper.PROLIFIC, username);
        } else if (UserHelper.getNumQuizzesMade(conn, username) == 10){
        	UserHelper.addAchievement(conn, UserHelper.PRODIGIOUS, username);
        }
        session.removeAttribute("quizID");
    	session.removeAttribute("quizName");
    	session.removeAttribute("quizDesc");
        RequestDispatcher dispatch = request.getRequestDispatcher("quizsummary.jsp?id=" + quizID);
 
		dispatch.forward(request, response);
	}

}
