package com.gk;

public class Application {
	public static void main(String[] args) {
		new Thread(new DecisionService()).start();
	}
}
