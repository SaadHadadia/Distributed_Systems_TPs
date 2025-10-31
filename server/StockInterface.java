package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StockInterface extends Remote {
    boolean stocker() throws RemoteException;
    boolean livrer() throws RemoteException;
    int getNbCartons() throws RemoteException;
    int getCapacite() throws RemoteException;
}
