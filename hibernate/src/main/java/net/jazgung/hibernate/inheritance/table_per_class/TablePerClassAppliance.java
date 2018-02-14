package net.jazgung.hibernate.inheritance.table_per_class;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Table;

@Table(name = "inheritance_table_per_class_appliance")
@DiscriminatorValue(TablePerClassItem.TYPE_APPLIANCE)
public class TablePerClassAppliance extends TablePerClassItem {

	private String name;

	@Column(name = "applianc_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
