package net.jazgung.hibernate.inheritance.single_table;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@DiscriminatorValue("TYPE_CELL_PHONE")
public class SingleTableCellPhone extends SingleTableItem {

	private String name;

	@Column(name = "cell_phone_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
