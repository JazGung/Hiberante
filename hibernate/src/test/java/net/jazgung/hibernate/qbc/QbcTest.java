package net.jazgung.hibernate.qbc;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import net.jazgung.hibernate.GenericTest;

public class QbcTest extends GenericTest {

	@Test
	public void testAssociation() {
		printCutOffRule("begin testAssociation");

		Criteria criteria = session.createCriteria(Person.class);
		criteria.createCriteria("house");
		criteria.createAlias("house.rooms", "rooms");
		criteria.list();

		printCutOffRule("end testAssociation");
	}

	@Test
	public void testRestriction() {
		printCutOffRule("begin testRestriction");

		Criteria personCriteria = session.createCriteria(Person.class);
		personCriteria.add(Restrictions.ilike("name", "%name%"));
		personCriteria.add(Restrictions.or(Restrictions.eq("age", 1), Restrictions.eq("age", 2)));// 多个条件之间的or连接
		personCriteria.list();

		printCutOffRule("end testRestriction");
	}

	@Test
	public void testProperty() {
		printCutOffRule("begin testProperty");

		Criteria criteria = session.createCriteria(Person.class);
		Property property = Property.forName("age");
		criteria.add(Restrictions.or(property.between(1, 2), property.between(4, 5)));// Property无法进行or，只能通过Restrictions完成
		criteria.list();

		printCutOffRule("end testProperty");
	}

	@Test
	public void testOrder() {
		printCutOffRule("begin testOrder");

		Criteria criteria = session.createCriteria(Person.class);
		criteria.createCriteria("house").addOrder(Order.asc("area"));
		criteria.createAlias("house.rooms", "rooms").addOrder(Order.asc("rooms.name"));
		criteria.list();

		printCutOffRule("end testOrder");
	}

	@Test
	public void testCreateCriteriaAndAlias() {
		printCutOffRule("begin testCreateCriteriaAndAlias");

		Criteria rootCriteria = session.createCriteria(Person.class);
		Criteria houseCriteria = rootCriteria.createCriteria("house");
		Criteria houseAlias = rootCriteria.createAlias("house", "house");

		System.out.println("rootCriteria == houseCriteria: " + (rootCriteria == houseCriteria));// createCriteria返回的是当前对象
		System.out.println("rootCriteria == houseAlias: " + (rootCriteria == houseAlias));// createAlias返回的是被调用对象

		printCutOffRule("end testCreateCriteriaAndAlias");
	}
}
