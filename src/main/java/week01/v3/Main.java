package week01.v3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.regex.Pattern;

import static week01.v3.res.ThreadResource.threadCounter;

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

	static private long startNanoTime = System.nanoTime();

	static private Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {

		log.info("Программа запустилась и обнаружила " + args.length + " аргументов");

		// программа может принимать путь к файлам или ссылки на веб-русурсы в виде аргументов
		Main main = new Main();
		main.workWithArguments(args);

		while(threadCounter > 0) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				log.error("! Сон программы прерван", e);
				break;
			}
		}

		log.info("Программа завершает свою работу");
		log.info(String.format("Программа выполнилась за %.3f ms", (System.nanoTime() - startNanoTime) / 1000000.));
	}

	// стримы, открытые для работы
	private InputStream[] inputStreams;

	/**
	 * При удалении объекта закрываем стримы.
	 * В рабочих потоках SumThread тоже стримы закрываются,
	 * но лучше перебдеть, тем более двойной close() работает без ошибок.
	 *
	 * @throws Throwable
	 */
	@Override
	protected void finalize() throws Throwable {
		if (inputStreams != null)
			for (InputStream inputStream : inputStreams) {
				try {
					// необходимо постараться закрыть все стримы, поэтому
					// программа не должна вылететь если с ошибкой закроется один из них
					inputStream.close();
				} catch (IOException e) {
					log.error("! Не удалось закрыть стрим", e);
				}
			}

		super.finalize();
	}

	/**
	 * Метод получает список ссылок, открывает стримы и запускает потоковую обработку.
	 *
	 * В этом методе открываем стримы. Здесь же и будем их закрывать.
	 *
	 * @param sourceList Список ссылок, могут содержать имя файла или URL
	 */
	public void workWithArguments(String[] sourceList) {
		if (sourceList.length == 0) {
			log.trace("Программа не обнаружила ресурсов на входе");
			return;
		}

		log.trace("Программа создает стримы");
		inputStreams = new InputStream[sourceList.length];
		try {
			for (int i = 0; i < sourceList.length; i++) {
				inputStreams[i] = workWithSource(sourceList[i]);
			}

			// запускаю треды в работу
			log.trace("Программа собрала список стримов");
			workWithStreams(inputStreams);

			// жду завершения тредов
			log.trace("Программа переходит в режим ожидания");
		} catch (IOException e) {
			log.error("! Программа выявила критическую ошибку чтения ресурса", e);
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
		threadCounter = isList.length;
		for (InputStream is : isList) {
			workWithStream(is);
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
