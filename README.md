# Poképong

## Disclaimer

Ce dépôt est une copie moins lourde du véritable dépôt qui se retouve à l'adresse suivante :

[https://gaufre.informatique.univ-paris-diderot.fr/eude/pong](https://gaufre.informatique.univ-paris-diderot.fr/eude/pong)

## Présentation

Ce Pong est un jeu de raquettes programmé en Java 17 avec JavaFX. Le projet est configuré avec Gradle utilisant le plugin JavaFX. Ce jeu est largement inspiré du jeu [Pong](https://fr.wikipedia.org/wiki/Pong), un classique des salles d'arcades dans les années 1970.

## Vidéo de présentation

Pour voir la vidéo, cliquez [ici](https://youtu.be/0Yl9IugLAX4).

### Télécharger Pong

Le plus pratique pour télécharger Pong afin de participer à son developpement, c'est de cloner le dépôt. Depuis la console :

```bash
$ git clone https://gaufre.informatique.univ-paris-diderot.fr/myteam/pong
```

## Exécution, compilation

Après avoir téléchargé/cloné les sources, vous pouvez compiler et exécuter le projet à l'aide de gradle.
Le principe c'est que le script `gradlew` dans le répertoire du projet téléchargera puis utilisera la version de gradle qui fonctionne avec le projet.

Pour compiler, il suffit d'exécuter, depuis le répertoire `pong` :

```bash
`./gradlew build`
```

Pour exécuter, il suffit d'exécuter, depuis le répertoire `pong` :

```bash
`./gradlew run`
```

Le projet en lui-même a besoin de Java 17 pour être compilé et exécuté.

### Executable

Pour produire un fichier executable du jeu, il suffit d'exécuter, depuis le répertoire `pong` :

```bash
`./gradlew clean`
`./gradlew build`
`./gradlew createExe`
```

L'executable apparaîtra dans le dossier :

```bash
`\build\launch4j`
```

# Comment jouer ?

Après avoir executé, vous êtes dans les menus. Naviguez avec les flèches et appuyez/sélectionnez avec ESPACE/ENTRER.

Dans les paramètres, choisir le paramètre à changer avec les flèches HAUT et BAS. Modifier le paramètre choisi avec les flèches DROITE et GAUCHE.

La raquette de gauche est contrôlée par les touches Z et S, alors que celle de droite est contrôlée par les flèches HAUT et BAS (si vous n'êtes pas contre l'ordinateur).

Comment activer son pouvoir ? Une fois que la jauge de puissance (jauge mauve) est pleine :
- J1 : ESPACE
- J2 : P

Comment télécharger le Visual Novel: Pokepong:
1. Télécharger l'archive ArchiveVisualNovelPokepong.tar.
2. Télécharger le dossier res trouvable sur le lien suivant: https://drive.google.com/drive/folders/1jOfngX7xK-IKKky-3aaXbhFOw79QZYRz?usp=sharing
3. Placer l'archive décompressée et le dossier res dans le même dossier nommé src.
4. Executer le programme
