package com.helloWorld.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helloWorld.model.Commnets;

public interface CommnetRepository extends JpaRepository<Commnets, Long> {
	List<Commnets> findCommnetByIssueId(Long issueId);
}
