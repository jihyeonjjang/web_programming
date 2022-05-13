package com.example.demo;

import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@RequiredArgsConstructor
public class DemoModel {

	@NonNull // id 값이 null이 되어서는 안된다.
	private String id; 
}
