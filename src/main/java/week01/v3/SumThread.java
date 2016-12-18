package week01.v3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import week01.v3.res.ThreadResource;

import java.io.IOException;
import java.io.InputStream;

/**
 * Класс потока
 */
public class SumThread extends Thread {
	// логгер
	static private Logger log = LoggerFactory.getLogger(SumThread.class);

	// рабочий стрим
	private InputStream stream;

	SumThread(InputStream is) {
		this.stream = is;
	}

	@Override
	public void run() {
		try {
			new StreamSummator().sumStream(stream);
		} catch (IOException | NumberFormatException e) {
			// исключение, сворачиваем лавочку
			log.error("! Во время работы с ресурсом произошла критическая ошибка", e);
			ThreadResource.normalWork = false;
		} finally {
			// уменьшаю счетчик потоков
			ThreadResource.threadCounter--;
		}
	}
}
