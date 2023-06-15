package com.santaellamorenofrancisco.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * Objeto en el que se define un juego
 * @author Francisco
 *
 */
@Entity 
@Table(name="games")
public class Game implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "description")
	private String description;
	@Column(name = "precio")
	private float precio;
	@Column(name = "early_access")
	private Boolean earlyaccess;
	@Column(name = "fecha_salida")
	private Date fechasalida;
	@Column(name = "verified")
	private Boolean verified;
	@Column(name = "valoracion")
	private Integer valoracion;
	@ManyToMany
	@JoinTable(
	  name = "library", 
	  joinColumns = @JoinColumn(name = "game_id"), 
	  inverseJoinColumns = @JoinColumn(name = "user_id"))
	  Set<User> userslibrary;
	@ManyToMany
	@JoinTable(
	  name = "wishlists", 
	  joinColumns = @JoinColumn(name = "game_id"), 
	  inverseJoinColumns = @JoinColumn(name = "user_id"))
	  Set<User> userswishlist;
	@ManyToMany
	@JoinTable(
			name= "games_genres",
			joinColumns = @JoinColumn(name="game_id"),
			inverseJoinColumns = @JoinColumn(name="genre_id"))
	Set<Genre> genreslist;
	
	
	@ManyToMany
	@JoinTable(
			name= "games_platforms",
			joinColumns = @JoinColumn(name="game_id"),
			inverseJoinColumns = @JoinColumn(name="platform_id"))
	Set<Platform> platforms;
	@ManyToOne
    @JoinColumn(name="user_id", nullable=false)
	private User publisher;	
	
	@OneToMany(mappedBy = "game")
	@JsonManagedReference
	private List<UserRating> ratings = new ArrayList<>();
	
	@OneToMany(mappedBy="game")
    private Set<File> files;
	


	public Game(Long id, String name, String description, float precio, Boolean earlyaccess, Date fechasalida,
			Boolean verified, Integer valoracion, Set<User> userslibrary, Set<User> userswishlist,
			Set<Genre> genreslist, Set<Platform> platforms, User publisher, List<UserRating> ratings, Set<File> files) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.precio = precio;
		this.earlyaccess = earlyaccess;
		this.fechasalida = fechasalida;
		this.verified = verified;
		this.valoracion = valoracion;
		this.userslibrary = userslibrary;
		this.userswishlist = userswishlist;
		this.genreslist = genreslist;
		this.platforms = platforms;
		this.publisher = publisher;
		this.ratings = ratings;
		this.files = files;
	}

	public Game() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public Boolean isEarlyaccess() {
		return earlyaccess;
	}

	public void setEarlyaccess(Boolean earlyaccess) {
		this.earlyaccess = earlyaccess;
	}

	public Date getFechasalida() {
		return fechasalida;
	}

	public void setFechasalida(Date fechasalida) {
		this.fechasalida = fechasalida;
	}

	public Set<User> getUserslibrary() {
		return userslibrary;
	}

	public void setUserslibrary(Set<User> userslibrary) {
		this.userslibrary = userslibrary;
	}

	public Set<User> getUserswishlist() {
		return userswishlist;
	}

	public void setUserswishlist(Set<User> userswishlist) {
		this.userswishlist = userswishlist;
	}

	public Set<Genre> getGenreslist() {
		return genreslist;
	}

	public void setGenreslist(Set<Genre> genreslist) {
		this.genreslist = genreslist;
	}

	public Set<Platform> getPlatforms() {
		return platforms;
	}

	public void setPlatforms(Set<Platform> platforms) {
		this.platforms = platforms;
	}

	public User getPublisher() {
		return publisher;
	}

	public void setPublisher(User publisher) {
		this.publisher = publisher;
	}

	public Set<File> getFiles() {
		return files;
	}

	public void setFiles(Set<File> files) {
		this.files = files;
	}
	

	public Boolean isVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}
	
	

	public Integer getValoracion() {
		return valoracion;
	}

	public void setValoracion(Integer valoracion) {
		this.valoracion = valoracion;
	}

	public List<UserRating> getRatings() {
		return ratings;
	}

	public void setRatings(List<UserRating> ratings) {
		this.ratings = ratings;
	}

	public Boolean getEarlyaccess() {
		return earlyaccess;
	}

	public Boolean getVerified() {
		return verified;
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", name=" + name + ", description=" + description + ", precio=" + precio
				+ ", earlyaccess=" + earlyaccess + ", fechasalida=" + fechasalida + ", verified=" + verified
				+ ", valoracion=" + valoracion + "]";
	}
	
}
