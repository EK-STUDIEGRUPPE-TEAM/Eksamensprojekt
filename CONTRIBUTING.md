# Contributing

Denne fil beskriver vores arbejdsgang, når vi arbejder sammen på projektet.

## Workflow

1. Klon repository
2. Opret en ny branch til din opgave
3. Lav ændringer
4. Commit dine ændringer
5. Push branchen til GitHub
6. Opret en Pull Request
7. Få review fra et andet teammedlem
8. Merge først når Pull Request er godkendt og pipeline er grøn

## Branching-regler

- Der må **ikke** pushes direkte til `main`
- Opret altid en ny branch til din opgave
- Branch-navne skal være korte og beskrivende, fx:
  - `task-controller-test`
  - `user-sessions`
  - `project-service-budget`
- Branches må **ikke** slettes før de er merget

## Commit-beskeder

Commit-beskeder skal være korte og tydelige, så andre kan forstå ændringen.

Eksempler:

- `Add task controller`
- `Fix login validation`
- `Update README`
- `Add Qodana workflow`

## Pull Requests

- Alle ændringer til `main` skal igennem Pull Request
- Der kræves minimum **1 godkendelse** fra et andet teammedlem før merge
- Du må **ikke** merge dit eget Pull Request uden klar aftale med teamet
- Mindre rettelser kan merges hurtigere, hvis teamet er enige
- Pull Request skal have en kort beskrivelse af ændringerne

## CI/CD Pipeline

- GitHub Actions kører automatisk build og test på alle Pull Requests til `main`
- Pull Request må ikke merges, hvis pipeline fejler
- Qodana bruges til kodeanalyse og kvalitetssikring
- Kritiske fejl fra Qodana bør rettes før merge
