package net.jazgung.hibernate.query;

import java.text.MessageFormat;
import java.util.Iterator;

import net.jazgung.hibernate.GenericTest;

import org.junit.Test;

public class QueryTest extends GenericTest {

	@Test
	public void testIterator() {
		int MAX = 10;
		for (int i = 0; i < MAX; i++) {
			Person person = new Person();
			session.saveOrUpdate(person);
		}

		session.clear();

		System.out.println("开始读取");
		@SuppressWarnings("unchecked")
		Iterator<Person> iterator = (Iterator<Person>) session.createQuery("from " + Person.class.getName()).iterate();

		int i = 0;
		while (iterator.hasNext()) {
			System.out.println(MessageFormat.format("处理第{0}个", i + 1));
			i++;
			iterator.next();
		}
	}
}
