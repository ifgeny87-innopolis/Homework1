package week01.v3.tools;

import java.io.*;
import java.math.BigInteger;
import java.util.Random;

/**
 * Приложение создает кучу тестов для тестирования домашки
 */
public class TestCreator {
	static private Random random = new Random();

	// где будут лежать файлы тестов
	static final private String testFilePath = "src/test/resources/v3/tests";

	// сколько тестов будем создавать
	static final private int testCount = 20;

	public static void main(String[] args) {
		// результат
		BigInteger result = BigInteger.ZERO;

		// пишем файлы с тестами, запоминаем сумму
		for (int i = 1; i <= testCount; i++) {
			try (OutputStream os = new FileOutputStream(testFilePath + "/test" + i + ".txt")) {
				// создаем файл теста
				BigInteger sum = createTest(os, (i + 1) * 50);

				// запоминаем общую сумму
				result = result.add(sum);
			} catch (IOException e) {
				System.out.println("! При создании тестов произошла ошибка");
				e.printStackTrace();
				return;
			}
		}

		// пишем файл с результатом
		try (OutputStream os = new FileOutputStream(testFilePath + "/result.txt")) {
			// запишем сколько тестов и какой результат суммы
			os.write(("" + testCount + "\n" + result).getBytes());
		} catch (IOException e) {
			System.out.println("! При создании файла результата произошла ошибка");
			e.printStackTrace();
		}
	}

	/**
	 * Создает отдельный тестовый файл
	 *
	 * @param os    Стрим для сохранения теста
	 * @param count Количество чисел для теста
	 * @return Вернет сумму четных положительных из теста
	 * @throws IOException Во время записи в файл может вылететь исключение, перехватим на верху
	 */
	static private BigInteger createTest(OutputStream os, int count) throws IOException {
		BigInteger sum = BigInteger.valueOf(0);

		for (int i = 0; i < count; i++) {
			long val = random.nextLong();
			if (val > 0 && val % 2 == 0) {
				sum = sum.add(BigInteger.valueOf(val));
			}
			if (i != 0)
				os.write(' ');
			os.write(("" + val).getBytes());
		}

		return sum;
	}
}
