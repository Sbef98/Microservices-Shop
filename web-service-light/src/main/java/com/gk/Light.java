package com.gk;

public class Light {
	String id;
	String description;
	double intensity;

	public Light(String id, String description, double intensity) {
		super();
		this.id = id;
		this.description = description;
		this.intensity = intensity;
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

	public double getIntensity() {
		return intensity;
	}

	public void setIntensity(double intensity) {
		this.intensity = intensity;
	}
}
