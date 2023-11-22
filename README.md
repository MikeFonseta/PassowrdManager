# Password Manager

[Vedi pagina sourceforge](https://sourceforge.net/projects/phppasswordmanager/)

## Avvio applicativo
Prima dell'avvio dell'applicativo sarà necessario scegliere una delle versioni disponibili nella cartella Versions.\
Per modificare la versione sarà necessario modificare le righe seguenti nel file docker-compose.yml cambiando Versions/9.13  in Versions/{Nome versione disponibile nella cartella Versions}/

```
volumes:
     - ./Versions/9.13/src:/var/www/html/
...
...
...
volumes:
     - ./Versions/9.13/initial.sql:/docker-entrypoint-initdb.d/db.sql
```

Per avviare l'applicativo:
```
docker-compose up
```
## Terminare applicativo
```
CTRL+C
```

## Test Selenium WebDriver con Java e JUnit
Progetto nella cartella SeleniumJava

## Test SeleniumIDE
Aprire PasswordManager.side con SeleniumIDE
