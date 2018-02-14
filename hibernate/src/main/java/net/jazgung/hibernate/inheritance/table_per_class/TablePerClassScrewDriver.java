package net.jazgung.hibernate.inheritance.table_per_class;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "inheritance_table_per_class_driver")
@Table(name = "inheritance_table_per_class_driver")
public class TablePerClassScrewDriver extends TablePerClassTool {

	private Float price;

	@Column(name = "screw_driver_price")
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}
}
