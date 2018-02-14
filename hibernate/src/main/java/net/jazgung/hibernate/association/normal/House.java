package net.jazgung.hibernate.association.normal;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import net.jazgung.hibernate.AbstractEntity;

@Entity(name = "association_normal_house")
@Table(name = "association_normal_house")
public class House extends AbstractEntity {

	private static final long serialVersionUID = -4416573797168716528L;

	private Long id;

	private Person owner;

	private Set<Person> tenements = new HashSet<Person>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "owner_id")
	@Fetch(FetchMode.SELECT)
	@LazyToOne(LazyToOneOption.PROXY)
	public Person getOwner() {
		return owner;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}

	@ManyToMany
	@JoinTable(name = "association_normal_house_tenement", joinColumns = @JoinColumn(name = "house_id"), inverseJoinColumns = @JoinColumn(name = "tenement_id"))
	public Set<Person> getTenements() {
		return tenements;
	}

	public void setTenements(Set<Person> tenements) {
		this.tenements = tenements;
	}

	public void addTenement(Person tenement) {
		tenements.add(tenement);

	}

}
