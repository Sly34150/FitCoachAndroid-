# Obtenir FitCoach.apk directement sur ton téléphone

Ce projet contient un workflow GitHub Actions qui compile automatiquement l'application Android et publie `app-debug.apk`.

## Première mise en place (à faire une seule fois)

1. Crée un compte sur https://github.com si tu n'en as pas.
2. Depuis GitHub, crée un nouveau dépôt nommé `FitCoachAndroid`.
   - Pour télécharger facilement l'APK depuis ton téléphone sans connexion, choisis **Public**.
   - Ne coche pas l'option qui ajoute automatiquement un README, `.gitignore` ou une licence.
3. Sur la page du dépôt, choisis **Add file > Upload files**.
4. Décompresse l'archive fournie et téléverse **tout le contenu du dossier `FitCoachAndroid`** (y compris le dossier caché `.github` et son workflow).
   - Important : le fichier doit se trouver à `.github/workflows/build-apk.yml` à la racine du dépôt.
5. Valide avec **Commit changes** sur la branche `main`.
6. Ouvre l'onglet **Actions** du dépôt. Le workflow **Build FitCoach APK** démarre automatiquement.
7. Attends que le travail affiche une coche verte. Le premier build peut prendre quelques minutes.

## Télécharger sur Android

Après une compilation réussie :

1. Ouvre le dépôt GitHub sur ton téléphone.
2. Ouvre **Releases** puis la dernière version `FitCoach APK - build ...`.
3. Dans **Assets**, touche `app-debug.apk` pour le télécharger.
4. Ouvre le fichier téléchargé et autorise, si Android le demande, l'installation de cette application depuis ton navigateur ou ton gestionnaire de fichiers.
5. Termine l'installation.

Tu peux aussi retrouver l'APK dans **Actions > dernier build réussi > Artifacts > FitCoach-APK**. Les artefacts Actions expirent après 30 jours ; la Release reste disponible tant que tu ne la supprimes pas.

## Pour les prochaines versions

Chaque modification envoyée sur la branche `main` déclenche une nouvelle compilation et une nouvelle Release.

## À savoir

- Le workflow compile un APK **debug** : il est installable pour un usage personnel, mais ce n'est pas une version signée pour une publication Play Store.
- Le dépôt public rend le code source accessible à tous. Ne téléverse aucune donnée personnelle.
- Ce prototype propose des illustrations schématiques et un coach vocal via la synthèse vocale Android. Il ne remplace pas un avis médical ni un encadrement professionnel.
