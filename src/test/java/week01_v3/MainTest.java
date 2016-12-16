package week01_v3;

import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import week01.v3.StreamSummator;
import week01.v3.res.ThreadResource;

import java.io.*;
import java.math.BigInteger;
import java.util.Scanner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * Тест программы
 *
 * Задача программы: (Вариант 3)
 *
 * Необходимо разработать программу, которая получает на вход список ресурсов, содержащих набор чисел и считает сууму всех положительных четных.
 * Каждый ресурс должен быть обработан в отдельном потоке.
 * Набор должен содержать лишь числа, унарный оператор "-" и пробелы.
 * Общая сумма должна отображаться на экране и изменяться в режиме реального времени. Все ошибки должны быть корректно обработаны, все API покрыто модульными тестами
 *
 * Как работает тест
 *
 * Тест знает пути к файлам с тестами
 */
public final class MainTest {
	// логгер
	static private Logger log = LoggerFactory.getLogger(MainTest.class);

	// где будут лежать файлы тестов
	static private String testFilePath = "src/test/resources/v3/tests";

	// список файлов для теста
	static File[] testFileList;

	// конечный результат для тетсовых файлов
	static BigInteger testSumResult = BigInteger.ZERO;

	/*PrintStream fakeSystemOut = new PrintStream(new OutputStream() {
		@Override
		public void write(int b) throws IOException {
			// ничего не делаем
		}
	});*/

	/**
	 * Читаю натсройки тестов, узнаю ответ и составляю список тетсовых файлов
	 */
	@BeforeClass
	static public void beforeClass() {
		// читаю настройки тестов в файле result.txt
		int testFileCount = 0;

		log.info("Читаю настройки тестов");

		try (Scanner scanner = new Scanner(new File(testFilePath + "/result.txt"))) {
			testFileCount = scanner.nextInt();
			testSumResult = new BigInteger(scanner.next());
		} catch (IOException e) {
			log.error("! Не получилось прочитать настройки тестов", e);
			e.printStackTrace();
			return;
		}

		// проверяю количество тестовых файлов в настройках
		assertTrue("! Тестовые файлы не обнаружены", testFileCount > 0);

		log.info("Проверяю наличие файлов для тестов");

		testFileList = new File[testFileCount];

		// составляю список текстовых файлов
		for (int i = 1; i <= testFileCount; i++) {
			File input = new File(testFilePath + "/test" + i + ".txt");
			if (!input.canRead()) break;
			testFileList[i - 1] = input;
		}
	}

	// переменная будет хранить время начала теста,
	// чтобы считать длительность работы теста
	long testNanoStart;

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
	 * Тест для метода StreamSummator.sumStream()
	 * В этом тесте подсовываются готовые тестовые файлы в виде стримов
	 * Конечный результат суммы будет записан
	 */
	@Test(timeout = 2000)
	public void streamSummatorSumStreamTest() {
		log.info("Запустился тест [MainTest.streamSummatorSumStreamTest]");

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

	/**
	 * Тест для метода StreamSummator.sumStream()
	 * В этом тесте подсовываются готовые тестовые файлы в виде стримов
	 * Конечный результат суммы будет записан
	 */
	@Test(timeout = 2000)
	public void streamSummatorSumStreamTest2() {
		log.info("Запустился тест [MainTest.streamSummatorSumStreamTest2]");

		// не хочу засорять System.out
		PrintStream oldSystemOut = System.out;

		// поехали, Юра
		try {
			// мокаем System.out
			System.setOut(mock(PrintStream.class));

			InputStream stream = mock(InputStream.class);
			when(stream.read()).thenReturn(49, 20, 65, 66, 67, 20, -1); // "1 ABC "
			new StreamSummator().sumStream(stream);

			log.info("RES = " + ThreadResource.get());
			fail("! Ошибка чтения не вылетела. Программа забила на ошибку чтоли?");
		} catch (IOException e) {
			// ошибка ожидается, ничего не делаю
		} finally {
			System.setOut(oldSystemOut);
		}
	}

	@Test(timeout = 1000)
	public void testMultiThread() {
		/*System.out.println("# Старт теста testMultiThread");

		// счетчик суммы для всех тестов
		BigInteger needResult = BigInteger.ZERO;

		// стрим лист
		FileInputStream[] inputStreams = new FileInputStream[testFileList.length];

		// буду запоминать System.out
		PrintStream systemOut = System.out;

		// рабочая программа
		Main main = new Main();

		// поехали, Юра
		try {
			System.setOut(fakeSystemOut);

			// открываю стримы файлов
			for (int i = 0; i < testFileList.length; i++) {
				try (FileInputStream output = new FileInputStream(outputPath + testFileList[i]);
				     Scanner scanFos = new Scanner(output)) {

					// суммирую общий показатель резальтутов
					needResult = needResult.add(new BigInteger(scanFos.next()));

					// открываю стрим для каждого теста
					inputStreams[i] = new FileInputStream(inputPath + testFileList[i]);
				} catch (IOException e) {
					// стрим файла закроется, передаю дальше
					throw e;
				}
			}

			// стримы открыты, старт
			main.workWithStreams(inputStreams);

			// работаю пока не дождусь нужную сумму
			// ну или пока тест не вылетит по таймауту
			while (true) {
				synchronized (main.summator) {
					main.summator.wait(20);
				}
				if (main.summator.get().equals(needResult))
					break;
			}

			// сумма совпала тест прошел!
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		} finally {
			// обязательно возвращаю System.out
			System.setOut(systemOut);

			// закрываю стримы на файлы тестов
			for (FileInputStream input : inputStreams) {
				if (input != null)
					try {
						input.close();
					} catch (IOException e) {
						// хреново, конечно, что это случилось, просто логирую
						System.out.println("Ну вообще редкая ошибка случилась");
						e.printStackTrace();
					}
			}
		}

		assertTrue(String.format("Тест testMultiThread не прошел - %s не равно %s",
				needResult, main.summator.get()),
				needResult.equals(main.summator.get()));*/
	}
}
