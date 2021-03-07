package br.com.erudio.math;

import org.springframework.stereotype.Service;

@Service
public class SimpleMath {
	
	public Double sum(Double firtNumber, Double secondNumber) {
		return firtNumber + secondNumber;
	}
	
	public Double subtraction(Double firtNumber, Double secondNumber) {
		return firtNumber - secondNumber;
	}

	public Double multiplication(Double firtNumber, Double secondNumber) {
		return firtNumber * secondNumber;
	}

	public Double division(Double firtNumber, Double secondNumber) {
		return firtNumber / secondNumber;
	}

	public Double mean(Double firtNumber, Double secondNumber) {
		return (firtNumber + secondNumber)/2;
	}

	public Double squareRoot(Double number) {
		return (Double)Math.sqrt(number);
	}

	
}
