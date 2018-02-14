package net.jazgung.hibernate.qbc;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.jazgung.hibernate.AbstractEntity;

@Entity(name = "qbc_Room")
@Table(name = "qbc_room")
public class Room extends AbstractEntity {

	private static final long serialVersionUID = 4053282505113827891L;

	private Long id;

	private String name;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
