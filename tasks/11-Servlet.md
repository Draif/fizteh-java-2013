## HTTP API

[HTTP](http://ru.wikipedia.org/wiki/HTTP) API для работы с базой.

Нужно сделать поддержку HTTP-сервера для обработки запросов к хранилищу.
Управление будет осуществляться с помощью HTTP GET запросов.

### Запуск и останов сервера

Как и в [задании про Telnet-сервер](09-Telnet.md), запускать и останавливать HTTP-сервер нужно из шелла.
Для этого будем использовать команды ```starthttp``` и ```stophttp```.
Аргументы и поведение аналогично командам для запуска и останова Telnet-сервера.

### Конфигурация сервера

Предлагается использовать HTTP-сервер [Jetty](http://www.eclipse.org/jetty/).
Вам нужно по запросу создать экземпляр сервера, подключить его к сокету, инициализировать его контекст.
В контекст нужно добавить нужные обработчики запросы - сервлеты.

[Пример запуска Jetty-сервера](../src/ru/fizteh/fivt/examples/JettyExample.java).

### Транзакция

Перед началом работы клиент создет транзакцию с помощью команды begin.
По окончании работы клиент должен завершить транзакцию командами commit и rollback.
При создании транзакции возвращается ее идентификатор. В последующих командах клиент должен указывать ID транзакции.

Идентификатор транзакции имеет фиксированный формат - ровно пять десятичных цифр. Цифры могут быть любыми.

В предыдущих работах вы использовали имитацию транзакций внутри треда. Придется немного расширить механизм.
Простой способ - сделать расширяемый по запросу пул диффов транзакций, а в ThreadLocal-переменных хранить ссылки на транзакции из этого пула.

### Команды

Для обработки GET запроса нужно реализовать соответствующий сервлет и подключить его по нужному пути.
В случае успешного выполнения команды нужно вывести результат и вернуть HTTP код ```200 OK```.
В случае ошибки нужно вернуть HTTP код ```400 BAD REQUEST``` либо ```500 SERVER ERROR```, в зависимости от ошибки, а также вывести поясняющий текст.
Формат поясняющего текста непринципиален.

Обратите внимание, что текст в урле должен быть экранирован, а в теле ответа - не должен.

#### begin
```
/begin?table=tablename
tid=00010
```

#### commit
```
/commit?tid=00010
diff=13
```
Провал в случае, если транзакция не найдена.

#### rollback
```
/rollback?tid=00010
diff=11
```
Провал в случае, если транзакция не найдена.
#### get
```
/get?tid=00010&key=Hello%20everybody
{1,null,3.0,"foo"}
```
В случае, если ключ не найден, нужно вернуть HTTP-код 400 и пожаловаться.
Провал в случае, если транзакция не найдена.

#### put
```
/put?tid=00010&key=Hello%20everybody&value=%3Crow%3E%3Cnull%3E%3C/row%3E
<row><val>1</val></row>
```
В случае, если ключ не найден, нужно вернуть HTTP-код 400 и пожаловаться.
Провал в случае, если транзакция не найдена.

#### size
```
/size?tid=10003
0
```
Провал в случае, если транзакция не найдена.

### Полезное

* К HTTP API можно обращаться с помощью браузера.
* Можно воспользоваться утилитами командной строки для работы с HTTP - ```curl``` или ```wget```.
* Библиотеки для работы с Jetty лежат в репозитории в папке ```/lib```.
