package net.jazgung.hibernate.association.normal;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.jazgung.hibernate.AbstractEntity;

@Entity(name = "association_normal_Person")
@Table(name = "association_normal_person")
public class Person extends AbstractEntity {

	private static final long serialVersionUID = 4258781403658496525L;

	private Long id;

	private String name;

	private Integer age;

	private Set<House> heldHoses = new HashSet<House>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@OneToMany(mappedBy = "tenements")
	public Set<House> getHeldHoses() {
		return heldHoses;
	}

	public void setHeldHoses(Set<House> heldHoses) {
		this.heldHoses = heldHoses;
	}

	public void addHouse(House house) {
		heldHoses.add(house);
	}

	@Override
	public String toString() {
		return super.toString() + "#" + name;
	}

}
