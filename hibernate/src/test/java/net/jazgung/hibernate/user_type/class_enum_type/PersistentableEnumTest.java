package net.jazgung.hibernate.user_type.class_enum_type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.junit.Assert;
import org.junit.Test;

import net.jazgung.hibernate.GenericTest;
import net.jazgung.hibernate.user_type.class_enum_type.Enum2.Enum2_1;
import net.jazgung.hibernate.user_type.class_enum_type.Enum2.Enum2_2;
import net.jazgung.hibernate.user_type.class_enum_type.Enum3.Enum3_1;
import net.jazgung.hibernate.user_type.class_enum_type.Enum3.Enum3_2;
import net.jazgung.hibernate.user_type.class_enum_type.LocalPerson.Local;
import net.jazgung.hibernate.user_type.class_enum_type.LocalPerson.Local.Asia;
import net.jazgung.hibernate.user_type.class_enum_type.Person.Sex;

public class PersistentableEnumTest extends GenericTest {

	@Test
	public void test1() {
		printCutOffRule("开始test1");
		{
			Person p = new Person();
			p.setSex(Sex.MALE);
			p.setName("男");
			session.saveOrUpdate(p);
		}
		{
			Person p = new Person();
			p.setSex(Sex.FEMALE);
			p.setName("女");
			session.saveOrUpdate(p);
		}
		printCutOffRule("完成test1");
	}

	@Test
	public void test2() {
		printCutOffRule("开始test2");
		long id = -2;
		session.createSQLQuery("insert into " + Person.TABLE_NAME + " (id, sex) values (?, ?)").setParameter(0, id).setParameter(1, -1)
				.executeUpdate();

		Person p = (Person) session.load(Person.class, id);
		Assert.assertTrue(p.getSex() != Sex.MALE);
		Assert.assertTrue(p.getSex() != Sex.FEMALE);
		Assert.assertEquals(Integer.class, p.getSex().getValue().getClass());
		printCutOffRule("完成test1");
	}

	@Test
	public void test3() {
		printCutOffRule("开始test3");
		long id = -3;
		session.createSQLQuery("insert into " + LocalPerson.TABLE_NAME + " (id, location) values (?, ?)").setParameter(0, id)
				.setParameter(1, "2").executeUpdate();

		LocalPerson p = (LocalPerson) session.load(LocalPerson.class, id);
		Local location = p.getLocation();
		System.out.println("开始assert");
		Assert.assertTrue(location != Local.Asia.RPC);
		Assert.assertTrue(location != Local.Euro.UK);
		Assert.assertTrue(!Local.Asia.RPC.equals(location));
		Assert.assertTrue(Local.Euro.UK.equals(location));
		printCutOffRule("完成test3");
	}

	// @Test
	public void test3_1() {
		printCutOffRule("开始test3_1");
		long id = -31;
		session.createSQLQuery("insert into " + LocalPerson.TABLE_NAME + " (id, location) values (?, ?)").setParameter(0, id)
				.setParameter(1, "2").executeUpdate();

		LocalPerson p = (LocalPerson) session.load(LocalPerson.class, id);
		Local location = p.getLocation();
		System.out.println("开始assert");
		Assert.assertTrue(location != Local.Asia.RPC);
		Assert.assertTrue(location != Local.Euro.UK);
		// Assert.assertTrue(location != Local.American.USA); 关闭注册到其他类
		Assert.assertTrue(!Local.Asia.RPC.equals(location));
		Assert.assertTrue(Local.Euro.UK.equals(location));
		// Assert.assertTrue(!Local.American.USA.equals(location)); 关闭注册到其他类
		printCutOffRule("完成test3_1");
	}

	// @Test
	public void test3_2() {
		printCutOffRule("开始test3_2");
		long id = -32;
		session.createSQLQuery("insert into " + LocalPerson.TABLE_NAME + " (id, location) values (?, ?)").setParameter(0, id)
				.setParameter(1, "3").executeUpdate();

		LocalPerson p = (LocalPerson) session.load(LocalPerson.class, id);
		Local location = p.getLocation();
		System.out.println("开始assert");
		Assert.assertTrue(location != Local.Asia.RPC);
		Assert.assertTrue(location != Local.Euro.UK);
		// Assert.assertTrue(location != Local.American.USA); 关闭注册到其他类
		Assert.assertTrue(!Local.Asia.RPC.equals(location));
		Assert.assertTrue(!Local.Euro.UK.equals(location));
		// Assert.assertTrue(!Local.American.USA.equals(location)); 关闭注册到其他类
		printCutOffRule("完成test3_2");
	}

	@Test
	public void test4_1() {
		printCutOffRule("开始test4_1");
		long id = -41;
		session.createSQLQuery("insert into " + LocalPerson.TABLE_NAME + " (id, location) values (?, ?)").setParameter(0, id)
				.setParameter(1, "1").executeUpdate();

		LocalPerson p = (LocalPerson) session.load(LocalPerson.class, id);
		System.out.println("开始load");
		Local location = p.getLocation();
		System.out.println("开始assert");
		Assert.assertTrue(location != Local.Asia.RPC);
		Assert.assertTrue(location != Local.Euro.UK);
		Assert.assertTrue(Local.Asia.RPC.equals(location));
		Assert.assertTrue(!Local.Euro.UK.equals(location));
		printCutOffRule("完成test4_1");
	}

	@Test
	public void test4_2() {
		printCutOffRule("开始test4_2");
		long id = -42;
		session.createSQLQuery("insert into " + LocalPerson.TABLE_NAME + " (id, location) values (?, ?)").setParameter(0, id)
				.setParameter(1, "1").executeUpdate();

		@SuppressWarnings("unused")
		Asia e = Local.Asia.RPC;
		System.out.println("开始load");
		LocalPerson p = (LocalPerson) session.load(LocalPerson.class, id);
		Local location = p.getLocation();
		System.out.println("开始assert");
		Assert.assertTrue(location == Local.Asia.RPC);
		Assert.assertTrue(location != Local.Euro.UK);
		Assert.assertTrue(Local.Asia.RPC.equals(location));
		Assert.assertTrue(!Local.Euro.UK.equals(location));
		printCutOffRule("完成test4_2");
	}

	// @Test
	public void test4_3() {
		printCutOffRule("开始test4_3");
		long id = -43;
		session.createSQLQuery("insert into " + LocalPerson.TABLE_NAME + " (id, location) values (?, ?)").setParameter(0, id)
				.setParameter(1, "1").executeUpdate();

		LocalPerson p = (LocalPerson) session.load(LocalPerson.class, id);
		System.out.println("开始load");
		Local location = p.getLocation();
		System.out.println("开始assert");
		Assert.assertTrue(location != Local.Asia.RPC);
		Assert.assertTrue(location != Local.Euro.UK);
		Assert.assertTrue(Local.Asia.RPC.equals(location));
		Assert.assertTrue(!Local.Euro.UK.equals(location));
		printCutOffRule("完成test4_3");
	}

	@Test
	public void testEnum1() {
		printCutOffRule("开始testEnum1");
		try {
			@SuppressWarnings("unused")
			Enum1 e1 = Enum1.ENUM1_1;
			Assert.fail();
		} catch (ExceptionInInitializerError e) {
			Throwable error = e;
			do {
				System.out.println(error.getMessage());
				error = error.getCause();
			} while (null != error);
		}
		printCutOffRule("完成testEnum1");
	}

	/**
	 * @deprecated 关闭注册到其他类
	 */
	@SuppressWarnings("unused")
	// @Test
	public void testEnum2() {
		printCutOffRule("开始testEnum2");
		Enum2_1 e1 = Enum2.Enum2_1.ENUM2_1;
		Enum2_2 e2 = Enum2.Enum2_2.ENUM2_2;
		printCutOffRule("完成testEnum2");
	}

	@SuppressWarnings("unused")
	@Test
	public void testEnum3() {
		printCutOffRule("开始testEnum3");
		Enum3_1 e1 = Enum3.Enum3_1.ENUM3_1;
		try {
			Enum3_2 e2 = Enum3.Enum3_2.ENUM3_2;
			Assert.fail();
		} catch (ExceptionInInitializerError e) {
			Throwable error = e;
			do {
				System.out.println(error.getMessage());
				error = error.getCause();
			} while (null != error);
		}
		printCutOffRule("完成testEnum3");
	}

	@SuppressWarnings("unused")
	@Test
	public void testEnum4() {
		printCutOffRule("开始testEnum4");

		try {
			Enum4 e1 = Enum4.ENUM4_1;
			Assert.fail();
		} catch (ExceptionInInitializerError e) {
			Throwable error = e;
			do {
				System.out.println(error.getMessage());
				error = error.getCause();
			} while (null != error);
		}
		printCutOffRule("完成testEnum4");
	}

	@SuppressWarnings("unused")
	@Test
	public void testEnum5() {
		printCutOffRule("开始testEnum5");
		try {
			Enum5 e1 = Enum5.ENUM5;
			Assert.fail();
		} catch (ExceptionInInitializerError e) {
			Throwable error = e;
			do {
				System.out.println(error.getMessage());
				error = error.getCause();
			} while (null != error);
		}
		printCutOffRule("完成testEnum5");
	}

	@Test
	public void testEnum6() {
		printCutOffRule("开始testEnum6");
		try {
			@SuppressWarnings("unused")
			Enum6 e1 = Enum6.ENUM6;
			Assert.fail();
		} catch (ExceptionInInitializerError e) {
			Throwable error = e;
			do {
				System.out.println(error.getMessage());
				error = error.getCause();
			} while (null != error);
		}
		printCutOffRule("完成testEnum6");
	}

	@Test
	public void testEnum7() {
		printCutOffRule("开始testEnum7");
		try {
			@SuppressWarnings("unused")
			Enum7 e1 = Enum7.ENUM7;
			Assert.fail();
		} catch (ExceptionInInitializerError e) {
			Throwable error = e;
			do {
				System.out.println(error.getMessage());
				error = error.getCause();
			} while (null != error);
		}
		printCutOffRule("完成testEnum7");
	}

	@Test
	public void testEnum8() {
		printCutOffRule("开始testEnum8");
		try {
			@SuppressWarnings("unused")
			Enum8 e1 = Enum8.ENUM8;
			Assert.fail();
		} catch (ExceptionInInitializerError e) {
			Throwable error = e;
			do {
				System.out.println(error.getMessage());
				error = error.getCause();
			} while (null != error);
		}
		printCutOffRule("完成testEnum8");
	}
}

@Entity(name = "PersistentableEnumPerson")
@Table(name = Person.TABLE_NAME)
class Person {

	public static final String TABLE_NAME = "class_enum_type_person";

	public static class Sex extends PersistentableEnum<Integer> {

		public static final String NAME = "net.jazgung.hibernate.user_type.class_enum_type.Person$Sex";

		private Sex() {
		}

		private Sex(Integer value) {
			super(value);
		}

		public static final Sex MALE = new Sex(1);
		public static final Sex FEMALE = new Sex(2);
	}

	private Long id;

	private Sex sex;

	private String name;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "sex")
	@Type(type = PersistentableEnumType.NAME, parameters = { @Parameter(name = PersistentableEnumType.ENUM, value = Sex.NAME) })
	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

@Entity(name = "PersistentableEnumLocalPerson")
@Table(name = LocalPerson.TABLE_NAME)
class LocalPerson {

	public static final String TABLE_NAME = "class_enum_type_local_person";

	public static class Local extends PersistentableEnum<String> {

		public static final String NAME = "net.jazgung.hibernate.user_type.class_enum_type.LocalPerson$Local";

		private Local() {
		}

		private Local(String value) {
			super(value);
		}

		public static class Asia extends Local {

			public static final String NAME = "net.jazgung.hibernate.user_type.class_enum_type.LocalPerson$Local$Asia";

			private Asia() {
			}

			private Asia(String value) {
				super(value);
			}

			public static final Asia RPC = new Asia("1");
		}

		public static class Euro extends Local {

			public static final String NAME = "net.jazgung.hibernate.user_type.class_enum_type.LocalPerson$Local$Euro";

			private Euro() {
			}

			private Euro(String value) {
				super(value);
			}

			public static final Euro UK = new Euro("2");
		}

		// 关闭注册到其他类
		// public static class American extends Local {
		//
		// public static final String NAME =
		// "net.jazgung.hibernate.user_type.class_enum_type.LocalPerson$Local$American";
		//
		// private American() {
		// }
		//
		// private American(String value) {
		// super(value);
		// }
		//
		// public static final American USA = new American("3");
		// }
	}

	private Long id;

	private Asia location;

	private String name;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "location")
	@Type(type = PersistentableEnumType.NAME, parameters = { @Parameter(name = PersistentableEnumType.ENUM, value = Asia.NAME) })
	public Local getLocation() {
		return location;
	}

	public void setLocation(Asia location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

/**
 * 相同的持久值在同一个类中
 * 
 * @author JazGung
 *
 */
class Enum1 extends PersistentableEnum<String> {

	protected Enum1(String value) {
		super(value);
	}

	public static final Enum1 ENUM1_1 = new Enum1("1");
	public static final Enum1 ENUM1_2 = new Enum1("1");
}

/**
 * 
 * @deprecated 关闭注册到其他类<br/>
 *             相同的持久值在不同类中，且注册到不同的类
 * 
 * @author JazGung
 *
 */
class Enum2 extends PersistentableEnum<String> {

	private Enum2() {
	}

	protected Enum2(String value, Class<? extends PersistentableEnum<String>> clazz) {
		super(value);
	}

	public static class Enum2_1 extends Enum2 {

		protected Enum2_1() {
		}

		protected Enum2_1(String value) {
			super(value, Enum2_1.class);
		}

		public static final Enum2_1 ENUM2_1 = new Enum2_1("1");
	}

	public static class Enum2_2 extends Enum2 {

		protected Enum2_2() {
		}

		protected Enum2_2(String value) {
			super(value, Enum2_2.class);
		}

		public static final Enum2_2 ENUM2_2 = new Enum2_2("1");
	}
}

/**
 * 相同的持久值在不同类中，且注册到同一个类
 * 
 * @author JazGung
 *
 */
class Enum3 extends PersistentableEnum<String> {

	private Enum3() {
	}

	protected Enum3(String value) {
		super(value);
	}

	public static class Enum3_1 extends Enum3 {

		@SuppressWarnings("unused")
		private Enum3_1() {
		}

		protected Enum3_1(String value) {
			super(value);
		}

		public static final Enum3_1 ENUM3_1 = new Enum3_1("1");
	}

	public static class Enum3_2 extends Enum3 {

		@SuppressWarnings("unused")
		private Enum3_2() {
		}

		protected Enum3_2(String value) {
			super(value);
		}

		public static final Enum3_2 ENUM3_2 = new Enum3_2("1");
	}

}

/**
 * 
 * 持久化值类型不一样但toString()一样
 * 
 * @author JazGung
 *
 */

class Enum4 extends PersistentableEnum<Object> {

	public static class Enum4_ extends PersistentableEnum<String> {
	}

	@SuppressWarnings("unused")
	private Enum4() {

	}

	protected Enum4(Object value) {
		super(value);
	}

	public static final Enum4 ENUM4_1 = new Enum4("1");
	public static final Enum4 ENUM4_2 = new Enum4(1);
}

/**
 * 无私有的无参构造函数
 * 
 * @author JazGung
 *
 */
class Enum5 extends PersistentableEnum<String> {

	protected Enum5(String value) {
		super(value);
	}

	public static final Enum5 ENUM5 = new Enum5("1");
}

/**
 * public的无参构造函数
 * 
 * @author JazGung
 *
 */
class Enum6 extends PersistentableEnum<String> {

	public Enum6() {
	}

	protected Enum6(String value) {
		super(value);
	}

	public static final Enum6 ENUM6 = new Enum6("1");
}

/**
 * protected的无参构造函数
 * 
 * @author JazGung
 *
 */
class Enum7 extends PersistentableEnum<String> {

	protected Enum7() {
	}

	protected Enum7(String value) {
		super(value);
	}

	public static final Enum7 ENUM7 = new Enum7("1");
}

/**
 * default的无参构造函数
 * 
 * @author JazGung
 *
 */
class Enum8 extends PersistentableEnum<String> {

	Enum8() {
	}

	protected Enum8(String value) {
		super(value);
	}

	public static final Enum8 ENUM8 = new Enum8("1");
}
