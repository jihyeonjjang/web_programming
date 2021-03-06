package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.ShopEntity;
import com.example.demo.persistence.ShopRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ShopService {
	
	@Autowired
	private ShopRepository repository;
	
	public List<ShopEntity> create(final ShopEntity entity) {
		// Validations
		validate(entity);
		
		repository.save(entity);
		
		log.info("Entity Id : {} is saved.", entity.getUserId());
		
		return repository.findByUserId(entity.getUserId());
	}

	private void validate(final ShopEntity entity) {
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
	
	public List<ShopEntity> load(final String userId) {
		return repository.findByUserId(userId);
	}
	
	public List<ShopEntity> retrieve(final String title) {
		return repository.findByTitle(title);
	}
	
	public List<ShopEntity> update(final ShopEntity entity) {
		validate(entity);

		final Optional<ShopEntity> original = repository.findById(entity.getId());

		original.ifPresent(shop -> {
			shop.setTitle(entity.getTitle());
			shop.setBrand(entity.getBrand());
			shop.setSize(entity.getSize());
			
			repository.save(shop);
		});
		
		return retrieve(entity.getTitle());
	}
	
	public List<ShopEntity> delete(final ShopEntity entity) {
		validate(entity);
	
		try {
			repository.delete(entity);
		} catch(Exception e)
		{
			log.error("error deleting entity ", entity.getId(), e);
			
			throw new RuntimeException("error deleting entity " + entity.getId());
		}
		return repository.findByUserId(entity.getUserId());
	}

}

