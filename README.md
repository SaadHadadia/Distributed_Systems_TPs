# TP RMI - Modeling distributed task for a store

Minimal Java RMI-like/IPC demo with a server and two clients.

## Quick build & run
1. Open a terminal and start the server:
   ```sh
   cd server
   javac *.java
   java StockServer
   ```

2. In another terminal, run a client:
   ```sh
   cd client
   javac *.java
   java ProductionClient   # or: java LivraisonClient
   ```

## Project files

```
Distributed_Systems_TPs
├──server/
|  ├── StockInterface.java       interface
|  ├── StockImpl.java            implementation
|  └── StockServer.java          server entrypoint
|
└── client/
    ├──ProductionClient.java     producer client
    └──LivraisonClient.java      delivery client
```

## Expected results

```
[SERVEUR] Registre RMI démarré sur le port 1099.
[SERVEUR] Serveur Stock lancé et prêt ! Service nommé 'StockService'
[SERVEUR]  Carton stocké. Total = 1/5
[SERVEUR]  Carton stocké. Total = 2/5
[SERVEUR]  Carton stocké. Total = 3/5
[SERVEUR]  Carton stocké. Total = 4/5
[SERVEUR]  Carton stocké. Total = 5/5
[SERVEUR]  Stock plein ! (5/5)
[SERVEUR]  Stock plein ! (5/5)
[SERVEUR]  Carton livré. Restant = 4/5
[SERVEUR]  Carton livré. Restant = 3/5
[SERVEUR]  Carton livré. Restant = 2/5
```
