package com.ncit.finder.functionality;

import java.util.Random;

public class RandomCodeGenerator {
	static public String generate() {
		String code = "";
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
 			 int r = random.nextInt(10);
 			 code = code + r; 
		}
		return code;
	}

}
