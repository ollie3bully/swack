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
 * ルーム作成画面を制御するServlet 本Servletの実行にはログインを必要とする
 */
@WebServlet("/CreateRoomServlet")
public class CreateRoomServlet extends LoginCheckServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO 必要な処理
		// ログイン情報から取得
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		try {
			UserModel userModel = new UserModel();
			List<User> userNameList = userModel.getUserName(user.getUserId());
			request.setAttribute("userNameList", userNameList);

		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
			return;
		}

		request.getRequestDispatcher("/WEB-INF/jsp/createroom.jsp").forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String Privated = req.getParameter("chk");
		String roomName = req.getParameter("roomName");
		String[] joinUserId = req.getParameterValues("userid");

		String roomID = "R0000";
		if (roomName.isEmpty()) {
			resp.sendRedirect("MainServlet?roomId=" + roomID);
		} else {
			// ログイン情報から取得
			HttpSession session = req.getSession();
			User user = (User) session.getAttribute("user");
			Room room = null;

			boolean privated = false;
			System.out.println("Privatedは、"+Privated);
			if (!(user.getUserId().equals("U0000"))) {
				privated = false;
			} else {
				if(Privated != null) {
					privated = false;
				} else {
					privated = true;
				}
			}

			boolean directed = false;
			RoomModel roomModel = new RoomModel();
			String roomId = null;
			try {
				roomId = roomModel.createRoomId();
				room = new Room(roomId, roomName, directed, privated);
				roomModel.createRoom(room, user.getUserId());
				System.out.println(user.getUserId());
				if(user.getUserId().equals("U0000")) {
					System.out.println("u0000じゃん");
					System.out.println(privated);
					roomModel.insertJoinRoom(roomId, user.getUserId());
				} else {
					System.out.println("u0000じゃないじゃん");
					System.out.println(privated);
					roomModel.insertJoinRoom(roomId, user.getUserId());
					roomModel.insertJoinRoom(roomId, "U0000");
				}
			} catch (SwackException e) {
				e.printStackTrace();
				req.setAttribute("errorMsg", ERR_SYSTEM);
				req.getRequestDispatcher("/WEB-INF/jsp/createroom.jsp").forward(req, resp);
			}

			if (joinUserId != null) {
				for (String join : joinUserId) {
					try {
						roomModel.insertJoinRoom(roomId, join);
					} catch (SwackException e) {
						e.printStackTrace();

					}
				}
			}
			resp.sendRedirect("MainServlet?roomId=" + roomId);
		}

	}
}