package week01.v3;

import week01.v3.res.ThreadResource;

import java.io.InputStream;

import static week01.v3.res.ThreadResource.waiter;

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
		synchronized (ThreadResource.class) {
			ThreadResource.threadCounter--;
		}

		synchronized (waiter) {
			waiter.notify();
		}
	}
}
