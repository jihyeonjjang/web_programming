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
		// (1) ������ ����Ƽ�� ��ȿ���� Ȯ���Ѵ�.
		validate(entity);
		
		// (2) �Ѱܹ��� ����Ƽ id�� �̿��� TodoEntity�� �����´�. �������� �ʴ� ����Ƽ�� ������Ʈ �� �� ���� �����̴�.
		final Optional<TodoEntity> original = repository.findById(entity.getId());
		
		// ���ٽ�
		original.ifPresent(todo -> {
			// (3) ��ȯ�� repository�� �����ϸ� ���� �� entity������ ���� �����.
			todo.setTitle(entity.getTitle());
			todo.setDone(entity.isDone());
			
			// (4) ������ ���̽��� �� ���� �����Ѵ�.
			repository.save(todo);
		});
		
		return retrieve(entity.getUserId());
	}
	
	public List<TodoEntity> delete(final TodoEntity entity) {
		// (1) ������ ����Ƽ�� ��ȿ���� Ȯ���Ѵ�.
		validate(entity);
		
		try {
		// (2) ����Ƽ�� �����Ѵ�.
		repository.delete(entity);
		} catch(Exception e)
		{
			// (3) exception �߻� �� id�� exception�� �α��Ѵ�.
			log.error("error deleting entity ", entity.getId(), e);
			
			// (4) ��Ʈ�ѷ��� exception�� ������. �����ͺ��̽� ���� ������ ĸ��ȭ�Ϸ��� e�� �������� �ʰ� �� exception ������Ʈ�� �����Ѵ�.
			throw new RuntimeException("error deleting entity " + entity.getId());
		}
		// (5) �� Todo ����Ʈ�� ������ �����Ѵ�.
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
	
//	// 3�� 21�� ����
//	public String testService() {
//		TodoEntity entity = TodoEntity.builder().title("������ ù Ÿ��Ʋ").build();
//		
//		repository.save(entity);
//		TodoEntity savedEntity = repository.findById(entity.getId()).get();
//		
//		return savedEntity.getTitle();
//	}
}
