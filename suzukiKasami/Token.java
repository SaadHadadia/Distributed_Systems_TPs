package suzukiKasami;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

public class Token implements Serializable {
    private static final long serialVersionUID = 1L;

    private int[] LN;
    private Queue<Integer> queue;

    public Token(int nbNodes) {
        this.LN = new int[nbNodes];
        this.queue = new LinkedList<>();
    }

    public int[] getLN() {
        return LN;
    }

    public void setLN(int index, int value) {
        LN[index] = value;
    }

    public Queue<Integer> getQueue() {
        return queue;
    }

    public void addToQueue(int nodeId) {
        if (!queue.contains(nodeId)) {
            queue.add(nodeId);
        }
    }

    public Integer pollQueue() {
        return queue.poll();
    }
}

