package com.helloWorld.controller;

import java.util.List;

import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.helloWorld.model.Chat;
import com.helloWorld.model.Invitation;
import com.helloWorld.model.Project;
import com.helloWorld.model.User;
import com.helloWorld.repository.InviteRequest;
import com.helloWorld.response.MessageResponse;
import com.helloWorld.service.InvitationService;
import com.helloWorld.service.ProjectService;
import com.helloWorld.service.UserService;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	InvitationService invitationService;
	
	@GetMapping
	public ResponseEntity<List<Project>> getProjects(
			@RequestParam(required = false)String category,
			@RequestParam(required = false)String tag,
			@RequestHeader("Authorization") String jwt
	) throws Exception{
		User user=userService.findUserProfileByJWT(jwt);
		List<Project> projects=projectService.getProjectByTeam(user, category, tag);
		return new ResponseEntity<>(projects,HttpStatus.OK);
	}
	

	
	@GetMapping("/{projectId}")
	public ResponseEntity<Project> getProjectById(
			@PathVariable Long projectId,
			@RequestHeader("Authorization") String jwt
	) throws Exception{
		User user=userService.findUserProfileByJWT(jwt);
		Project projects=projectService.getProjectById(projectId);
		return new ResponseEntity<>(projects,HttpStatus.OK);
	}

	
	@PostMapping
	public ResponseEntity<Project> createProject(
			@RequestBody Project project,
			@RequestHeader("Authorization") String jwt
	) throws Exception{
		User user=userService.findUserProfileByJWT(jwt);
		Project projects=projectService.createProject(project, user);
		return new ResponseEntity<>(projects,HttpStatus.OK);
	}
	
	@PatchMapping("/{projectId}")
	public ResponseEntity<Project> updateProject(
			@PathVariable Long projectId,
			@RequestBody Project project,
			@RequestHeader("Authorization") String jwt
	) throws Exception{
		User user=userService.findUserProfileByJWT(jwt);
		Project projects=projectService.updateProject(project, projectId);
		return new ResponseEntity<>(projects,HttpStatus.OK);
	}
	
	@DeleteMapping("/{projectId}")
	public ResponseEntity<MessageResponse> deleteProject(
			@PathVariable Long projectId,
			@RequestHeader("Authorization") String jwt
	) throws Exception{
		User user=userService.findUserProfileByJWT(jwt);
		projectService.deleteProject(projectId, user.getId());
		MessageResponse response=new MessageResponse("Project Deleted");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<Project>> searchProject(
			@RequestParam(required = false)String keyword,
			@RequestHeader("Authorization") String jwt
	) throws Exception{
		User user=userService.findUserProfileByJWT(jwt);
		List<Project> projects=projectService.searchProject(keyword, user);
		return new ResponseEntity<>(projects,HttpStatus.OK);
	}
	
	@GetMapping("/{projectId}/chat")
	public ResponseEntity<Chat> getChatByProjectId(
			@PathVariable Long projectId,
			@RequestHeader("Authorization") String jwt
	) throws Exception{
		User user=userService.findUserProfileByJWT(jwt);
		Chat chat=projectService.getChatByProjectId(projectId);
		return new ResponseEntity<>(chat,HttpStatus.OK);
	}
	
	@PostMapping("/invite")
	public ResponseEntity<MessageResponse> inviteProject(
			@RequestBody InviteRequest req,
			@RequestBody Project project,
			@RequestHeader("Authorization") String jwt
	) throws Exception{
		User user=userService.findUserProfileByJWT(jwt);
		invitationService.sendInvitation(req.getEmail(), req.getProjectId());
		MessageResponse response=new MessageResponse("User Invitation Sent Successfully!!");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping("/accept_invitation")
	public ResponseEntity<Invitation> acceptInvitation(
			@RequestParam String token,
			@RequestBody Project project,
			@RequestHeader("Authorization") String jwt
	) throws Exception{
		User user=userService.findUserProfileByJWT(jwt);
		Invitation invitation=  invitationService.acceptInvitation(token,user.getId());
		projectService.addUserToProject(invitation.getId(), user.getId());
		return new ResponseEntity<>(invitation,HttpStatus.ACCEPTED);
	}
	
	
}
