package com.coderscampus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileService {
	
	public String[] readFile() throws IOException {
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader("data.txt"));
			
			// Declaring and initializing an array of string
			String[] lines = new String[4];

			// Declaring and initializing the current line of string
			String line;

			// Declares and initializing an iterator
			int i = 0;

			while ((line = reader.readLine()) != null) {
				// Parse line of text from the file
				lines[i] = line;

				// Increment count by 1
				i++;
			}
			return lines;

		} finally {
			reader.close();
		}
	}
	
}
