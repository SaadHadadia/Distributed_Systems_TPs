package nodes;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import suzukiKasami.*;

public class NodeServer0 {
    private static final int NODE_ID = 0;
    private static final int RMI_PORT = 1099;
    private static final int NB_NODES = 3;

    public static void main(String[] args) {
        try {
            // Créer le registre RMI
            Registry registry = LocateRegistry.createRegistry(RMI_PORT);
            System.out.println("═══════════════════════════════════════");
            System.out.println("  SERVEUR NOEUD " + NODE_ID);
            System.out.println("═══════════════════════════════════════");
            System.out.println("✓ Registre RMI démarré sur port " + RMI_PORT);

            // Créer le noeud (avec le jeton initial)
            Node node = new Node(NODE_ID, NB_NODES, true);
            registry.rebind("Node" + NODE_ID, node);
            System.out.println("✓ Noeud " + NODE_ID + " enregistré\n");

            System.out.println("En attente de connexions des autres noeuds...");
            System.out.println("Tapez 'connect' pour se connecter aux autres noeuds");
            System.out.println("Tapez 'request' pour demander la section critique");
            System.out.println("Tapez 'quit' pour quitter\n");

            Scanner scanner = new Scanner(System.in);
            while (true) {
                String cmd = scanner.nextLine().trim();

                if (cmd.equals("connect")) {
                    // Se connecter aux autres noeuds
                    node.registerRemoteNode(1, "localhost", 1100);
                    node.registerRemoteNode(2, "localhost", 1101);
                    System.out.println("\n✓ Connexions établies\n");

                } else if (cmd.equals("request")) {
                    new Thread(() -> node.requestCriticalSection()).start();

                } else if (cmd.equals("quit")) {
                    System.out.println("Arrêt du serveur...");
                    System.exit(0);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
