package com.helloWorld.model;

import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	@OneToOne
	private Project project;
	private String name;
	@JsonIgnore
	@OneToMany(mappedBy = "chat",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Messages> message;
	@ManyToMany
	private List<User> users=new ArrayList<>();
}
