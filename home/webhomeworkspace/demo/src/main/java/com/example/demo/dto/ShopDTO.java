package com.example.demo.dto;

import com.example.demo.model.ShopEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShopDTO {
	private String id;
	private String userId;
	private String title;
	private String brand;
	private Float size;
	
	public ShopDTO(final ShopEntity entity) {
		this.id = entity.getId();
		this.userId = entity.getUserId();
		this.title = entity.getTitle();
		this.brand = entity.getBrand();
		this.size = entity.getSize();
	}
	
	public static ShopEntity toEntity(final ShopDTO dto) {
		return ShopEntity.builder()
				.id(dto.getId())
				.userId(dto.getUserId())
				.title(dto.getTitle())
				.brand(dto.getBrand())
				.size(dto.getSize())
				.build();
	}
}

