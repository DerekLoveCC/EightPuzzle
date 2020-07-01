import java.util.ArrayList;

/**
 * 表示状态节点的类
 */
public class EightPuzzleNode {
    private int[] _numbers = new int[9];//存放数字的数组
    private int _depth;//该节点所在的层次，根节点在0层
    private int _evaluation;//计算的估算函数值
    public EightPuzzleNode Parent;//该节点的父节点，根节点即初始节点的父节点为空

    //构造函数，主要用于解析用户的输入
    public EightPuzzleNode(String numberString) {
        if (numberString == null || numberString.isEmpty()) {
            System.out.println("Invalid  number. please input numbers like 1,2,3,4,5,6,7,8,0");
            return;
        }
        String[] numberParts = numberString.split(",");
        for (int i = 0; i < numberParts.length; i++) {
            _numbers[i] = Integer.parseInt(numberParts[i]);
        }
    }

    //构造函数
    public EightPuzzleNode(int[] numbers) {
        for (int i = 0; i < _numbers.length; i++) {
            _numbers[i] = numbers[i];
        }
    }

    //获取估值
    public int get_evaluation() {
        return _evaluation;
    }

    //判断两个节点的状态是否一致，如果numbers数组相同则状态相同。
    public boolean isSameState(EightPuzzleNode otherNode) {
        if (this == otherNode) {
            return true;
        }

        if (this._numbers == otherNode._numbers) {
            return true;
        }

        for (int i = 0; i < this._numbers.length && i < otherNode._numbers.length; i++) {
            if (_numbers[i] != otherNode._numbers[i]) {
                return false;
            }
        }

        return true;
    }

    //计算估值并存放在估值成员变量里
    public void computeEvaluation() {
        int temp = 0;
        for (int i = 0; i < _numbers.length; i++) {
            if (_numbers[i] != EightPuzzleMain.TARGET_NODE._numbers[i]) {
                temp++;
            }
        }

        _evaluation = temp + _depth;
    }

    //输出该节点所表示的状态
    public void print() {
        System.out.println(_numbers[0] + "," + _numbers[1] + "," + _numbers[2]);
        System.out.println(_numbers[3] + "," + _numbers[4] + "," + _numbers[5]);
        System.out.println(_numbers[6] + "," + _numbers[7] + "," + _numbers[8]);
    }

    //计算该节点所表示状态的逆序数
    public int computeInverseNumberCount() {
        int count = 0;
        for (int i = 0; i < _numbers.length; i++) {
            for (int j = i + 1; j < _numbers.length; j++) {
                if (_numbers[j] < _numbers[i] && _numbers[j] != 0) {
                    count++;
                }
            }
        }

        return count;
    }

    //获取节点的深度
    public int get_depth() {
        return _depth;
    }

    /**
     * 通过移动0来获得不同的后续状态
     */
    public ArrayList<EightPuzzleNode> generateSubStateNodes() {
        int indexOfZero = -1;
        for (int i = 0; i < _numbers.length; i++) {//查找空格即0元素的index
            if (_numbers[i] == 0) {
                indexOfZero = i;
                break;
            }
        }

        ArrayList<EightPuzzleNode> result = new ArrayList<>();//创建一个列表用于存放新的状态节点

        //创建一个新数组
        int[] newNumbers = new int[9];
        if (moveUp(indexOfZero, newNumbers)) {//如果往上移动成功，则创建新节点并放入返回结果中
            EightPuzzleNode newPuzzleNode = new EightPuzzleNode(newNumbers);
            newPuzzleNode._depth = this._depth + 1;
            newPuzzleNode.Parent = this;
            newPuzzleNode.computeEvaluation();
            result.add(newPuzzleNode);
        }
        if (moveDown(indexOfZero, newNumbers)) {//如果往上移动成功，则创建新节点并放入返回结果中
            EightPuzzleNode newPuzzleNode = new EightPuzzleNode(newNumbers);
            newPuzzleNode._depth = this._depth + 1;
            newPuzzleNode.Parent = this;
            newPuzzleNode.computeEvaluation();
            result.add(newPuzzleNode);
        }
        if (moveLeft(indexOfZero, newNumbers)) {//如果往上移动成功，则创建新节点并放入返回结果中
            EightPuzzleNode newPuzzleNode = new EightPuzzleNode(newNumbers);
            newPuzzleNode._depth = this._depth + 1;
            newPuzzleNode.Parent = this;
            newPuzzleNode.computeEvaluation();
            result.add(newPuzzleNode);
        }
        if (moveRight(indexOfZero, newNumbers)) {//如果往上移动成功，则创建新节点并放入返回结果中
            EightPuzzleNode newPuzzleNode = new EightPuzzleNode(newNumbers);
            newPuzzleNode._depth = this._depth + 1;
            newPuzzleNode.Parent = this;
            newPuzzleNode.computeEvaluation();
            result.add(newPuzzleNode);
        }
        return result;
    }


    /**
     * 复制numbers数组。
     */
    private void copyNumbers(int[] destArray) {
        for (int i = 0; i < _numbers.length; i++) {
            destArray[i] = _numbers[i];
        }
    }

    /**
     * 往上移动
     */
    private boolean moveUp(int indexOfZero, int[] newNumbers) {
        if (indexOfZero < 3) {
            return false;
        }
        copyNumbers(newNumbers);//复制numbers数组

        newNumbers[indexOfZero] = newNumbers[indexOfZero - 3];
        newNumbers[indexOfZero - 3] = 0;
        return true;
    }

    /**
     * 往上移动
     */
    private boolean moveDown(int indexOfZero, int[] newNumbers) {
        if (indexOfZero > 5) {
            return false;
        }

        copyNumbers(newNumbers);//复制numbers数组
        newNumbers[indexOfZero] = newNumbers[indexOfZero + 3];
        newNumbers[indexOfZero + 3] = 0;
        return true;
    }

    /**
     * 往左移动
     */
    private boolean moveLeft(int indexOfZero, int[] newNumbers) {
        if (indexOfZero == 0 || indexOfZero == 3 || indexOfZero == 6) {
            return false;
        }

        copyNumbers(newNumbers);//复制numbers数组
        newNumbers[indexOfZero] = newNumbers[indexOfZero - 1];
        newNumbers[indexOfZero - 1] = 0;
        return true;
    }

    /**
     * 往右移动
     */
    private boolean moveRight(int indexOfZero, int[] newNumbers) {
        if (indexOfZero == 2 || indexOfZero == 5 || indexOfZero == 8) {
            return false;
        }

        copyNumbers(newNumbers);//复制numbers数组
        newNumbers[indexOfZero] = newNumbers[indexOfZero + 1];
        newNumbers[indexOfZero + 1] = 0;
        return true;
    }
}
