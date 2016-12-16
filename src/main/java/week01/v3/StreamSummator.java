package week01.v3;

import week01.v3.res.ThreadResource;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StreamSummator {
	/**
	 * Берет стрим, читает из него числа и суммирует только четные положительные из них.
	 * Возвращает результатом сумму этих чисел.
	 *
	 * @param is Поток с данными
	 */
	public void sumStream(InputStream is) {
		Scanner scanner = new Scanner(is);

		// читаю все числа со стрима
		while (scanner.hasNext() && ThreadResource.normalWork) {
			long val;

			// пробую прочитать и привести к LONG следующее число
			try {
				val = scanner.nextLong();
			} catch (InputMismatchException e) {
				// исключение, сворачиваем лавочку
				System.out.println("! При чтении очередного числа ошибка произошла");
				e.printStackTrace();
				synchronized (ThreadResource.class) {
					ThreadResource.normalWork = false;
				}
				return;
			}

			if (val > 0 && (val % 2 == 0)) {
				// нашел число, которое нужно добавить к сумме
				synchronized (ThreadResource.class) {
					ThreadResource.add(BigInteger.valueOf(val));
					System.out.println(ThreadResource.get().toString());
				}
			}
		}
	}
}
