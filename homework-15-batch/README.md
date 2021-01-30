### Spring batch

#### При запуске баз данных с использованием Docker
* Запуск контейнера mysql : docker run --name some-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:tag
* Запуск контейнера mongo : docker run --name some-mongo -p 27017:27017 -d mongo:tag

#### При запуске баз данных с использованием Docker
Команда для запуска миграции 'start'