package net.jazgung.hibernate;

import java.io.Serializable;

/**
 * 对象父类，实现Serializable接口 重写equals和hashCode方法：根据id来判断对象的相等 子类必须有id的属性
 * 
 * @author peak
 */
public abstract class AbstractEntity implements Serializable {

	private static final long serialVersionUID = 5674301313143471084L;

	public abstract Object getId();

	@Override
	public boolean equals(Object object) {
		if (object == null || (!(object instanceof AbstractEntity) && (this.getClass().getName().equals(object.getClass().getName()))))
			return false;
		AbstractEntity other = (AbstractEntity) object;
		return getId().equals(other.getId());
	}

	@Override
	public int hashCode() {
		if (null == getId()) {
			return super.hashCode();
		}
		return getId().hashCode();
	}
}
