package net.jazgung.hibernate.qbc;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.jazgung.hibernate.AbstractEntity;

@Entity(name = "qbc_Person")
@Table(name = "qbc_person")
public class Person extends AbstractEntity {

	private static final long serialVersionUID = 4258781403658496525L;

	private Long id;

	private String name;

	private Integer age;

	private House house;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@OneToOne
	@JoinColumn(name = "house_id")
	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

}
