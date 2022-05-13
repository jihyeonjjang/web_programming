package com.example.demo;

import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@RequiredArgsConstructor
public class DemoModel {

	@NonNull // id ���� null�� �Ǿ�� �ȵȴ�.
	private String id; 
}
