package servlet;

import static parameter.Messages.*;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ChatLog;
import bean.Room;
import bean.User;
import exception.SwackException;
import model.ChatModel;
import model.UserModel;

/**
 * メイン画面を制御するServlet. 本Servletの実行にはログインを必要とする
 */
@WebServlet("/MainServlet")
public class MainServlet extends LoginCheckServlet  {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 画面から取得
		String roomId = request.getParameter("roomId");
		if (roomId == null) {
			// 初期ルームをeveryoneにする
			roomId = "R0000";
		}
		// ログイン情報から取得
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		UserModel userModel = new UserModel();
		try {
			ChatModel chatModel = new ChatModel();
			Room room = chatModel.getRoom(roomId, user.getUserId());
			List<Room> roomList = chatModel.getRoomList(user.getUserId());
			List<Room> directList = chatModel.getDirectList(user.getUserId());
			List<ChatLog> chatLogList = chatModel.getChatlogList(roomId);
			List<User> userList = userModel.getUserNameList(roomId);
			
//		List<user> userList = chatModel.getUserList(user.getUserName());
			
			// JSPに値を渡す
			request.setAttribute("room", room);
			request.setAttribute("roomList", roomList);
			request.setAttribute("directList", directList);
			request.setAttribute("chatLogList", chatLogList);
			request.setAttribute("user", user);		
			request.setAttribute("userList", userList);
			} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			return;
		}
		request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 画面から取得
		String roomId = request.getParameter("roomId");
		String message = request.getParameter("message");
		
		//ログイン情報から取得
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		try {
			new ChatModel().saveChatLog(roomId,user.getUserId(),message);
		}catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			return;
		}
		//GET処理にリダイレクト
		response.sendRedirect("MainServlet?roomId=" + roomId);
	}

}