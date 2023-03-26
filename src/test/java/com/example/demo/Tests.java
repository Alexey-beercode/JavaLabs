package com.example.demo;

import com.example.demo.exception.IllegalArgumetsException;
import com.example.demo.model.NumberModel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@SpringBootTest
class Tests {

	@Test
	void logicTest() {
		NumberModel number = new NumberModel();
		number.setNumber(4);
		String expectedResult="Четное и не простое";
		boolean testResult = number.checkNumber()==expectedResult;
		assertTrue(testResult);
	}

	@Test
	void exceptionTest() throws IllegalArgumetsException {
		NumberModel number=new NumberModel();
		number.setNumber(-4);
		boolean result=false;
		try {
			number.isNegativeNumber();
		}
		catch (IllegalArgumetsException exception){
			result=true;
		}
		assertNotEquals(false,result);
	}
}