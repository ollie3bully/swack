package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import exception.SwackException;
import model.RoomModel;

/**
 * Servlet implementation class LeavingServlet
 */
@WebServlet("/LeavingServlet")
public class LeavingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		  HttpSession session = request.getSession();
		  User user = (User) session.getAttribute("user");
		  
		  String roomId = request.getParameter("roomId2");
		  RoomModel roomModel = new RoomModel();
		  
		  try {
			roomModel.deleteUser(roomId,user.getUserId());
		 } catch (SwackException e) {
			e.printStackTrace();
	   	}
		  
		  response.sendRedirect("MainServlet?roomId=R0000");
		  
	}


}
