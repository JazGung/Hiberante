package net.jazgung.hibernate.session;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;

import net.jazgung.hibernate.GenericTest;

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

	@Test
	public void testSession() {
		SessionFactory sessionFactory = configure.buildSessionFactory();
		Session session = sessionFactory.openSession();// 比较基准--通过openSession()获取
		Session sessionByGetCurrentSessionFromSessionFactory = sessionFactory.getCurrentSession();// 比较基准--通过getCurrentSession()获取

		// 同一个SessionFactory两次调用openSesion()两个Session对象不是同一个对象
		Session sessionByOpenSessionFromSessionFactory = sessionFactory.openSession();
		System.out.println("session == sessionByOpenSessionFromSessionFactory: " + (session == sessionByOpenSessionFromSessionFactory));

		// 同一个SessionFactory的openSesion()和getCurrentSession()两个Session对象不是同一个对象
		System.out.println(
				"session == sessionByGetCurrentSessionFromSessionFactory: " + (session == sessionByGetCurrentSessionFromSessionFactory));

		// 同一个SessionFactory两次调用getCurrentSession()两个Session对象是同一个对象
		Session otherSessionByGetCurrentSessionFromSessionFactory = sessionFactory.getCurrentSession();
		System.out.println("sessionByGetCurrentSessionFromSessionFactory == otherSessionByGetCurrentSessionFromSessionFactory: "
				+ (sessionByGetCurrentSessionFromSessionFactory == otherSessionByGetCurrentSessionFromSessionFactory));

		SessionFactory otherSessionFactory = configure.buildSessionFactory();
		// 不同SessionFactory调用openSesion()两个Session对象不是同一个对象
		Session sessionByOpenSessionFromOtherSessionFactory = otherSessionFactory.openSession();
		System.out.println(
				"session == sessionByOpenSessionFromOtherSessionFactory: " + (session == sessionByOpenSessionFromOtherSessionFactory));

		Session sessionByGetCurrentSessionFromOtherSessionFactory = otherSessionFactory.getCurrentSession();
		// 不同SessionFactory的openSesion()和getCurrentSession()两个Session对象不是同一个对象
		System.out.println("session == sessionByGetCurrentSessionFromOtherSessionFactory: "
				+ (session == sessionByGetCurrentSessionFromOtherSessionFactory));
		// 不同SessionFactory分别调用getCurrentSession()两个Session对象不是同一个对象
		System.out.println("sessionByGetCurrentSessionFromSessionFactory == sessionByGetCurrentSessionFromOtherSessionFactory: "
				+ (sessionByGetCurrentSessionFromSessionFactory == sessionByGetCurrentSessionFromOtherSessionFactory));
	}
}
