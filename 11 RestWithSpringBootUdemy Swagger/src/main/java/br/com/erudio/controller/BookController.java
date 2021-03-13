package br.com.erudio.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.services.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Book Endpoint", description = "Descripton for book", tags = {"BookEndpoint"})
@RestController
@RequestMapping("/api/books/v1")
public class BookController {
	
	@Autowired
	private BookService services;

	@ApiOperation(value = "Buscar todos os livros.")
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"})
	public List<BookVO> findAll() {
		List<BookVO> booksVOs = services.findAll();
		booksVOs
			.stream()
			.forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));
		return booksVOs;
	}

	@ApiOperation(value = "Buscar um livro pelo identificador.")
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"})
	public BookVO findById(@PathVariable("id") Long id) {
		BookVO booksVO = services.findById(id);
		booksVO.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		return booksVO;
	}

	@ApiOperation(value = "Criar o registro de um novo livro.")
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"},
			     consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"})
	public BookVO create(@RequestBody BookVO book) throws Exception {
		BookVO booksVO = services.create(book);
		booksVO.add(linkTo(methodOn(BookController.class).findById(booksVO.getKey())).withSelfRel());
		return booksVO;

	}
	
	@ApiOperation(value = "Alterar registro de um livro.")
	@PutMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"},
			    consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"})
	public BookVO update(@RequestBody BookVO book) {
		BookVO booksVO =  services.update(book);
		booksVO.add(linkTo(methodOn(BookController.class).findById(booksVO.getKey())).withSelfRel());
		return booksVO;
	}

	@ApiOperation(value = "Remover um livro.")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		services.delete(id);
		return ResponseEntity.ok().build();
	}
	
	
	
	

}
