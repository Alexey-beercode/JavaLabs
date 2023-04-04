package com.example.demo;

import com.example.demo.exception.IllegalArgumetsException;
import com.example.demo.model.CheckModel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class Tests {

	@Test
	void checkNumberTestWithEvenNumber() throws IllegalArgumetsException {
		String expectedResult="Четное и не простое";
		assertEquals(expectedResult,CheckModel.checkNumber(4));
	}
	@Test
	void checkNumberTestWithPrimeNumber() throws IllegalArgumetsException {
		String expectedResult="Не четное и простое";
		assertEquals(expectedResult,CheckModel.checkNumber(3));
	}

	@Test
	void exceptionTest() throws IllegalArgumetsException {
		boolean result=false;
		try {
			CheckModel.checkNumber(-1);
			fail();
		}
		catch (IllegalArgumetsException exception){
			return;
		}
	}
}