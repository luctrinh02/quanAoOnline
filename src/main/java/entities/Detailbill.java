package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the detailbill database table.
 * 
 */
@Entity
@NamedQuery(name="Detailbill.findAll", query="SELECT d FROM Detailbill d")
public class Detailbill implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DetailbillPK id;

	private int amount;

	private int price;

	//bi-directional many-to-one association to Bill
	@ManyToOne
	@JoinColumn(name="billId")
	private Bill bill;

	//bi-directional many-to-one association to Product
	@ManyToOne
	private Product product;

	public Detailbill() {
	}

	public DetailbillPK getId() {
		return this.id;
	}

	public void setId(DetailbillPK id) {
		this.id = id;
	}

	public int getAmount() {
		return this.amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Bill getBill() {
		return this.bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "Detailbill [id=" + id + ", amount=" + amount + ", price=" + price + "]";
	}
	
}