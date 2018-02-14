package net.jazgung.hibernate.inheritance.joined;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "inheritance_joined_tool")
@Table(name = "inheritance_joined_tool")
@DiscriminatorValue(JoinedItem.TYPE_TOOL)
public class JoinedTool extends JoinedItem {

	private String name;

	@Column(name = "tool_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
