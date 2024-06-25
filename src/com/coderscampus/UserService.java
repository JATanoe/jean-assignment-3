package com.coderscampus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class UserService {

	private Scanner scanner;

	private String input;

	private boolean isLoggedIn;
	private int loginAttemptsLeft;

	private User[] users;
	private User currentUser;

	public UserService() {
		this.scanner = new Scanner(System.in);
		this.loginAttemptsLeft = 5;
		this.isLoggedIn = false;
		this.currentUser = null;
	}

	public String getInput() {
		return this.input;
	}

	public void setInput(String _message) {
		System.out.println(_message);
		this.input = this.scanner.next();
	}

	public boolean isLoggedIn() {
		return this.isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public int getLoginAttemptsLeft() {
		return this.loginAttemptsLeft;
	}

	public void setLoginAttemptsLeft(int loginAttemptsLeft) {
		this.loginAttemptsLeft -= loginAttemptsLeft;
	}

	public User[] getUsers() {
		return users;
	}

	public void setUsers(User[] users) {
		this.users = users;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public User getCurrentUser() {
		return this.currentUser;
	}

	// Create User object from an String Array
	public User createUser(String[] params) {
		String username = params[0];
		String password = params[1];
		String name = params[2];

		return new User(username, password, name);
	}

	public void getData() throws IOException {
		FileService fileService = new FileService();
		String[] lines = fileService.readFile();
		User[] users = new User[lines.length];

		// Declares and initializing a line counter
		int count = 0;
		
		for (String line: lines) {
			// Parse line of text from the file
			String[] values = line.split(",");

			// Create an User object
			User user = this.createUser(values);

			// Add created User to User objects Arrays
			users[count] = user;

			// Increment count by 1
			count++;
		}
		
		this.setUsers(users);

	}

	private void validate(String _username, String _password) {
		for (User user : this.users) {
			if (user.getUsername().equalsIgnoreCase(_username) & user.getPassword().equals(_password)) {
				this.setCurrentUser(user);
			}
		}
	}

	public void run() throws IOException {
		this.getData();
		
		while (!this.isLoggedIn()) {
			// Check the number of login attempts left
			if (this.getLoginAttemptsLeft() < 1) {
				System.out.println("Too many failed login attempts, you are now locked out.");
				break;
			}
			
			// Prompt the user to enter a username
			this.setInput("Enter your email: ");
			String username = this.getInput();

			// Prompt the user to enter a password
			this.setInput("Enter your password: ");
			String password = this.getInput();

			// Validate the user credentials
			this.validate(username, password);
			// Log in and greet the matching user
			if (this.getCurrentUser() != null) {
				this.setLoggedIn(true);
				System.out.println("Welcome " + this.currentUser.getName());
				break;
			}
			// If no match found
			else {
				this.setLoggedIn(false);
				this.setLoginAttemptsLeft(1);
				System.out.println("Invalid login, please try again.");
			}
		}

		this.scanner.close();
	}

}
