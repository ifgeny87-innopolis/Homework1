package week01.v3.validators;

/**
 * Проверяет, можно ли преобразовать объект к Integer
 */
public class IntValidator implements Validator {
	@Override
	public boolean validate(Object o) {
		if (o instanceof String) {
			try {
				Integer.parseInt((String) o);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return (o instanceof Integer || o instanceof Character || o instanceof Byte);
		}
	}
}
