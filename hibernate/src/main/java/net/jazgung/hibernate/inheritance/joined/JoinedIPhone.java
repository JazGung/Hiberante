package net.jazgung.hibernate.inheritance.joined;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "inheritance_joined_iphone")
@Table(name = "inheritance_joined_iphone")
public class JoinedIPhone extends JoinedCellPhone {

	private Float price;

	@Column(name = "iphone_price")
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}
}
