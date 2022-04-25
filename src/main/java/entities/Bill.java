package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the bills database table.
 * 
 */
@Entity
@Table(name="bills")
@NamedQuery(name="Bill.findAll", query="SELECT b FROM Bill b")
public class Bill implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	private Date date;

	private byte status;

	@Column(name="total_money")
	private int totalMoney;

	@Column(name="user_id")
	private int userId;

	//bi-directional one-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id",insertable=false, updatable=false)
	private User user;

	//bi-directional many-to-one association to Detailbill
	@OneToMany(mappedBy="bill")
	private List<Detailbill> detailbills;
	
	public Bill() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public int getTotalMoney() {
		return this.totalMoney;
	}

	public void setTotalMoney(int totalMoney) {
		this.totalMoney = totalMoney;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Detailbill> getDetailbills() {
		return this.detailbills;
	}

	public void setDetailbills(List<Detailbill> detailbills) {
		this.detailbills = detailbills;
	}

	public Detailbill addDetailbill(Detailbill detailbill) {
		getDetailbills().add(detailbill);
		detailbill.setBill(this);

		return detailbill;
	}

	public Detailbill removeDetailbill(Detailbill detailbill) {
		getDetailbills().remove(detailbill);
		detailbill.setBill(null);

		return detailbill;
	}

	@Override
	public String toString() {
		return "Bill [id=" + id + ", date=" + date + ", status=" + status + ", totalMoney=" + totalMoney + ", userId="
				+ userId + "]";
	}
	
}