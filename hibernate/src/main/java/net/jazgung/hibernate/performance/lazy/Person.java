package net.jazgung.hibernate.performance.lazy;

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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import net.jazgung.hibernate.AbstractEntity;

@Entity(name = "performance_lazy_person")
@Table(name = "performance_lazy_person")
public class Person extends AbstractEntity {

	private static final long serialVersionUID = 6316464026858315652L;

	private Long id;

	private String name;

	private MonodirectionalToOneDefaultLazyCar monodirectionalDefaultCar;

	private MonodirectionalToOneFalseLazyCar monodirectionalFalseCar;

	private MonodirectionalToOneProxyCar monodirectionalProxyCar;

	private MonodirectionalToOneNoProxyCar monodirectionalNoProxyCar;

	private BidirectionalToOneDefaultLazyCar bidirectionalDefaultCar;

	private BidirectionalToOneFalseLazyCar bidirectionalFalseCar;

	private BidirectionalToOneProxyCar bidirectionalProxyCar;

	private BidirectionalToOneNoProxyCar bidirectionalNoProxyCar;

	private List<Car> defaultCars = new ArrayList<Car>();

	private List<Car> trueCars = new ArrayList<Car>();

	private List<Car> falseCars = new ArrayList<Car>();

	private List<Car> extraCars = new ArrayList<Car>();

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
	@JoinColumn(name = "monodirectional_default_car_id")
	// 默认为FALSE
	@Fetch(FetchMode.SELECT)
	@Cascade(CascadeType.SAVE_UPDATE)
	public MonodirectionalToOneDefaultLazyCar getMonodirectionalDefaultCar() {
		return monodirectionalDefaultCar;
	}

	public void setMonodirectionalDefaultCar(MonodirectionalToOneDefaultLazyCar monodirectionalDefaultCar) {
		this.monodirectionalDefaultCar = monodirectionalDefaultCar;
	}

	@OneToOne
	// FALSE，及时加载，获取当前对象后立即获取关联对象
	@LazyToOne(LazyToOneOption.FALSE)
	@JoinColumn(name = "monodirectional_false_car_id")
	@Fetch(FetchMode.SELECT)
	@Cascade(CascadeType.SAVE_UPDATE)
	public MonodirectionalToOneFalseLazyCar getMonodirectionalFalseCar() {
		return monodirectionalFalseCar;
	}

	public void setMonodirectionalFalseCar(MonodirectionalToOneFalseLazyCar monodirectionalFalseCar) {
		this.monodirectionalFalseCar = monodirectionalFalseCar;
	}

	@OneToOne
	// PROXY，延迟加载，当访问关联对象任一方法或字段时再发SQL获取
	@LazyToOne(LazyToOneOption.PROXY)
	@JoinColumn(name = "monodirectional_proxy_car_id")
	@Fetch(FetchMode.SELECT)
	@Cascade(CascadeType.SAVE_UPDATE)
	public MonodirectionalToOneProxyCar getMonodirectionalProxyCar() {
		return monodirectionalProxyCar;
	}

	public void setMonodirectionalProxyCar(MonodirectionalToOneProxyCar monodirectionalProxyCar) {
		this.monodirectionalProxyCar = monodirectionalProxyCar;
	}

	@OneToOne
	// NO_PROXY，延迟加载，当访问关联对象任一持久化字段时再发SQL获取，需要CGLib进行字节码增强，否则与PROXY无区别
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "monodirectional_no_proxy_car_id")
	@Fetch(FetchMode.SELECT)
	@Cascade(CascadeType.SAVE_UPDATE)
	public MonodirectionalToOneNoProxyCar getMonodirectionalNoProxyCar() {
		return monodirectionalNoProxyCar;
	}

	public void setMonodirectionalNoProxyCar(MonodirectionalToOneNoProxyCar monodirectionalNoProxyCar) {
		this.monodirectionalNoProxyCar = monodirectionalNoProxyCar;
	}

	@OneToOne(mappedBy = "person")
	// 默认为FALSE
	@Fetch(FetchMode.SELECT)
	@Cascade(CascadeType.SAVE_UPDATE)
	public BidirectionalToOneDefaultLazyCar getBidirectionalDefaultCar() {
		return bidirectionalDefaultCar;
	}

	public void setBidirectionalDefaultCar(BidirectionalToOneDefaultLazyCar bidirectionalDefaultCar) {
		this.bidirectionalDefaultCar = bidirectionalDefaultCar;
	}

	@OneToOne(mappedBy = "person")
	// FALSE，及时加载，获取当前对象后立即获取关联对象
	@LazyToOne(LazyToOneOption.FALSE)
	@Fetch(FetchMode.SELECT)
	@Cascade(CascadeType.SAVE_UPDATE)
	public BidirectionalToOneFalseLazyCar getBidirectionalFalseCar() {
		return bidirectionalFalseCar;
	}

	public void setBidirectionalFalseCar(BidirectionalToOneFalseLazyCar bidirectionalFalseCar) {
		this.bidirectionalFalseCar = bidirectionalFalseCar;
	}

	@OneToOne(mappedBy = "person")
	// PROXY，外键在远端，延迟不起效
	@LazyToOne(LazyToOneOption.PROXY)
	@Fetch(FetchMode.SELECT)
	@Cascade(CascadeType.SAVE_UPDATE)
	public BidirectionalToOneProxyCar getBidirectionalProxyCar() {
		return bidirectionalProxyCar;
	}

	public void setBidirectionalProxyCar(BidirectionalToOneProxyCar bidirectionalProxyCar) {
		this.bidirectionalProxyCar = bidirectionalProxyCar;
	}

	@OneToOne(mappedBy = "person")
	// NO_PROXY，外键在远端，延迟不起效
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@Fetch(FetchMode.SELECT)
	@Cascade(CascadeType.SAVE_UPDATE)
	public BidirectionalToOneNoProxyCar getBidirectionalNoProxyCar() {
		return bidirectionalNoProxyCar;
	}

	public void setBidirectionalNoProxyCar(BidirectionalToOneNoProxyCar bidirectionalNoProxyCar) {
		this.bidirectionalNoProxyCar = bidirectionalNoProxyCar;
	}

	@ManyToMany
	// 默认为TRUE
	@JoinTable(name = "performance_lazy_default", joinColumns = { @JoinColumn(name = "person_id") }, inverseJoinColumns = {
			@JoinColumn(name = "card_id") })
	@Cascade(CascadeType.SAVE_UPDATE)
	public List<Car> getDefaultCars() {
		return defaultCars;
	}

	public void setDefaultCars(List<Car> defaultCars) {
		this.defaultCars = defaultCars;
	}

	@ManyToMany
	// TRUE，延迟加载，当返回集合中任一对象时再发SQL获取集合中的对象
	@LazyCollection(LazyCollectionOption.TRUE)
	@JoinTable(name = "performance_lazy_true", joinColumns = { @JoinColumn(name = "person_id") }, inverseJoinColumns = {
			@JoinColumn(name = "card_id") })
	@Cascade(CascadeType.SAVE_UPDATE)
	public List<Car> getTrueCars() {
		return trueCars;
	}

	public void setTrueCars(List<Car> trueCars) {
		this.trueCars = trueCars;
	}

	@ManyToMany
	// FALSE，及时加载，获取当前对象后立即获取关联对象集合
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "performance_lazy_false", joinColumns = { @JoinColumn(name = "person_id") }, inverseJoinColumns = {
			@JoinColumn(name = "card_id") })
	@Cascade(CascadeType.SAVE_UPDATE)
	public List<Car> getFalseCars() {
		return falseCars;
	}

	public void setFalseCars(List<Car> falseCars) {
		this.falseCars = falseCars;
	}

	@ManyToMany
	// EXTRA，延迟加载，据说比TRUE更智能，但现在没有发现差异
	@LazyCollection(LazyCollectionOption.EXTRA)
	@JoinTable(name = "performance_lazy_extra", joinColumns = { @JoinColumn(name = "person_id") }, inverseJoinColumns = {
			@JoinColumn(name = "card_id") })
	@Cascade(CascadeType.SAVE_UPDATE)
	public List<Car> getExtraCars() {
		return extraCars;
	}

	public void addDefaultCar(Car car) {
		defaultCars.add(car);
	}

	public void setExtraCars(List<Car> extraCars) {
		this.extraCars = extraCars;
	}

	public void addTrueCar(Car car) {
		trueCars.add(car);
	}

	public void addFalseCar(Car car) {
		falseCars.add(car);
	}

	public void addExtraCar(Car car) {
		extraCars.add(car);
	}
}
