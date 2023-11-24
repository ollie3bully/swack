package servlet;

import static parameter.Messages.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exception.SwackException;
import model.ChatModel;

/**
 * チャット削除を制御するServlet 本Servletの実行にはログインを必要とする
 */
@WebServlet("/DeleteChatServlet")
public class DeleteChatServlet extends LoginCheckServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ログイン情報から取得
//		HttpSession session = request.getSession();
		

		// 画面から取得
		String roomId = request.getParameter("roomId");
		int deleteChatLogId = Integer.parseInt(request.getParameter("deleteChatLogId"));
		System.out.println("ルームID:" + roomId + ",削除するチャットID:" + deleteChatLogId);

		try {

			// TODO 画面に必要な項目を渡す
			ChatModel chatModel = new ChatModel();
			chatModel.deleteChatLog(deleteChatLogId);
		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			return;
		}
		// GET処理にリダイレクト
		response.sendRedirect("MainServlet?roomId=" + roomId);
	}

}
