## passportServices

Учебный проект
1. В проекте два сервиса.
1.1 Первый использует архитектура REST
1.2 Второй использует методы первого сервиса, используя RestTemplate
2. Поддерживаются следующие методы:
- /save, сохранить данные паспорта
- /update?id=*, обновить данные паспорта
- /delete?id=*, удалить данные паспорта
- /find, загрузить все паспорта
- /find?seria=*, загрузить паспорта с заданной серией
- /unavaliabe, загрузить паспорта чей срок вышел
- /find-replaceable, загрузить паспорта, которые нужно заменить в ближайшие 3 месяца

[![Java CI with Maven](https://github.com/KarnaukhovKirill/passportServices/actions/workflows/github-actions-demo.yml/badge.svg?branch=main&event=status)](https://github.com/KarnaukhovKirill/passportServices/actions/workflows/github-actions-demo.yml)