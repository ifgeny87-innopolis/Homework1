package week01.v3;

import week01.v3.res.ThreadResource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.regex.Pattern;

import static week01.v3.res.ThreadResource.*;

/**
 * Вариант 3
 *
 * Необходимо разработать программу, которая получает на вход список ресурсов, содержащих набор чисел и считает сууму всех положительных четных.
 * Каждый ресурс должен быть обработан в отдельном потоке.
 * Набор должен содержать лишь числа, унарный оператор "-" и пробелы.
 * Общая сумма должна отображаться на экране и изменяться в режиме реального времени. Все ошибки должны быть корректно обработаны, все API покрыто модульными тестами.
 *
 * Дополнительные условия:
 * - ресурсы могут содержать ошибки
 * - ресурсы могут не существовать
 * - в случае исключения программа должна завершить свою работу без ошибок, выдав предупреждение
 */
public class Main {
	public static void main(String[] args) {
		// программа может принимать путь к файлам или ссылки на веб-русурсы в виде аргументов
		new Main().workWithArguments(args);
		//http://dixi.tk/test_resource2_expected_result.txt
		//http://dixi.tk/test_resource2.txt
		//"C:\\Projects\\java\\Homework01\\src\\test\\java\\week01\\v3\\input\\0.txt"
	}

	/**
	 * Метод получает список ссылок, открывает стримы и запускает потоковую обработку.
	 *
	 * В этом методе открываем стримы. Здесь же и будем их закрывать.
	 *
	 * @param sourceList Список ссылок, могут содержать имя файла или URL
	 */
	public void workWithArguments(String[] sourceList) {
		InputStream[] streams = new InputStream[sourceList.length];
		try {
			for (int i = 0; i < sourceList.length; i++) {
				streams[i] = workWithSource(sourceList[i]);
			}

			// запускаю треды в работу
			workWithStreams(streams);

			// жду завершения тредов
			while (threadCounter > 0)
				synchronized (waiter) {
					try {
						waiter.wait(100);
					} catch (InterruptedException e) {
					}
				}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// закрываю стримы
			for (InputStream stream : streams) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// регуляркой буду проверяь, относится ли ссылка к веб-ссылке
	Pattern pattern = Pattern.compile("^http://");

	/**
	 * Метод получает ссылку и возвращает открытый стрим.
	 * По ссылке различает только веб-ссылки, остальные читает как файлы.
	 *
	 * @param sourceLink
	 * @return
	 * @throws IOException
	 */
	public InputStream workWithSource(String sourceLink) throws IOException {
		InputStream stream;
		if (pattern.matcher(sourceLink).find()) {
			stream = new URL(sourceLink).openStream();
		} else {
			stream = new FileInputStream(sourceLink);
		}
		return stream;
	}

	/**
	 * Получает список стримов и запускает их в работу.
	 *
	 * @param isList
	 */

	public void workWithStreams(InputStream[] isList) {
		for (InputStream is : isList)
			workWithStream(is);
	}

	/**
	 * Получает стрим и запускает его в работу.
	 *
	 * @param is
	 */
	public void workWithStream(InputStream is) {
		new SumThread(is).start();
	}
}
