package net.jazgung.hibernate.inheritance.joined;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

@MappedSuperclass
@Table(name = "inheritance_joined_cell_phone")
@DiscriminatorValue("TYPE_CELL_PHONE")
public class JoinedCellPhone extends JoinedItem {

	private String name;

	@Column(name = "cell_phone_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
