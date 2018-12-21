package com.zj.server;

public class TestGetClassName {

	public static void main(String[] args) {
		
		User user = new User(1L,"战歌", 2);
		String name = user.getClass().getSimpleName();
		System.out.println(name);
	}

}

class User{
	private Long id;
	private String name;
	private int age;
	
	public User() {}
	public User(Long id, String name, int age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}
