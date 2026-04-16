# Spécifications des Workflows CI/CD

Ce document décrit les spécifications techniques et fonctionnelles des workflows de Continuous Integration pour le projet java-improvements. Ces spécifications sont indépendantes de la plateforme d'exécution (GitHub Actions, GitLab CI, Jenkins, etc.) et servent de source de vérité pour la génération automatique des fichiers de configuration CI.

## Principes Généraux

- **Langage**: Les configurations doivent être générées en tant que workflows automatisés
- **Environnement**: La version Java à utiliser est définie par la propriété `maven.compiler.target` dans le fichier `pom.xml` à la racine du projet
- **Outil de build**: Maven
- **Framework de test**: JUnit 5 (Jupiter) et jqwik

## Workflow 1: Validation des Pull Requests

### Déclencheurs
- Création d'une nouvelle Pull Request
- Mise à jour (push) de commits sur une Pull Request existante
- Événements de modification de code source (fichiers `.java`, `pom.xml`, ou fichiers de configuration Maven)

### Étapes du Workflow

#### Étape 1: Préparation de l'environnement
- Utiliser la version Java définie par `maven.compiler.target` dans le `pom.xml` à la racine
- Cloner le dépôt à partir de la branche source de la PR
- Initialiser le cache Maven pour accélérer les builds ultérieurs

#### Étape 2: Construction du projet
- Exécuter `mvn clean package -DskipTests`
- Compiler tous les modules du projet multi-module Maven
- En cas d'échec de la compilation, le workflow doit s'arrêter et rapporter l'erreur

#### Étape 3: Exécution des tests
- Exécuter `mvn test`
- Tous les tests doivent passer (tests unitaires et tests de propriété jqwik)
- Les résultats de test doivent être collectés et rapportés
- En cas d'échec de test, le workflow doit échouer et afficher les détails des tests échoués

#### Étape 4: Analyse de la couverture de code
- Générer un rapport de couverture de code avec JaCoCo ou outil équivalent
- Vérifier que la couverture globale dépasse 90%
- Si la couverture est inférieure à 90%, le workflow doit échouer avec un message d'erreur indiquant le seuil non atteint
- Les détails du rapport de couverture doivent être disponibles pour consultation

#### Étape 5: Commentaire de résumé de couverture de code
- Publier un commentaire sur la Pull Request avec un tableau récapitulatif
- Le tableau doit contenir :
  - **Colonne 1**: Nom du module
  - **Colonne 2**: Pourcentage de couverture de code (ex: `85.2%`)
  - **Colonne 3**: Statut (✅ si ≥ 90%, ❌ si < 90%)
  - Exemple :
    ```
    | Module | Couverture | Statut |
    |--------|-----------|--------|
    | java-14-examples | 92.5% | ✅ |
    | java-15-examples | 87.3% | ❌ |
    | Global | 91.2% | ✅ |
    ```
- Le commentaire doit être visible avant de merger la PR

### Résultat
- La PR reçoit un statut de validation qui indique le succès ou l'échec du workflow
- Un commentaire détaillé avec la couverture par module est visible dans la PR
- En cas de succès, la PR peut être fusionnée
- En cas d'échec, les détails des erreurs doivent être visibles au développeur

---

## Workflow 2: Validation de la Branche Principale

### Déclencheurs
- Push de commits sur la branche `main`
- Déclenché automatiquement après chaque merge d'une PR

### Étapes du Workflow

#### Étape 1: Préparation de l'environnement
- Utiliser la version Java définie par `maven.compiler.target` dans le `pom.xml` à la racine
- Cloner le dépôt à partir de la branche `main`
- Initialiser le cache Maven pour accélérer les builds ultérieurs

#### Étape 2: Construction du projet
- Exécuter `mvn clean package -DskipTests`
- Compiler tous les modules du projet multi-module Maven
- En cas d'échec de la compilation, le workflow doit s'arrêter et rapporter l'erreur

#### Étape 3: Exécution des tests
- Exécuter `mvn test`
- Tous les tests doivent passer (tests unitaires et tests de propriété jqwik)
- Les résultats de test doivent être collectés et rapportés
- En cas d'échec de test, le workflow doit échouer et envoyer une notification

#### Étape 4: Analyse de la couverture de code
- Générer un rapport de couverture de code avec JaCoCo ou outil équivalent
- Vérifier que la couverture globale dépasse 90%
- Si la couverture est inférieure à 90%, le workflow doit échouer avec un message d'erreur indiquant le seuil non atteint
- Les détails du rapport de couverture doivent être archivés pour historique et traçabilité

#### Étape 5: Résumé de couverture de code
- Générer un résumé de couverture par module avec tableau récapitulatif
- Le tableau doit contenir :
  - **Colonne 1**: Nom du module
  - **Colonne 2**: Pourcentage de couverture de code (ex: `85.2%`)
  - **Colonne 3**: Statut (✅ si ≥ 90%, ❌ si < 90%)
- Afficher ce résumé dans les logs du workflow ou en tant qu'artifact

### Résultat
- En cas de succès, la branche main est maintenue dans un état de qualité validé
- Un résumé de couverture par module est généré et archivé
- En cas d'échec, une alerte doit être générée (notification, rapport, etc.)
- L'historique des builds doit être conservé pour audit et debugging

---

## Configuration Commune

### Seuils et Paramètres
- **Couverture de code minimum**: 90%
- **Version Java cible**: Définie par la propriété `maven.compiler.target` dans `pom.xml`
- **Framework de test**: JUnit 5, jqwik

### Artefacts et Rapports
- Rapports de test (format JUnit XML)
- Rapports de couverture (format HTML, XML, ou format de la plateforme)
- Logs de build complets
- Messages d'erreur détaillés en cas d'échec

### Performance et Optimisations
- Utiliser le cache Maven pour les dépendances
- Paralléliser l'exécution des tests si la plateforme le permet
- Stopper les workflows au premier point de rupture pour économiser les ressources

### Notifications et Rapports
- Les résultats doivent être disponibles dans l'interface de la plateforme CI
- Les métriques de couverture doivent être affichées dans les rapports
- Les URL des rapports doivent être facilement accessibles dans les détails du workflow