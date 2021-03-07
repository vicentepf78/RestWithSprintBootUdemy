package br.com.erudio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.converter.DozerConverter;
import br.com.erudio.converter.custom.BooksConverter;
import br.com.erudio.data.model.Books;
import br.com.erudio.data.vo.v1.BooksVO;
import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.repository.BooksRepository;

@Service
public class BooksService {
	
	@Autowired
	BooksRepository repository;
	
	@Autowired
	BooksConverter converter;

	public BooksVO create(BooksVO book) {
		var entity = DozerConverter.parseObject(book, Books.class);
		return DozerConverter.parseObject(repository.save(entity), BooksVO.class);
	}
	

	public BooksVO update(BooksVO book) {
		
		var entity = repository.findById(book.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records founds for this id!"));
		
		entity.setAuthor(book.getAuthor());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		entity.setTitle(book.getTitle());
		
		var vo = DozerConverter.parseObject(repository.save(entity), BooksVO.class);
		
		return vo;
	}
	
	public void delete(Long id) {
		Books entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records founds for this id!"));
		
		repository.delete(entity);
	}


	public BooksVO findById(Long id) {
		var entity = repository.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException("No records founds for this id!"));
		
		return DozerConverter.parseObject(entity, BooksVO.class);
	}

	public List<BooksVO> findAll() {
		return DozerConverter.parseListObjects(repository.findAll(), BooksVO.class);
	}
	

}
