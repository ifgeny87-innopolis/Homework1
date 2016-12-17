# Homework1

## Тестирование под нагрузкой

Список ссылок для тестирования из 20 веб-файлов и 20 локальных файлов 

    http://dixi.tk/test_resource1.txt http://dixi.tk/test_resource2.txt http://dixi.tk/test_resource3.txt http://dixi.tk/test_resource4.txt http://dixi.tk/test_resource5.txt http://dixi.tk/test_resource6.txt http://dixi.tk/test_resource7.txt http://dixi.tk/test_resource8.txt http://dixi.tk/test_resource9.txt http://dixi.tk/test_resource10.txt http://dixi.tk/test_resource11.txt http://dixi.tk/test_resource12.txt http://dixi.tk/test_resource13.txt http://dixi.tk/test_resource14.txt http://dixi.tk/test_resource15.txt http://dixi.tk/test_resource16.txt http://dixi.tk/test_resource17.txt http://dixi.tk/test_resource18.txt http://dixi.tk/test_resource19.txt http://dixi.tk/test_resource20.txt
    src\test\resources\v3\tests\test1.txt src\test\resources\v3\tests\test2.txt src\test\resources\v3\tests\test3.txt src\test\resources\v3\tests\test4.txt src\test\resources\v3\tests\test5.txt src\test\resources\v3\tests\test6.txt src\test\resources\v3\tests\test7.txt src\test\resources\v3\tests\test8.txt src\test\resources\v3\tests\test9.txt src\test\resources\v3\tests\test10.txt src\test\resources\v3\tests\test11.txt src\test\resources\v3\tests\test12.txt src\test\resources\v3\tests\test13.txt src\test\resources\v3\tests\test14.txt src\test\resources\v3\tests\test15.txt src\test\resources\v3\tests\test16.txt src\test\resources\v3\tests\test17.txt src\test\resources\v3\tests\test18.txt src\test\resources\v3\tests\test19.txt src\test\resources\v3\tests\test20.txt

Конечный результат должен равняться

    26966985658699766020458
    ну или
    26_966_985_658_699_766_020_458
    а более чем 26 секстилионов
    
Замеры по времени выполнения.

Условие:

- 20 тестовых текстовых файлов, без веб-ссылок
- Минимум 1 пробный тест без учета времени *(чтобы закешировать тестовые файлы в ОС)*
- После проведения пробного теста провести минимум 10 тестов подряд для определения AVG времени работы программы

Результаты и среднее время работы приложения:

- `v0.0.1` - 1100 мс
- `v0.0.2` - убрал лишние synchronized блоки, добавил volatile для переменной суммы, теперь среденее время составляет 850 мс 