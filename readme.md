Руководство пользования.

Для начала работы необходимо создать 2 БД mysql

Основную БД и прописать к ней подключение в файле

src/main/resources/application.properties

Тестовую БД и прописать к ней подключение в файле

src/test/resources/application.properties

Для тестирования методов необходимо через postman отправлять запросы, например: 
lockalhost:8099/files

Или можно перейти в раздел тестирования и запустить в режиме отладки.

После завершения тестов, как правило вызываются методы, которы очищают тестовую БД и хранилище