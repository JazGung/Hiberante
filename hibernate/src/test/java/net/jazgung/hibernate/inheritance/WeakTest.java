package net.jazgung.hibernate.inheritance;

import net.jazgung.hibernate.GenericTest;
import net.jazgung.hibernate.association.weak.House;
import net.jazgung.hibernate.association.weak.Person;

import org.junit.Assert;
import org.junit.Test;

public class WeakTest extends GenericTest {

	@Test
	public void testComma() {
		House house = new House();
		house.setArea(100);
		session.saveOrUpdate(house);

		Person person = new Person();
		person.setAge(18);
		person.setName("JazGung");
		person.setHouseId(house.getId());
		session.saveOrUpdate(person);

		Person p = (Person) session.createQuery(
				"select p from " + Person.class.getName() + " as p, " + House.class.getName() + " as h where p.houseId = h.id and p.id = "
						+ person.getId()).uniqueResult();

		Assert.assertNotNull(p);
	}

	@Test
	public void testLeftJoinOn() {
		House house = new House();
		house.setArea(100);
		session.saveOrUpdate(house);

		Person person = new Person();
		person.setAge(18);
		person.setName("JazGung");
		person.setHouseId(house.getId());
		session.saveOrUpdate(person);

		try {// HQL不允许有on
			session.createQuery(
					"select p from " + Person.class.getName() + " as p left join " + House.class.getName()
							+ " as h on p.houseId = h.id where p.id = " + person.getId()).uniqueResult();
			Assert.fail("testLeftJoinOn, 未抛出异常");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testLeftJoinWhere() {
		House house = new House();
		house.setArea(100);
		session.saveOrUpdate(house);

		Person person = new Person();
		person.setAge(18);
		person.setName("JazGung");
		person.setHouseId(house.getId());
		session.saveOrUpdate(person);

		try {// 尝试使用where也报错
			session.createQuery(
					"select p from " + Person.class.getName() + " as p left join " + House.class.getName()
							+ " as h where p.houseId = h.id and p.id = " + person.getId()).uniqueResult();

			Assert.fail("testLeftJoinWhere, 未抛出异常");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
