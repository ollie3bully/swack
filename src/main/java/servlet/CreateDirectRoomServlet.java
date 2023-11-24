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

import bean.Room;
import bean.User;
import exception.SwackException;
import model.RoomModel;
import model.UserModel;

@WebServlet("/CreateDirectRoomServlet")
public class CreateDirectRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

				request.getRequestDispatcher("/WEB-INF/jsp/createdirect.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String [] directUserId = request.getParameterValues("userid");
		
		// ログイン情報から取得
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Room room = null;
		
		String roomName=user.getUserId() + ",";
		if(directUserId!=null) {
			for (String id : directUserId) {
				roomName = roomName + id + ",";
			}
		}
		roomName = "P" + roomName;
		roomName = roomName.substring(0, roomName.length()-1);
//		System.out.println(roomName);
		boolean privated =true;
		boolean directed = true;
		RoomModel roomModel = new RoomModel();
		String roomId="";
		try {
			roomId = roomModel.roomExists(roomName);
		} catch (SwackException e) {
			e.printStackTrace();
		}
		if(roomId != null) {
			response.sendRedirect("MainServlet?roomId=" + roomId);
		}else {
			//部屋作成処理
			try {
				roomId =roomModel.createRoomId();
				room = new Room(roomId,roomName,directed,privated);
				roomModel.createRoom(room,user.getUserId());
				roomModel.insertJoinRoom(roomId,user.getUserId());
			} catch (SwackException e) {
				e.printStackTrace();
				request.setAttribute("errorMsg", ERR_SYSTEM);
				request.getRequestDispatcher("/WEB-INF/jsp/createdirect.jsp").forward(request, response);
			}
			
			if(directUserId!=null) {
				for (String join : directUserId) {
					try {
						roomModel.insertJoinRoom(roomId,join);
					} catch (SwackException e) {
						e.printStackTrace();
					}
				}
			}
			response.sendRedirect("MainServlet?roomId=" + roomId);
		}
		
	}

}
