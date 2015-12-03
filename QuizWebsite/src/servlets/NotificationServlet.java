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
import db.NotificationsHelper;
import db.QuizHelper;
import db.UserHelper;
import entities.Challenge;
import entities.Note;
import entities.NotificationAbstract;
import entities.Quiz;
import entities.User;

/**
 * Servlet implementation class NotificationServleg
 */
@WebServlet("/NotificationServlet")
public class NotificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NotificationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
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
		String type = request.getParameter("type");
		String recipientName = request.getParameter("recipient");
		String senderName = request.getParameter("sender");
		User sender = UserHelper.getUserByID(conn, senderName);
		User recipient = UserHelper.getUserByID(conn, recipientName);
		
		if (type.equals("note"))
		{
			String text = request.getParameter("text");
			Note note = new Note(NotificationsHelper.NOTE_TYPE, -1, sender, recipient, text);
			NotificationsHelper.addNote(conn, note);
			RequestDispatcher dispatch = request.getRequestDispatcher("note.jsp?type=submit&to="+recipientName);
			dispatch.forward(request, response);
		}
		else if (type.equals("challenge"))
		{
			String link = (String)request.getParameter("link");
			String idFromLink = "";
			for (int i = 0; i < link.length(); i++)
			{
				if (Character.isDigit(link.charAt(i)))
				{
					idFromLink = link.substring(i);
					break;
				}
			}
			
			if (idFromLink.isEmpty())
			{
				RequestDispatcher dispatch = request.getRequestDispatcher("error.jsp");
				dispatch.forward(request, response);
			}
			int QuizID = Integer.parseInt(idFromLink);
			
			Quiz quiz = QuizHelper.getQuizByID(conn, QuizID);
			Challenge challenge = new Challenge(NotificationsHelper.CHALLENGE, -1, sender, recipient, quiz, link);
			NotificationsHelper.addChallenge(conn, challenge);
			RequestDispatcher dispatch = request.getRequestDispatcher("challenge.jsp?type=submit&to=" + recipientName);
			dispatch.forward(request, response);
		}
	}

}
