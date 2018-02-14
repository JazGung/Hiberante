package net.jazgung.hibernate.user_type.class_enum_type;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import org.hibernate.util.ReflectHelper;

public class PersistentableEnumType implements UserType, ParameterizedType {

	public static final String NAME = "net.jazgung.hibernate.user_type.class_enum_type.PersistentableEnumType";

	public static final String ENUM = "enumClass";

	private int sqlType;

	private static final Map<Class<?>, Integer> sqlTypes = new HashMap<Class<?>, Integer>();

	static {
		sqlTypes.put(String.class, Types.VARCHAR);
		sqlTypes.put(Integer.class, Types.INTEGER);
	}

	private Class<? extends PersistentableEnum<?>> enumClass;

	@Override
	public int[] sqlTypes() {
		return new int[] { sqlType };
	}

	@Override
	public Class<? extends PersistentableEnum<?>> returnedClass() {
		return enumClass;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		if (null != x && null != y) {
			return x.equals(y);
		} else {
			return x == y;
		}
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		return x == null ? 0 : x.hashCode();
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
		Object object = rs.getObject(names[0]);

		if (rs.wasNull()) {
			return null;
		}

		return PersistentableEnum.valueOf(object, returnedClass());
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
		if (null == value) {
			st.setNull(index, sqlType);
		} else {
			st.setObject(index, ((PersistentableEnum<?>) value).getValue(), sqlType);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setParameterValues(Properties parameters) {
		String enumClassName = parameters.getProperty(ENUM);
		try {
			enumClass = ReflectHelper.classForName(enumClassName, this.getClass()).asSubclass(PersistentableEnum.class);
			calcSqlType(enumClass);
		} catch (ClassNotFoundException exception) {
			throw new HibernateException("Enum class not found", exception);
		}
	}

	@SuppressWarnings("unchecked")
	private void calcSqlType(Class<? extends PersistentableEnum<?>> genericClass) {
		while (!(genericClass.getGenericSuperclass() instanceof java.lang.reflect.ParameterizedType)
				&& genericClass != PersistentableEnum.class) {
			genericClass = (Class<? extends PersistentableEnum<?>>) genericClass.getSuperclass();
		}
		Type genericType = genericClass.getGenericSuperclass();
		if (!(genericType instanceof java.lang.reflect.ParameterizedType)) {
			throw new RuntimeException(genericClass.getName() + "未对持久化值的泛型类型进行实例化");
		}

		Type[] params = ((java.lang.reflect.ParameterizedType) genericType).getActualTypeArguments();
		sqlType = sqlTypes.get((Class<?>) params[0]);
	}
}
