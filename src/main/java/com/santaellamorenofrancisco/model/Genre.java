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

@Entity
@Table(name = "Genres")
public class Genre implements Serializable {
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
	@JoinTable(
	  name = "games_genres", 
	  joinColumns = @JoinColumn(name = "genres_id"), 
	  inverseJoinColumns = @JoinColumn(name = "games_id"))
	  Set<Game> gameslist;
	
	public Genre(Long id, String name, Set<Game> gameslist) {
		super();
		this.id = id;
		this.name = name;
		this.gameslist = gameslist;
	}

	public Genre() {
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

	public Set<Game> getGameslist() {
		return gameslist;
	}

	public void setGameslist(Set<Game> gameslist) {
		this.gameslist = gameslist;
	}

	@Override
	public String toString() {
		return "Genre [id=" + id + ", name=" + name + "]";
	}

	
}
