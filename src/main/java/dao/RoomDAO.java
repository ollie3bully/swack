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

import bean.Room;
import exception.SwackException;

public class RoomDAO {
	public String selectMaxRoomId() throws SwackException {//佐々木
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
	
	public boolean createRoom(Room room,String userId) throws SwackException {//佐々木
		String sqlUserAdd = "INSERT INTO ROOMS VALUES(?, ?, ?, ?, ?)";

		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			PreparedStatement pStmt = conn.prepareStatement(sqlUserAdd);
			pStmt.setString(1, room.getRoomId());
			pStmt.setString(2, room.getRoomName());
			pStmt.setString(3, userId);
			pStmt.setBoolean(4, room.isDirected());
			pStmt.setBoolean(5, room.isPrivated());

			int result = pStmt.executeUpdate();
			if (result != 1) {
				return false;
			}

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		return true;

	}
	
	public boolean insertJoinRoom(String roomId, String join) throws SwackException {//joinはユーザーID
		String sqlUserAdd = "INSERT INTO JOINROOM VALUES(?, ?)";

		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			PreparedStatement pStmt = conn.prepareStatement(sqlUserAdd);
			pStmt.setString(1, roomId);
			pStmt.setString(2, join);

			int result = pStmt.executeUpdate();
			if (result != 1) {
				return false;
			}

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}
		return true;
	}
	
	public Room getRoomInf(String roomID) throws SwackException {
		String sql = "SELECT ROOMID,ROOMNAME FROM ROOMS WHERE ROOMID = ?";
		
		Room room = null;
		
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, roomID);
			
			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				String roomId = rs.getString("ROOMID");
				String roomName = rs.getString("ROOMNAME");
				// mask password
				room = new Room(roomId, roomName);
			}

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}
		return room;
	}
	
	public List<Room> getRoomName(String userId) throws SwackException {
		String sql = "SELECT DISTINCT ROOMID,ROOMNAME FROM ROOMS WHERE ROOMID NOT IN(SELECT DISTINCT R.ROOMID FROM ROOMS R JOIN JOINROOM J ON J.ROOMID = R.ROOMID WHERE J.USERID = ?) AND PRIVATED = FALSE";
		System.out.println(userId);
		List<Room> roomNameList = new ArrayList<Room>();
		Room room = null;

		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				String roomID = rs.getString("ROOMID");
				String roomName = rs.getString("ROOMNAME");

				room = new Room(roomID, roomName, 0, true);
				roomNameList.add(room);
			}

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}

		return roomNameList;
	}
	
	public String roomExists(String roomName) throws SwackException {
		String sql = "SELECT ROOMID FROM ROOMS WHERE ROOMNAME = ?";
		
		String roomId=null;
		
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, roomName);
			
			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				String roomID = rs.getString("ROOMID");
				roomId = roomID;
			}

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}
		return roomId;
	}
	
	public void deleteUser(String roomId, String userId) throws SwackException {
		String sqlUserAdd = "DELETE FROM JOINROOM WHERE ROOMID = ? AND USERID = ?";

		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			PreparedStatement pStmt = conn.prepareStatement(sqlUserAdd);
			pStmt.setString(1, roomId);
			pStmt.setString(2, userId);

			int result = pStmt.executeUpdate();
			

		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}
		return ;
		
	}
}
