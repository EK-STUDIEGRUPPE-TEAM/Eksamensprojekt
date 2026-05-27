# Projektkalkulationsværktøj 

> En webbaseret databaseapplikation udviklet til [Alpha Solutions](https://www.alpha-solutions.com/), der giver brugere muligheden for at nedbryde projekter i delprojekter og opgaver, samt. estimere tid og omkostninger.

*Af Daniel, Markus, Abbas & Oliver*

[Scrum Board](https://github.com/orgs/EK-STUDIEGRUPPE-TEAM/projects/1)

## Dokumentation

Projektets dokumentation og diagrammer findes her:

[Se dokumentation](docs/README.md)
---

## Teknologier

| Teknologi | Version | Anvendelse |
|----------|---------|------------|
| Java | 21.0.8 | Programmeringssprog |
| Spring Boot | 4.0.6 | Backend webapplikation |
| JDBC | via Spring Boot | Databaseforbindelse |
| Thymeleaf | via Spring Boot | HTML templates |
| HTML | 5 | Struktur på websider |
| CSS | 3 | Styling af websider |
| MySQL | 8.0.45 | Produktionsdatabase |
| MySQL connector | 9.7.0 | Databasedriver til MySQL |
| H2 | 2.4.240 | Test-/udviklingsdatabase |
| Maven | 3.9.15 | Build tool og dependency management |
| IntelliJ IDEA | 2025.3.2 | Udviklingsmiljø |
| Azure App Service | Version ikke relevant | Deployment/hosting |

## Kør lokalt

1. git clone vores repository
```bash
   git clone https://github.com/EK-STUDIEGRUPPE-TEAM/EKSAMENSPROJEKT-2SEM.git
```
3. Opret MySQL-database og kør SQL-script fra `/src/main/resources/schema.sql`
4. Indsæt dine databaseoplysninger som enviroment variables i Spring Boot configurations:
    - `DEV_URL`
    - `DEV_USER` 
    - `DEV_PASSWORD`
5. Start applikationen
5. Åbn browseren med `http://localhost:8080`
