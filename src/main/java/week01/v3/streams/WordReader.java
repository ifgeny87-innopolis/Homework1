package week01.v3.streams;

import java.io.*;
import java.util.*;

/**
 * Класс WordReader позволяет считывать InputStream по словам, используя разделители.
 *
 * На вход получает InputStream;
 * При переборе байт из InputStream в качестве разделителей слов использует табуляцию (0x8),
 * символ перевода строки (0xA, 0xD), символ пробела (0x20) и окончание стрима;
 * Читаемое слово не должно превышать MEM_SIZE (по умолчанию 8 КиБ или 8192 байт);
 * Алгоритм запоминает состояние буфера и позицию каретки между вызовами метода findNext;
 * Алгоритм обрабатывает двойные разделители и не вернет пустого слова если это не конец стрима;
 * Например, для набора байт [72, 101, 108, 108, 111, 32, 32, 75, 105, 116, 116, 121] алгоритм
 * вернет два слова [72, 101, 108, 108, 111] и [75, 105, 116, 116, 121].
 */
public class WordReader implements Closeable {
	// размер буфера по умолчанию
	static final private int BUF_SIZE = 0x200;

	// размер буфера хранения строки
	static final private int MEM_SIZE = 0x200;

	// рабочий стрим
	private InputStream inputStream;

	/**
	 * Конструктор запоминает переданный стрим
	 *
	 * @param inputStream Стрим на вход
	 */
	public WordReader(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	// буффер для чтения из ридера
	private byte[] buffer = new byte[BUF_SIZE];

	// сколько было считано в буфер в последний раз
	private int len;

	// текущая позиция в буфере
	private int pos;

	// буфер для хранения строки
	protected byte[] mem = new byte[MEM_SIZE];

	// на сколько забит буфер строки
	protected int memLen;

	/**
	 * Прогоняет считанный буфер (читает при необходимости из стрима)
	 * и ищет символ разрыва (таб, перевод строки или пробел, либо конец стрима)
	 *
	 * @throws IOException возможна ошибка чтения стрима
	 */
	protected void findNext() throws IOException {
		memLen = 0;

		while (true) {
			if (pos >= len) {
				// если еще не начинали читать или дошли до конца буфера
				// то читаем поток
				if ((len = inputStream.read(buffer)) <= 0) {
					// если читать нечего, завершаем цикл
					break;
				}

				// прочитан новый буфер, поэтому переход в начало
				pos = 0;
			}

			while (pos < len) {
				byte b = buffer[pos++];

				// исключаю символы-разделители
				if (b == 0x8 || b == 0xA || b == 0xD || b == 0x20) {
					// найден разделитель
					if (memLen != 0) {
						return;
					}
				} else {
					// запоминаю считанный байт в буфере памяти
					mem[memLen++] = b;
				}
			}
		}
	}

	/**
	 * Выполняет поиск очередного слова, вернет буфер этого слова
	 *
	 * @return байты слова до разделителя
	 * @throws IOException возможна ошибка чтения стрима
	 */
	public byte[] next() throws IOException {
		findNext();

		if (memLen == 0) {
			return new byte[0];
		} else {
			return Arrays.copyOfRange(mem, 0, memLen);
		}
	}

	/**
	 * Выполняет поиск очередного слова, вернет строку
	 *
	 * @return строка до разделителя
	 * @throws IOException возможна ошибка чтения стрима
	 */
	public String nextString() throws IOException {
		findNext();

		if (memLen == 0) {
			return null;
		} else {
			return new String(mem, 0, memLen);
		}
	}

	@Override
	public void close() throws IOException {
		inputStream.close();
	}
}
