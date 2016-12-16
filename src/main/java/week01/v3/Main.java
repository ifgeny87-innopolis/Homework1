package week01.v3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	static long startNanoTime = System.nanoTime();

	static private Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {

		log.info("Программа запустилась и обнаружила " + args.length + " аргументов");

		// программа может принимать путь к файлам или ссылки на веб-русурсы в виде аргументов
		new Main().workWithArguments(args);

		log.info("Программа завершает свою работу");
		log.info(String.format("Программа выполнилась за %.3f ms", (System.nanoTime() - startNanoTime) / 1000000.));
	}

	/**
	 * Метод получает список ссылок, открывает стримы и запускает потоковую обработку.
	 *
	 * В этом методе открываем стримы. Здесь же и будем их закрывать.
	 *
	 * @param sourceList Список ссылок, могут содержать имя файла или URL
	 */
	public void workWithArguments(String[] sourceList) {
		if(sourceList.length == 0) {
			log.trace("Программа не обнаружила ресурсов на входе");
			return;
		}

		log.trace("Программа создает стримы");
		InputStream[] streams = new InputStream[sourceList.length];
		try {
			for (int i = 0; i < sourceList.length; i++) {
				streams[i] = workWithSource(sourceList[i]);
			}

			// запускаю треды в работу
			log.trace("Программа собрала список стримов");
			workWithStreams(streams);

			// жду завершения тредов
			log.trace("Программа переходит в режим ожидания");
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
		log.trace("Программа определяет тип ссылки");
		if (pattern.matcher(sourceLink).find()) {
			log.trace("Это ссылка");
			stream = new URL(sourceLink).openStream();
		} else {
			log.trace("Это локальный файл");
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
		for (InputStream is : isList) {
			workWithStream(is);
			synchronized (ThreadResource.class) {
				threadCounter++;
			}
		}
	}

	/**
	 * Получает стрим и запускает его в работу.
	 *
	 * @param is
	 */
	public void workWithStream(InputStream is) {
		log.trace("Программа запускает новый поток");
		new SumThread(is).start();
	}
}
