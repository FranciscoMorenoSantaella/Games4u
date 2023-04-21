package com.santaellamorenofrancisco.model;

import java.io.Serial;
import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Objeto en el que se define una orden
 * @author Francisco
 *
 */
@Entity
@Table(name = "orders_")
public class Order implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	//@JsonIgnoreProperties(value = { "shoppingcartlist", "product" },allowSetters = true)

	@ManyToOne(cascade = CascadeType.MERGE)
	@JsonIgnore
	@JoinColumn(name = "shoppingcart_id", nullable = false)
	private ShoppingCart shoppingcart;
	@ManyToOne(cascade = CascadeType.MERGE)	
	//@JsonIgnoreProperties(value = { "orderlist", "shoppingcart" },allowSetters = true)
	@JoinColumn(name = "game_id", nullable = false)
	private Game game;
	
	public Order(Long id, ShoppingCart shoppingcart, Game game) {
		super();
		this.id = id;
		this.shoppingcart = shoppingcart;
		this.game = game;
	}

	public Order() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ShoppingCart getShoppingcart() {
		return shoppingcart;
	}

	public void setShoppingcart(ShoppingCart shoppingcart) {
		this.shoppingcart = shoppingcart;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", shoppingcart=" + shoppingcart + ", game=" + game + "]";
	}
	
	
	
	
	
}
