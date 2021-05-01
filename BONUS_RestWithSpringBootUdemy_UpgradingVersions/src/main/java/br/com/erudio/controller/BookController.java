package br.com.erudio.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Book Endpoint")
@RestController
@RequestMapping("/api/book/v1")
public class BookController {

  @Autowired
  private BookService services;


  @Operation(summary = "Buscar todos os livros.")
  @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
      "application/x-yaml"})
  public ResponseEntity<CollectionModel<BookVO>> findAll(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "limit", defaultValue = "12") int limit,
      @RequestParam(value = "direction", defaultValue = "asc") String direction) {

    var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

    Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "author"));

    Page<BookVO> books = services.findAll(pageable);
    books.stream().forEach(
        p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));


    return ResponseEntity.ok(CollectionModel.of(books));
  }

  @Operation(summary = "Buscar um livro pelo identificador.")
  @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_XML_VALUE, "application/x-yaml"})
  public BookVO findById(@PathVariable("id") Long id) {
    BookVO booksVO = services.findById(id);
    booksVO.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
    return booksVO;
  }

  @Operation(summary = "Criar o registro de um novo livro.")
  @PostMapping(
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
          "application/x-yaml"},
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
          "application/x-yaml"})
  public BookVO create(@RequestBody BookVO book) throws Exception {
    BookVO booksVO = services.create(book);
    booksVO.add(linkTo(methodOn(BookController.class).findById(booksVO.getKey())).withSelfRel());
    return booksVO;

  }

  @Operation(summary = "Alterar registro de um livro.")
  @PutMapping(
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
          "application/x-yaml"},
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
          "application/x-yaml"})
  public BookVO update(@RequestBody BookVO book) {
    BookVO booksVO = services.update(book);
    booksVO.add(linkTo(methodOn(BookController.class).findById(booksVO.getKey())).withSelfRel());
    return booksVO;
  }

  @Operation(summary = "Remover um livro.")
  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") Long id) {
    services.delete(id);
    return ResponseEntity.ok().build();
  }



}
