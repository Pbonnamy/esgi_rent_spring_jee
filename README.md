### Promotion :
AL
### Classe :
4AL1

### Membres du groupe :
* BOISSET Emmanuel
* BONNAMY Pierre
* MARLEIX Noe

Lien postman :
https://app.getpostman.com/join-team?invite_code=d1b7dc276d24da8671dd611d3cc979a9&target_code=a1042ca568a424af4a82c20679f155d8

### Les difficultés rencontrées :

**Emmanuel** : Je n'en ai pas énormément rencontré pour ma part, sachant que j'ai déjà utilisé spring boot auparavant, notamment durant le projet de clean code. Tout de même, certaines difficultés ont été rencontrées.
  1. **Création du patch:** dans un premier temps je n'avais pas pensé à créer un nouveau DTO, ce qui m'a beaucoup bloqué car j'ai tenté de mettre en place des règles en utilisant un updateMapToEntity, ce qui n'était pas raisonnable. Cependant après reflexion, cela ne brisait pas les principes SOLID.
  2. **Le taux de couverture des tests:** atteindre les 100 % était impossible en raison de certain tests qui étaient intestables. Malgré tout, j'ai tout de même réussi à atteindre les 91 % mais sans une classe de test à cause de l'intercepteur.
  3. **L'intercepteur:** la mise en place de l'intercepteur Spring boot était compliqué dans sa configuration car nous avons décidé de faire du token une variable d'environnement, ainsi lors de la mise en place j'ai eu pas mal de souci avec le package Dotenv, car il ne reconnaissait pas le fichier lors des tests mais dans l'application et inversement si cela marchait dans les tests. Le test de l'intercepteur en lui-même n'a pas pu être fait à cause de la méthode statique de Dotenv "load" qui ne peut pas être mocké.

**Noe** : 
1. **Le choix du reverse proxy:** J'ai fait le choix de ne pas suivre à la lettre le sujet et de mettre en place un front qui ferait office de reverse proxy. Ce n'était pas un choix simple car il sortait des sentiers battus et a requis une réflexion sur la logique et l'architecture de l'application.
2. **Les tests:** ayant choisi une logique totalement différente des exemples vus en cours, et étant peu à l'aise avec les tests, rédiger les tests unitaires a été une épreuve. Le taux de couverture des tests est au final de 96 %.

## Spécificités
1. Protection des API Spring : les API Spring ne sont pas appelables depuis un autre client que Front. Un token d'authentification a été mis en place. Les API Spring contrôlent ce token en le comparant avec celui présent dans le [fichier d'environnement](.env) à la racine. Le front récupère quant à lui le token dans son [fichier de properties](RentFrontAPI/src/main/resources/config.properties). Attention, les deux tokens doivent être identiques sous peine de ne pas pouvoir requêter les API Spring.
2. Au premier lancement de l'application, executer la commande `docker compose -f docker-compose.yml` pour initier la base de donnée. Suite à cette opération, lancer les scrips SQL disponibles dans les dossiers SQL de [RentCarsApi](RentCarsAPI/src/main/resources/sql/rental_car.sql) et [RentPropertiesApi](RentPropertiesAPI/src/main/resources/sql).
3. 

## Fonctionnalités non implémentées
Nous avons implémenté toutes les fonctionnalités demandées dans le sujet.
## Fonctionnalités supplémentaires


Nous avons implémenté certaines fonctionnalités supplémentaires:
1. Un interceptor présent dans l'api Spring [RentPropertiesApi](RentPropertiesAPI/src/main/java/fr/rent/application/interceptor) ainsi que dans l'api [RentCarsProperties](RentCarsAPI/src/main/java/fr/esgi/rent/interceptor/AuthInterceptor.java)
2. Un [reverse proxy](RentFrontAPI/src/main/java/fr/esgi/api) en tant que front