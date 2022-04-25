package entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the detailbill database table.
 * 
 */
@Embeddable
public class DetailbillPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int billId;

	@Column(name="product_id", insertable=false, updatable=false)
	private int productId;

	public DetailbillPK() {
	}
	public int getBillId() {
		return this.billId;
	}
	public void setBillId(int billId) {
		this.billId = billId;
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
		if (!(other instanceof DetailbillPK)) {
			return false;
		}
		DetailbillPK castOther = (DetailbillPK)other;
		return 
			(this.billId == castOther.billId)
			&& (this.productId == castOther.productId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.billId;
		hash = hash * prime + this.productId;
		
		return hash;
	}
	@Override
	public String toString() {
		return "DetailbillPK [billId=" + billId + ", productId=" + productId + "]";
	}
	
}