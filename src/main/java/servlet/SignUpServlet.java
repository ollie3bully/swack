package servlet;

import static parameter.Messages.*;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import exception.SwackException;
import model.UserModel;

/**
 * 新規登録処理を実行するServlet
 */
@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/jsp/signup.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// リクエストパラメータを取得
		String userName = request.getParameter("userName");
		String mailAddress = request.getParameter("mailAddress");
		String password = request.getParameter("password");
		// パラメータチェック
		StringBuilder errorMsg = new StringBuilder();
		if (userName == null || userName.length() == 0) {
			errorMsg.append("氏名が入っていません<br>");
		}
		if (mailAddress == null || mailAddress.length() == 0) {
			errorMsg.append("メールアドレスが入っていません<br>");
		}
		if (password == null || password.length() == 0) {
			errorMsg.append("パスワードが入っていません<br>");
		}
		if (errorMsg.length() > 0) {
			// エラー
			request.setAttribute("errorMsg", errorMsg.toString());
			request.getRequestDispatcher("WEB-INF/jsp/signup.jsp").forward(request, response);
			return;
		}
		try {
			UserModel userModel = new UserModel();

			// 既に登録済みのメールアドレスかをチェック
			if (userModel.exists(mailAddress)) {
				// エラーメッセージと入力値をリクエストに設定し、ログイン画面に遷移
				request.setAttribute("errorMsg", ERR_USERS_ISREGISTERED);
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/signup.jsp");
				dispatcher.forward(request, response);
				return;
			}
			// ユーザIDを作成し、USERSテーブルに登録
			User user = new User(userModel.createUserId(), userName, mailAddress, password);
			userModel.registerUsers(user);

		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("WEB-INF/jsp/signup.jsp").forward(request, response);
			return;

		}

		request.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(request, response);

	}
}