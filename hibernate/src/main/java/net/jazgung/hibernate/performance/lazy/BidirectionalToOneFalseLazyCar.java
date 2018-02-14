package net.jazgung.hibernate.performance.lazy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import net.jazgung.hibernate.AbstractEntity;

@Entity(name = "performance_lazy_bidirectional_to_one_false_lazy_car")
@Table(name = "performance_lazy_bidirectional_to_one_false_lazy_car")
public class BidirectionalToOneFalseLazyCar extends AbstractEntity {

	private static final long serialVersionUID = 7202650421795031883L;

	private Long id;

	private String name;

	private Person person;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void print() {
		System.out.println("print");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToOne
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "person_id")
	@Cascade(CascadeType.SAVE_UPDATE)
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
}
