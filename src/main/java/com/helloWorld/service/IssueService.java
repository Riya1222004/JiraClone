package com.helloWorld.service;

import java.util.List;
import java.util.Optional;

import com.helloWorld.model.Issue;
import com.helloWorld.model.User;
import com.helloWorld.request.IssueRequest;

public interface IssueService {
	Issue getIssueById(Long issueId) throws Exception;
	
	List<Issue> getIssueByProjectId(Long projectId) throws Exception;
	
	Issue createIssue(IssueRequest issueRequest,User user) throws Exception;
	
	//Optional<Issue> updateIssue(Long issueId,IssueRequest issueRequest,Long userId) throws Exception;
	
	void deleteIssue(Long issueId,Long userId) throws Exception;
	
	Issue adduserToIssue(Long issueId,Long userId) throws Exception;
	
	Issue updateIssueStatus(Long issueId,String status) throws Exception;
}
