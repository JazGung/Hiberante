package net.jazgung.hibernate.inheritance.single_table;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;

@DiscriminatorValue(SingleTableItem.TYPE_APPLIANCE)
public class SingleTableAppliance extends SingleTableItem {

	private String name;

	@Column(name = "applianc_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
