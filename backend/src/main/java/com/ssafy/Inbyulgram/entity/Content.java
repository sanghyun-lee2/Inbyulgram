package com.ssafy.Inbyulgram.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Content {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int uid;
	private String path;
	private String title;
	private String password;
	
	@Builder
	public Content() {
		
	}
	
	@Builder
	public Content(String path, String title, String password) {
		super();
		this.path = path;
		this.title = title;
		this.password = password;
	}
	
	public int getUid() {
		return this.uid;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getTitle() {
		return this.title;
	}
}
