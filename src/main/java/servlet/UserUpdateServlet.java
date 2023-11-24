package servlet;

import static parameter.Messages.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import exception.SwackException;
import model.UserModel;

@WebServlet("/UserUpdateServlet")
public class UserUpdateServlet extends LoginCheckServlet {
	private static final long serialVersionUID = 1L;
	
//	userUpdate.jspに遷移←ログインJSPパクッていいかも
//	アップデートのためmodel→users.dao各作成
//	ユーザー名とパスワードだから重複チェックなくてよし
//基本的にログインサーブレットの模倣で良し
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/jsp/userupdate.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// パラメータ取得
				String userName = request.getParameter("userName");
				String password = request.getParameter("password");

				// パラメータチェック
				StringBuilder errorMsg2 = new StringBuilder();
				if (userName == null || userName.length() == 0) {
					errorMsg2.append("ユーザー名が入っていません<br>");
				}
				if (password == null || password.length() == 0) {
					errorMsg2.append("パスワードが入っていません<br>");
				}
				if (errorMsg2.length() > 0) {
					// エラー
					request.setAttribute("errorMsg", errorMsg2.toString());
					request.getRequestDispatcher("WEB-INF/jsp/userupdate.jsp").forward(request, response);
					return;
				}
				// ログイン情報から取得
				HttpSession session = request.getSession();
				User user = (User) session.getAttribute("user");

				// 処理
				UserModel userModel = new UserModel();
				try {
					userModel.updateUser(userName,password,user.getUserId());
				} catch (SwackException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
					request.setAttribute("errorMsg", ERR_SYSTEM);
					request.getRequestDispatcher("WEB-INF/jsp/userupdate.jsp").forward(request, response);
					return;
				}
				user = new User(user.getUserId(), userName, user.getMailAddress(), password);
				session.setAttribute("user", user);
				
				response.sendRedirect("MainServlet");
	}

}
