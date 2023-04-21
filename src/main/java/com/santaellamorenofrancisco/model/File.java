package com.santaellamorenofrancisco.model;

import java.io.Serial;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Objeto en el que se define un archivo
 * @author Francisco
 *
 */
@Entity
@Table(name = "files")
public class File implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "uniquename", unique = true)
	private String uniquename;
	@Column(name = "originalname")
	private String originalname;
	@Column(name = "url")
	private String url;
	@Column(name = "executable")
	private Boolean executable;
	@ManyToOne
	@JoinColumn(name = "game_id", nullable = false)
	private Game game;
		
	File(Long id, String uniquename, String originalname, String url, Boolean executable, Game game) {
		super();
		this.id = id;
		this.uniquename = uniquename;
		this.originalname = originalname;
		this.url = url;
		this.executable = executable;
		this.game = game;
	}

	public File() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUniquename() {
		return uniquename;
	}

	public void setUniquename(String uniquename) {
		this.uniquename = uniquename;
	}

	public String getOriginalname() {
		return originalname;
	}

	public void setOriginalname(String originalname) {
		this.originalname = originalname;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Boolean getExecutable() {
		return executable;
	}

	public void setExecutable(Boolean executable) {
		this.executable = executable;
	}

	@Override
	public String toString() {
		return "File [id=" + id + ", uniquename=" + uniquename + ", originalname=" + originalname + ", url=" + url
				+ ", game=" + game + "]";
	}
}
