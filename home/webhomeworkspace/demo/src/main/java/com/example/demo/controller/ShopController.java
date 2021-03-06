package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.ShopDTO;
import com.example.demo.model.ShopEntity;
import com.example.demo.service.ShopService;

@RestController
@RequestMapping("shop")
public class ShopController {
   
   @Autowired
   private ShopService service;
   
   @PostMapping
   public ResponseEntity<?> createShop(@RequestBody ShopDTO dto) {
	   try {
		   ShopEntity entity = ShopDTO.toEntity(dto);
		   
		   entity.setId(null);
		   
		   List<ShopEntity> entities = service.create(entity);
		   
		   List<ShopDTO> dtos = entities.stream().map(ShopDTO::new).collect(Collectors.toList());
		   
		   ResponseDTO<ShopDTO> response = ResponseDTO.<ShopDTO>builder().data(dtos).build();
		   
		   return ResponseEntity.ok().body(response);
	   } catch(Exception e) {
		   String error = e.getMessage();
		   ResponseDTO<ShopDTO> response = ResponseDTO.<ShopDTO>builder().error(error).build();
		   return ResponseEntity.badRequest().body(response);
	   }	   
   }

   
   @GetMapping
   public ResponseEntity<?> loadShopList() {
		   String temporaryUserId = "JangJiHyeon";
		   
		   List<ShopEntity> entities = service.load(temporaryUserId);
		   
		   List<ShopDTO> dtos = entities.stream().map(ShopDTO::new).collect(Collectors.toList());
		   
		   ResponseDTO<ShopDTO> response = ResponseDTO.<ShopDTO>builder().data(dtos).build();
		   
		   return ResponseEntity.ok().body(response);
   }
   
   @PostMapping("/retrieve")
   public ResponseEntity<?> retrieveShopList(@RequestBody ShopDTO dto) {
		   ShopEntity entity = ShopDTO.toEntity(dto);
		   
		   List<ShopEntity> entities = service.retrieve(entity.getTitle());
		   
		   List<ShopDTO> dtos = entities.stream().map(ShopDTO::new).collect(Collectors.toList());
		   
		   ResponseDTO<ShopDTO> response = ResponseDTO.<ShopDTO>builder().data(dtos).build();
		   
		   return ResponseEntity.ok().body(response);
   }
   
   
   @PutMapping
   public ResponseEntity<?> updateShop(@RequestBody ShopDTO dto) {
		   String temporaryUserId = "JangJiHyeon"; // temporary user id
		   
		   ShopEntity entity = ShopDTO.toEntity(dto);
		   
		   entity.setUserId(temporaryUserId);
		   
		   entity.setId((service.retrieve(entity.getTitle())).get(0).getId());
		   
		   List<ShopEntity> entities = service.update(entity);
		   
		   List<ShopDTO> dtos = entities.stream().map(ShopDTO::new).collect(Collectors.toList());
		   
		   ResponseDTO<ShopDTO> response = ResponseDTO.<ShopDTO>builder().data(dtos).build();
		   
		   return ResponseEntity.ok().body(response);
   }
   
   @DeleteMapping
   public ResponseEntity<?> deleteShop(@RequestBody ShopDTO dto) {
	   try {
		   String temporaryUserId = "JangJiHyeon";
		   
		   ShopEntity entity = ShopDTO.toEntity(dto);
		   
		   entity.setUserId(temporaryUserId);
		   
		   entity.setId((service.retrieve(entity.getTitle())).get(0).getId());
		   
		   List<ShopEntity> entities = service.delete(entity);
		   
		   List<ShopDTO> dtos = entities.stream().map(ShopDTO::new).collect(Collectors.toList());
		   
		   ResponseDTO<ShopDTO> response = ResponseDTO.<ShopDTO>builder().data(dtos).build();
		   
		   return ResponseEntity.ok().body(response);
	   } catch(Exception e) {
		   String error = e.getMessage();
		   ResponseDTO<ShopDTO> response = ResponseDTO.<ShopDTO>builder().error(error).build();
		   return ResponseEntity.badRequest().body(response);
	   }	   
   }
   
}
