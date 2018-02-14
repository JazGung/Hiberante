package net.jazgung.hibernate.performance.fetch;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.jazgung.hibernate.AbstractEntity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity(name = "performance_fetch_person")
@Table(name = "performance_fetch_person")
public class Person extends AbstractEntity {

	private static final long serialVersionUID = 3532253102864679168L;

	private Long id;

	private String name;

	private ToOneDefaultFetchCar defaultFetchCar;

	private ToOneJoinFetchCar joinFetchCar;

	private ToOneSelectFetchCar selectFetchCar;

	private List<Car> defaultFetchCars = new ArrayList<Car>();

	private List<Car> joinFetchCars = new ArrayList<Car>();

	private List<Car> selectFetchCars = new ArrayList<Car>();

	private List<Car> subSelectFetchCars = new ArrayList<Car>();

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
	// 对于ToOne，默认为FetchMode.JOIN模式
	@JoinColumn(name = "default_fectch_car_id")
	@Cascade(CascadeType.SAVE_UPDATE)
	public ToOneDefaultFetchCar getDefaultFetchCar() {
		return defaultFetchCar;
	}

	public void setDefaultFetchCar(ToOneDefaultFetchCar defaultFetchCar) {
		this.defaultFetchCar = defaultFetchCar;
	}

	@OneToOne
	// FetchMode.JOIN模式，将会在同一条SQL中获取关联对象
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "join_fetch_car_id")
	@Cascade(CascadeType.SAVE_UPDATE)
	public ToOneJoinFetchCar getJoinFetchCar() {
		return joinFetchCar;
	}

	public void setJoinFetchCar(ToOneJoinFetchCar joinFetchCar) {
		this.joinFetchCar = joinFetchCar;
	}

	@OneToOne
	// FetchMode.SELECT模式，将会再发一条SQL中获取关联对象
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "select_fetch_car_id")
	@Cascade(CascadeType.SAVE_UPDATE)
	public ToOneSelectFetchCar getSelectFetchCar() {
		return selectFetchCar;
	}

	public void setSelectFetchCar(ToOneSelectFetchCar selectFetchCar) {
		this.selectFetchCar = selectFetchCar;
	}

	@ManyToMany
	// 对于ToMany，默认为FetchMode.SELECT模式
	@JoinTable(name = "performance_fetch_default_fetch", joinColumns = { @JoinColumn(name = "person_id") }, inverseJoinColumns = { @JoinColumn(name = "card_id") })
	@Cascade(CascadeType.SAVE_UPDATE)
	public List<Car> getDefaultFetchCars() {
		return defaultFetchCars;
	}

	public void setDefaultFetchCars(List<Car> defaultFetchCars) {
		this.defaultFetchCars = defaultFetchCars;
	}

	@ManyToMany
	// FetchMode.JOIN模式，将会在同一条SQL中获取关联对象集合
	@Fetch(FetchMode.JOIN)
	@JoinTable(name = "performance_fetch_join", joinColumns = { @JoinColumn(name = "person_id") }, inverseJoinColumns = { @JoinColumn(name = "card_id") })
	@Cascade(CascadeType.SAVE_UPDATE)
	public List<Car> getJoinFetchCars() {
		return joinFetchCars;
	}

	public void setJoinFetchCars(List<Car> joinFetchCars) {
		this.joinFetchCars = joinFetchCars;
	}

	@ManyToMany
	// FetchMode.SELECT模式，将会对每一个对象再发一条SQL中获取关联对象
	@Fetch(FetchMode.SELECT)
	@JoinTable(name = "performance_fetch_select", joinColumns = { @JoinColumn(name = "person_id") }, inverseJoinColumns = { @JoinColumn(name = "card_id") })
	@Cascade(CascadeType.SAVE_UPDATE)
	public List<Car> getSelectFetchCars() {
		return selectFetchCars;
	}

	public void setSelectFetchCars(List<Car> selectFetchCars) {
		this.selectFetchCars = selectFetchCars;
	}

	@ManyToMany
	// FetchMode.SUBSELECT模式，将会对当前所有对象仅发一条SQL获取关联对象
	@Fetch(FetchMode.SUBSELECT)
	@JoinTable(name = "performance_fetch_sub_select", joinColumns = { @JoinColumn(name = "person_id") }, inverseJoinColumns = { @JoinColumn(name = "card_id") })
	@Cascade(CascadeType.SAVE_UPDATE)
	public List<Car> getSubSelectFetchCars() {
		return subSelectFetchCars;
	}

	public void setSubSelectFetchCars(List<Car> subSelectFetchCars) {
		this.subSelectFetchCars = subSelectFetchCars;
	}

	public void addDefaultFetchCar(Car car) {
		defaultFetchCars.add(car);
	}

	public void addJoinFetchCar(Car car) {
		joinFetchCars.add(car);
	}

	public void addSelectFetchCar(Car car) {
		selectFetchCars.add(car);
	}

	public void addSubSelectFetchCar(Car car) {
		subSelectFetchCars.add(car);
	}

}
