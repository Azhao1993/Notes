import java.util.*;

public class Main {

    public static void main(String[] args) {

        // test3:
        int[][] edges = { {1,2,1},{2,3,3},{1,3,100} };
        int res = minPath(3,edges,1,3);
        System.out.println(res);

        //test1:
        // int[][] intervals = {{1,3},{2,6},{8,10},{15,18}};
        // merge(intervals);

        //test2:
        //System.out.println(valid("internationalization","i12iz6"));
        //System.out.println(valid("apple","a2e"));
    }

    // 1.合并时间区间:O(N)
    public static int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }
        LinkedList<int[]> queue = new LinkedList<int[]>();

        queue.add(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            int[] pollLast = queue.getLast();
            if (intervals[i][0] > pollLast[1]) {
                queue.add(intervals[i]);
            }  else if (intervals[i][1] >= pollLast[1]) {
                pollLast[1] = intervals[i][1];
            }
        }

        int size = queue.size();
        int[][] res = new int[size][2];

        for (int i = 0; i < size; i++) {
            res[i] = queue.pop();
        }
        return res;
    }
    // 2. 缩写校验 O(N)
    public static boolean valid(String word, String abbr){
        // word
        int i = 0;
        int length = word.length();
        // abbr
        int start = 0;
        int size = abbr.length();

        while(i < length && start < size) {
            if (word.charAt(i) == abbr.charAt(start)) {
                start++;
                i++;
            } else if (isNum(abbr,start)) {
                int end = start;
                while (end < size && isNum(abbr,end)){
                    end++;
                }
                int num = Integer.parseInt(abbr.substring(start,end));
                start = end;
                i += num;
                if (i > length) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    private static boolean isNum(String abbr, int i) {
        return abbr.charAt(i) >= '0' && abbr.charAt(i) < '9';
    }


    // 3. 最小惩罚
    // 最短路径算法：Floyd算法（对比Dijkstra算法）
    // n个节点 1 - n;
    // edges[i] 表示 1条边: edges[i][0] <--> edges[i][1] 代价:edges[i][2]
    // start -> end
    public static int minPath(int n,int[][] edges, int start,int end) {
        // 初始化
        int[][] mMatrix = new int[n][n]; // nodeIndex: 0 - n-1 ,题目是 1 - n
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                mMatrix[i][j] = i == j ? 0 : -1;
            }
        }
        for (int i = 0; i < edges.length;i++) {
            mMatrix[edges[i][0] - 1][edges[i][1] - 1] = edges[i][2];
            mMatrix[edges[i][1] - 1][edges[i][0] - 1] = edges[i][2];
        }
        // 计算最短路径O(N^3)
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    // 如果经过下标为k顶点路径比原两点间路径更短，则更新dist[i][j]和path[i][j]
                    int tmp = (mMatrix[i][k] == -1 || mMatrix[k][j]== -1) ? -1 : (mMatrix[i][k] | mMatrix[k][j]);
                    if (mMatrix[i][j] > tmp) {
                        // "i到j最短路径"对应的值设，为更小的一个(即经过k)
                        mMatrix[i][j] = tmp;
                    }
                }
            }
        }

        return mMatrix[start-1][end-1];
    }

}


