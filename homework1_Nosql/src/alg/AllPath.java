package alg;

import java.util.ArrayList;
import java.util.List;

public class AllPath {
    public List<String> binaryTreePaths(TreeNode root){
        List<String> paths=new ArrayList<>();
        if(root==null){
            return paths;
        }

        List<String>leftPaths=binaryTreePaths(root.getLeft());
        List<String>rightPaths=binaryTreePaths(root.getRight());

        for (String path :
                leftPaths) {
            paths.add(root.getVal()+"->"+path);
        }
        for (String path :
                rightPaths) {
            paths.add(root.getVal()+"->"+path);
        }

        if(paths.size()==0){
            paths.add(""+root.getVal());
        }

        return paths;

    }

    public static void main(String[] args) {
        TreeNode root=new TreeNode(1);
        root.setLeft(new TreeNode(2));
        root.setRight(new TreeNode(3));
        root.getLeft().setLeft(new TreeNode(4));
        root.getLeft().setRight(new TreeNode(5));

        AllPath allPath=new AllPath();
        allPath.binaryTreePaths(root).forEach(f->{
            System.out.println(f);
        });
    }
}
