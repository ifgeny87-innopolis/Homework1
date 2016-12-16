package week01_v3.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

import static org.junit.Assert.assertTrue;

/**
 * Вспомогательный класс для проведения тестирования
 */
public class GetTestFilesHelper {
	// логгер
	static private Logger log = LoggerFactory.getLogger(GetTestFilesHelper.class);

	/**
	 * Составляет список тестов в указанной папке и возвращает массив из двух переменных:
	 * 1. сумма чисел по задаче для всех тестов
	 * 2. список File объектов с указанием на тестовый файл
	 *
	 * @param path Путь к папке с тестами
	 * @return Вернет null если ошибка
	 */
	static public Object[] get(String path) {
		// читаю настройки тестов в файле result.txt
		int testFileCount;
		BigInteger sum;
		File[] testFileList;

		log.trace("Читаются настройки тестов");

		try (Scanner scanner = new Scanner(new File(path + "/result.txt"))) {
			testFileCount = scanner.nextInt();
			sum = new BigInteger(scanner.next());
		} catch (IOException e) {
			log.error("! Не получилось прочитать настройки тестов", e);
			e.printStackTrace();
			return null;
		}

		// проверяю количество тестовых файлов в настройках
		assertTrue("! Тестовые файлы не обнаружены", testFileCount > 0);

		log.trace("Проверяется наличие файлов для тестов");

		testFileList = new File[testFileCount];

		// составляю список текстовых файлов
		for (int i = 1; i <= testFileCount; i++) {
			File input = new File(path + "/test" + i + ".txt");
			if (!input.canRead()) break;
			testFileList[i - 1] = input;
		}

		// нужно вернуть две переменные:
		// - сумма по всем тестам
		// - список файлов для тестов
		return new Object[]{sum, testFileList};
	}
}
