package net.jazgung.hibernate.performance.fetch;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.jazgung.hibernate.AbstractEntity;

@Entity(name = "performance_brand_car")
@Table(name = "performance_brand_car")
public class Brand extends AbstractEntity {

	private static final long serialVersionUID = -610040178803197614L;

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
}
