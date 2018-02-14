package net.jazgung.hibernate.user_type.class_enum_type;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class PersistentableEnum<T> {

	private boolean finalized = true;

	private Object value;

	private static Map<Class<? extends PersistentableEnum<?>>, Map<String, PersistentableEnum<?>>> values = new HashMap<Class<? extends PersistentableEnum<?>>, Map<String, PersistentableEnum<?>>>();

	private static Map<Class<? extends PersistentableEnum<?>>, Class<? extends PersistentableEnum<?>>> types = new HashMap<Class<? extends PersistentableEnum<?>>, Class<? extends PersistentableEnum<?>>>();

	protected PersistentableEnum() {

	}

	@SuppressWarnings("unchecked")
	protected PersistentableEnum(T value) {
		this.value = value;

		Class<? extends PersistentableEnum<?>> clazz = (Class<? extends PersistentableEnum<?>>) this.getClass();
		while (PersistentableEnum.class != clazz.getSuperclass()) {
			clazz = (Class<? extends PersistentableEnum<?>>) clazz.getSuperclass();
		}
		System.out.println(MessageFormat.format("找到{0}的注册分类{1}", this.getClass(), clazz.getName()));
		checkAndRegister(clazz, this);
	}

	@SuppressWarnings("unused")
	private PersistentableEnum(T value, Class<? extends PersistentableEnum<?>> expertedClassifier) {
		this.value = value;
		checkAndRegister(expertedClassifier, this);
	}

	@SuppressWarnings("unchecked")
	public static void checkAndRegister(Class<? extends PersistentableEnum<?>> expertedType, PersistentableEnum<?> persistentableEnum) {
		// 检查
		if (null == persistentableEnum) {
			throw new RuntimeException("持久化对象不能null");
		}

		if (null == persistentableEnum.value) {
			throw new RuntimeException("持久化值不能为null");
		}

		String value = persistentableEnum.value.toString();

		if (null == value) {
			throw new RuntimeException("持久化值不能为null");
		}

		if (null == expertedType) {
			throw new RuntimeException(persistentableEnum.toString() + "的持久化类型不能为null");
		}

		Class<? extends PersistentableEnum<?>> registeredType = types.get(persistentableEnum.getClass());
		if (null == registeredType) {
			types.put((Class<? extends PersistentableEnum<?>>) persistentableEnum.getClass(), expertedType);// 将当前对象的类于持久化值注册的持久化类型进行关联
		} else if (registeredType != expertedType) {
			throw new RuntimeException("持久化对象" + persistentableEnum.toString() + "要求注册到的持久化类型" + expertedType.getName()
					+ "与当前对象所属类其他持久化值已注册的持久化类型" + registeredType.getName() + "不同");
		}

		try {
			Constructor<? extends PersistentableEnum<?>> constructor = (Constructor<? extends PersistentableEnum<?>>) persistentableEnum
					.getClass().getDeclaredConstructor();
			if (!Modifier.isPrivate(constructor.getModifiers())) {
				throw new RuntimeException("在" + persistentableEnum.getClass().getName() + "中必须有私有的无参数构造方法");
			}
		} catch (Throwable e) {
			throw new RuntimeException("在" + persistentableEnum.getClass().getName() + "中检查无参数构造方法不通过", e);
		}

		// 注册
		Map<String, PersistentableEnum<?>> valueAndEnumValues = values.get(expertedType);
		if (null == valueAndEnumValues) {
			valueAndEnumValues = new HashMap<String, PersistentableEnum<?>>();
			values.put(expertedType, valueAndEnumValues);
		}

		PersistentableEnum<?> enumValue = valueAndEnumValues.get(value);
		if (null != enumValue) {
			throw new RuntimeException("持久化值重复，在分类" + expertedType.getName() + "中有两个枚举对象的持久化值都为" + value);
		}
		System.out.println(MessageFormat.format("将持久化值{0}与对象{1}中注册到类{2}", value, persistentableEnum.toString(), expertedType.getName()));
		valueAndEnumValues.put(value, persistentableEnum);
	}

	@SuppressWarnings("unchecked")
	public T getValue() {
		return (T) value;
	}

	public static PersistentableEnum<?> valueOf(Object value, Class<? extends PersistentableEnum<?>> returnClass) {
		if (null == returnClass) {
			throw new RuntimeException("返回类型不能为null");
		}

		if (null == value) {
			throw new RuntimeException("value不能为null");
		}

		String valueText = value.toString();
		if (null == valueText) {
			throw new RuntimeException("持久化值不能为null");
		}

		try {
			Map<String, PersistentableEnum<?>> valueAndEnumValues = values.get(types.get(returnClass));
			PersistentableEnum<?> enumValue = null;
			if (null != valueAndEnumValues) {
				enumValue = valueAndEnumValues.get(valueText);
			}

			if (null == enumValue) {
				System.out.println(MessageFormat.format("持久化值{0}没有找到常量化枚举对象，创建返回类型{1}的非常量化枚举对象", valueText, returnClass.getName()));
				return getUnfinalizedObject(value, returnClass);
			} else if (!returnClass.isAssignableFrom(enumValue.getClass())) {
				System.out.println(MessageFormat.format("持久化值{0}找到的常量化枚举对象类型{1}与返回类型{2}不一致且不是其子类的对象，创建返回类型的非常量化枚举对象", valueText,
						enumValue.getClass().getName(), returnClass.getName()));
				return getUnfinalizedObject(value, returnClass);
			} else {
				System.out.println(MessageFormat.format("持久化值{0}找到常量化枚举对象{1}与返回类型{2}一致或是其子类的对象", valueText, enumValue.toString(),
						returnClass.getName()));
				return enumValue;
			}
		} catch (Throwable e) {
			throw new RuntimeException(MessageFormat.format("无法在{0}找到持久化值为{1}的枚举对象", returnClass.getName(), valueText), e);
		}
	}

	private static PersistentableEnum<?> getUnfinalizedObject(Object value, Class<? extends PersistentableEnum<?>> clazz)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Constructor<? extends PersistentableEnum<?>> constructor = clazz.getDeclaredConstructor();
		constructor.setAccessible(true);
		PersistentableEnum<?> unfinalizedObject = constructor.newInstance();
		constructor.setAccessible(false);
		System.out.println(MessageFormat.format("将持久化值{0}复制给未常量化的枚举对象", value));
		unfinalizedObject.value = value;
		unfinalizedObject.finalized = false;
		return unfinalizedObject;
	}

	@Override
	public boolean equals(Object obj) {
		if (null != obj && null != types.get(this.getClass()) && types.get(this.getClass()) == types.get(obj.getClass())) {
			return value.toString().equals(((PersistentableEnum<?>) obj).value.toString());
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return new StringBuffer().append(getClass().getName()).append('#').append(value).append('!').append(finalized ? "常量化" : "非常量化")
				.append('@').append(Integer.toHexString(hashCode())).toString();
	}
}
