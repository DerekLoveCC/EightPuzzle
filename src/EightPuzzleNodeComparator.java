import java.util.Comparator;

/**
 * Comparator类，用于PriorityQueue中EightPuzzleNode的比较
 */
public class EightPuzzleNodeComparator implements Comparator<EightPuzzleNode> {
    /**
     * 覆盖Comparator接口中的compare函数，根据evaluation值决定大小
     */
    @Override
    public int compare(EightPuzzleNode node1, EightPuzzleNode node2) {
        if (node1.get_evaluation() == node2.get_evaluation()) {
            return 0;
        }
        return node1.get_evaluation() > node2.get_evaluation() ? 1 : -1;
    }
}
