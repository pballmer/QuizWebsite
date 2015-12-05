package servlets;

import java.io.IOException;
import java.util.ArrayList;

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
 * Servlet implementation class MCEditServlet
 */
@WebServlet("/MCEditServlet")
public class MCEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MCEditServlet() {
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
		int numOptions = Integer.parseInt(request.getParameter("numOptions"));
		ArrayList<String> options = new ArrayList<String>();
		for (int i = 0; i <= numOptions; ++i) {
			String param = "option" + i;
			String option = request.getParameter(param);
			if (!option.isEmpty()) options.add(option);
		}
		String answer = request.getParameter("answer");
		int questionID = Integer.parseInt(request.getParameter("questionID"));
		ServletContext context = getServletContext();
		DBConnection conn = (DBConnection) context.getAttribute("Database Connection");
		if (text.isEmpty() && options.isEmpty() && answer.isEmpty()) {
			QuestionHelper.deleteQuestion(conn, questionID, QuestionHelper.MULTIPLE_CHOICE);
		} else {
			QuestionHelper.setMCAttributes(conn, questionID, text, options, answer);
		}
        RequestDispatcher dispatch = request.getRequestDispatcher("editquiz.jsp");
		dispatch.forward(request, response);
	}
}
