package net.jazgung.hibernate.performance.lazy;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import net.jazgung.hibernate.GenericTest;

public class LazyTest extends GenericTest {

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
			MonodirectionalToOneDefaultLazyCar car = new MonodirectionalToOneDefaultLazyCar();
			car.setName("monodirectional default car");
			person.setMonodirectionalDefaultCar(car);
		}

		{
			MonodirectionalToOneFalseLazyCar car = new MonodirectionalToOneFalseLazyCar();
			car.setName("monodirectional false car");
			person.setMonodirectionalFalseCar(car);
		}

		{
			MonodirectionalToOneProxyCar car = new MonodirectionalToOneProxyCar();
			car.setName("monodirectional proxy car");
			person.setMonodirectionalProxyCar(car);
		}

		{
			MonodirectionalToOneNoProxyCar car = new MonodirectionalToOneNoProxyCar();
			car.setName("monodirectional no proxy car");
			person.setMonodirectionalNoProxyCar(car);
		}

		{
			BidirectionalToOneDefaultLazyCar car = new BidirectionalToOneDefaultLazyCar();
			car.setName("bidirectional default car");
			person.setBidirectionalDefaultCar(car);
			car.setPerson(person);
		}

		{
			BidirectionalToOneFalseLazyCar car = new BidirectionalToOneFalseLazyCar();
			car.setName("bidirectional false car");
			person.setBidirectionalFalseCar(car);
			car.setPerson(person);
		}

		{
			BidirectionalToOneProxyCar car = new BidirectionalToOneProxyCar();
			car.setName("bidirectional proxy car");
			person.setBidirectionalProxyCar(car);
			car.setPerson(person);
		}

		{
			BidirectionalToOneNoProxyCar car = new BidirectionalToOneNoProxyCar();
			car.setName("bidirectional no proxy car");
			person.setBidirectionalNoProxyCar(car);
			car.setPerson(person);
		}

		{
			for (int i = 0; i < 4; i++) {
				Car car = new Car();
				car.setName("default car " + (i + 0));
				person.addDefaultCar(car);
			}
		}

		{
			for (int i = 0; i < 4; i++) {
				Car car = new Car();
				car.setName("true car " + (i + 0));
				person.addTrueCar(car);
			}
		}

		{
			for (int i = 0; i < 4; i++) {
				Car car = new Car();
				car.setName("false car " + (i + 0));
				person.addFalseCar(car);
			}
		}

		{
			for (int i = 0; i < 4; i++) {
				Car car = new Car();
				car.setName("extra car " + (i + 0));
				person.addExtraCar(car);
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
		System.out.println("开始测试load");

		Session session = sessionFactory.openSession();

		System.out.println("开始读取Person");
		// Lazy对load有效，当不要求Lazy时，Fecth生效
		Person personFromDb = (Person) session.load(Person.class, id);
		System.out.println("完成读取Person");

		assertFetch(person, personFromDb);

		System.out.println("完成测试load");
	}

	@Test
	public void testGet() {
		System.out.println("开始测试get");

		Session session = sessionFactory.openSession();

		System.out.println("开始读取Person");
		// Lazy对get有效，当不要求Lazy时，Fecth生效
		Person personFromDb = (Person) session.load(Person.class, id);
		System.out.println("完成读取Person");
		assertFetch(person, personFromDb);

		System.out.println("完成测试get");
	}

	@Test
	public void testQueryUnique() {
		System.out.println("开始测试query unique");

		Session session = sessionFactory.openSession();

		System.out.println("开始读取Person");
		// Lazy对查询唯一的HQL有效，即使不要求Lazy，Fecth也无效
		Person personFromDb = (Person) session.createQuery("from " + Person.class.getName() + " p where p.name = :name")
				.setString("name", PERSON_NAME).uniqueResult();
		System.out.println("完成读取Person");

		assertFetch(person, personFromDb);

		System.out.println("完成测试query unique");
	}

	@Test
	public void testQueryList() {
		Person person = createPerson();
		{
			Transaction tx = session.beginTransaction();
			session.persist(person);
			tx.commit();
		}

		{
			System.out.println("开始测试query list");

			Session session = sessionFactory.openSession();

			System.out.println("开始读取Person");
			@SuppressWarnings("unchecked")
			// Lazy对查询List的HQL有效，即使不要求Lazy，Fecth也无效
			List<Person> personsFromDb = session.createQuery("from " + Person.class.getName() + " p where p.name = :name")
					.setString("name", PERSON_NAME).list();
			System.out.println("完成读取Person");

			int i = 0;
			for (Person personFromDb : personsFromDb) {
				System.out.println("personsFromDb" + i);
				assertFetch(person, personFromDb);
				i++;
			}

			System.out.println("完成测试query list");
		}

		{
			Transaction tx = session.beginTransaction();
			session.merge(person);
			session.delete(person);
			tx.commit();
		}
	}

	@Test
	public void testQbc() {
		System.out.println("开始测试QBC");

		Session session = sessionFactory.openSession();

		System.out.println("开始读取Person");
		// Lazy对查询唯一的QBC有效，当不要求Lazy时，Fecth生效
		Person personFromDb = (Person) session.createCriteria(Person.class).add(Restrictions.eq("name", PERSON_NAME)).uniqueResult();
		System.out.println("完成读取Person");

		assertFetch(person, personFromDb);

		System.out.println("完成测试QBC");
	}

	private void assertFetch(Person person, Person personFromDb) {
		personFromDb.getName();
		System.out.println("对比：" + (personFromDb == person));

		System.out.println("--------------------------------------------------------");
		{
			System.out.println("开始访问MonodirectionalDefaultCar");
			System.out.println("开始获取MonodirectionalDefaultCar");
			MonodirectionalToOneDefaultLazyCar car = personFromDb.getMonodirectionalDefaultCar();
			System.out.println("完成获取MonodirectionalDefaultCar");
			System.out.println(car.getName());
			System.out.println("完成访问MonodirectionalDefaultCar");
		}

		System.out.println("--------------------------------------------------------");
		{
			System.out.println("开始访问MonodirectionalFalseCar");
			System.out.println("开始获取MonodirectionalFalseCar");
			MonodirectionalToOneFalseLazyCar car = personFromDb.getMonodirectionalFalseCar();
			car.print();
			System.out.println("完成获取MonodirectionalFalseCar");
			System.out.println(car.getName());
			System.out.println("完成访问MonodirectionalFalseCar");
		}

		{
			System.out.println("开始访问MonodirectionalProxyCar");
			System.out.println("--------------------------------------------------------");
			System.out.println("开始获取MonodirectionalProxyCar");
			MonodirectionalToOneProxyCar car = personFromDb.getMonodirectionalProxyCar();
			car.print();
			System.out.println("完成获取MonodirectionalProxyCar");
			System.out.println(car.getName());
			System.out.println("完成访问MonodirectionalProxyCar");
		}

		{
			System.out.println("开始访问MonodirectionalNoProxyCar");
			System.out.println("--------------------------------------------------------");
			System.out.println("开始获取MonodirectionalNoProxyCar");
			MonodirectionalToOneNoProxyCar car = personFromDb.getMonodirectionalNoProxyCar();
			car.print();
			System.out.println("完成获取MonodirectionalNoProxyCar");
			System.out.println(car.getName());
			System.out.println("完成访问MonodirectionalNoProxyCar");
		}

		System.out.println("--------------------------------------------------------");
		System.out.println("开始访问DefaultCars");
		System.out.println("开始获取DefaultCars");
		List<Car> defaultCars = personFromDb.getDefaultCars();
		System.out.println("完成获取DefaultCars");
		for (Car car : defaultCars) {
			car.print();
			System.out.println(car.getName());
		}
		System.out.println("完成访问DefaultCars");

		System.out.println("--------------------------------------------------------");
		System.out.println("开始访问FalseCars");
		System.out.println("开始获取FalseCars");
		List<Car> joinFetchCars = personFromDb.getFalseCars();
		System.out.println("完成获取FalseCars");
		for (Car car : joinFetchCars) {
			car.print();
			System.out.println(car.getName());
		}
		System.out.println("完成访问FalseCars");

		System.out.println("--------------------------------------------------------");
		System.out.println("开始访问TrueCars");
		System.out.println("开始获取TrueCars");
		List<Car> selectFetchCars = personFromDb.getTrueCars();
		System.out.println("完成获取TrueCars");
		for (Car car : selectFetchCars) {
			car.print();
			System.out.println(car.getName());
		}
		System.out.println("完成访问TrueCars");

		System.out.println("--------------------------------------------------------");
		System.out.println("开始访问ExtraCars");
		System.out.println("开始获取ExtraCars");
		List<Car> subSelectFetchCars = personFromDb.getExtraCars();
		System.out.println("完成获取ExtraCars");
		for (Car car : subSelectFetchCars) {
			car.print();
			System.out.println(car.getName());
		}
		System.out.println("完成访问ExtraCars");
	}
}
