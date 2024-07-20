package com.helloWorld.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helloWorld.model.Issue;
import com.helloWorld.model.IssueDTO;
import com.helloWorld.model.User;
import com.helloWorld.request.IssueRequest;
import com.helloWorld.response.AuthResponse;
import com.helloWorld.response.MessageResponse;
import com.helloWorld.service.IssueService;
import com.helloWorld.service.UserService;

@RestController
@RequestMapping("/api/issues")
public class IssueController {
	@Autowired
	IssueService issueService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/{issueId}") 
	public ResponseEntity<Issue> getIssueById(@PathVariable Long issueId) throws Exception{
		return ResponseEntity.ok(issueService.getIssueById(issueId));	
	}
	
	@GetMapping("/project/{projectId}") 
	public ResponseEntity<List<Issue>> getIssueByProjectId(@PathVariable Long projectId) throws Exception{
		return ResponseEntity.ok(issueService.getIssueByProjectId(projectId));	
	}
	
	@PostMapping
	public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueRequest issueRequest,
												@RequestHeader("Authorization") String token)
														throws Exception{
															
		User tokenuser=userService.findUserProfileByJWT(token);
		User user=userService.findUserById(tokenuser.getId());
		
		Issue createdIssue=issueService.createIssue(issueRequest, tokenuser);
		IssueDTO issueDTO=new IssueDTO();
		
		issueDTO.setDescription(createdIssue.getDescription());
		issueDTO.setDueDate(createdIssue.getDueDate());
		issueDTO.setPriority(createdIssue.getDescription());
		issueDTO.setProject(createdIssue.getProject());
		issueDTO.setProjectId(createdIssue.getProjectId());
		issueDTO.setStatus(createdIssue.getStatus());
		issueDTO.setTitle(createdIssue.getTitle());
		issueDTO.setTag(createdIssue.getTags());
		issueDTO.setAssignee(createdIssue.getAssignee());
		issueDTO.setId(createdIssue.getId());
		
		return ResponseEntity.ok(issueDTO);
		
	}
	
	@DeleteMapping("/{issueId}")
	public ResponseEntity<MessageResponse> deleteIssue(@PathVariable Long issueId,
													@RequestHeader("Authorization") String token)
													throws Exception{
		User user=userService.findUserProfileByJWT(token);
		issueService.deleteIssue(issueId,user.getId());
		
		MessageResponse messageResponse=new MessageResponse();
		messageResponse.setMessage("Issue Deleted");
		
		return ResponseEntity.ok(messageResponse);
	}
	
	@PutMapping("/issueId/assignee/{userId}")
	public ResponseEntity<Issue> addUserToIssue(@PathVariable Long issueId,
												@PathVariable Long userId) throws Exception{
		Issue issue=issueService.adduserToIssue(issueId, userId);
		return ResponseEntity.ok(issue);
	}
	
	@PutMapping("/{issueId}/status/{status}")
	public ResponseEntity<Issue> updateStatus(@PathVariable String status,@PathVariable Long issueId) throws Exception{
		Issue issue = issueService.updateIssueStatus(issueId, status);
		return ResponseEntity.ok(issue);
	}
}
