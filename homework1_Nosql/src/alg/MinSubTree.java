package alg;

public class MinSubTree {
    private TreeNode minTreeNode;
    private int minSum;

    private TreeNode getMinTreeNode(TreeNode root){
        minSum=Integer.MAX_VALUE;
        minTreeNode = null;
        getSum(root);
        return minTreeNode;
    }

    public int getSum(TreeNode root){
        if(root==null){
            return 0;
        }
        int sum =getSum(root.getLeft())+getSum(root.getRight())+root.getVal();
        if(minSum>sum){
            minTreeNode=root;
            minSum=sum;
        }
        return sum;
    }

    public static void main(String[] args) {
        TreeNode root=new TreeNode(1);
        root.setLeft(new TreeNode(2));
        root.setRight(new TreeNode(3));
        root.getLeft().setLeft(new TreeNode(4));
        root.getLeft().setRight(new TreeNode(5));

        MinSubTree minSubTree=new MinSubTree();
        System.out.println(minSubTree.getMinTreeNode(root).getVal());
    }
}
