package com.santaellamorenofrancisco.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Objeto en el que se define un carro de la compra
 * @author Francisco
 *
 */
@Entity
@Table(name="shoppingcart")
public class ShoppingCart implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "pay_date")
	private LocalDateTime paydate;
	@Column(name = "total_price")
	private Double totalprice;
	@Column(name = "ispayed")
	private Boolean ispayed;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "user_id")
	//@JsonIgnore
	private User user;
	@OneToMany(mappedBy = "game", cascade = CascadeType.MERGE)
	//@JsonIgnore
	private Set<Order> orderlist;
	
	public ShoppingCart(Long id, LocalDateTime paydate, Double totalprice, Boolean ispayed, User user,
			Set<Order> orderlist) {
		super();
		this.id = id;
		this.paydate = paydate;
		this.totalprice = totalprice;
		this.ispayed = ispayed;
		this.user = user;
		this.orderlist = orderlist;
	}

	public ShoppingCart() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getPaydate() {
		return paydate;
	}

	public void setPaydate(LocalDateTime paydate) {
		this.paydate = paydate;
	}

	public Double getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(Double totalprice) {
		this.totalprice = totalprice;
	}

	public Boolean getIspayed() {
		return ispayed;
	}

	public void setIspayed(Boolean ispayed) {
		this.ispayed = ispayed;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Order> getOrderlist() {
		return orderlist;
	}

	public void setOrderlist(Set<Order> orderlist) {
		this.orderlist = orderlist;
	}

	@Override
	public String toString() {
		return "ShoppingCart [id=" + id + ", paydate=" + paydate + ", totalprice=" + totalprice + ", ispayed=" + ispayed
				+ ", user=" + user + ", orderlist=" + orderlist + "]";
	}

}
