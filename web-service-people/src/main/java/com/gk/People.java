package com.gk;

public class People {
	String id;
	String description;
	double density;
	double age;
	double mood;

	public People(String id, String description, double density, double age, double mood) {
		super();
		this.id = id;
		this.description = description;
		this.density = density;
		this.age = age;
		this.mood = mood;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getDensity() {
		return density;
	}

	public void setDensity(double density) {
		this.density = density;
	}

	public double getAge() {
		return age;
	}

	public void setAge(double age) {
		this.age = age;
	}

	public double getMood() {
		return mood;
	}

	public void setMood(double mood) {
		this.mood = mood;
	}
}
