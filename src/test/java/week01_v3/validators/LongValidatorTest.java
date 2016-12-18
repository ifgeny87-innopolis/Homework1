package week01_v3.validators;

import org.junit.Test;
import week01.v3.validators.LongValidator;
import week01.v3.validators.Validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LongValidatorTest {
	@Test
	public void test01() {
		Validator v = new LongValidator();

		// trues
		assertTrue(v.validate("123456789"));
		assertTrue(v.validate(new Byte((byte)132)));
		assertTrue(v.validate((byte)123));
		assertTrue(v.validate(new Character('x')));
		assertTrue(v.validate('x'));
		assertTrue(v.validate(new Integer(123456789)));
		assertTrue(v.validate(123456789));
		assertTrue(v.validate(new Long(123456789123456789L)));
		assertTrue(v.validate(123456789123456789L));

		// falses
		assertFalse(v.validate("123."));
		assertFalse(v.validate("foo"));
		assertFalse(v.validate(true));
		assertFalse(v.validate(123.));
		assertFalse(v.validate(123f));
		assertFalse(v.validate(new Object()));
		assertFalse(v.validate(new int[] {1}));
	}
}
