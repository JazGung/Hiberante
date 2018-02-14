package net.jazgung.hibernate.performance.lazy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.jazgung.hibernate.AbstractEntity;

@Entity(name = "performance_lazy_car")
@Table(name = "performance_lazy_car")
public class Car extends AbstractEntity {

	private static final long serialVersionUID = 1251396409631319118L;

	private Long id;

	private String name;

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

	public void print() {
		System.out.println("print");
	}
}
