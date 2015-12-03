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

import db.AnnounceHelper;
import db.DBConnection;
import db.QuizHelper;
import db.UserHelper;
import entities.User;

/**
 * Servlet implementation class AdministrationServlet
 */
@WebServlet("/AdministrationServlet")
public class AdministrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdministrationServlet() {
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
		String type = request.getParameter("type");
		
		if (type.equals("announcement"))
		{
			String text = request.getParameter("text");
			int status = AnnounceHelper.addAnnouncement(conn, text);
			String message = "";
			String color = "";
			if (status == -1)
			{
				message = "Error occured when adding your announcement.";
				color = "red";
			}
			else
			{
				message = "Announcement has been added to the site.";
				color = "green";
			}
			RequestDispatcher dispatch = request.getRequestDispatcher("admin.jsp?message=" + message + "&color=" + color );
			dispatch.forward(request, response);
		}
		else if (type.equals("user"))
		{
			String user = request.getParameter("user");
			int status = UserHelper.removeUser(conn, user);
			String message = "";
			String color="";
			if (status == -1)
			{
				message = "Error occured when removing " + user + ".";
				color = "red";
			}
			else
			{
				message = user + " has been removed.";
				color = "green";
			}
			RequestDispatcher dispatch = request.getRequestDispatcher("admin.jsp?message=" + message + "&color=" + color );
			dispatch.forward(request, response);
		}
		else if (type.equals("quiz"))
		{
			String quiz = request.getParameter("link");
			int quizid = getID(quiz);
			int status = QuizHelper.removeQuiz(conn, quizid);
			String message = "";
			String color="";
			if (status == -1)
			{
				message = "Error occured when removing Quiz " + quizid + ".";
				color="red";
			}
			else
			{
				message = "Quiz " + quizid + " has been removed.";
				color="green";
			}
			RequestDispatcher dispatch = request.getRequestDispatcher("admin.jsp?message=" + message + "&color=" + color );
			dispatch.forward(request, response);
		}
		else if (type.equals("quizHistory"))
		{
			String quiz = request.getParameter("link");
			int quizid = getID(quiz);
			int takeStatus = QuizHelper.removeQuizHistory(conn, quizid);
			int status = QuizHelper.removeQuiz(conn, quizid);
			String message = "";
			String color = "";
			if (status == -1 || takeStatus == -1)
			{
				message = "Error occured when removing Quiz" + quizid + "'s history.";
				color = "red";
			}
			else
			{
				message = "Quiz's has been removed.";
				color = "green";
			}
			RequestDispatcher dispatch = request.getRequestDispatcher("admin.jsp?message=" + message + "&color=" + color );
			dispatch.forward(request, response);
		}
		else if (type.equals("admin"))
		{
			String user = request.getParameter("name");
			int status = UserHelper.makeAdmin(conn, user);
			String message = "";
			String color = "";
			if (status == -1)
			{
				message = "Error occured when promoting " + user + " to an Administrative role.";
				color = "red";
			}
			else
			{
				message = user + " has been promoted to Administrator.";
				color = "green";
			}
			RequestDispatcher dispatch = request.getRequestDispatcher("admin.jsp?message=" + message + "&color=" + color );
			dispatch.forward(request, response);
		}
		else {
			RequestDispatcher dispatch = request.getRequestDispatcher("admin.jsp");
			dispatch.forward(request, response);
		}
	}
	
	private static int getID(String link)
	{
		String idFromLink = "";
		for (int i = 0; i < link.length(); i++)
		{
			if (Character.isDigit(link.charAt(i)))
			{
				idFromLink = link.substring(i);
				break;
			}
		}
		if (idFromLink.isEmpty()) return -1;
		return Integer.parseInt(idFromLink);
	}

}

