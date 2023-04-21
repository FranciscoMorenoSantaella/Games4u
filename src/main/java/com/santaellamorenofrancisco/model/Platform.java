package com.santaellamorenofrancisco.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Objeto en el que se define una plataforma
 * @author Francisco
 *
 */
@Entity
@Table(name = "platforms")
public class Platform implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "name")
	private String name;
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "games_platforms", joinColumns = @JoinColumn(name = "platform_id"), inverseJoinColumns = @JoinColumn(name = "game_id"))
	Set<Game> gamesplatforms;

	public Platform(Long id, String name, Set<Game> gamesplatforms) {
		super();
		this.id = id;
		this.name = name;
		this.gamesplatforms = gamesplatforms;
	}
 
	public Platform() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Game> getGamesplatforms() {
		return gamesplatforms;
	}

	public void setGamesplatforms(Set<Game> gamesplatforms) {
		this.gamesplatforms = gamesplatforms;
	}

	@Override
	public String toString() {
		return "Platform [id=" + id + ", name=" + name + "]";
	}

	

}
