package net.jazgung.hibernate.inheritance.table_per_class;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "inheritance_table_per_class_television")
@Table(name = "inheritance_table_per_class_television")
public class TablePerClassTelevision extends TablePerClassAppliance {

	private Float price;

	@Column(name = "television_price")
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

}
