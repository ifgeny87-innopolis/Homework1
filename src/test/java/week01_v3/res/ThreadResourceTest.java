package week01_v3.res;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import week01.v3.res.ThreadResource;

import java.math.BigInteger;

import static org.junit.Assert.*;

/**
 * Проверка класса StreamSummator
 */
public class ThreadResourceTest {
	// логгер
	static private Logger log = LoggerFactory.getLogger(ThreadResourceTest.class);

	@Before
	public void before() {
		ThreadResource.reset();
	}

	/**
	 * Проверяю, правильно ли работает сумматор
	 */
	@Test
	public void addAndGetTest() {
		log.info("Запустился тест [ThreadResourceTest.addAndGetTest]");

		ThreadResource.add(BigInteger.valueOf(2L * Integer.MAX_VALUE));
		ThreadResource.add(BigInteger.valueOf(705032706L));

		assertEquals("! Счетчик суммирует неверно",
				"" + 5_000_000_000L,
				ThreadResource.get().toString());
	}
}
