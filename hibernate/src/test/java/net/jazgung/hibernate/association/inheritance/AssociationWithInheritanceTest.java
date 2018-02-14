package net.jazgung.hibernate.association.inheritance;

import net.jazgung.hibernate.GenericTest;
import net.jazgung.hibernate.inheritance.joined.JoinedTool;
import net.jazgung.hibernate.inheritance.single_table.SingleTableItem;
import net.jazgung.hibernate.inheritance.table_per_class.TablePerClassItem;

import org.junit.Test;

public class AssociationWithInheritanceTest extends GenericTest {

	@Test
	public void test() {
		{
			AssociationWithInheritance bean = new AssociationWithInheritance();
			bean.setJoinedItem(new JoinedTool());
			session.saveOrUpdate(bean);
		}
		{
			AssociationWithInheritance bean = new AssociationWithInheritance();
			bean.setSingleTableItem(new SingleTableItem());
			session.saveOrUpdate(bean);
		}
		{
			AssociationWithInheritance bean = new AssociationWithInheritance();
			bean.setTablePerClassItem(new TablePerClassItem());
			session.saveOrUpdate(bean);
		}
	}

}
