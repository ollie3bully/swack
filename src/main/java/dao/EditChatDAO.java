package dao;

import static parameter.DAOParameters.*;
import static parameter.Messages.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import exception.SwackException;

public class EditChatDAO {
	public void editChatLog(int editChatLogId,String editMessage)throws SwackException {
		String sql = "UPDATE CHATLOG SET MESSAGE = ? WHERE CHATLOGID = ?";
		
		//SQL文実行
		try (Connection conn = DriverManager.getConnection(DB_ENDPOINT, DB_USERID, DB_PASSWORD)) {
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(2,editChatLogId);
			pStmt.setString(1,editMessage);

			int rs = pStmt.executeUpdate();
		} catch (SQLException e) {
			throw new SwackException(ERR_DB_PROCESS, e);
		}

	}
}
