package week01.v3;

import week01.v3.res.ThreadResource;

import java.io.InputStream;

/**
 * Класс потока
 */
public class SumThread extends Thread {

	private InputStream stream;

	SumThread(InputStream is) {
		this.stream = is;
	}

	@Override
	public void run() {
		new StreamSummator().sumStream(stream);

		// уменьшаю счетчик потоков
		ThreadResource.threadCounter--;
	}
}
