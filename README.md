# Projektkalkulationsværktøj 

> En webbaseret databaseapplikation udviklet til [Alpha Solutions](https://www.alpha-solutions.com/), der giver brugere muligheden for at nedbryde projekter i delprojekter og opgaver, samt. estimere tid og omkostninger.

*Af Daniel, Markus, Abbas & Oliver*

[Scrum Board](https://github.com/orgs/EK-STUDIEGRUPPE-TEAM/projects/1)

---

## Teknologier

| **Teknologi** 	| **Version**                 	|
|---------------	|-----------------------------	|
| Java          	| 21.0.8 Eclipse              	|
| Spring Boot   	| 4.0.6                       	|
| Thymeleaf     	| 4.0.6 (via Spring Boot)     	|
| HTML          	| 5                           	|
| CSS           	| 3                           	|
| JDBC          	| 9.7.0 (via MySQL Connector) 	|
| H2            	| 2.4.240                     	|
| MySQL         	| 8.0.45                      	|
| Maven         	| 3.9.15                      	|
| IntelliJ IDE  	| 2025.3.2                    	|
| Azure         	|                             	|

## Kør lokalt

1. git clone vores repository
```bash
   git clone https://github.com/EK-STUDIEGRUPPE-TEAM/EKSAMENSPROJEKT-2SEM.git
```
3. Opret MySQL-database og kør SQL-script fra `/src/main/resources/schema.sql`
4. Indsæt dine databaseoplysninger som enviroment variables i Spring Boot configurations:
    - `DB_URL`
    - `DB_USER` 
    - `DB_PASSWORD`
5. Start applikationen
5. Åbn browseren med `http://localhost:8080`
