package net.jazgung.hibernate.performance.fetch;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.jazgung.hibernate.AbstractEntity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity(name = "performance_fetch_to_one_select_fetch_car")
@Table(name = "performance_fetch_to_one_select_fetch_car")
public class ToOneSelectFetchCar extends AbstractEntity {

	private static final long serialVersionUID = 7202650421795031883L;

	private Long id;

	private String name;

	private Brand brand;

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

	@OneToOne
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "join_brand_id")
	@Cascade(CascadeType.SAVE_UPDATE)
	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}
}
