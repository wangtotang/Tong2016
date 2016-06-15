package com.tong.tong2016.bean.db;

public class User {

	private String userId;
	private String userIcon;
	private String userName;
	private Gender userGender;

	public enum Gender {MALE, FEMALE}

	public User() {
	}

	public User(String userId, String userIcon, String userName, Gender userGender) {
		this.userId = userId;
		this.userIcon = userIcon;
		this.userName = userName;
		this.userGender = userGender;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Gender getUserGender() {
		return userGender;
	}

	public void setUserGender(Gender userGender) {
		this.userGender = userGender;
	}
}
