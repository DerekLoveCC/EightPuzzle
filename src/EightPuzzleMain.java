import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

/**
 * 主类：包含程序入口点main函数
 */
public class EightPuzzleMain {
    private final EightPuzzleNode initialNode;//初始节点
    public static EightPuzzleNode TARGET_NODE;//目标节点
    private PriorityQueue<EightPuzzleNode> OpenQueue;//成员变量，用于存放尚未检查的节点
    private ArrayList<EightPuzzleNode> ClosedList;//用于存放已经检查过的节点

    /**
     * 构造函数：做一些初始化操作
     */
    public EightPuzzleMain(EightPuzzleNode initialNode, EightPuzzleNode targetNode) {
        this.initialNode = initialNode;//保存初始节点
        TARGET_NODE = targetNode;//保存目标节点

        OpenQueue = new PriorityQueue<EightPuzzleNode>(new EightPuzzleNodeComparator());//初始化Queue队列
        ClosedList = new ArrayList<>();//初始化ClosedList列表
    }

    /**
     * 入口点main函数
     */
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);//创建scanner对象，用于读取user的输入
        System.out.println("请输入初始状态，空白子用0表示:");//提示用户输入初始状态
        String initialStateNumberStr = scanner.nextLine();//读取用户输入的初始状态
        EightPuzzleNode initialNode = new EightPuzzleNode(initialStateNumberStr);//构造初始状态节点

        System.out.println("请输入结束状态，空白子用0表示:");//提示用户输入目标状态
        String targetStateNumberStr = scanner.nextLine();//读取用户输入的目标状态
        EightPuzzleNode targetNode = new EightPuzzleNode(targetStateNumberStr);//构造目标状态节点
        scanner.close();//读取用户输入后，关闭scanner

        EightPuzzleMain puzzleQueueMain = new EightPuzzleMain(initialNode, targetNode);//创建一个EightPuzzleMain对象
        puzzleQueueMain.solve();//调用solve函数以求问题的解
    }

    /**
     * 求解问题的主函数
     */
    private void solve() {
        //检查问题是否可解，如果不可解，则输出信息并退出程序
        int initialStateInverseNumberCount = initialNode.computeInverseNumberCount();
        int targetStateInverseNumberCount = TARGET_NODE.computeInverseNumberCount();
        if ((initialStateInverseNumberCount + targetStateInverseNumberCount) % 2 != 0) {
            System.out.println("It is not resolvable.");
            return;
        }

        initialNode.computeEvaluation();//计算初始状态节点的评估值
        OpenQueue.add(initialNode);//把初始状态放入OpenQueue

        //遍历存放节点的优先级队列，直到找到解或者队列为空
        while (!OpenQueue.isEmpty()) {
            EightPuzzleNode puzzleNode = OpenQueue.peek();//从队列中取出一个节点
            OpenQueue.remove(puzzleNode);

            if (puzzleNode.isSameState(TARGET_NODE)) {
                //判断节点状态是否和目标状态一致，如果一致则打印结果并退出
                printResult(puzzleNode);
                return;
            }

            ClosedList.add(puzzleNode);//把当前状态放入已检查列表中

            for (EightPuzzleNode nextStateNode : puzzleNode.generateSubStateNodes()) {//生成当前状态的后续状态,
                if (ClosedList.stream().anyMatch(c -> c.isSameState(nextStateNode))) {//检查该状态是否已经检测过，如果已检测过，就跳过
                    continue;
                }
                nextStateNode.computeEvaluation();//计算节点的评估值
                OpenQueue.add(nextStateNode);//把节点放入待检查队列
            }
        }
        System.out.println("没有找到解");//输出失败信息
    }

    /**
     * 输出成功结果函数
     */
    private static void printResult(EightPuzzleNode puzzleNode) {
        System.out.println("Found after " + puzzleNode.get_depth() + " steps as below");//打印出经过几步找到的结果
        Stack<EightPuzzleNode> nodeStack = new Stack<>();//初始化一个栈来存储路径上的节点
        nodeStack.push(puzzleNode);//把找到的节点压入栈中
        while (puzzleNode.Parent != null) {//从找到的节点开始往上回溯，直到根节点，依次放入栈中
            nodeStack.push(puzzleNode.Parent);
            puzzleNode = puzzleNode.Parent;
        }

        while (!nodeStack.isEmpty()) {//输出栈中的节点信息，即打印整个路径上的所有节点
            puzzleNode = nodeStack.pop();
            puzzleNode.print();
            System.out.println("======>");
        }
    }
}
