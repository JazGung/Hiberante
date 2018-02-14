package net.jazgung.hibernate.user_type.enum_type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;
import org.junit.Test;

import net.jazgung.hibernate.GenericTest;

public class UserTypeTest extends GenericTest {

	@Test
	public void test() {
		Person p1 = new Person();
		p1.setSex(Person.Sex.MALE);
		p1.setNationality(Person.Nationality.USA);
		session.saveOrUpdate(p1);

		Person p2 = new Person();
		p2.setSex(Person.Sex.FEMALE);
		p2.setNationality(Person.Nationality.RPC);
		session.saveOrUpdate(p2);

		Person p3 = new Person();
		p3.setSex(Person.Sex.UNKNOWN);
		p3.setNationality(Person.Nationality.UNKNOWN);
		session.saveOrUpdate(p3);
	}

}

@Entity
@Table(name = "user_type_person")
class Person {

	private Long id;

	private Sex sex;

	private Nationality nationality;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "sex")
	@Type(type = EnumIntegerUserType.NAME, parameters = { @Parameter(name = EnumType.ENUM, value = Sex.NAME) })
	public Sex getSex() {
		return sex;
	}

	@Column(name = "nationality", length = 32)
	@Type(type = EnumStringUserType.NAME, parameters = { @Parameter(name = EnumType.ENUM, value = Nationality.NAME) })
	public Nationality getNationality() {
		return nationality;
	}

	public void setNationality(Nationality nationality) {
		this.nationality = nationality;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	enum Sex implements EnumPersistentable<Integer> {

		MALE(1), FEMALE(2), UNKNOWN(-1);

		Sex(int value) {
			this.value = value;
		}

		public static final String NAME = "net.jazgung.hibernate.user_type.enum_type.Person$Sex";

		private int value;

		public Integer getValue() {
			return value;
		}
	}

	enum Nationality implements EnumPersistentable<String> {

		USA(), RPC(), UNKNOWN();

		public static final String NAME = "net.jazgung.hibernate.user_type.enum_type.Person$Nationality";

		public String getValue() {
			return name();
		}
	}
}