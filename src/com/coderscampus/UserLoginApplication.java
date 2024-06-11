package com.coderscampus;

import java.io.IOException;

public class UserLoginApplication  {

	public static void main(String[] args) throws IOException {
		
		UserService service = new UserService();
		service.run();

	}

}
