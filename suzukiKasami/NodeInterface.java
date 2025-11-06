package suzukiKasami;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NodeInterface extends Remote {
    void requestToken(int nodeId, int requestNumber) throws RemoteException;
    void receiveToken(Token token) throws RemoteException;
    int getNodeId() throws RemoteException;
    void registerRemoteNode(int id, String host, int port) throws RemoteException;
}