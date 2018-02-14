package net.jazgung.hibernate.inheritance.joined;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "inheritance_joined_television")
@Table(name = "inheritance_joined_television")
public class JoinedTelevision extends JoinedAppliance {

	private Float price;

	@Column(name = "television_price")
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

}
