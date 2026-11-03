# biblio-api

API REST de gestion d'une petite bibliothèque (Spring Boot 3.5, Java 25, architecture MVC).

## Démarrer

```bash
./mvnw spring-boot:run
```

- Swagger UI : http://localhost:8080/swagger-ui.html
- Les données sont gardées **en mémoire** (perdues à l'arrêt).

## Structure

| Package | Rôle |
|---|---|
| `model` | Le modèle : `Livre` |
| `repository` | Accès aux données (en mémoire) |
| `service` | Logique métier |
| `controller` | Contrôleur REST (+ `LivreRequest`, corps JSON des requêtes) |
| `exception` | Exceptions et traduction en codes HTTP |

## Endpoints

| Méthode | URL | Description |
|---|---|---|
| GET | `/api/livres` | Lister les livres |
| GET | `/api/livres/{id}` | Obtenir un livre |
| POST | `/api/livres` | Ajouter un livre |
| PUT | `/api/livres/{id}` | Modifier un livre |
| DELETE | `/api/livres/{id}` | Supprimer un livre |
| POST | `/api/livres/{id}/emprunter` | Emprunter un livre |
| POST | `/api/livres/{id}/retourner` | Retourner un livre |
| GET | `/api/livres/{id}/frais-retard?joursRetard=N` | Frais de retard en $ |

---

> Remplacez le contenu ci-dessous par vos réponses du TP (voir `ENONCE_TP.md`).


1- La classe Livre joue le role du modèle. LivreController est le controlleur. La logique métier ce trouve dans le package service(LivreService)

2- GestionnaireErreurs gère les exceptions en les transformant en réponse http. Le code http pour un livre introuvable: 404 et pour un double emprunt: 409

3- les données sont réinitialiser au redémarrage

4- la requete est recu par le controller(.emprunter) qui appele le service et sa méthode d'emprunt.Le service vérifie l'existence en cherchant le Id. L'état du livre est ensuite modifié et sauvegarde via LivreRepository.save(livre).

A.3 Tester avec Swagger

GET /api/livres: Le Petit Prince, Une brève histoire du temps, Bonheur d'occasion

POST /api/livres:code 201, id:4

POST /api/livres:code 400

POST /api/livres/1/emprunter: code 200, code 409("Ce livre est déjà emprunté")

GET /api/livres/2/frais-retard?joursRetard=100: "frais": 20

## Partie D — Code Smells
| Nom du Code Smell | Fichier | Lignes / Méthode | Pourquoi c'est un problème ? |
| :--- | :--- | :--- | :--- |
| **Complex Conditionals** | `LivreService.java` | `calculerFraisRetard` | Multiples structures `if/else` imbriquées et valeurs en dur qui rigidifient l'ajout de nouvelles catégories. |
| **Long Method** | `LivreService.java` | `calculerFraisRetard` | La méthode combine le choix des règles métier, le calcul et le plafonnement en un seul bloc. |
| **Duplicate Code** | `LivreService.java` | `creer` / `modifier` | Les validations de champs (titre, auteur, année) sont dupliquées entre la création et la modification. |