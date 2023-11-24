package servlet;

import static parameter.Messages.*;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import exception.SwackException;
import model.UserModel;

/**
 * Servlet implementation class MakeRiddanceServlet
 */
@WebServlet("/MakeRiddanceServlet")
public class MakeRiddanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		try {
			UserModel userModel = new UserModel();
			List<User> ridUserList = userModel.ridUserName();
			request.setAttribute("ridUserList", ridUserList);

		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		}
		request.getRequestDispatcher("/WEB-INF/jsp/makeriddance.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] ridUsers = request.getParameterValues("userid");
		if (ridUsers != null) {
			for (String ridUserId : ridUsers) {
				System.out.println(ridUserId);
			}

			UserModel userModel = new UserModel();

			if (ridUsers != null) {
				for (String ridUserId : ridUsers) {
					try {
						userModel.ridUser(ridUserId);
					} catch (SwackException e) {
						e.printStackTrace();
					}
				}
			}

			response.sendRedirect("MainServlet");
		}

	}
}
