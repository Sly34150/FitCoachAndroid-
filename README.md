# FitCoach — prototype Android

Prototype Android natif (Kotlin + vues XML), niveau débutant.

## Fonctionnalités incluses
- Choix de 2 à 6 jours/semaine, durée de séance et lieu (salle, maison, mixte).
- Génération d'un planning de base.
- Fiches d'exercices débutants avec consignes en français et illustration schématique animée par sélection.
- Synthèse vocale Android en français pour annoncer les exercices et le compte à rebours.
- Timer de séance et bouton suivant.
- Séance d'étirements sans matériel à domicile.

## Ouvrir et installer
1. Installer Android Studio (version récente) et le JDK intégré.
2. Ouvrir le dossier `FitCoachAndroid` dans Android Studio.
3. Laisser Gradle synchroniser le projet (connexion internet requise au premier build pour les dépendances Android Gradle Plugin).
4. Connecter le téléphone en USB avec le débogage USB activé, puis cliquer sur Run.
5. Pour créer un APK : **Build > Build Bundle(s) / APK(s) > Build APK(s)**.

Le projet est un prototype de départ : les dessins sont des schémas simplifiés, le planning est une logique de base et la progression/sauvegarde, les images détaillées par exercice, les annonces de répétitions via détection de mouvement et la personnalisation avancée restent à développer. Le coach vocal utilise la synthèse vocale Android installée sur le téléphone.

## Télécharger l'APK sans Android Studio
Consulte le fichier `README-APK.md` : il explique comment lancer la compilation automatique avec GitHub Actions et télécharger l'APK depuis ton téléphone.
