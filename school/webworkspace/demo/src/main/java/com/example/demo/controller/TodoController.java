package com.example.demo.controller;

//import java.util.ArrayList;
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
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;

@RestController
@RequestMapping("todo")
public class TodoController {
   
   @Autowired
   private TodoService service;
   
   @PostMapping
   public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
	   try {
		   String temporaryUserId = "temporary-user"; // temporary user id
		   
		   // (1) TodoEntity�� ��ȯ�Ѵ�.
		   TodoEntity entity = TodoDTO.toEntity(dto);
		   
		   // (2) id�� null�� �ʱ�ȭ�Ѵ�. ���� ��ÿ��� id�� ����� �ϱ� �����̴�.
		   entity.setId(null);
		   
		   // (3) �ӽ� ����� ���̵� �������ش�.
		   entity.setUserId(temporaryUserId);
		   
		   // (4) ���񽺸� �̿��� Todo ����Ƽ�� �����Ѵ�.
		   List<TodoEntity> entities = service.create(entity);
		   
		   // (5) �ڹ� ��Ʈ���� �̿��� ���ϵ� ��ƼƼ ����Ʈ�� TodoDTO����Ʈ�� ��ȯ�Ѵ�.
		   List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		   
		   // (6) ��ȯ�� TodoDTO����Ʈ�� �̿��� ResponseDTO�� �ʱ�ȭ�Ѵ�.
		   ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		   
		   // (7) ResponseDTO�� �����Ѵ�.
		   return ResponseEntity.ok().body(response);
	   } catch(Exception e) {
		   // (8)Ȥ�� ���ܰ� �ִ� ��� dto ��� error�� �޽����� �־� �����Ѵ�.
		   String error = e.getMessage();
		   ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
		   return ResponseEntity.badRequest().body(response);
	   }	   
   }
   
   @GetMapping
   public ResponseEntity<?> retrievetodoList() {
		   String temporaryUserId = "temporary-user"; // temporary user id
		   
		   // (1) ���� �޼����� retrieve()�޼��带 ����� Todo ����Ʈ�� �����´�.
		   List<TodoEntity> entities = service.retrieve(temporaryUserId);
		   
		   // (2)
		   List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		   
		   // (6) ��ȯ�� TodoDTO����Ʈ�� �̿��� ResponseDTO�� �ʱ�ȭ�Ѵ�.
		   ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		   
		   // (7) ResponseDTO�� �����Ѵ�.
		   return ResponseEntity.ok().body(response);
   }
   
   @PutMapping
   public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto) {
		   String temporaryUserId = "temporary-user"; // temporary user id
		   
		   // (1) dto�� entity�� ��ȯ�Ѵ�.
		   TodoEntity entity = TodoDTO.toEntity(dto);
		   
		   // (2) id�� temporaryUserId�� �ʱ�ȭ�Ѵ�.
		   entity.setUserId(temporaryUserId);
		   
		   // (3) ���񽺸� �̿��� entity�� ������Ʈ�Ѵ�.
		   List<TodoEntity> entities = service.update(entity);
		   
		   // (5) �ڹ� ��Ʈ���� �̿��� ���ϵ� ��ƼƼ ����Ʈ�� TodoDTO����Ʈ�� ��ȯ�Ѵ�.
		   List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		   
		   // (6) ��ȯ�� TodoDTO����Ʈ�� �̿��� ResponseDTO�� �ʱ�ȭ�Ѵ�.
		   ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		   
		   // (7) ResponseDTO�� �����Ѵ�.
		   return ResponseEntity.ok().body(response);
   }
   
   @DeleteMapping
   public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto) {
	   try {
		   String temporaryUserId = "temporary-user"; // temporary user id
		   
		   // (1) TodoEntity�� ��ȯ�Ѵ�.
		   TodoEntity entity = TodoDTO.toEntity(dto);
		   
		   // (2) �ӽ� ����� ���̵� �������ش�.
		   entity.setUserId(temporaryUserId);
		   
		   // (3) ���񽺸� �̿��� Todo ����Ƽ�� �����Ѵ�.
		   List<TodoEntity> entities = service.delete(entity);
		   
		   // (5) �ڹ� ��Ʈ���� �̿��� ���ϵ� ��ƼƼ ����Ʈ�� TodoDTO����Ʈ�� ��ȯ�Ѵ�.
		   List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		   
		   // (6) ��ȯ�� TodoDTO����Ʈ�� �̿��� ResponseDTO�� �ʱ�ȭ�Ѵ�.
		   ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		   
		   // (7) ResponseDTO�� �����Ѵ�.
		   return ResponseEntity.ok().body(response);
	   } catch(Exception e) {
		   // (8)Ȥ�� ���ܰ� �ִ� ��� dto ��� error�� �޽����� �־� �����Ѵ�.
		   String error = e.getMessage();
		   ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
		   return ResponseEntity.badRequest().body(response);
	   }	   
   }
   
}

//   // 3�� 21�� ����
//   @GetMapping("/test")
//   public ResponseEntity<?> testTodo() {
//      String str = service.testService();
//      
//      List<String> list = new ArrayList();
//      list.add(str);
//      
//      ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
//      
//      return ResponseEntity.ok().body(response);
//   }
   
//   // ����
//   @GetMapping("/testTodo")
//   public ResponseEntity<?> todoControllerResponseEntity() {
//      String id = "111";
//      String title = "JangJiHyeon";
//      
//      TodoDTO response = TodoDTO.builder().id(id).title(title).done(true).build();
//      
//      return ResponseEntity.ok().body(response);
//   }