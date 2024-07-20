package com.helloWorld.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helloWorld.model.Commnets;
import com.helloWorld.model.Issue;
import com.helloWorld.model.User;
import com.helloWorld.repository.CommnetRepository;
import com.helloWorld.repository.IssueRepository;
import com.helloWorld.repository.UserRepository;

import lombok.extern.java.Log;

@Service
public class CommentServiceImple implements CommentService {
	
	@Autowired 
	private CommnetRepository commnetRepository;
	
	@Autowired
	private IssueRepository issueRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Commnets createCommnets(Long issueId, Long userId, String content) throws Exception {
		
		
		Optional<Issue> issueOptional=issueRepository.findById(issueId);
		Optional<User> userOptional=userRepository.findById(userId);
		
		if(issueOptional.isEmpty()) {
			throw new Exception("No issue id found at "+issueId);
		}
		
		if(userOptional.isEmpty()) {
			throw new Exception("No user id found at "+userId);
		}
		
		Issue issue=issueOptional.get();
		User user=userOptional.get();
		
		
		
		Commnets commnets=new Commnets();
		
		commnets.setIssue(issue);
		commnets.setUser(user);
		commnets.setCreateDateTime(LocalDate.now());
		commnets.setContent(content);
		
		Commnets savedCommnets=commnetRepository.save(commnets);
		
		issue.getComments().add(savedCommnets);
		
		return savedCommnets;
	}

	@Override
	public void deleteCommnets(Long commentId, Long userId) throws Exception {
		
		Optional<Commnets> commnetOptional=commnetRepository.findById(commentId);
		Optional<User> userOptional=userRepository.findById(userId);
		
		if(commnetOptional.isEmpty()) {
			throw new Exception("No issue id found at "+commentId);
		}
		
		if(userOptional.isEmpty()) {
			throw new Exception("No user id found at "+userId);
		}
		
		Commnets commnets=commnetOptional.get();
		User user=userOptional.get();
		
		if(commnets.getUser().equals(user)) {
			commnetRepository.delete(commnets);
		}
		else {
			throw new Exception("Cooment id not found "+commentId );
		}
	}

	@Override
	public List<Commnets> findCommnetsByIssueId(Long issueId) {
		
		return commnetRepository.findCommnetByIssueId(issueId);
	}

}
