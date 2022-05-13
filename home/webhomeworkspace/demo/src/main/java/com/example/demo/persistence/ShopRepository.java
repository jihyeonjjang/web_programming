package com.example.demo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ShopEntity;

@Repository
public interface ShopRepository extends JpaRepository<ShopEntity, String> {
	List<ShopEntity> findByUserId(String userId);
	List<ShopEntity> findByTitle(String title);
}
