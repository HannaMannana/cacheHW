1. Класс main выполняет функцию контроллера, все методы выполняются в нем.
2. В мейне указаны dataSource и liquibase, для запуска и конфигурации БД.
3. CachingUserDaoProxy - выполняет CRUD с добавлением кэша, который указан в applicationCache.yml