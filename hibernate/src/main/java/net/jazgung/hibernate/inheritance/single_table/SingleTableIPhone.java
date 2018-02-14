package net.jazgung.hibernate.inheritance.single_table;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class SingleTableIPhone extends SingleTableCellPhone {

	private Float price;

	@Column(name = "iphone_price")
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}
}
