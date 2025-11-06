# TP Java - Exclusion Mutuelle avec jeton

Ce projet implémentant l’algorithme **Suzuki-Kasami** d’exclusion mutuelle à base de Jeton.

## Quick build & run
Pour compiler et exécuter le projet, utilisez les commandes suivantes :

Pour chaque noeud, Ouvrir un terminal et lancer le  Noeud (NodeServer0, NodeServer1, NodeServer2, ...):

```sh
   cd nodes
   javac *.java
   java NodeServer[Numéro du noeud] # Changer le numéro du noeud selon le cas [0, 1, 2, ...]
```

## Project files

```
Distributed_Systems_TPs
├──nodes/
|  ├── NodeServer0.java        Serveur pour Noeud 0
|  ├── NodeServer1.java        Serveur pour Noeud 1
|  └── NodeServer2.java        Serveur pour Noeud 2
|
└── suzukiKasami/
    ├── Node.java              Classe principale du noeud
    ├── NodeInterface.java     Interface RMI
    └── Token.java             Classe représentant le jeton
```

## Expected results

### Lancement des noeuds

#### Noeud 0 Output

```
═══════════════════════════════════════
  SERVEUR NOEUD 0
═══════════════════════════════════════
✓ Registre RMI démarré sur port 1099
✓ Noeud 0 possède le jeton initial
✓ Noeud 0 enregistré

En attente de connexions des autres noeuds...
Tapez 'connect' pour se connecter aux autres noeuds
Tapez 'request' pour demander la section critique
Tapez 'quit' pour quitter
```

#### Noeud 1 Output

```
═══════════════════════════════════════
  SERVEUR NOEUD 1
═══════════════════════════════════════
✓ Registre RMI démarré sur port 1100
✓ Noeud 1 enregistré

Tapez 'connect' pour se connecter aux autres noeuds
Tapez 'request' pour demander la section critique
Tapez 'quit' pour quitter
```

#### Noeud 2 Output

```
═══════════════════════════════════════
  SERVEUR NOEUD 2
═══════════════════════════════════════
✓ Registre RMI démarré sur port 1101
✓ Noeud 2 enregistré

Tapez 'connect' pour se connecter aux autres noeuds
Tapez 'request' pour demander la section critique
Tapez 'quit' pour quitter
```

### Connection des noeuds
```
connect
✓ Noeud 0 connecté au noeud 1 sur localhost:1100
✓ Noeud 0 connecté au noeud 2 sur localhost:1101

✓ Connexions établies
```

### Demande de section critique par le Noeud possédant le jeton
```
request

→ Noeud 0 demande la SC (séquence: 1)
>>> Noeud 0 ENTRE EN SECTION CRITIQUE <<<
<<< Noeud 0 SORT DE SECTION CRITIQUE <<<
```

### Demande de section critique par un autre noeud

#### Noeud 1 Output (Ne possède pas le jeton)
```
request

→ Noeud 1 demande la SC (séquence: 1)
>>> Noeud 1 ENTRE EN SECTION CRITIQUE <<<
<<< Noeud 1 SORT DE SECTION CRITIQUE <<<
```

#### Noeud 0 Output (Possède le jeton)

```
Noeud 0 reçoit requête de noeud 1 (numéro: 1)
=> Noeud 0 envoie le jeton à noeud 1
```