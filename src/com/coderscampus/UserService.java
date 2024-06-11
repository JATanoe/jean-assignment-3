package com.coderscampus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class UserService {

	private BufferedReader reader = null;
	private Scanner scanner;

	private String input;

	private boolean isLoggedIn;
	private int loginAttemptsLeft;

	private User[] users;
	private User currentUser;

	public UserService() {
		this.scanner = new Scanner(System.in);
		this.loginAttemptsLeft = 4;
		this.isLoggedIn = false;
		this.users = new User[10];
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

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public User getCurrentUser() {
		return this.currentUser;
	}

	public User[] getData() throws IOException {
		try {
			this.reader = new BufferedReader(new FileReader("data.txt"));

			// Declaring and initializing a line of string
			String line;

			// Declares and initializing a line counter
			int count = 0;

			while ((line = this.reader.readLine()) != null) {
				// Parse line of text from the file
				String[] values = line.split(",");

				// Create an User object
				User user = this.createUser(values);

				// Add new User to the User objects Arrays
				this.users[count] = user;

				// Increment count by 1
				count++;
			}

			// Copy the User objects Array
			// and truncate the length to the number of lines
			return Arrays.copyOf(this.users, count);

		} finally {
			this.reader.close();
		}
	}

	// Create User object from an String Array
	public User createUser(String[] params) {
		String username = params[0];
		String password = params[1];
		String name = params[2];

		return new User(username, password, name);
	}

	private void validate(String _username, String _password) {
		for (User user : this.users) {
			if (user.getUsername().equalsIgnoreCase(_username) & user.getPassword().equals(_password)) {
				this.setCurrentUser(user);
			}
		}
	}

	public void run() throws IOException {
		// Store the data
		this.users = this.getData(); 

		while (!this.isLoggedIn()) {
			// Prompt the user to enter a username
			this.setInput("Enter your email: ");
			String username = this.getInput();

			// Prompt the user to enter a password
			this.setInput("Enter your password: ");
			String password = this.getInput();

			// Validate the user credentials
			this.validate(username, password);
			
			// Check the number of login attempts left
			if (this.getLoginAttemptsLeft() == 0) {
				System.out.println("Too many failed login attempts, you are now locked out.");
				break;
				
			} 
			// Log in and greet the matching user 
			else if (this.getCurrentUser() != null) {
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
