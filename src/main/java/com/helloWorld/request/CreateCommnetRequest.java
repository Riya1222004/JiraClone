package com.helloWorld.request;

import lombok.Data;

@Data
public class CreateCommnetRequest {
	private Long issueId;
	private String commnetContent;
}
