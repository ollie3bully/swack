package servlet;

import static parameter.Messages.*;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Room;
import bean.User;
import exception.SwackException;
import model.RoomModel;
import model.UserModel;

/**
 * メンバー招待画面を制御するServlet 本Servletの実行にはログインを必要とする
 */
@WebServlet("/JoinMemberServlet")
public class JoinMemberServlet extends LoginCheckServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				// ログイン情報から取得
				HttpSession session = request.getSession();
				User user = (User) session.getAttribute("user");
				// 画面から取得
				String roomID = request.getParameter("roomId");
				System.out.println(roomID);
				if (roomID == null) {
					// 初期ルームをeveryoneにする
					roomID = "R0000";
				}
				try {
					UserModel userModel = new UserModel();
					RoomModel roomModel = new RoomModel();
					Room room = roomModel.getRoomInf(roomID);
					List<User> joinUserNameList = userModel.getJoinUserName(roomID);
					request.setAttribute("joinUserNameList", joinUserNameList);
					request.setAttribute("room", room);

				} catch (SwackException e) {
					e.printStackTrace();
					request.setAttribute("errorMsg", ERR_SYSTEM);
					request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
					return;
				}

		request.getRequestDispatcher("/WEB-INF/jsp/joinmember.jsp").forward(request, response);

	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String [] joinUserId = req.getParameterValues("userid");
		String roomId = req.getParameter("roomId");

		RoomModel roomModel = new RoomModel();
		
		if(joinUserId!=null) {
			for (String join : joinUserId) {
				try {
					roomModel.insertJoinRoom(roomId,join);
				} catch (SwackException e) {
					e.printStackTrace();
					
				}
			}
		}
		resp.sendRedirect("MainServlet?roomId=" + roomId);
		
		
	}

}