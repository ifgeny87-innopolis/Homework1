package week01.v3.validators;

/**
 * Проверяет, можно ли преобразовать объект к Integer
 */
public class LongValidator implements Validator {
	@Override
	public boolean validate(Object o) {
		if (o instanceof String) {
			try {
				Long.parseLong((String) o);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return (o instanceof Long || o instanceof Integer
					|| o instanceof Character || o instanceof Byte);
		}
	}
}
