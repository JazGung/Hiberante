package net.jazgung.hibernate.inheritance.joined;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "inheritance_joined_screw_driver")
@Table(name = "inheritance_joined_screw_driver")
public class JoinedScrewDriver extends JoinedTool {

	private Float price;

	@Column(name = "screw_driver_price")
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}
}
