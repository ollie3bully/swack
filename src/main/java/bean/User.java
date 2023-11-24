package bean;

import java.io.Serializable;

/**
 * ユーザ情報を管理するBean
 */
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	/** ユーザID */
	private String userId;
	/** ユーザ名 */
	private String userName;
	/** メールアドレス */
	private String mailAddress;
	/** パスワード */
	private String password;
	/** アカウント失効フラグ */
	private boolean approved;
	
	public User() {
		// for JSP
	}
	/** デフォルトのコンストラクタ */
	public User(String userId, String userName, String mailAddress, String password) {
		this.userId = userId;
		this.userName = userName;
		this.mailAddress = mailAddress;
		this.password = password;
	}
	
	/** ログインチェック用のコンストラクタ */
	public User(String userId, String userName, String mailAddress,boolean approved) {
		this.userId = userId;
		this.userName = userName;
		this.mailAddress = mailAddress;
		this.approved = approved;
	}

	public String getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public String getPassword() {
		return password;
	}

	public boolean isApproved() {
		return approved;
	}

}