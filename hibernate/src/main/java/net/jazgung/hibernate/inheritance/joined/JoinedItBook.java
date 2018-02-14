package net.jazgung.hibernate.inheritance.joined;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity(name = "inheritance_joined_it_book")
@Table(name = "inheritance_joined_it_book")
@PrimaryKeyJoinColumn(name = "it_book_id")
public class JoinedItBook extends JoinedBook {

	private Float price;

	@Column(name = "it_book_price")
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}
}
