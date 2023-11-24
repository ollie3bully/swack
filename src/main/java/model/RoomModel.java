package model;

import java.util.List;

import bean.Room;
import dao.RoomDAO;
import exception.SwackException;

public class RoomModel {
	private static final String PREFIX_ROOM_ID = "R";
	private static final String START_NO = "0001";
	
	public String createRoomId() throws SwackException {
		RoomDAO roomdao = new RoomDAO();
		String maxRoomId = roomdao.selectMaxRoomId();
		if (maxRoomId != null) {
			return PREFIX_ROOM_ID + String.format("%04d", Integer.parseInt(maxRoomId.substring(1)) + 1);
		} else {
			return PREFIX_ROOM_ID + START_NO;
		}
	}
	
	public void insertJoinRoom(String roomId, String join) throws SwackException {
		RoomDAO roomdao = new RoomDAO();
		roomdao.insertJoinRoom(roomId,join);
	}
	
	public void createRoom(Room room, String userId) throws SwackException {
		RoomDAO roomdao = new RoomDAO();
		roomdao.createRoom(room,userId);
	}
	
	public Room getRoomInf(String roomID) throws SwackException {
		RoomDAO roomdao = new RoomDAO();
		return roomdao.getRoomInf(roomID);
	}
	public List<Room> getRoomName(String userId)throws SwackException {
		return  new RoomDAO().getRoomName(userId);
	}
	public String roomExists(String roomName) throws SwackException {//すでに存在するダイレクトチャットのroomIDをもってくる、存在していなかったらnull
		RoomDAO roomdao = new RoomDAO();
		return roomdao.roomExists(roomName);
	}
	public void deleteUser(String roomId, String userId) throws SwackException {
		RoomDAO roomdao = new RoomDAO();
		roomdao.deleteUser(roomId,userId);
	}
}
