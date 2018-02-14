package net.jazgung.hibernate.session;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.jazgung.hibernate.AbstractEntity;

@Entity(name = "session_person")
@Table(name = "session_person")
public class Person extends AbstractEntity {

	private static final long serialVersionUID = 6316464026858315652L;

	private Long id;

	private String name;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public Long getId() {
		System.out.println("getId, id: " + id);
		return id;
	}

	public void setId(Long id) {
		System.out.println("setId, id: " + id);
		this.id = id;
	}

	public String getName() {
		System.out.println("getName, name: " + name);
		return name;
	}

	public void setName(String name) {
		System.out.println("setName, name: " + name);
		this.name = name;
	}
}
