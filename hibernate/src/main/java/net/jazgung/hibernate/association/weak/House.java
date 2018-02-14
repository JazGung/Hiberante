package net.jazgung.hibernate.association.weak;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.jazgung.hibernate.AbstractEntity;

@Entity(name = "association_weak_House")
@Table(name = "association_weak_house")
public class House extends AbstractEntity {

	private static final long serialVersionUID = -4416573797168716528L;

	private Long id;

	private Integer area;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}
}
