package entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the carts database table.
 * 
 */
@Entity
@Table(name = "carts")
@NamedQuery(name = "Cart.findAll", query = "SELECT c FROM Cart c")
public class Cart implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CartPK id;

	private int amount;

	private double price;
	// bi-directional many-to-one association to Product
	@ManyToOne
	private Product product;

	// bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	public Cart() {
	}

	public CartPK getId() {
		return this.id;
	}

	public void setId(CartPK id) {
		this.id = id;
	}

	public int getAmount() {
		return this.amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Cart [id=" + id + ", amount=" + amount + ", price=" + price + ", product=" + product + "]";
	}
	
	
}