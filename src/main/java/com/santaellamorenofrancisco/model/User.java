package com.santaellamorenofrancisco.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Objeto en el que se define un usuario
 * @author Francisco
 *
 */
@Entity
@Table(name = "users")
public class User implements Serializable {
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "email")
	private String email;
	@Column(name = "admin")
	private Boolean admin;
	@Column(name = "uid")
	private String uid;
	@Column(name = "balance")
	private Double balance;
	@JsonIgnore
	@ManyToMany(mappedBy = "userswishlist")
	Set<Game> gameswishlist;
	@OneToMany(mappedBy="publisher")
	@JsonIgnore
	private Set<Game> gamespublished;
	@JsonIgnore
	@ManyToMany(mappedBy = "userslibrary")
	Set<Game> gameslibrary;
	// Cada usuario tiene una wishlist (una wishlist tiene uno o varios juegos una
	// wishlist pertenece solo a un usuario)
	// Un usuario tiene una o varias ordenes y una orden pertenece a un solo usuario
	// Un usuario sube ninguno uno o varios juegos y un juego es subido por un
	// usuario
	
	public User(Long id, String name, String email, Boolean admin, String uid, Set<Game> gameswishlist,
			Set<Game> gamespublished, Set<Game> gameslibrary) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.admin = admin;
		this.uid = uid;
		this.gameswishlist = gameswishlist;
		this.gamespublished = gamespublished;
		this.gameslibrary = gameslibrary;
	}
	
	public User() {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Set<Game> getGameswishlist() {
		return gameswishlist;
	}

	public void setGameswishlist(Set<Game> gameswishlist) {
		this.gameswishlist = gameswishlist;
	}

	public Set<Game> getGamespublished() {
		return gamespublished;
	}

	public void setGamespublished(Set<Game> gamespublished) {
		this.gamespublished = gamespublished;
	}

	public Set<Game> getGameslibrary() {
		return gameslibrary;
	}

	public void setGameslibrary(Set<Game> gameslibrary) {
		this.gameslibrary = gameslibrary;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", admin=" + admin + ", uid=" + uid
				+ ", balance=" + balance + "]";
	}

}