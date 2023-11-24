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
 * メッセージ編集を制御するServlet 本Servletの実行にはログインを必要とする
 */
@WebServlet("/EditChatServlet")
public class EditChatServlet extends LoginCheckServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO 必要な処理
		// 画面から取得
		String roomId = request.getParameter("roomId");
		int editChatLogId = Integer.parseInt(request.getParameter("editChatLogId"));
		String editMessage = request.getParameter("editMessage");
		System.out.println("ルームID:" + roomId + ",編集するチャットID:" + editChatLogId + ",編集メッセージ:" + editMessage);
		try {
			ChatModel chatModel = new ChatModel();
			chatModel.editChatLogId(editChatLogId, editMessage);
		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			return;
		}

		// GET処理にリダイレクト
		response.sendRedirect("MainServlet?roomId=" + roomId + "&chatLogId=" + editChatLogId);
	}
}
