package net.jazgung.hibernate.inheritance.table_per_class;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * 采用{@link javax.persistence.InheritanceType#TABLE_PER_CLASS
 * InheritanceType.TABLE_PER_CLASS} 进行映射<br/>
 * 特点是继承树上的具体类都需要对应的表，各表之间采用主键进行关联，字段与根基类中声明的主键字段相同，且无法修改字段名<br/>
 * 继承树如下：<br/>
 * {@link net.jazgung.hibernate.inheritance.table_per_class.TablePerClassItem
 * TablePerClassItem}<br/>
 * |__
 * {@link net.jazgung.hibernate.inheritance.table_per_class.TablePerClassAppliance
 * TablePerClassAppliance}：具体类，但未配置了{@link javax.persistence.Entity @Entity}
 * ，不需要对应的表，类中声明的字段都不会存入数据库<br/>
 * &emsp; |__
 * {@link net.jazgung.hibernate.inheritance.table_per_class.TablePerClassTelevision
 * TablePerClassTelevision}<br/>
 * |__
 * {@link net.jazgung.hibernate.inheritance.table_per_class.TablePerClassBook
 * TablePerClassBook}： 抽象类，即使配置了{@link javax.persistence.Entity @Entity}
 * ，也不需要对应的表，但配置了{@link javax.persistence.Entity @Entity}，类中声明的所有字段都会存入子类对应的表中<br/>
 * &emsp; |__
 * {@link net.jazgung.hibernate.inheritance.table_per_class.TablePerClassItBook
 * TablePerClassItBook}<br/>
 * |__
 * {@link net.jazgung.hibernate.inheritance.table_per_class.TablePerClassCellPhone
 * TablePerClassCellPhone}：具体类，但未配置了{@link javax.persistence.Entity @Entity}
 * ，不需要对应的表，但配置了{@link javax.persistence.MappedSuperclass @MappedSuperclass}
 * ，类中声明的所有字段都会存入子类对应的表中<br/>
 * &emsp; |__
 * {@link net.jazgung.hibernate.inheritance.table_per_class.TablePerClassIPhone
 * TablePerClassIPhone}<br/>
 * |__
 * {@link net.jazgung.hibernate.inheritance.table_per_class.TablePerClassTool
 * TablePerClassTool}：具体类，配置了{@link javax.persistence.Entity @Entity}
 * ，需要对应的表，类中声明的所有字段都会存入类对应的表<br/>
 * &emsp; |__
 * {@link net.jazgung.hibernate.inheritance.table_per_class.TablePerClassScrewDriver
 * TablePerClassScrewDriver}<br/>
 */
@Entity(name = "inheritance_table_per_class_item")
@Table(name = "inheritance_table_per_class_item")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class TablePerClassItem {

	public static final String TYPE_BOOK = "book";

	public static final String TYPE_CELL_PHONE = "cell_phone";

	public static final String TYPE_APPLIANCE = "appliance";

	public static final String TYPE_TOOL = "tool";

	private Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
