package net.jazgung.hibernate.session;

import net.jazgung.hibernate.GenericTest;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

public class SessionTest extends GenericTest {

	@Test
	public void testReadWithoutTransactionAndAutoFlush() {
		System.out.println("begin testReadWithoutTransactionAndAutoFlush");
		Person p = new Person();
		p.setName("test");

		System.out.println("begin open session");
		Session session = sessionFactory.openSession();
		System.out.println("end open session");
		session.setFlushMode(FlushMode.AUTO);

		System.out.println("begin save");
		session.save(p);
		System.out.println("end save");

		System.out.println("begin read");
		session.createQuery("from " + Person.class.getName());
		System.out.println("end read");
		System.out.println("end testReadWithoutTransactionAndAutoFlush");
	}

	@Test
	public void testReadWithoutTransactionManualFlush() {
		System.out.println("begin testReadWithoutTransactionManualFlush");
		Person p = new Person();
		p.setName("test");

		System.out.println("begin open session");
		Session session = sessionFactory.openSession();
		System.out.println("end open session");
		session.setFlushMode(FlushMode.MANUAL);

		System.out.println("begin save");
		session.save(p);
		System.out.println("end save");

		System.out.println("begin read");
		session.createQuery("from " + Person.class.getName());
		System.out.println("end read");
		System.out.println("end testReadWithoutTransactionManualFlush");
	}

	@Test
	public void testReadWithTransactionAndAutoFlush() {
		System.out.println("begin testReadWithTransactionAndAutoFlush");
		Person p = new Person();
		p.setName("test");

		System.out.println("begin open session");
		Session session = sessionFactory.openSession();
		System.out.println("end open session");
		System.out.println("begin transaction");
		Transaction tx = session.beginTransaction();
		System.out.println("end transaction");
		session.setFlushMode(FlushMode.AUTO);

		System.out.println("begin save");
		session.save(p);
		System.out.println("end save");

		System.out.println("begin read");
		session.createQuery("from " + Person.class.getName());
		System.out.println("end read");
		System.out.println("end testReadWithTransactionAndAutoFlush");

		tx.commit();
	}

	@Test
	public void testReadWithTransactionManualFlush() {
		System.out.println("begin testReadWithTransactionManualFlush");
		Person p = new Person();
		p.setName("test");

		System.out.println("begin open session");
		Session session = sessionFactory.openSession();
		System.out.println("end open session");
		System.out.println("begin transaction");
		Transaction tx = session.beginTransaction();
		System.out.println("end transaction");
		session.setFlushMode(FlushMode.MANUAL);

		System.out.println("begin save");
		session.save(p);
		System.out.println("end save");

		System.out.println("begin read");
		session.createQuery("from " + Person.class.getName());
		System.out.println("end read");
		System.out.println("end testReadWithTransactionManualFlush");
		tx.commit();
	}
}
