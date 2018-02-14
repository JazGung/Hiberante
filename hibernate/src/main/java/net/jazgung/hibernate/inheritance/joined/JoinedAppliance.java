package net.jazgung.hibernate.inheritance.joined;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Table;

@Table(name = "inheritance_joined_appliance")
@DiscriminatorValue(JoinedItem.TYPE_APPLIANCE)
public class JoinedAppliance extends JoinedItem {

	private String name;

	@Column(name = "applianc_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
