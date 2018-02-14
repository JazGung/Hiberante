package net.jazgung.hibernate.performance.fetch;

import java.util.List;

import net.jazgung.hibernate.GenericTest;

import org.hibernate.QueryException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.Test;

public class FetchTest extends GenericTest {

	private final static String PERSON_NAME = "Jaz Gung";

	private Person person;

	private long id;

	@Override
	public void setUp() {
		super.setUp();

		person = createPerson();

		session.persist(person);
		tx.commit();

		id = person.getId();
	}

	private Person createPerson() {
		Person person = new Person();
		person.setName(PERSON_NAME);

		{
			ToOneDefaultFetchCar car = new ToOneDefaultFetchCar();
			car.setName("default car");
			person.setDefaultFetchCar(car);
		}

		{
			ToOneJoinFetchCar car = new ToOneJoinFetchCar();
			car.setName("join car");
			person.setJoinFetchCar(car);
		}

		{
			ToOneSelectFetchCar car = new ToOneSelectFetchCar();
			car.setName("select car");
			person.setSelectFetchCar(car);
		}

		{
			for (int i = 0; i < 4; i++) {
				Car car = new Car();
				car.setName("default car " + (i + 0));
				person.addDefaultFetchCar(car);
			}
		}

		{
			for (int i = 0; i < 4; i++) {
				Car car = new Car();
				car.setName("join car " + (i + 0));
				person.addJoinFetchCar(car);
			}
		}

		{
			for (int i = 0; i < 4; i++) {
				Car car = new Car();
				car.setName("select car " + (i + 0));
				person.addSelectFetchCar(car);
			}
		}

		{
			for (int i = 0; i < 4; i++) {
				Car car = new Car();
				car.setName("sub select car " + (i + 0));
				person.addSubSelectFetchCar(car);
			}
		}

		return person;
	}

	@Override
	public void tearDown() {
		clear(person);

		super.tearDown();
	}

	private void clear(Person person) {
		tx = session.beginTransaction();

		session.merge(person);
		session.delete(person);
	}

	@Test
	public void testLoad() {
		printCutOffRule("开始测试load");

		Session session = sessionFactory.openSession();

		printCutOffRule("开始读取Person");
		// FetchMode对load有效
		Person personFromDb = (Person) session.load(Person.class, id);
		printCutOffRule("完成读取Person");

		assertFetch(person, personFromDb);

		printCutOffRule("完成测试load");
	}

	@Test
	public void testGet() {
		printCutOffRule("开始测试get");

		Session session = sessionFactory.openSession();

		printCutOffRule("开始读取Person");
		// FetchMode对load有效
		Person personFromDb = (Person) session.load(Person.class, id);
		printCutOffRule("完成读取Person");
		assertFetch(person, personFromDb);

		printCutOffRule("完成测试get");
	}

	@Test
	public void testQueryUnique() {
		printCutOffRule("开始测试query unique");

		Session session = sessionFactory.openSession();

		printCutOffRule("开始读取Person");
		// FetchMode对查询唯一的HQL，查询的根类中配置无效，但关联类中配置有效（因为获取关联对象本质上是与get/load一致，ToOne和ToMany一致）
		Person personFromDb = (Person) session.createQuery("from " + Person.class.getName() + " p where p.name = :name")
				.setString("name", PERSON_NAME).uniqueResult();
		printCutOffRule("完成读取Person");

		assertFetch(person, personFromDb);

		printCutOffRule("完成测试query unique");
	}

	@Test
	public void testQueryList() {
		// 准备第二条记录
		Person person = createPerson();
		{
			Transaction tx = session.beginTransaction();
			session.persist(person);
			tx.commit();
		}

		{
			printCutOffRule("开始测试query list");

			Session session = sessionFactory.openSession();

			printCutOffRule("开始读取Person");
			// FetchMode对查询List的HQL，查询的根类中配置无效，但关联类中配置有效（因为获取关联对象本质上是与get/load一致，ToOne和ToMany一致）。
			// Hiberate会对查出的对象的每一个ToOne字段发一条select进行查询，因此附带的select的数量为M，M为对象中ToOne的字段的数量
			// Hibernate: //HQL对应的SQL
			// select
			// person0_.id as id62_,
			// person0_.default_fectch_car_id as default3_62_,
			// person0_.join_fetch_car_id as join4_62_,
			// person0_.name as name62_,
			// person0_.select_fetch_car_id as select5_62_
			// from
			// performance_fetch_person person0_
			// where
			// person0_.name=?
			// Hibernate: //抓取defaultFetchCar
			// select
			// toonedefau0_.id as id64_1_,
			// toonedefau0_.join_brand_id as join3_64_1_,
			// toonedefau0_.name as name64_1_,
			// brand1_.id as id67_0_,
			// brand1_.name as name67_0_
			// from
			// performance_fetch_to_one_default_fetch_car toonedefau0_
			// left outer join
			// performance_brand_car brand1_
			// on toonedefau0_.join_brand_id=brand1_.id
			// where
			// toonedefau0_.id=?
			// Hibernate: //抓取joinFetchCar
			// select
			// toonejoinf0_.id as id65_1_,
			// toonejoinf0_.join_brand_id as join3_65_1_,
			// toonejoinf0_.name as name65_1_,
			// brand1_.id as id67_0_,
			// brand1_.name as name67_0_
			// from
			// performance_fetch_to_one_join_fetch_car toonejoinf0_
			// left outer join
			// performance_brand_car brand1_
			// on toonejoinf0_.join_brand_id=brand1_.id
			// where
			// toonejoinf0_.id=?
			// Hibernate: //抓取selectFetchCar
			// select
			// tooneselec0_.id as id66_1_,
			// tooneselec0_.join_brand_id as join3_66_1_,
			// tooneselec0_.name as name66_1_,
			// brand1_.id as id67_0_,
			// brand1_.name as name67_0_
			// from
			// performance_fetch_to_one_select_fetch_car tooneselec0_
			// left outer join
			// performance_brand_car brand1_
			// on tooneselec0_.join_brand_id=brand1_.id
			// where
			// tooneselec0_.id=?
			// Hibernate:
			// select
			// toonedefau0_.id as id64_1_,
			// toonedefau0_.join_brand_id as join3_64_1_,
			// toonedefau0_.name as name64_1_,
			// brand1_.id as id67_0_,
			// brand1_.name as name67_0_
			// from
			// performance_fetch_to_one_default_fetch_car toonedefau0_
			// left outer join
			// performance_brand_car brand1_
			// on toonedefau0_.join_brand_id=brand1_.id
			// where
			// toonedefau0_.id=?
			// Hibernate:
			// select
			// toonejoinf0_.id as id65_1_,
			// toonejoinf0_.join_brand_id as join3_65_1_,
			// toonejoinf0_.name as name65_1_,
			// brand1_.id as id67_0_,
			// brand1_.name as name67_0_
			// from
			// performance_fetch_to_one_join_fetch_car toonejoinf0_
			// left outer join
			// performance_brand_car brand1_
			// on toonejoinf0_.join_brand_id=brand1_.id
			// where
			// toonejoinf0_.id=?
			// Hibernate:
			// select
			// tooneselec0_.id as id66_1_,
			// tooneselec0_.join_brand_id as join3_66_1_,
			// tooneselec0_.name as name66_1_,
			// brand1_.id as id67_0_,
			// brand1_.name as name67_0_
			// from
			// performance_fetch_to_one_select_fetch_car tooneselec0_
			// left outer join
			// performance_brand_car brand1_
			// on tooneselec0_.join_brand_id=brand1_.id
			// where
			// tooneselec0_.id=?
			// Hibernate:
			// select
			// joinfetchc0_.person_id as person1_62_2_,
			// joinfetchc0_.card_id as card2_2_,
			// car1_.id as id63_0_,
			// car1_.join_brand_id as join3_63_0_,
			// car1_.name as name63_0_,
			// brand2_.id as id67_1_,
			// brand2_.name as name67_1_
			// from
			// performance_fetch_join joinfetchc0_
			// inner join
			// performance_fetch_car car1_
			// on joinfetchc0_.card_id=car1_.id
			// left outer join
			// performance_brand_car brand2_
			// on car1_.join_brand_id=brand2_.id
			// where
			// joinfetchc0_.person_id=?
			// Hibernate:
			// select
			// joinfetchc0_.person_id as person1_62_2_,
			// joinfetchc0_.card_id as card2_2_,
			// car1_.id as id63_0_,
			// car1_.join_brand_id as join3_63_0_,
			// car1_.name as name63_0_,
			// brand2_.id as id67_1_,
			// brand2_.name as name67_1_
			// from
			// performance_fetch_join joinfetchc0_
			// inner join
			// performance_fetch_car car1_
			// on joinfetchc0_.card_id=car1_.id
			// left outer join
			// performance_brand_car brand2_
			// on car1_.join_brand_id=brand2_.id
			// where
			// joinfetchc0_.person_id=?
			@SuppressWarnings("unchecked")
			List<Person> personsFromDb = session.createQuery("from " + Person.class.getName() + " p where p.name = :name")
					.setString("name", PERSON_NAME).list();
			printCutOffRule("完成读取Person");

			System.out.println("结果数：" + personsFromDb.size());
			int i = 0;
			for (Person personFromDb : personsFromDb) {
				printCutOffRule("personsFromDb" + i);
				assertFetch(person, personFromDb);
				i++;
			}

			printCutOffRule("完成测试query list");
		}

		{
			Transaction tx = session.beginTransaction();
			session.merge(person);
			session.delete(person);
			tx.commit();
		}
	}

	@Test
	public void testQueryListFetch1() {
		Person person = createPerson();
		{
			Transaction tx = session.beginTransaction();
			session.persist(person);
			tx.commit();
		}

		{
			printCutOffRule("开始测试query list fetch 1");

			Session session = sessionFactory.openSession();

			printCutOffRule("开始读取Person");
			@SuppressWarnings("unchecked")
			// 如果需要在HQL查询时在同一条sql中获取关联对象，必须使用join fetch，但关联对象中的Fetch配置无效
			// Hiberate会对查出的对象的每一个ToOne字段发一条select进行查询，因此附带的select的数量为N*M，N为查询出的对象数量，M为对象中ToOne的字段的数量
			List<Person> personsFromDb = session
					.createQuery("from " + Person.class.getName() + " p left join fetch p.defaultFetchCar c where c.name like :name")
					.setString("name", "%car%").list();
			printCutOffRule("完成读取Person");

			int i = 0;
			for (Person personFromDb : personsFromDb) {
				printCutOffRule("personsFromDb" + i);
				assertFetch(person, personFromDb);
				i++;
			}

			printCutOffRule("完成测试query list fetch 1");
		}

		{
			Transaction tx = session.beginTransaction();
			session.merge(person);
			session.delete(person);
			tx.commit();
		}
	}

	@Test
	public void testQueryListFetch2() {
		Person person = createPerson();
		{
			Transaction tx = session.beginTransaction();
			session.persist(person);
			tx.commit();
		}

		{
			printCutOffRule("开始测试query list fetch 2");

			Session session = sessionFactory.openSession();

			printCutOffRule("开始读取Person");
			@SuppressWarnings("unchecked")
			// 在from使用join fetch，在where中还是可以用.的方式引用关联对象中的字段，但关联对象中的Fetch配置无效
			List<Person> personsFromDb = session
					.createQuery(
							"from " + Person.class.getName()
									+ " p left join fetch p.defaultFetchCar where p.defaultFetchCar.name like :name")
					.setString("name", "%car%").list();
			printCutOffRule("完成读取Person");

			int i = 0;
			for (Person personFromDb : personsFromDb) {
				printCutOffRule("personsFromDb" + i);
				assertFetch(person, personFromDb);
				i++;
			}

			printCutOffRule("完成测试query list fetch 2");
		}

		{
			Transaction tx = session.beginTransaction();
			session.merge(person);
			session.delete(person);
			tx.commit();
		}
	}

	@Test
	public void testQueryListFetch3_1() {
		Person person = createPerson();
		{
			Transaction tx = session.beginTransaction();
			session.persist(person);
			tx.commit();
		}

		{
			printCutOffRule("开始测试query list fetch 3-1");

			Session session = sessionFactory.openSession();

			printCutOffRule("开始读取Person");

			@SuppressWarnings("unchecked")
			// HQL允许能够对同一个字段同时进行join和join fetch，结果无法转换为对象
			List<Person> personsFromDb = session
					.createQuery(
							"from " + Person.class.getName()
									+ " p left join p.defaultFetchCar c left join fetch p.defaultFetchCar where c.name like :name")
					.setString("name", "%car%").list();
			printCutOffRule("完成读取Person");

			try {
				for (@SuppressWarnings("unused")
				Person personFromDb : personsFromDb) {// 返回对象没有转换为对象，而是一个Object的列表
					Assert.fail("query list fetch 5-1，未抛出异常");
				}
			} catch (ClassCastException e) {
				System.out.println(e.getMessage());
			}

			printCutOffRule("完成测试query list fetch 3-1");
		}

		{
			Transaction tx = session.beginTransaction();
			session.merge(person);
			session.delete(person);
			tx.commit();
		}
	}

	@Test
	public void testQueryListFetch3_2() {
		Person person = createPerson();
		{
			Transaction tx = session.beginTransaction();
			session.persist(person);
			tx.commit();
		}

		{
			printCutOffRule("开始测试query list fetch 3-2");

			Session session = sessionFactory.openSession();

			printCutOffRule("开始读取Person");

			@SuppressWarnings("unchecked")
			// HQL允许能够对同一个字段进行多次join fetch，结果可以转换为对象
			List<Person> personsFromDb = session
					.createQuery(
							"from " + Person.class.getName()
									+ " p left join fetch p.defaultFetchCar c left join fetch p.defaultFetchCar where c.name like :name")
					.setString("name", "%car%").list();
			printCutOffRule("完成读取Person");

			int i = 0;
			for (Person personFromDb : personsFromDb) {
				printCutOffRule("personsFromDb" + i);
				assertFetch(person, personFromDb);
				i++;
			}

			printCutOffRule("完成测试query list fetch 3-2");
		}

		{
			Transaction tx = session.beginTransaction();
			session.merge(person);
			session.delete(person);
			tx.commit();
		}
	}

	@Test
	public void testQueryListFetch4() {
		Person person = createPerson();
		{
			Transaction tx = session.beginTransaction();
			session.persist(person);
			tx.commit();
		}

		{
			printCutOffRule("开始测试query list fetch 4");

			Session session = sessionFactory.openSession();

			printCutOffRule("开始读取Person");

			@SuppressWarnings("unchecked")
			// HQL中的fetch可以用.进行级联书写，但Hiberanet只会将路径最后的对象一次性读出而不会一次性读出路径上所有关联对象
			List<Person> personsFromDb = session
					.createQuery("from " + Person.class.getName() + " p left join fetch p.defaultFetchCar.brand b where b.name like :name")
					.setString("name", "%car%").list();
			printCutOffRule("完成读取Person");

			int i = 0;
			for (Person personFromDb : personsFromDb) {
				printCutOffRule("personsFromDb" + i);
				assertFetch(person, personFromDb);
				i++;
			}

			printCutOffRule("完成测试query list fetch 4");
		}

		{
			Transaction tx = session.beginTransaction();
			session.merge(person);
			session.delete(person);
			tx.commit();
		}
	}

	@Test
	public void testQueryListFetch5_1() {
		Person person = createPerson();
		{
			Transaction tx = session.beginTransaction();
			session.persist(person);
			tx.commit();
		}

		{
			printCutOffRule("开始测试query list fetch 5-1");

			Session session = sessionFactory.openSession();

			printCutOffRule("开始读取Person");

			// HQL中对ToMany进行fetch时，必须显示的对ToMany进行fetch
			try {
				session.createQuery(
						"from " + Person.class.getName() + " p left join fetch p.defaultFetchCars.brand b where b.name like :name")
						.setString("name", "%car%").list();// 对ToMany没有显示fetch，而是使用的.级联，因此报错
				Assert.fail("query list fetch 5-1，未抛出异常");
			} catch (QueryException e) {
				System.out.println(e.getMessage());
			}

			printCutOffRule("完成测试query list fetch 5-1");
		}

		{
			Transaction tx = session.beginTransaction();
			session.merge(person);
			session.delete(person);
			tx.commit();
		}
	}

	@Test
	public void testQueryListFetch5_2() {
		Person person = createPerson();
		{
			Transaction tx = session.beginTransaction();
			session.persist(person);
			tx.commit();
		}

		{
			printCutOffRule("开始测试query list fetch 5-2");

			Session session = sessionFactory.openSession();

			printCutOffRule("开始读取Person");

			// HQL中对ToMany进行fetch时，必须显示的对ToMany，并且对ToMany关联的对象进行fetch时，不能使用.进行级联书写，必须对ToMany赋别名然后使用别名级联
			try {
				session.createQuery(
						"from "
								+ Person.class.getName()
								+ " p left join fetch p.defaultFetchCars left join fetch p.defaultFetchCars.brand b where b.name like :name")
						.setString("name", "%car%").list();// 虽然现实对ToMany进行了fetch，但对ToMany关联的对象进行fetch使用了.进行级联书写，因此报错
			} catch (QueryException e) {
				System.out.println(e.getMessage());
			}

			printCutOffRule("完成测试query list fetch 5-2");
		}

		{
			Transaction tx = session.beginTransaction();
			session.merge(person);
			session.delete(person);
			tx.commit();
		}
	}

	@Test
	public void testQueryListFetch5_3() {
		Person person = createPerson();
		{
			Transaction tx = session.beginTransaction();
			session.persist(person);
			tx.commit();
		}

		{
			printCutOffRule("开始测试query list fetch 5-3");

			Session session = sessionFactory.openSession();

			printCutOffRule("开始读取Person");

			@SuppressWarnings("unchecked")
			// HQL中对ToMany进行fetch时，必须显示的对ToMany，并且对ToMany关联的对象进行fetch时使用了ToMany的别名级联
			List<Person> personsFromDb = session
					.createQuery(
							"from " + Person.class.getName()
									+ " p left join fetch p.defaultFetchCars c left join fetch c.brand b where b.name like :name")
					.setString("name", "%car%").list();
			printCutOffRule("完成读取Person");

			int i = 0;
			for (Person personFromDb : personsFromDb) {
				printCutOffRule("personsFromDb" + i);
				assertFetch(person, personFromDb);
				i++;
			}

			printCutOffRule("完成测试query list fetch 5-3");
		}

		{
			Transaction tx = session.beginTransaction();
			session.merge(person);
			session.delete(person);
			tx.commit();
		}
	}

	private void assertFetch(Person person, Person personFromDb) {
		System.out.println(personFromDb.getId() + "，" + personFromDb.getName());
		printCutOffRule("对比：" + (personFromDb == person));

		{
			printCutOffRule("");
			printCutOffRule("开始访问DefaultFetchCar");
			printCutOffRule(personFromDb.getDefaultFetchCar().getName());
			printCutOffRule("完成访问DefaultFetchCar");
		}

		{
			printCutOffRule("");
			printCutOffRule("开始访问JoinFetchCar");
			printCutOffRule(personFromDb.getJoinFetchCar().getName());
			printCutOffRule("完成访问JoinFetchCar");
		}

		{
			printCutOffRule("");
			printCutOffRule("开始访问SelectFetchCar");
			printCutOffRule(personFromDb.getSelectFetchCar().getName());
			printCutOffRule("完成访问SelectFetchCar");
		}

		{
			printCutOffRule("");
			printCutOffRule("开始访问DefaultFetchCars");
			printCutOffRule("开始获取DefaultFetchCars");
			List<Car> cars = personFromDb.getDefaultFetchCars();
			printCutOffRule("完成获取DefaultFetchCars");
			for (Car car : cars) {
				printCutOffRule(car.getName());
			}
			printCutOffRule("完成访问DefaultFetchCars");
		}

		{
			printCutOffRule("");
			printCutOffRule("开始访问JoinFetchCars");
			printCutOffRule("开始获取JoinFetchCars");
			List<Car> cars = personFromDb.getJoinFetchCars();
			printCutOffRule("完成获取JoinFetchCars");
			for (Car car : cars) {
				printCutOffRule(car.getName());
			}
			printCutOffRule("完成访问JoinFetchCars");
		}

		{
			printCutOffRule("");
			printCutOffRule("开始访问SelectFetchCars");
			printCutOffRule("开始获取SelectFetchCars");
			List<Car> cars = personFromDb.getSelectFetchCars();
			printCutOffRule("完成获取SelectFetchCars");
			for (Car car : cars) {
				printCutOffRule(car.getName());
			}
			printCutOffRule("完成访问SelectFetchCars");
		}

		{
			printCutOffRule("");
			printCutOffRule("开始访问SubSelectFetchCars");
			printCutOffRule("开始获取SubSelectFetchCars");
			List<Car> cars = personFromDb.getSubSelectFetchCars();
			printCutOffRule("完成获取SubSelectFetchCars");
			for (Car car : cars) {
				printCutOffRule(car.getName());
			}
			printCutOffRule("完成访问SubSelectFetchCars");
		}
	}
}
