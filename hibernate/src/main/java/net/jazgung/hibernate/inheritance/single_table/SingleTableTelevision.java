package net.jazgung.hibernate.inheritance.single_table;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class SingleTableTelevision extends SingleTableAppliance {

	private Float price;

	@Column(name = "television_price")
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

}
