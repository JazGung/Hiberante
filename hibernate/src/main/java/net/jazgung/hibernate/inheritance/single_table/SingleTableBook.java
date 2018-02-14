package net.jazgung.hibernate.inheritance.single_table;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(SingleTableItem.TYPE_BOOK)
public abstract class SingleTableBook extends SingleTableItem {

	private String name;

	@Column(name = "book_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
