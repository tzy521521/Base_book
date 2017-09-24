package chapter29;

/**
 * Created by tzy on 2017/9/22.
 */
public class DisjSets {
    private int[] s;
    private int count;//记录并查集中子集合的个数(子树的个数)

    public DisjSets(int numElements) {
        //构造函数，负责初始化并查集
        s=new int[numElements];
        count=numElements;
        for (int i = 0; i <s.length ; i++) {
            s[i]=-1;
        }
    }

    public int getCount() {
        return count;
    }

    public int[] getS() {
        return s;
    }

    public void unionByHeight(int root1, int root2){
        //union操作不采用策略，会影响Find操作。
        s[root2]=root1;
        count--;
    }

    public int find(int x){
        //find 操作
        if(s[x]<0)
            return x;
        else
            return find(s[x]);
    }
}
