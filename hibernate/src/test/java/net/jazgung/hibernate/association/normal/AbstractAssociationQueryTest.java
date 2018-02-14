package net.jazgung.hibernate.association.normal;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;

import net.jazgung.hibernate.GenericTest;

public class AbstractAssociationQueryTest extends GenericTest {

	private static Boolean inited = false;
	protected static Person father;
	protected static Person mother;
	protected static Person son;
	protected static Person daughter;
	protected static House home;

	@Before
	public void beforeTest() {
		if (!inited) {
			father = new Person();
			father.setName("father");
			session.saveOrUpdate(father);

			home = new House();
			home.setOwner(father);
			session.saveOrUpdate(home);

			mother = new Person();
			mother.setName("mother");
			home.addTenement(mother);
			session.saveOrUpdate(mother);

			son = new Person();
			son.setName("son");
			home.addTenement(son);
			session.saveOrUpdate(son);

			daughter = new Person();
			daughter.setName("daughter");
			home.addTenement(daughter);
			session.saveOrUpdate(daughter);
			commitAndOpenNewSession();
			inited = true;
		}
	}

	protected void checkHouse(List<House> houses) {
		Assert.assertEquals(1, houses.size());
		Assert.assertEquals(home, houses.get(0));
	}

	protected void checkObjectArray(List<Object[]> result, Person person) {
		for (Object o : result.get(0)) {
			System.out.println(o);
		}
		for (Person p : ((House) result.get(0)[0]).getTenements()) {
			System.out.println(p);
		}
		Assert.assertEquals(1, result.size());
		Assert.assertEquals(home, result.get(0)[0]);
		Assert.assertEquals(person, result.get(0)[1]);
	}

}
