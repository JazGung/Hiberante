package net.jazgung.hibernate.inheritance.joined;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * 采用{@link javax.persistence.InheritanceType#JOINED InheritanceType.JOINED}
 * 进行映射<br/>
 * 特点是继承树上所有配置{@link javax.persistence.Entity @Entity}
 * 的类都需要对应表，各表之间采用主键进行关联，字段与根基类中声明的主键字段相同，但可以使用
 * {@link javax.persistence.PrimaryKeyJoinColumn PrimaryKeyJoinColumn} 进行修改<br/>
 * 继承树如下：<br/>
 * {@link net.jazgung.hibernate.inheritance.joined.JoinedItem JoinedItem} <br/>
 * |__ {@link net.jazgung.hibernate.inheritance.joined.JoinedAppliance
 * JoinedAppliance}：具体类，未配置了{@link javax.persistence.Entity @Entity}
 * ，不需要对应的表，类中声明的字段都不会存入数据库<br/>
 * &emsp; |__ {@link net.jazgung.hibernate.inheritance.joined.JoinedTelevision
 * JoinedTelevision}<br/>
 * |__ {@link net.jazgung.hibernate.inheritance.joined.JoinedBook JoinedBook}
 * ：抽象类，配置了{@link javax.persistence.Entity @Entity}，需要对应的表，类中声明的字段都会存入数据库<br/>
 * &emsp; |__ {@link net.jazgung.hibernate.inheritance.joined.JoinedItBook
 * JoinedItBook}：配置了{@link javax.persistence.PrimaryKeyJoinColumn
 * PrimaryKeyJoinColumn}，可以修改子表主键的列名，新的列不需要在域对象中定义对应的字段<br/>
 * |__ {@link net.jazgung.hibernate.inheritance.joined.JoinedCellPhone
 * JoinedCellPhone} ：具体类，未配置了{@link javax.persistence.Entity @Entity}
 * ，不需要对应的表，但配置了{@link javax.persistence.MappedSuperclass @MappedSuperclass}
 * ，类中声明的所有字段都会存入子类对应的表<br/>
 * &emsp; |__ {@link net.jazgung.hibernate.inheritance.joined.JoinedIPhone
 * JoinedIPhone}<br/>
 * |__ {@link net.jazgung.hibernate.inheritance.joined.JoinedTool JoinedTool}
 * ：具体类，配置了{@link javax.persistence.Entity @Entity}，需要对应的表，类中声明的字段会存入数据库<br/>
 * &emsp; |__ {@link net.jazgung.hibernate.inheritance.joined.JoinedScrewDriver
 * JoinedScrewDriver}<br/>
 */
@Entity(name = "inheritance_joined_item")
@Table(name = "inheritance_joined_item")
@Inheritance(strategy = InheritanceType.JOINED)
// 对InheritanceType.JOINED貌似无效
// @DiscriminatorColumn(name = "type", discriminatorType =
// DiscriminatorType.STRING)
public class JoinedItem {

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
