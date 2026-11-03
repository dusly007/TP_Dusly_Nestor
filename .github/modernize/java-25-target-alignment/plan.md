# Modernization Plan: Java 25 Target Alignment

**Project**: biblio-api

## Technical Framework

- **Language/runtime**: Java; assessment reports JDK 21
- **Framework**: Spring Boot 3.5.7
- **Build tool**: Maven Wrapper; Maven distribution 3.9.9
- **Database**: In-memory application data
- **Key dependencies**: Spring Web, springdoc OpenAPI, Spring Boot Test

## Overview

Align the project's declared and documented Java runtime target with Java 25.
The assessment reports Java 21 in `pom.xml`, while the README and CI already
refer to Java 25. The plan is limited to the Java target and directly related
build/runtime configuration, documentation, and CI. Spring Boot and other
frameworks, cloud readiness, Docker, and deployment are out of scope.

## Migration Impact Summary

| Application | Current target | Desired target | Scope |
|---|---|---|---|
| biblio-api | Java 21 / 25 references | Java 25 | Build, runtime, docs, CI |

## Assessment Evidence

- `azure-java-version-02000`: **Java Version is not the latest LTS**;
  incident in `pom.xml:21`; assessed JDK is 21.
- `pom.xml` declares Java 21 and Spring Boot 3.5.7.
- `README.md` identifies Java 25.
- `.github/workflows/ci.yml` tests Java 21 and 25 and publishes the Java 25 JAR.
- The Maven Wrapper distribution is 3.9.9; no wrapper upgrade is requested.

## Open Questions & Questionnaire

- [ ] Q: Does the existing Spring Boot 3.5.7 configuration support Java 25
  for this project? → Confirm during execution before changing the target. Keep
  the framework version unchanged; if the target is incompatible, stop and
  report the blocker rather than expanding scope to a framework upgrade.

## Scope Constraints

- Set Java 25 as the single intended runtime/build target.
- Align Maven configuration, CI, and Java runtime documentation with that target.
- Retain Maven Wrapper 3.9.9 and Spring Boot 3.5.7 unless a compatibility
  blocker is found; do not plan framework or dependency upgrades.
- Do not modify unrelated application behavior, tests, frontend projects,
  Unity projects, cloud configuration, containers, or deployment.
- Validate the Java 25 build and existing unit tests; preserve unrelated
  working-tree changes.
