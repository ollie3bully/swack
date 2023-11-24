package bean;

import java.io.Serializable;

/**
 * ルーム情報を管理するBean
 */
public class Room implements Serializable {
	private static final long serialVersionUID = 1L;

	/** ルームID */
	private String roomId;
	/** ルーム名 */
	private String roomName;
	/** 参加メンバー数 */
	private int memberCount;
	/** ダイレクトチャットか */
	private boolean directed;
	/** パブリックか (仮変数名)*/
	private boolean privated;
	
	public Room() {
	}

	public Room(String roomId, String roomName, int memberCount, boolean directed) {
		this.roomId = roomId;
		this.roomName = roomName;
		this.memberCount = memberCount;
		this.directed = directed;
	}

	// 一覧表示用(参加メンバー数0名,ダイレクトはfalseで固定)
	public Room(String roomId, String roomName) {
		this(roomId, roomName, 0, false);
	}
	
	//ルーム作成用
	public Room(String roomId, String roomName, boolean directed, boolean privated) {
		this.roomId = roomId;
		this.roomName = roomName;
		this.directed = directed;
		this.privated = privated;
	}
	

	public String getRoomId() {
		return roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public boolean isPrivated() {
		return privated;
	}

	public int getMemberCount() {
		return memberCount;
	}

	public boolean isDirected() {
		return directed;
	}

}
