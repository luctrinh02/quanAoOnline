package entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the carts database table.
 * 
 */
@Embeddable
public class CartPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="user_id", insertable=false, updatable=false)
	private int userId;

	@Column(name="product_id", insertable=false, updatable=false)
	private int productId;	

	public CartPK() {
	}
	public int getUserId() {
		return this.userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getProductId() {
		return this.productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CartPK)) {
			return false;
		}
		CartPK castOther = (CartPK)other;
		return 
			(this.userId == castOther.userId)
			&& (this.productId == castOther.productId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId;
		hash = hash * prime + this.productId;
		
		return hash;
	}
	@Override
	public String toString() {
		return "CartPK [userId=" + userId + ", productId=" + productId + "]";
	}
	
}