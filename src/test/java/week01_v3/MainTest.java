package week01_v3;

import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import week01.v3.StreamSummator;
import week01.v3.res.ThreadResource;
import week01_v3.helpers.GetTestFilesHelper;

import java.io.*;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
