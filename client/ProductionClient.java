package client;

import server.StockInterface;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ProductionClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            StockInterface stock = (StockInterface) registry.lookup("StockService");

            System.out.println("Client Production connecté au stock.");
            for (int i = 0; i < 7; i++) {
                Thread.sleep(1000);
                stock.stocker();
            }

            System.out.println("État final du stock : " + stock.getNbCartons() + "/" + stock.getCapacite());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

