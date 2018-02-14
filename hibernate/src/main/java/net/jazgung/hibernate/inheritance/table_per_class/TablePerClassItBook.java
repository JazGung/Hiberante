package net.jazgung.hibernate.inheritance.table_per_class;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "inheritance_table_per_class_it_book")
@Table(name = "inheritance_table_per_class_it_book")
public class TablePerClassItBook extends TablePerClassBook {

	private Float price;

	@Column(name = "it_book_price")
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}
}
