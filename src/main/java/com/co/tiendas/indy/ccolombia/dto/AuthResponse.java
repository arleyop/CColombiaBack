package com.co.tiendas.indy.ccolombia.dto;

public class AuthResponse {
    private boolean ok;
    private String path;
    private String msg;
    private String token;
    private String id;
    private String name;
    private String email;
    private String role;
    
    
    
	public AuthResponse() {
		super();
	}
	public boolean isOk() {
		return ok;
	}
	public void setOk(boolean ok) {
		this.ok = ok;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public AuthResponse(boolean ok, String path, String msg, String token, String id, String name, String email,
			String role) {
		super();
		this.ok = ok;
		this.path = path;
		this.msg = msg;
		this.token = token;
		this.id = id;
		this.name = name;
		this.email = email;
		this.role = role;
	}
    
}
