package suzukiKasami;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.NotBoundException;
import java.util.HashMap;
import java.util.Map;

public class Node extends UnicastRemoteObject implements NodeInterface {
    private int nodeId;
    private int nbNodes;
    private int[] RN;
    private int sequenceNumber;
    private Token token;
    private boolean hasToken;
    private Map<Integer, NodeInterface> nodes;
    private boolean requestingCS;

    public Node(int nodeId, int nbNodes, boolean initialToken) throws RemoteException {
        this.nodeId = nodeId;
        this.nbNodes = nbNodes;
        this.RN = new int[nbNodes];
        this.sequenceNumber = 0;
        this.nodes = new HashMap<>();
        this.requestingCS = false;

        if (initialToken) {
            this.token = new Token(nbNodes);
            this.hasToken = true;
            System.out.println("✓ Noeud " + nodeId + " possède le jeton initial");
        } else {
            this.hasToken = false;
        }
    }

    @Override
    public void registerRemoteNode(int id, String host, int port) throws RemoteException {
        try {
            Registry remoteRegistry = LocateRegistry.getRegistry(host, port);
            NodeInterface remoteNode = (NodeInterface) remoteRegistry.lookup("Node" + id);
            nodes.put(id, remoteNode);
            System.out.println("✓ Noeud " + nodeId + " connecté au noeud " + id + " sur " + host + ":" + port);
        } catch (NotBoundException e) {
            System.err.println("✗ Erreur: Noeud " + id + " non trouvé sur " + host + ":" + port);
        }
    }

    @Override
    public int getNodeId() throws RemoteException {
        return nodeId;
    }

    public synchronized void requestCriticalSection() {
        sequenceNumber++;
        requestingCS = true;
        RN[nodeId] = sequenceNumber;

        System.out.println("\n→ Noeud " + nodeId + " demande la SC (séquence: " + sequenceNumber + ")");

        if (!hasToken) {
            for (Map.Entry<Integer, NodeInterface> entry : nodes.entrySet()) {
                try {
                    entry.getValue().requestToken(nodeId, sequenceNumber);
                } catch (RemoteException e) {
                    System.err.println("✗ Erreur lors de l'envoi de la requête: " + e.getMessage());
                }
            }

            while (!hasToken) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        enterCriticalSection();
    }

    @Override
    public synchronized void requestToken(int senderId, int requestNumber) throws RemoteException {
        RN[senderId] = Math.max(RN[senderId], requestNumber);

        System.out.println("Noeud " + nodeId + " reçoit requête de noeud " + senderId +
                " (numéro: " + requestNumber + ")");

        if (hasToken && !requestingCS) {
            if (RN[senderId] == token.getLN()[senderId] + 1) {
                sendToken(senderId);
            }
        }
    }

    @Override
    public synchronized void receiveToken(Token receivedToken) throws RemoteException {
        System.out.println("Noeud " + nodeId + " reçoit le jeton");
        this.token = receivedToken;
        this.hasToken = true;
        notifyAll();
    }

    private void enterCriticalSection() {
        System.out.println(">>> Noeud " + nodeId + " ENTRE EN SECTION CRITIQUE <<<");

        try {
            Thread.sleep(3000); // Simuler le travail
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        exitCriticalSection();
    }

    private synchronized void exitCriticalSection() {
        System.out.println("<<< Noeud " + nodeId + " SORT DE SECTION CRITIQUE <<<\n");

        requestingCS = false;
        token.setLN(nodeId, RN[nodeId]);

        for (int i = 0; i < nbNodes; i++) {
            if (i != nodeId && RN[i] == token.getLN()[i] + 1) {
                token.addToQueue(i);
            }
        }

        Integer nextNode = token.pollQueue();
        if (nextNode != null) {
            sendToken(nextNode);
        }
    }

    private synchronized void sendToken(int targetNodeId) {
        if (hasToken) {
            System.out.println("=> Noeud " + nodeId + " envoie le jeton à noeud " + targetNodeId);
            try {
                NodeInterface targetNode = nodes.get(targetNodeId);
                if (targetNode != null) {
                    targetNode.receiveToken(token);
                    hasToken = false;
                    token = null;
                }
            } catch (RemoteException e) {
                System.err.println("✗ Erreur lors de l'envoi du jeton: " + e.getMessage());
            }
        }
    }
}