package net.jazgung.hibernate.association.normal;

import java.util.List;

import org.hibernate.QueryException;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.SQLGrammarException;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试Hibernate使用QBC对关联字段查询<br/>
 * 对于ToMany，必须使用createCriteria创建新的Criteria对象才能设置查询条件；只能在建新的Criteria对象三设置id查询
 * <br/>
 * 对于ToOne，不必在使用createCriteria创建新的Criteria才能查询条件；如果没创建新的Criteria对象，
 * 可以直接在原Criteria对象上设置对象或id查询，否则只能在创建新的Criteria对象上设置id查询<br/>
 * 无论ToMany还是ToOne，无论是否使用createCriteria创建新的Criteria对象，查询结果都是调用session.
 * createCriteria(Class)的参数对应的对象
 * 
 * @author JazGung
 *
 */
public class AssociationQueryQbcTest extends AbstractAssociationQueryTest {

	@Test
	public void testToManyById() {
		printCutOffRule("testToManyById");
		try {
			session.createCriteria(House.class).add(Restrictions.eq("tenements.id", mother.getId())).list();
		} catch (QueryException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testToManyByIdWithCreateCriteria() {
		printCutOffRule("testToManyByIdWithCreateCriteria");
		checkHouse(session.createCriteria(House.class).createCriteria("tenements").add(Restrictions.eq("id", mother.getId())).list());
	}

	@Test
	public void testToManyByObject() {
		printCutOffRule("testToManyByObject");
		try {
			session.createCriteria(House.class).add(Restrictions.eq("tenements", mother)).list();
			Assert.fail();
		} catch (SQLGrammarException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testToManyByObjectWithCreateCriteria() {
		// QBC createCriteria后无法再根据对象进行读取
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testToOneById() {
		printCutOffRule("testToOneById");
		checkHouse(session.createCriteria(House.class).add(Restrictions.eq("owner.id", father.getId())).list());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testToOneByIdWithCreateCriteria() {
		printCutOffRule("testToOneByIdWithCreateCriteria");
		checkHouse(session.createCriteria(House.class).createCriteria("owner").add(Restrictions.eq("id", father.getId())).list());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testToOneByObject() {
		printCutOffRule("testToOneByObject");
		checkHouse((List<House>) session.createCriteria(House.class).add(Restrictions.eq("owner", father)).list());
	}

	@Test
	public void testToOneByObjectWithCreateCriteria() {
		// QBC createCriteria后无法再根据对象进行读取
	}

}
