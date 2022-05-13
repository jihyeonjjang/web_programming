package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Shop")
public class ShopEntity {
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id; // �� ������Ʈ�� ���̵�
	private String userId; // �� ������Ʈ�� ������ ������� ���̵�
	private String title; // �º�PC�� �̸� (��: iPad Air 4)
	private String brand; // �º�PC�� ȸ�� (��: Apple)
	private Float size; // �º�PC�� ȭ�� ũ�� (��:10.9��ġ)
}
