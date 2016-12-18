package week01.v3;

import week01.v3.res.ThreadResource;
import week01.v3.streams.LongReader;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

public class StreamSummator {
	/**
	 * Берет стрим, читает из него числа и суммирует только четные положительные из них.
	 * Возвращает результатом сумму этих чисел.
	 *
	 * @param is Поток с данными
	 */
	public void sumStream(InputStream is) throws IOException {
		LongReader reader = new LongReader(is);

		// читаю числа по словам и работаю с ними
		Long value;
		while (ThreadResource.normalWork && (value = reader.nextLong()) != null) {
			if (value > 0 && (value % 2 == 0)) {
				// нашел нужное число
				// использую локи чтобы выводить в консоль сумму по возрастанию
				try {
					ThreadResource.locker.lock();

					ThreadResource.add(BigInteger.valueOf(value));
//					System.out.println(ThreadResource.get().toString());
				}
				finally {
					ThreadResource.locker.unlock();
				}
			}
		}
	}
}
