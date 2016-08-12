package com.entity;

public class RegisterResponseVo {
	private String tel;
	private String userName;
	private String image;
	private String idNum;
	private String code;
	private String time;
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getUserId() {
		return userName;
	}

	public void setUserId(String userId) {
		this.userName = userId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "RegisterVo [tel=" + tel + ", userName=" + userName + ", image=" + image + ", idNum=" + idNum + ", code="
				+ code + ", time=" + time + ", password=" + password + "]";
	}
}
