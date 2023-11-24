package model;

import java.util.List;

import bean.User;
import dao.UsersDAO;
import exception.SwackException;

/**
 * ユーザ機能を実行するクラス
 */
public class UserModel {
	private static final String PREFIX_USER_ID = "U";
	private static final String START_NO = "0001";

	public String createUserId() throws SwackException {
		UsersDAO usersDAO = new UsersDAO();
		String maxUserId = usersDAO.selectMaxUserId();
		if (maxUserId != null) {
			return PREFIX_USER_ID + String.format("%04d", Integer.parseInt(maxUserId.substring(1)) + 1);
		} else {
			return PREFIX_USER_ID + START_NO;
		}
	}

	public boolean exists(String mailAddress) throws SwackException {
		UsersDAO usersDAO = new UsersDAO();
		boolean result = usersDAO.exists(mailAddress);
		return result;
	}

	public void registerUsers(User user) throws SwackException {
		UsersDAO usersDAO = new UsersDAO();
		boolean result = usersDAO.insert(user);
		if (result) {
			// TODO ダイレクト用ルーム作成
		}
		return;
	}

	public List<User> getUserName(String userId) throws SwackException {
		return new UsersDAO().getUserName(userId);
	}

	public List<User> ridUserName() throws SwackException {
		return new UsersDAO().ridUserName();
	}


	public void updateUser(String userName, String password, String userId) throws SwackException {
		UsersDAO usersDAO = new UsersDAO();
		usersDAO.updateUser(userName, password, userId);

	}

	public void ridUser(String ridUserId) throws SwackException {
		new UsersDAO().ridUser( ridUserId);
	}

	public List<User> getJoinUserName(String roomID) throws SwackException {
		return new UsersDAO().getJoinUserName(roomID);
		// return new UsersDAO().getUserName(userId);
	}
	
	public List<User> getUserNameList(String roomId) throws SwackException {
		UsersDAO usersDAO = new UsersDAO();
		return usersDAO.getUserNameList(roomId);
	}
}