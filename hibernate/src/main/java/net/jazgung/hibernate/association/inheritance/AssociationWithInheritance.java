package net.jazgung.hibernate.association.inheritance;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.jazgung.hibernate.AbstractEntity;
import net.jazgung.hibernate.inheritance.joined.JoinedItem;
import net.jazgung.hibernate.inheritance.single_table.SingleTableItem;
import net.jazgung.hibernate.inheritance.table_per_class.TablePerClassItem;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "associtation_joined")
public class AssociationWithInheritance extends AbstractEntity {

	private static final long serialVersionUID = 8450044482571046496L;

	private Long id;

	private JoinedItem joinedItem;

	private SingleTableItem singleTableItem;

	private TablePerClassItem tablePerClassItem;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "joined_item_id", referencedColumnName = "id")
	@Cascade(CascadeType.SAVE_UPDATE)
	public JoinedItem getJoinedItem() {
		return joinedItem;
	}

	public void setJoinedItem(JoinedItem joinedItem) {
		this.joinedItem = joinedItem;
	}

	@ManyToOne
	@JoinColumn(name = "sing_teTable_item_id", referencedColumnName = "id")
	@Cascade(CascadeType.SAVE_UPDATE)
	public SingleTableItem getSingleTableItem() {
		return singleTableItem;
	}

	public void setSingleTableItem(SingleTableItem singleTableItem) {
		this.singleTableItem = singleTableItem;
	}

	@ManyToOne
	@JoinColumn(name = "table_per_class_item_id", referencedColumnName = "id")
	@Cascade(CascadeType.SAVE_UPDATE)
	public TablePerClassItem getTablePerClassItem() {
		return tablePerClassItem;
	}

	public void setTablePerClassItem(TablePerClassItem tablePerClassItem) {
		this.tablePerClassItem = tablePerClassItem;
	}

}
