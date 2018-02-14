package net.jazgung.hibernate.association.normal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.exception.SQLGrammarException;
import org.hibernate.hql.ast.QuerySyntaxException;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试Hibernate使用HQL对关联字段查询<br/>
 * <br/>
 * 对于ToMany，必须使用在HQL的from子句中使用join关联被查询的字段；可以用对象或id查询<br/>
 * 对于ToOne，不必在使用在HQL的from子句中使用join关联被查询的字段；可以用对象或id查询<br/>
 * <br/>
 * 无论ToMany还是ToOne，查出结果都如下情况：<br/>
 * 不使用join，将直接查出from子句中的类对应的对象<br/>
 * 使用了join关联但没fetch，查询出来的结果都是2个元素的Object[]的List，其中第1个元素是from子句中根类的对象，
 * 第2个元素是作为参数的类型的对象<br/>
 * 使用了join fetch关联，将直接查出from子句中的类对应的对象<br/>
 * 
 * @author JazGung
 *
 */
public class AssociationQueryHqlTest extends AbstractAssociationQueryTest {

	private static Map<String, Object> values = new HashMap<String, Object>();

	@Override
	public void beforeTest() {
		super.beforeTest();
		values.put("mother", mother);
		values.put("motherId", mother.getId());
		values.put("owner", father);
		values.put("ownerId", father.getId());
	}

	@Test
	public void testToManyById() {
		printCutOffRule("testToManyById");
		try {
			String hql = "from " + House.class.getName() + "tenements.id = :mother.id";
			session.createQuery(hql).setProperties(values).list();
			Assert.fail();
		} catch (QuerySyntaxException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testToManyByIdWithJoin() {
		printCutOffRule("testToManyByIdWithJoin");
		String hql = "from " + House.class.getName() + " h inner join h.tenements t where t.id = :motherId";
		checkObjectArray(session.createQuery(hql).setProperties(values).list(), mother);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testToManyByIdWithJoinFetch() {
		printCutOffRule("testToManyByIdWithJoinFetch");
		String hql = "from " + House.class.getName() + " h inner join fetch h.tenements t where t.id = :motherId";
		checkHouse(session.createQuery(hql).setProperties(values).list());
	}

	@Test
	public void testToManyByObject() {
		printCutOffRule("testToManyByObject");
		String hql = "from " + House.class.getName() + " where tenements = :mother";
		try {
			session.createQuery(hql).setProperties(values).list();
			Assert.fail();
		} catch (SQLGrammarException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testToManyByObjectWithJoin() {
		printCutOffRule("testToManyByObjectWithJoin");
		String hql = "from " + House.class.getName() + " h inner join h.tenements t where t = :mother";
		checkObjectArray((List<Object[]>) session.createQuery(hql).setProperties(values).list(), mother);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testToManyByObjectWithJoinFetch() {
		printCutOffRule("testToManyByObjectWithJoinFetch");
		String hql = "from " + House.class.getName() + " h inner join fetch h.tenements t where t = :mother";
		checkHouse(session.createQuery(hql).setProperties(values).list());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testToOneById() {
		printCutOffRule("testToOneById");
		String hql = "from " + House.class.getName() + " where owner.id = :ownerId";
		session.createQuery(hql).setProperties(values).list();
		checkHouse(session.createQuery(hql).setProperties(values).list());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testToOneByIdWithJoin() {
		printCutOffRule("testToOneByIdWithJoin");
		String hql = "from " + House.class.getName() + " h inner join h.owner o where o.id = :ownerId";
		checkObjectArray(session.createQuery(hql).setProperties(values).list(), father);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testToOneByIdWithJoinFetch() {
		printCutOffRule("testToOneByIdWithJoinFetch");
		String hql = "from " + House.class.getName() + " h inner join fetch h.owner o where o.id = :ownerId";
		checkHouse(session.createQuery(hql).setProperties(values).list());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testToOneByObject() {
		printCutOffRule("testToOneByObject");
		String hql = "from " + House.class.getName() + " where owner = :owner";
		checkHouse(session.createQuery(hql).setProperties(values).list());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testToOneByObjectWithJoin() {
		printCutOffRule("testToOneByObjectWithJoin");
		String hql = "from " + House.class.getName() + " h inner join h.owner o where o = :owner";
		checkObjectArray(session.createQuery(hql).setProperties(values).list(), father);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testToOneByObjectWithJoinFetch() {
		printCutOffRule("testToOneByObjectWithJoinFetch");
		String hql = "from " + House.class.getName() + " h inner join fetch h.owner o where o = :owner";
		checkHouse(session.createQuery(hql).setProperties(values).list());

	}
}
