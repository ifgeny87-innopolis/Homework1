package week01.v3.res;

import java.math.BigInteger;

/**
 * Класс, отвечающий за синхронизацию ресурсов между потоками
 */
public class ThreadResource {

	/** public **/

	// сколько потоков в работе
	static public Integer threadCounter;

	// флаг нормальной работы приложения
	static public Boolean normalWork;

	/** private **/

	// счетчик суммы
	static volatile private BigInteger value;

	static {
		reset();
	}

	/**
	 * Выполняет сброс для очередного запуска выполнения работы
	 */
	static public void reset() {
		threadCounter = 0;
		normalWork = true;
		value = BigInteger.ZERO;
	}

	/**
	 * @param value Инкремент к общей сумме
	 */
	static public void add(BigInteger value) {
		ThreadResource.value = ThreadResource.value.add(value);
	}

	/**
	 * @return Возвращает общую сумму
	 */
	static public BigInteger get() {
		return value;
	}
}
