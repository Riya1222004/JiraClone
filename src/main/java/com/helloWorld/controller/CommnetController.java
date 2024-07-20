package com.helloWorld.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helloWorld.model.Commnets;
import com.helloWorld.model.User;
import com.helloWorld.request.CreateCommnetRequest;
import com.helloWorld.response.MessageResponse;
import com.helloWorld.service.CommentService;
import com.helloWorld.service.UserService;

@RestController
@RequestMapping("/api/comments")
public class CommnetController {

	@Autowired
	CommentService commentService;
	
	@Autowired
	UserService userService;	
	
	@PostMapping
	public ResponseEntity<Commnets> createCommnet(@RequestBody CreateCommnetRequest req,
													@RequestHeader("Authorization") String jwt) throws Exception{
		User user=userService.findUserProfileByJWT(jwt);
		Commnets createdCommnets=commentService.createCommnets(req.getIssueId(), user.getId(), req.getCommnetContent());
		return new ResponseEntity<>(createdCommnets,HttpStatus.CREATED);
	}
	
	@DeleteMapping("{commentId}")
	public ResponseEntity<MessageResponse> deleteCommnet(@PathVariable Long commentId,
														@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user=userService.findUserProfileByJWT(jwt);
		commentService.deleteCommnets(commentId, user.getId());
		MessageResponse messageResponse=new MessageResponse();
		messageResponse.setMessage("Cooment Deleted Successfullly");
		
		return new ResponseEntity<>(messageResponse,HttpStatus.OK);
	}
	
	@GetMapping("{issueId}")
	public ResponseEntity<List<Commnets>> getCommnetsByIssueId(@PathVariable Long issueId){
		List<Commnets> commnets=commentService.findCommnetsByIssueId(issueId);
		return new ResponseEntity<>(commnets,HttpStatus.OK);
	}
}	
