package net.jazgung.hibernate.inheritance;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.jazgung.hibernate.GenericTest;
import net.jazgung.hibernate.inheritance.joined.JoinedIPhone;
import net.jazgung.hibernate.inheritance.joined.JoinedItBook;
import net.jazgung.hibernate.inheritance.joined.JoinedItem;
import net.jazgung.hibernate.inheritance.joined.JoinedScrewDriver;
import net.jazgung.hibernate.inheritance.joined.JoinedTelevision;
import net.jazgung.hibernate.inheritance.single_table.SingleTableIPhone;
import net.jazgung.hibernate.inheritance.single_table.SingleTableItBook;
import net.jazgung.hibernate.inheritance.single_table.SingleTableItem;
import net.jazgung.hibernate.inheritance.single_table.SingleTableScrewDriver;
import net.jazgung.hibernate.inheritance.single_table.SingleTableTelevision;
import net.jazgung.hibernate.inheritance.table_per_class.TablePerClassIPhone;
import net.jazgung.hibernate.inheritance.table_per_class.TablePerClassItBook;
import net.jazgung.hibernate.inheritance.table_per_class.TablePerClassItem;
import net.jazgung.hibernate.inheritance.table_per_class.TablePerClassScrewDriver;
import net.jazgung.hibernate.inheritance.table_per_class.TablePerClassTelevision;

import org.junit.Test;

public class InheritanceTest extends GenericTest {

	@Test
	public void testJoinedSave() {
		printCutOffRule("testJoined");

		JoinedTelevision joinedTelevision = new JoinedTelevision();
		joinedTelevision.setName("joinedTelevision");
		joinedTelevision.setPrice(Float.parseFloat("19.84"));

		JoinedItBook joinedItBook = new JoinedItBook();
		joinedItBook.setName("joinedItBook");
		joinedItBook.setPrice(Float.parseFloat("19.84"));

		JoinedScrewDriver joinedScrewDriver = new JoinedScrewDriver();
		joinedScrewDriver.setName("joinedScrewDriver");
		joinedScrewDriver.setPrice(Float.parseFloat("19.84"));

		JoinedIPhone joinedIPhone = new JoinedIPhone();
		joinedIPhone.setName("joinedIPhone");
		joinedIPhone.setPrice(Float.parseFloat("19.84"));

		session.saveOrUpdate(joinedTelevision);
		session.saveOrUpdate(joinedItBook);
		session.saveOrUpdate(joinedScrewDriver);
		session.saveOrUpdate(joinedIPhone);

		printCutOffRule("testJoined");
	}

	@Test
	public void testSingleTableSave() {
		printCutOffRule("testSingleTable");

		SingleTableTelevision singleTableTelevision = new SingleTableTelevision();
		singleTableTelevision.setName("singleTableTelevision");
		singleTableTelevision.setPrice(Float.parseFloat("19.84"));

		SingleTableItBook singleTableItBook = new SingleTableItBook();
		singleTableItBook.setName("singleTableItBook");
		singleTableItBook.setPrice(Float.parseFloat("19.84"));

		SingleTableScrewDriver singleTableScrewDriver = new SingleTableScrewDriver();
		singleTableScrewDriver.setName("singleTableScrewDriver");
		singleTableScrewDriver.setPrice(Float.parseFloat("19.84"));

		SingleTableIPhone singleTableIPhone = new SingleTableIPhone();
		singleTableIPhone.setName("singleTableIPhone");
		singleTableIPhone.setPrice(Float.parseFloat("19.84"));

		session.saveOrUpdate(singleTableTelevision);
		session.saveOrUpdate(singleTableItBook);
		session.saveOrUpdate(singleTableScrewDriver);
		session.saveOrUpdate(singleTableIPhone);

		printCutOffRule("testSingleTable");
	}

	@Test
	public void testTablePerClassSave() {
		printCutOffRule("testTablePerClass");

		TablePerClassTelevision tablePerClassTelevision = new TablePerClassTelevision();
		tablePerClassTelevision.setName("tablePerClassTelevision");
		tablePerClassTelevision.setPrice(Float.parseFloat("19.84"));

		TablePerClassItBook tablePerClassItBook = new TablePerClassItBook();
		tablePerClassItBook.setName("tablePerClassItBook");
		tablePerClassItBook.setPrice(Float.parseFloat("19.84"));

		TablePerClassScrewDriver tablePerClassScrewDriver = new TablePerClassScrewDriver();
		tablePerClassScrewDriver.setName("tablePerClassScrewDriver");
		tablePerClassScrewDriver.setPrice(Float.parseFloat("19.84"));

		TablePerClassIPhone tablePerClassIPhone = new TablePerClassIPhone();
		tablePerClassIPhone.setName("tablePerClassIPhone");
		tablePerClassIPhone.setPrice(Float.parseFloat("19.84"));

		session.saveOrUpdate(tablePerClassTelevision);
		session.saveOrUpdate(tablePerClassItBook);
		session.saveOrUpdate(tablePerClassScrewDriver);
		session.saveOrUpdate(tablePerClassIPhone);

		printCutOffRule("testTablePerClass");
	}

	@Test
	public void testJoinedQuerySameSession() {
		printCutOffRule("testJoinedQuerySameSession");

		JoinedTelevision joinedTelevision = new JoinedTelevision();
		joinedTelevision.setName("joinedTelevision");
		joinedTelevision.setPrice(Float.parseFloat("19.84"));

		JoinedItBook joinedItBook = new JoinedItBook();
		joinedItBook.setName("joinedItBook");
		joinedItBook.setPrice(Float.parseFloat("19.84"));

		JoinedScrewDriver joinedScrewDriver = new JoinedScrewDriver();
		joinedScrewDriver.setName("joinedScrewDriver");
		joinedScrewDriver.setPrice(Float.parseFloat("19.84"));

		JoinedIPhone joinedIPhone = new JoinedIPhone();
		joinedIPhone.setName("joinedIPhone");
		joinedIPhone.setPrice(Float.parseFloat("19.84"));

		session.saveOrUpdate(joinedTelevision);
		session.saveOrUpdate(joinedItBook);
		session.saveOrUpdate(joinedScrewDriver);
		session.saveOrUpdate(joinedIPhone);

		// 记录存入数据库的数据
		Set<JoinedItem> joinedItems = new HashSet<JoinedItem>();
		joinedItems.add(joinedTelevision);
		joinedItems.add(joinedItBook);
		joinedItems.add(joinedScrewDriver);
		joinedItems.add(joinedIPhone);

		commitAndOpenNewTransaction();

		List<?> joinedItemsFromDb = session.createQuery("from " + JoinedItem.class.getName()).list();
		validate(joinedItems, joinedItemsFromDb);

		printCutOffRule("testJoinedQuerySameSession");
	}

	@Test
	public void testSingleTableQuerySameSession() {
		printCutOffRule("testSingleTableQuerySameSession");

		SingleTableTelevision singleTableTelevision = new SingleTableTelevision();
		singleTableTelevision.setName("singleTableTelevision");
		singleTableTelevision.setPrice(Float.parseFloat("19.84"));

		SingleTableItBook singleTableItBook = new SingleTableItBook();
		singleTableItBook.setName("singleTableItBook");
		singleTableItBook.setPrice(Float.parseFloat("19.84"));

		SingleTableScrewDriver singleTableScrewDriver = new SingleTableScrewDriver();
		singleTableScrewDriver.setName("singleTableScrewDriver");
		singleTableScrewDriver.setPrice(Float.parseFloat("19.84"));

		SingleTableIPhone singleTableIPhone = new SingleTableIPhone();
		singleTableIPhone.setName("singleTableIPhone");
		singleTableIPhone.setPrice(Float.parseFloat("19.84"));

		session.saveOrUpdate(singleTableTelevision);
		session.saveOrUpdate(singleTableItBook);
		session.saveOrUpdate(singleTableScrewDriver);
		session.saveOrUpdate(singleTableIPhone);

		Set<SingleTableItem> singleTableItems = new HashSet<SingleTableItem>();
		singleTableItems.add(singleTableTelevision);
		singleTableItems.add(singleTableItBook);
		singleTableItems.add(singleTableScrewDriver);
		singleTableItems.add(singleTableIPhone);

		commitAndOpenNewTransaction();

		List<?> singleTableItemsFromDb = session.createQuery("from " + SingleTableItem.class.getName()).list();
		validate(singleTableItems, singleTableItemsFromDb);

		printCutOffRule("testSingleTableQuerySameSession");
	}

	@Test
	public void testTablePerClassQuerySameSession() {
		printCutOffRule("testSingleTableQuerySameSession");

		TablePerClassTelevision tablePerClassTelevision = new TablePerClassTelevision();
		tablePerClassTelevision.setName("tablePerClassTelevision");
		tablePerClassTelevision.setPrice(Float.parseFloat("19.84"));

		TablePerClassItBook tablePerClassItBook = new TablePerClassItBook();
		tablePerClassItBook.setName("tablePerClassItBook");
		tablePerClassItBook.setPrice(Float.parseFloat("19.84"));

		TablePerClassScrewDriver tablePerClassScrewDriver = new TablePerClassScrewDriver();
		tablePerClassScrewDriver.setName("tablePerClassScrewDriver");
		tablePerClassScrewDriver.setPrice(Float.parseFloat("19.84"));

		TablePerClassIPhone tablePerClassIPhone = new TablePerClassIPhone();
		tablePerClassIPhone.setName("tablePerClassIPhone");
		tablePerClassIPhone.setPrice(Float.parseFloat("19.84"));

		session.saveOrUpdate(tablePerClassTelevision);
		session.saveOrUpdate(tablePerClassItBook);
		session.saveOrUpdate(tablePerClassScrewDriver);
		session.saveOrUpdate(tablePerClassIPhone);

		Set<TablePerClassItem> tablePerClassItems = new HashSet<TablePerClassItem>();
		tablePerClassItems.add(tablePerClassTelevision);
		tablePerClassItems.add(tablePerClassItBook);
		tablePerClassItems.add(tablePerClassScrewDriver);
		tablePerClassItems.add(tablePerClassIPhone);

		commitAndOpenNewTransaction();

		List<?> tablePerClassItemsFromDb = session.createQuery("from " + TablePerClassItem.class.getName()).list();
		validate(tablePerClassItems, tablePerClassItemsFromDb);

		printCutOffRule("testSingleTableQuerySameSession");
	}

	@Test
	public void testJoinedQueryDifferentSession() {
		printCutOffRule("testJoinedQueryDifferentSession");

		JoinedTelevision joinedTelevision = new JoinedTelevision();
		joinedTelevision.setName("joinedTelevision");
		joinedTelevision.setPrice(Float.parseFloat("19.84"));

		JoinedItBook joinedItBook = new JoinedItBook();
		joinedItBook.setName("joinedItBook");
		joinedItBook.setPrice(Float.parseFloat("19.84"));

		JoinedScrewDriver joinedScrewDriver = new JoinedScrewDriver();
		joinedScrewDriver.setName("joinedScrewDriver");
		joinedScrewDriver.setPrice(Float.parseFloat("19.84"));

		JoinedIPhone joinedIPhone = new JoinedIPhone();
		joinedIPhone.setName("joinedIPhone");
		joinedIPhone.setPrice(Float.parseFloat("19.84"));

		session.saveOrUpdate(joinedTelevision);
		session.saveOrUpdate(joinedItBook);
		session.saveOrUpdate(joinedScrewDriver);
		session.saveOrUpdate(joinedIPhone);

		// 记录存入数据库的数据
		Set<JoinedItem> joinedItems = new HashSet<JoinedItem>();
		joinedItems.add(joinedTelevision);
		joinedItems.add(joinedItBook);
		joinedItems.add(joinedScrewDriver);
		joinedItems.add(joinedIPhone);

		commitAndOpenNewSession();

		List<?> joinedItemsFromDb = session.createQuery("from " + JoinedItem.class.getName()).list();
		validate(joinedItems, joinedItemsFromDb);

		printCutOffRule("testJoinedQueryDifferentSession");
	}

	@Test
	public void testSingleTableQueryDifferentSession() {
		printCutOffRule("testSingleTableQueryDifferentSession");

		SingleTableTelevision singleTableTelevision = new SingleTableTelevision();
		singleTableTelevision.setName("singleTableTelevision");
		singleTableTelevision.setPrice(Float.parseFloat("19.84"));

		SingleTableItBook singleTableItBook = new SingleTableItBook();
		singleTableItBook.setName("singleTableItBook");
		singleTableItBook.setPrice(Float.parseFloat("19.84"));

		SingleTableScrewDriver singleTableScrewDriver = new SingleTableScrewDriver();
		singleTableScrewDriver.setName("singleTableScrewDriver");
		singleTableScrewDriver.setPrice(Float.parseFloat("19.84"));

		SingleTableIPhone singleTableIPhone = new SingleTableIPhone();
		singleTableIPhone.setName("singleTableIPhone");
		singleTableIPhone.setPrice(Float.parseFloat("19.84"));

		session.saveOrUpdate(singleTableTelevision);
		session.saveOrUpdate(singleTableItBook);
		session.saveOrUpdate(singleTableScrewDriver);
		session.saveOrUpdate(singleTableIPhone);

		Set<SingleTableItem> singleTableItems = new HashSet<SingleTableItem>();
		singleTableItems.add(singleTableTelevision);
		singleTableItems.add(singleTableItBook);
		singleTableItems.add(singleTableScrewDriver);
		singleTableItems.add(singleTableIPhone);

		commitAndOpenNewSession();

		List<?> singleTableItemsFromDb = session.createQuery("from " + SingleTableItem.class.getName()).list();
		validate(singleTableItems, singleTableItemsFromDb);

		printCutOffRule("testSingleTableQueryDifferentSession");
	}

	@Test
	public void testTablePerClassQueryDifferentSession() {
		printCutOffRule("testTablePerClassQueryDifferentSession");

		TablePerClassTelevision tablePerClassTelevision = new TablePerClassTelevision();
		tablePerClassTelevision.setName("tablePerClassTelevision");
		tablePerClassTelevision.setPrice(Float.parseFloat("19.84"));

		TablePerClassItBook tablePerClassItBook = new TablePerClassItBook();
		tablePerClassItBook.setName("tablePerClassItBook");
		tablePerClassItBook.setPrice(Float.parseFloat("19.84"));

		TablePerClassScrewDriver tablePerClassScrewDriver = new TablePerClassScrewDriver();
		tablePerClassScrewDriver.setName("tablePerClassScrewDriver");
		tablePerClassScrewDriver.setPrice(Float.parseFloat("19.84"));

		TablePerClassIPhone tablePerClassIPhone = new TablePerClassIPhone();
		tablePerClassIPhone.setName("tablePerClassIPhone");
		tablePerClassIPhone.setPrice(Float.parseFloat("19.84"));

		session.saveOrUpdate(tablePerClassTelevision);
		session.saveOrUpdate(tablePerClassItBook);
		session.saveOrUpdate(tablePerClassScrewDriver);
		session.saveOrUpdate(tablePerClassIPhone);

		Set<TablePerClassItem> tablePerClassItems = new HashSet<TablePerClassItem>();
		tablePerClassItems.add(tablePerClassTelevision);
		tablePerClassItems.add(tablePerClassItBook);
		tablePerClassItems.add(tablePerClassScrewDriver);
		tablePerClassItems.add(tablePerClassIPhone);

		commitAndOpenNewSession();

		List<?> tablePerClassItemsFromDb = session.createQuery("from " + TablePerClassItem.class.getName()).list();
		validate(tablePerClassItems, tablePerClassItemsFromDb);

		printCutOffRule("testTablePerClassQueryDifferentSession");
	}

	private void validate(Collection<?> prepareObjects, Collection<?> resultObjects) {
		System.out.println("resultObjects.size()：" + resultObjects.size());
		for (Object resultObject : resultObjects) {
			System.out.println("是否在准备的数据集合中：" + prepareObjects.contains(resultObject) + "，类型：" + resultObject.getClass().getName());
		}
	}
}
