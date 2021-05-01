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
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.data.vo.v2.PersonVOV2;
import br.com.erudio.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

// @CrossOrigin
@Tag(name = "Person Endpoint")
@RestController
@RequestMapping("/api/person/v1")
public class PersonController {

  @Autowired
  private PersonService services;


  @Operation(summary = "Find all people recorded.")
  @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
      "application/x-yaml"})
  public ResponseEntity<CollectionModel<PersonVO>> findAll(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "limit", defaultValue = "12") int limit,
      @RequestParam(value = "direction", defaultValue = "asc") String direction) {

    var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

    Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"));

    Page<PersonVO> persons = services.findAll(pageable);
    persons.stream().forEach(
        p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

    Link findAllLink =
        linkTo(methodOn(PersonController.class).findAll(page, limit, direction)).withSelfRel();

    return ResponseEntity.ok(CollectionModel.of(persons, findAllLink));
  }

  @Operation(summary = "Find all people recorded with firstName.")
  @GetMapping(value = "/findPersonByName/{firstName}", produces = {MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_XML_VALUE, "application/x-yaml"})
  public ResponseEntity<CollectionModel<PersonVO>> findPersonByName(
      @PathVariable("firstName") String firstName,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "limit", defaultValue = "12") int limit,
      @RequestParam(value = "direction", defaultValue = "asc") String direction) {

    var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

    Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"));

    Page<PersonVO> persons = services.findPersonByName(firstName, pageable);
    persons.stream().forEach(
        p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));


    return ResponseEntity.ok(CollectionModel.of(persons));
  }


  // @CrossOrigin(origins = "http://localhost:8080")
  @Operation(summary = "Buscar uma pessoa pelo identificador.")
  @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_XML_VALUE, "application/x-yaml"})
  public PersonVO findById(@PathVariable("id") Long id) {
    PersonVO personVO = services.findById(id);
    personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
    return personVO;
  }

  // @CrossOrigin(origins = {"http://localhost:8080", "http:www.google.com.br"})
  @Operation(summary = "Criar uma pessoa.")
  @PostMapping(
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
          "application/x-yaml"},
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
          "application/x-yaml"})
  public PersonVO create(@RequestBody PersonVO person) throws Exception {
    PersonVO personVO = services.create(person);
    personVO
        .add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel());
    return personVO;

  }

  @Operation(summary = "Criar uma pessoa versão 2.")
  @PostMapping(value = "/v2", produces = {MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_XML_VALUE, "application/x-yaml"})
  public PersonVOV2 createV2(@RequestBody PersonVOV2 person) throws Exception {
    return services.createV2(person);
  }

  @Operation(summary = "Atualizar registro de uma pessoa.")
  @PutMapping(
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
          "application/x-yaml"},
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
          "application/x-yaml"})
  public PersonVO update(@RequestBody PersonVO person) {
    PersonVO personVO = services.update(person);
    personVO
        .add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel());
    return personVO;
  }

  @Operation(summary = "Apagar registro de uma pessoal.")
  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") Long id) {
    services.delete(id);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Desativa uma pessoa pelo ID.")
  @PatchMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_XML_VALUE, "application/x-yaml"})
  public PersonVO disablePerson(@PathVariable("id") Long id) {
    PersonVO personVO = services.disablePerson(id);
    personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
    return personVO;
  }



}
