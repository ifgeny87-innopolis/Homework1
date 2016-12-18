package week01.v3.streams;

import week01.v3.validators.LongValidator;

import java.io.IOException;
import java.io.InputStream;

/**
 * Класс LongReader помогает читать InputStream по словам и преобразовывать слова в {@link Long}.
 *
 * Если слово не удается привести к {@link Long}, вызывает {@link NumberFormatException}.
 *
 * Например, следующий код будет пытаться читать числа пока не закончится стрим:
 * >    LongReader reader = new LongReader(new FileInputStream("test.txt"));
 * >    while(true)
 * >        try {
 * >            l = longReader.nextLong();
 * >            if(l == null)
 * >                break;
 * >            System.out.println(l);
 * >        }
 * >        catch(NumberFormatException e) {
 * >        }
 */
public class LongReader extends WordReader {
	static private LongValidator validator = new LongValidator();

	/** super translate **/
	public LongReader(InputStream inputStream) {
		super(inputStream);
	}

	/**
	 * Читает очереденое слово из стрима и пробует привести его к Long.
	 * Вернет null, если алгоритм дошел до конца {@link java.io.InputStream}.
	 *
	 * @return Long число, null если дошел до конца стрима
	 * @throws IOException Если случилась ошибка чтения стрима
	 */
	public Long nextLong() throws IOException {
		findNext();

		if (memLen == 0) {
			return null;
		} else {
			String s = new String(mem, 0, memLen);
			if (validator.validate(s))
				return Long.parseLong(s);
			else
				throw new NumberFormatException();
		}
	}
}
