package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TodoService {
	
	@Autowired
	private TodoRepository repository;
	
	public List<TodoEntity> create(final TodoEntity entity) {
		// Validations
		validate(entity);
		
		repository.save(entity);
		
		log.info("Entity Id : {} is saved.", entity.getUserId());
		
		return repository.findByUserId(entity.getUserId());
	}
	
	public List<TodoEntity> retrieve(final String userId) {
		return repository.findByUserId(userId);
	} 
	
	public List<TodoEntity> update(final TodoEntity entity) {
		// (1) 저장할 엔터티가 유효한지 확인한다.
		validate(entity);
		
		// (2) 넘겨받은 엔터티 id를 이용해 TodoEntity를 가져온다. 존재하지 않는 엔터티는 업데이트 할 수 없기 때문이다.
		final Optional<TodoEntity> original = repository.findById(entity.getId());
		
		// 람다식
		original.ifPresent(todo -> {
			// (3) 반환된 repository가 존재하면 값을 새 entity값으로 덮어 씌운다.
			todo.setTitle(entity.getTitle());
			todo.setDone(entity.isDone());
			
			// (4) 데이터 베이스에 새 값을 저장한다.
			repository.save(todo);
		});
		
		return retrieve(entity.getUserId());
	}
	
	public List<TodoEntity> delete(final TodoEntity entity) {
		// (1) 저장할 엔터티가 유효한지 확인한다.
		validate(entity);
		
		try {
		// (2) 엔터티를 삭제한다.
		repository.delete(entity);
		} catch(Exception e)
		{
			// (3) exception 발생 시 id와 exception을 로깅한다.
			log.error("error deleting entity ", entity.getId(), e);
			
			// (4) 컨트롤러로 exception을 보낸다. 데이터베이스 내부 로직을 캡슐화하려면 e를 리턴하지 않고 새 exception 오브젝트를 리턴한다.
			throw new RuntimeException("error deleting entity " + entity.getId());
		}
		// (5) 새 Todo 리스트를 가져와 리턴한다.
		return retrieve(entity.getUserId());
	}
	
	private void validate(final TodoEntity entity) {
		// Validations
		if(entity == null) {
			log.warn("Entity cannot be null.");
			throw new RuntimeException("Entity cannot be null.");
		}
				
		if(entity.getUserId() == null) {
			log.warn("Unknown user.");
			throw new RuntimeException("Unknown user.");
		}
	}
	
//	// 3월 21일 수업
//	public String testService() {
//		TodoEntity entity = TodoEntity.builder().title("장지현 첫 타이틀").build();
//		
//		repository.save(entity);
//		TodoEntity savedEntity = repository.findById(entity.getId()).get();
//		
//		return savedEntity.getTitle();
//	}
}
