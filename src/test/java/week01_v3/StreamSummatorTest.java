package week01_v3;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import week01.v3.StreamSummator;
import week01.v3.res.ThreadResource;
import week01_v3.helpers.GetTestFilesHelper;
import week01_v3.helpers.TestConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

// TODO Удалить эту Debug info из релиза
// 681384807344823852990    http://dixi.tk/test_resource2.txt
// 110911656356766547142    C:\Projects\java\Homework01\src\test\resources\v3\tests\test1.txt

public class StreamSummatorTest {
	// логгер
	static private Logger log = LoggerFactory.getLogger(StreamSummatorTest.class);

	// конечный результат для тетсовых файлов
	static private BigInteger testSumResult;

	// список файлов для теста
	static private File[] testFileList;

	/**
	 * Читаю настройки тестов, узнаю ответ и составляю список тетсовых файлов
	 */
	@BeforeClass
	static public void beforeClass() {
		log.info("Запустился класс с тестами StreamSummatorTest");

		log.trace("Собирается список тестов");
		Object[] result = GetTestFilesHelper.get(TestConfig.testFilePath);

		assertNotNull("! Не удалось загрузить тесты", result);
		assertEquals("! Вспомогательный метод вернул не то, что ожидалось",
				result.length, 2);

		testSumResult = (BigInteger) result[0];
		testFileList = (File[]) result[1];
	}

	// переменная будет хранить время начала теста, чтобы считать длительность работы теста
	private long testNanoStart;

	@Before
	public void before() {
		// сброс перед тестом
		ThreadResource.reset();

		testNanoStart = System.nanoTime();
	}

	@After
	public void after() {
		long length = System.nanoTime() - testNanoStart;
		log.info(String.format("Тест выполнился за %f ms\n", length / 1000000.));
	}

	/**
	 * В этом тесте подсовываются готовые тестовые файлы в виде стримов
	 */
	@Test(timeout = 2000)
	public void sumStreamTestNormal() {
		log.info("Запустился тест [sumStreamTest]");

		String s = null;



		// не хочу засорять System.out
		PrintStream oldSystemOut = System.out;

		// поехали, Юра
		try {
			// мокаем System.out
			System.setOut(mock(PrintStream.class));

			for (File testFile : testFileList) {
				try (FileInputStream input = new FileInputStream(testFile)) {
					// тест одного файла
					new StreamSummator().sumStream(input);
				} catch (Exception e) {
					// файлы закрылись, все ок, передаю ошибку выше
					throw e;
				}
			}
		} catch (IOException e) {
			log.error("! Произошла ошибка в работе программы при нормальном тесте", e);
			e.printStackTrace();
			fail(e.getMessage());
		} finally {
			// восстанавливаю System.out
			System.setOut(oldSystemOut);
		}

		// сверяю конечный результат
		assertEquals("! Конечный результат отличается", testSumResult, ThreadResource.get());
	}
}
