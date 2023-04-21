package com.santaellamorenofrancisco.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

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
	private boolean earlyaccess;
	@Column(name = "fecha_salida")
	private Date fechasalida;
	@Column(name = "verified")
	private boolean verified;
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
	@ManyToMany(mappedBy = "gameslist")
	Set<Genre> genreslist;
	@ManyToMany(mappedBy = "gamesplatforms")
	Set<Platform> platforms;
	@ManyToOne
    @JoinColumn(name="user_id", nullable=false)
	private User publisher;
	
	@OneToMany(mappedBy="game")
    private Set<File> files;

	public Game(Long id, String name, String description, float precio, boolean earlyaccess, Date fechasalida,
			Set<User> userslibrary, Set<User> userswishlist, Set<Genre> genreslist, Set<Platform> platforms,
			User publisher, Set<File> files) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.precio = precio;
		this.earlyaccess = earlyaccess;
		this.fechasalida = fechasalida;
		this.userslibrary = userslibrary;
		this.userswishlist = userswishlist;
		this.genreslist = genreslist;
		this.platforms = platforms;
		this.publisher = publisher;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
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

	public boolean isEarlyaccess() {
		return earlyaccess;
	}

	public void setEarlyaccess(boolean earlyaccess) {
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
	

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", name=" + name + ", description=" + description + ", precio=" + precio
				+ ", earlyaccess=" + earlyaccess + ", fechasalida=" + fechasalida + ", verified=" + verified
				+ ", userslibrary=" + userslibrary + ", userswishlist=" + userswishlist + ", genreslist=" + genreslist
				+ ", platforms=" + platforms + ", publisher=" + publisher + ", files=" + files + "]";
	}
}
