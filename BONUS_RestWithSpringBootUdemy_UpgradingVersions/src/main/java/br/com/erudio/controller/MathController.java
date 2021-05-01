package br.com.erudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.converters.NumberConverter;
import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.math.SimpleMath;

@RestController
public class MathController {
	
	@Autowired
	private SimpleMath math;
	
	@Autowired
	private NumberConverter converter;
	
	
	@RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double sum(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws Exception {
		validarParametros(numberOne, numberTwo);
		
		return math.sum(converter.convertToDouble(numberOne), converter.convertToDouble(numberTwo));
	}
	
	@RequestMapping(value = "/subtraction/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double subtracao(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws Exception  {
		validarParametros(numberOne, numberTwo);
		
		return math.subtraction(converter.convertToDouble(numberOne), converter.convertToDouble(numberTwo));
	}
	
	@RequestMapping(value = "/multiplication/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double multiplicacao(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws Exception  {
		validarParametros(numberOne, numberTwo);
		
		return math.multiplication(converter.convertToDouble(numberOne), converter.convertToDouble(numberTwo));
	}

	@RequestMapping(value = "/division/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double divisao(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws Exception  {
		validarParametros(numberOne, numberTwo);
		
		return math.division(converter.convertToDouble(numberOne), converter.convertToDouble(numberTwo));
	}

	@RequestMapping(value = "/mean/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double media(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws Exception  {
		validarParametros(numberOne, numberTwo);
		
		return math.mean(converter.convertToDouble(numberOne), converter.convertToDouble(numberTwo));
	}

	
	@RequestMapping(value = "/squareRoot/{numberOne}", method = RequestMethod.GET)
	public Double raiz(@PathVariable("numberOne") String numberOne) throws Exception  {
		validarParametros(numberOne, null);
		
		return math.squareRoot(converter.convertToDouble(numberOne).doubleValue());
	}
	
	
	private void validarParametros(String numberOne, String numberTwo) {
		if(!converter.isNumeric(numberOne) || !converter.isNumeric(numberTwo)) {
			throw new ResourceNotFoundException("Please set a numeric value!");
		}
	}
	
	
	
	

}
