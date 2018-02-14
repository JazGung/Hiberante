package net.jazgung.hibernate.inheritance.single_table;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class SingleTableItBook extends SingleTableBook {

	private Float price;

	@Column(name = "it_book_price")
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}
}
