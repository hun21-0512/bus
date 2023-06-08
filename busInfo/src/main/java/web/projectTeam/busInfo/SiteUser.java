package web.projectTeam.busInfo;

import jakarta.persistence.*;

@Entity
public class SiteUser {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long userNo;
	
	@Column(nullable=false, length=20, name="username")
	private String name;
	@Column(length=50, unique=true, nullable=false, name="email")
	private String email;
	@Column(length=50, unique=true, nullable=false, name="phone_number")
	private String phoneNumber;
	@Column(length=255, nullable=true, name="address")
	private String address;
	@Column(nullable=false, length=20, name="password")
	private String passwd;
	
	
	public Long getUserNo() {
		return userNo;
	}
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public String getPasswd() {
		return passwd;
	}
	
	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	
}
