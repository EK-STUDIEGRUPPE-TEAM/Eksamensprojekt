# Contributing

## Workflow

1. Clone repository 
2. Opret branch
3. Lav ændringer
4. Commit
5. Push
6. Opret Pull Request

## Branching regler
- Der må **ikke** pushes direkte til `main`
- Opret altid en ny branch til din opgave
- Branch-navne skal være korte og beskrivende, fx:
  - `task-controller-test`
  - `user-sessions`
  - `project-service-budget`
- Branches må **ikke** slettes før de er merget

## Pull Requests
- Alle ændringer til `main` skal igennem Pull Request
- Der kræves minimum **1 godkendelse** fra et andet teammedlem før merge
- Du må **ikke** merge dit eget PR uden klar tilladelse fra teamet
- Undtagelse: små ukonsekvente ændringer kan merges hvis behov opstår

## CI/CD Pipeline
- GitHub Actions kører automatisk build og test på alle Pull Requests til `main`
- PR må ikke merges hvis pipeline fejler
- Qodana skal være mindst neutral
