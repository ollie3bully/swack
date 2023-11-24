package dao;

import static parameter.DAOParameters.*;
import static parameter.Messages.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.User;
import exception.SwackException;

public class UsersDAO {
	public User select(String mailAddress, String password) throws SwackException {
		String sql = "SELECT USERID, USERNAME,APPROVED FROM USERS WHERE MAILADDRESS = ? AND PASSWORD = ?";

		User user = null;

		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, mailAddress);
			pStmt.setString(2, password);

			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				String userId = rs.getString("USERID");
				String userName = rs.getString("USERNAME");
				boolean approved = rs.getBoolean("APPROVED");
				// mask password
				user = new User(userId, userName, mailAddress,approved);
			}

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		return user;

	}

	public boolean exists(String mailAddress) throws SwackException {
		String sql = "SELECT * FROM USERS WHERE MAILADDRESS = ?";

		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, mailAddress);

			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				// exists
				return true;
			}
		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}
		return false;
	}

	public String selectMaxUserId() throws SwackException {
		String sql = "SELECT MAX(USERID) AS MAX_USERID FROM USERS ";

		String maxUserId = null;

		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			PreparedStatement pStmt = conn.prepareStatement(sql);

			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				maxUserId = rs.getString("MAX_USERID");
			}

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		return maxUserId;

	}

	public String selectMaxRoomId() throws SwackException {// 佐々木
		String sql = "SELECT MAX(ROOMID) AS MAX_ROOMID FROM ROOMS ";

		String maxRoomId = null;

		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			PreparedStatement pStmt = conn.prepareStatement(sql);

			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				maxRoomId = rs.getString("MAX_ROOMID");
			}

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		return maxRoomId;

	}

	public List<User> getUserName(String userId) throws SwackException {// 佐々木
		String sql = "SELECT USERNAME,USERID FROM USERS WHERE USERID <> ? AND USERID <> 'U0000' ";

		List<User> userNameList = new ArrayList<User>();
		User user = null;

		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);

			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				String userName = rs.getString("USERNAME");
				String userID = rs.getString("USERID");

				user = new User(userID, userName, "********", "********");
				userNameList.add(user);
			}

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		return userNameList;

	}

	public boolean insert(User user) throws SwackException {
		String sqlUserAdd = "INSERT INTO USERS(USERID, USERNAME, MAILADDRESS, PASSWORD) VALUES(?, ?, ?, ?)";
		String sqlJoinRoom = "INSERT INTO JOINROOM(ROOMID, USERID) VALUES('R0000', ?)";

		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			PreparedStatement pStmt = conn.prepareStatement(sqlUserAdd);
			pStmt.setString(1, user.getUserId());
			pStmt.setString(2, user.getUserName());
			pStmt.setString(3, user.getMailAddress());
			pStmt.setString(4, user.getPassword());

			int result = pStmt.executeUpdate();
			if (result != 1) {
				return false;
			}

			PreparedStatement pStmt2 = conn.prepareStatement(sqlJoinRoom);
			pStmt2.setString(1, user.getUserId());

			int result2 = pStmt2.executeUpdate();
			if (result2 != 1) {
				return false;
			}

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		return true;

	}

	public void updateUser(String userName, String password, String userId) throws SwackException {
		String sqlUserAdd = "UPDATE USERS SET USERNAME=?,PASSWORD=? WHERE USERID=?";

		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			PreparedStatement pStmt = conn.prepareStatement(sqlUserAdd);
			pStmt.setString(1, userName);
			pStmt.setString(2, password);
			pStmt.setString(3, userId);

			int result = pStmt.executeUpdate();

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}
		return;
	}

	public List<User> ridUserName() throws SwackException {

		String sql = "SELECT USERNAME,USERID FROM USERS WHERE USERID <> 'U0000' ";

		List<User> ridUserNameList = new ArrayList<User>();
		User user = null;
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			PreparedStatement pStmt = conn.prepareStatement(sql);

			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				String userID = rs.getString("USERID");
				String userName = rs.getString("USERNAME");
				user = new User(userID, userName, "**", "**");
				ridUserNameList.add(user);

			}

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		return ridUserNameList;
	}

	public void ridUser(String ridUserId) throws SwackException {
		String sql = "UPDATE USERS SET APPROVED = FALSE WHERE USERID = ?";
		// SQL文実行
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, ridUserId);

			int rs = pStmt.executeUpdate();
		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}
	}

	public List<User> getJoinUserName(String roomID) throws SwackException {
		String sql = "SELECT USERNAME,USERID FROM USERS WHERE USERID NOT IN"
				+ " (SELECT USERID FROM JOINROOM WHERE ROOMID=?) AND USERID <> 'U0000' ";

		List<User> userNameList = new ArrayList<User>();
		User user = null;

		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, roomID);

			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				String userName = rs.getString("USERNAME");
				String userID = rs.getString("USERID");

				user = new User(userID, userName, "********", "********");
				userNameList.add(user);
			}

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		return userNameList;
	}
	
	public List<User> getUserNameList(String roomId) throws SwackException {//ルームに参加しているメンバーを表示させるためのメソッド
		String sql = "SELECT USERNAME,U.USERID FROM USERS U JOIN JOINROOM J ON U.USERID = J.USERID WHERE ROOMID = ? AND "
				+ "U.USERID <> 'U0000' ";
	
		List<User> userNameList = new ArrayList<User>();
		User user = null;

		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, roomId);

			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				String userName = rs.getString("USERNAME");
				String userID = rs.getString("USERID");
				
				user = new User(userID, userName, "********", "********");
				userNameList.add(user);
			}

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		return userNameList;
	}
}