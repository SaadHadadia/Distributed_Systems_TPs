package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class StockImpl extends UnicastRemoteObject implements StockInterface {

    private final int capacite;
    private int nbCartons;

    public StockImpl(int capacite) throws RemoteException {
        super();
        this.capacite = capacite;
        this.nbCartons = 0;
    }

    @Override
    public synchronized boolean stocker() throws RemoteException {
        if (nbCartons < capacite) {
            nbCartons++;
            System.out.println("[SERVEUR]  Carton stocké. Total = " + nbCartons + "/" + capacite);
            return true;
        } else {
            System.out.println("[SERVEUR]  Stock plein ! (" + nbCartons + "/" + capacite + ")");
            return false;
        }
    }

    @Override
    public synchronized boolean livrer() throws RemoteException {
        if (nbCartons > 0) {
            nbCartons--;
            System.out.println("[SERVEUR]  Carton livré. Restant = " + nbCartons + "/" + capacite);
            return true;
        } else {
            System.out.println("[SERVEUR]  Stock vide !");
            return false;
        }
    }

    @Override
    public synchronized int getNbCartons() throws RemoteException {
        return nbCartons;
    }

    @Override
    public int getCapacite() throws RemoteException {
        return capacite;
    }
}