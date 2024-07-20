package com.helloWorld.service;

import java.util.List;

import com.helloWorld.model.Commnets;

import lombok.extern.java.Log;

public interface CommentService {
	
	Commnets createCommnets(Long issueId,Long userId,String commnets) throws Exception;
	
	void deleteCommnets(Long commentId,Long userId) throws Exception;
	
	List<Commnets> findCommnetsByIssueId(Long issueId);
}
