package net.jazgung.hibernate.inheritance.table_per_class;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "inheritance_table_per_class_iphone")
@Table(name = "inheritance_table_per_class_iphone")
public class TablePerClassIPhone extends TablePerClassCellPhone {

	private Float price;

	@Column(name = "iphone_price")
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}
}
