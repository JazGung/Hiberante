package net.jazgung.hibernate.inheritance.single_table;

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
 * 采用{@link javax.persistence.InheritanceType#SINGLE_TABLE SINGLE_TABLE} 进行映射<br/>
 * 特点是继承树上的所有类的字段都存储在一张表中，需要一个区分的字段，可以在根基类使用
 * {@link javax.persistence.DiscriminatorColumn @DiscriminatorColumn} ，然后在子类中使用
 * {@link javax.persistence.DiscriminatorValue @DiscriminatorValue}
 * 配置不同的子类在这个字段存储的值<br/>
 * 根据不同的注解配置，映射情况如下：<br/>
 * {@link net.jazgung.hibernate.inheritance.single_table.SingleTableItem
 * SingleTableItem} <br/>
 * |__
 * {@link net.jazgung.hibernate.inheritance.single_table.SingleTableAppliance
 * SingleTableAppliance} ：具体类，但未配置了{@link javax.persistence.Entity @Entity}
 * ，不需要对应的表，类中声明的字段都不会存入数据库<br/>
 * &emsp; |__
 * {@link net.jazgung.hibernate.inheritance.single_table.SingleTableTelevision
 * SingleTableTelevision} <br/>
 * |__ {@link net.jazgung.hibernate.inheritance.single_table.SingleTableBook
 * SingleTableBook} ： 抽象类，即使配置了{@link javax.persistence.Entity @Entity}
 * ，也不需要对应的表，但配置了 {@link javax.persistence.Entity @Entity} ，类中声明的所有字段都会存入父类对应的表<br/>
 * &emsp; |__
 * {@link net.jazgung.hibernate.inheritance.single_table.SingleTableItBook
 * SingleTableItBook} <br/>
 * |__
 * {@link net.jazgung.hibernate.inheritance.single_table.SingleTableCellPhone
 * SingleTableCellPhone} ：具体类，但未配置了{@link javax.persistence.Entity @Entity}
 * ，不需要对应的表，但配置了 {@link javax.persistence.MappedSuperclass @MappedSuperclass}
 * ，类中声明的所有字段都会存入父类对应的表<br/>
 * &emsp; |__
 * {@link net.jazgung.hibernate.inheritance.single_table.SingleTableIPhone
 * SingleTableIPhone} <br/>
 * |__ {@link net.jazgung.hibernate.inheritance.single_table.SingleTableTool
 * SingleTableTool} ：具体类，配置了{@link javax.persistence.Entity @Entity}
 * ，需要对应的表，类中声明的所有字段都会存入父类对应的表<br/>
 * &emsp; |__
 * {@link net.jazgung.hibernate.inheritance.single_table.SingleTableScrewDriver
 * SingleTableScrewDriver} <br/>
 */
@Entity(name = "inheritance_single_table_item")
@Table(name = "inheritance_single_table_item")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class SingleTableItem {

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
