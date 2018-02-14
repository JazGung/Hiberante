package net.jazgung.hibernate.inheritance.joined;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "inheritance_joined_book")
@Table(name = "inheritance_joined_book")
@DiscriminatorValue(JoinedItem.TYPE_BOOK)
public abstract class JoinedBook extends JoinedItem {

	private String name;

	@Column(name = "book_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
