package net.jazgung.hibernate.inheritance.table_per_class;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "inheritance_table_per_class_book")
@Table(name = "inheritance_table_per_class_book")
@DiscriminatorValue(TablePerClassItem.TYPE_BOOK)
public abstract class TablePerClassBook extends TablePerClassItem {

	private String name;

	@Column(name = "book_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
