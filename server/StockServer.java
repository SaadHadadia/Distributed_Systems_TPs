package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StockServer {
    public static void main(String[] args) {
        try {
            int capacite = 5;
            // Démarrer le registre RMI local sur le port 1099
            Registry registry = LocateRegistry.createRegistry(1099);
            System.out.println("[SERVEUR] Registre RMI démarré sur le port 1099.");

            // Créer et publier l'objet distant
            StockImpl stock = new StockImpl(capacite);
            registry.rebind("StockService", stock);
            System.out.println("[SERVEUR] Serveur Stock lancé et prêt ! Service nommé 'StockService'");

            // Empêcher le programme de se terminer auto
            synchronized (StockServer.class) {
                StockServer.class.wait();
            }

        } catch (Exception e) {
            System.err.println("[SERVEUR] Erreur :");
            e.printStackTrace();
        }
    }
}
