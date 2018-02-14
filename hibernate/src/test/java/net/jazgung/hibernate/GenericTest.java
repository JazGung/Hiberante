package net.jazgung.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class GenericTest {

	private static final int CUT_OFF_RULE_LENGTH = 140;

	protected static Configuration configure;

	protected SessionFactory sessionFactory;

	protected Session session;

	protected Transaction tx;

	@BeforeClass
	public static void before() {
		configure = new Configuration().configure();

		SchemaExport schemaExport = new SchemaExport(configure);
		schemaExport.create(true, true);
	}

	public static void main(String[] args) {
		before();
	}

	@Before
	public void setUp() {
		sessionFactory = configure.buildSessionFactory();
		session = sessionFactory.openSession();

		tx = session.beginTransaction();
	}

	@After
	public void tearDown() {
		commitTransaction();
		closeSession();
	}

	protected void commitAndOpenNewTransaction() {
		commitTransaction();

		tx = session.beginTransaction();
	}

	protected void commitAndOpenNewSession() {
		commitTransaction();

		closeSession();

		session = sessionFactory.openSession();
		tx = session.beginTransaction();
	}

	protected void commitAndOpenNewSessionWithoutTransaction() {
		commitTransaction();

		closeSession();

		session = sessionFactory.openSession();
	}

	protected void closeSession() {
		if (null != session && session.isOpen()) {
			session.close();
		}
	}

	protected void commitTransaction() {
		if (null != tx && !tx.wasCommitted() && !tx.wasRolledBack()) {
			tx.commit();
			tx = null;
		}
	}

	protected void printCutOffRule(String message) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < CUT_OFF_RULE_LENGTH; i++) {
			sb.append('-');
		}
		sb.append('\n');

		int beforLength = (int) ((CUT_OFF_RULE_LENGTH - message.length()) / 2);
		int afterLength = CUT_OFF_RULE_LENGTH - message.length() - beforLength;
		for (int i = 0; i < beforLength; i++) {
			sb.append('-');
		}
		sb.append(message);
		for (int i = 0; i < afterLength; i++) {
			sb.append('-');
		}
		sb.append('\n');

		for (int i = 0; i < CUT_OFF_RULE_LENGTH; i++) {
			sb.append('-');
		}

		System.out.println(sb.toString());
	}

	protected List<?> find(String queryString, Map<?, ?> values) {
		Query query = session.createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query.list();
	}
}
