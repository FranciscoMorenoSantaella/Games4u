package com.santaellamorenofrancisco.model;

import java.io.Serial;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "users_ratings")
public class UserRating {
	@Serial
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnoreProperties(value = {"user"}, allowSetters = true)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
   
    @ManyToOne
    @JoinColumn(name = "game_id")
    @JsonBackReference
    private Game game;
    
    @Column(columnDefinition = "INT DEFAULT 0")
    private int valoracion;

	public UserRating(Long id, User user, Game game, int valoracion) {
		super();
		this.id = id;
		this.user = user;
		this.game = game;
		this.valoracion = valoracion;
	}

	public UserRating() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public void setGame(Game game) {
		this.game = game;
	}

	public int getValoracion() {
		return valoracion;
	}

	public void setValoracion(int valoracion) {
		this.valoracion = valoracion;
	}

	public User getUser() {
		return user;
	}

	public Game getGame() {
		return game;
	}

	@Override
	public String toString() {
		return "UserRating [id=" + id + ", user=" + user + ", game=" + game + ", valoracion=" + valoracion + "]";
	}


   
}
