package com.helloWorld.service;

import java.util.List;

import com.helloWorld.model.Chat;
import com.helloWorld.model.Project;
import com.helloWorld.model.User;

public interface ProjectService {
	Project createProject(Project project,User user) throws Exception;
	
	List<Project> getProjectByTeam(User user,String category,String tag) throws Exception;
	
	Project getProjectById(Long projectId) throws Exception;
	
	void deleteProject(Long projectId,Long userId) throws Exception;
	
	Project updateProject(Project updatedProject,Long Id) throws Exception;
	
	void addUserToProject(Long projectId,Long userId) throws Exception;
	
	void removeUserFromProject(Long projectId,Long userId) throws Exception;
	
	Chat getChatByProjectId(Long projectId) throws Exception;
	
	List<Project> searchProject(String keyWord,User user) throws Exception;
}
