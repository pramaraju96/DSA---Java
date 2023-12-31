package DSAinJava.BinaryTree;
import java.util.*;
public class ArrayToBST {
    static class Node{
        int data;
        Node left;
        Node right;
        public Node(int data){
            this.data=data;
            this.left=this.right=null;
        }
    }
    //preorder created just in order to print the created BST
    public static void preorder(Node root){
        if(root==null){
            return;
        }
        System.out.print(root.data+" ");
        preorder(root.left);
        preorder(root.right);
    }
    public static Node createBST(int[] arr,int st,int end){
        if(st>end){
            return null;
        }
        int mid=(st+end)/2;
        Node root=new Node(arr[mid]);
        root.left=createBST(arr, st, mid-1);
        root.right=createBST(arr,mid+1,end);
        return root;
    }
    public static void getInorder(Node root,ArrayList<Integer>inorder){
        if(root==null){
            return;
        }
        getInorder(root.left,inorder);
        inorder.add(root.data);
        getInorder(root.right,inorder);
    }
    public static Node createBST(ArrayList<Integer>inorder,int st,int end){
        if(st>end){
            return null;
        }
        int mid=(st+end)/2;
        Node root=new Node(inorder.get(mid));
        root.left=createBST(inorder,st,mid-1);
        root.right=createBST(inorder, mid+1, end);
        return root;
    }
    public static Node balanceBST(Node root){
        //inorder seq stored in a list
        ArrayList<Integer>inorder=new ArrayList<>();
        getInorder(root,inorder);
        //sorted inorder->balanced BST
        root=createBST(inorder,0, inorder.size()-1);
        return root;
    }
    public static List<Node> uniqueBST(int n){
        return Helper(1,n);
    }
    public static List<Node>Helper(int start,int end){
        List<Node>result=new ArrayList<>();
        if(start>end){
            result.add(null);
            return result;
        }
        for(int mid=start;mid<=end;mid++){
            List<Node>left=Helper(start,mid-1);
            List<Node>right=Helper(mid+1,end);
            for(Node l:left){
                for(Node r:right){
                    Node root=new Node(mid);
                    root.left=l;
                    root.right=r;
                    result.add(root);
                }
            }
        }
        return result;
    }
    public List<TreeNode> allPossibleFBT(int n) {
        Map<Integer,List<TreeNode>>dp=new HashMap<>();
        return Helper(n,dp);
    }
    private List<TreeNode> Helper(int n, Map<Integer,List<TreeNode>>dp){
        if(n==0||n%2==0){
            return new ArrayList<>();
        }
        if(n==1){
            List<TreeNode>list=new ArrayList<>();
            list.add(new TreeNode(0));
            return list;
        }
        if(dp.containsKey(n)){
            return dp.get(n);
        }
        List<TreeNode>ans=new ArrayList<>();
        for(int l=0;l<n;l++){
            int r=n-1-l;//root-left
            List<TreeNode>left=Helper(l,dp);
            List<TreeNode>right=Helper(r,dp);
            for(TreeNode le:left){
                for(TreeNode ri:right){
                    TreeNode root=new TreeNode(0);
                    root.left=le;
                    root.right=ri;
                    ans.add(root);
                }
            }
        }
        dp.put(n,ans);
        return ans;
    }
    public static void main(String[] args){
        int[] arr={3,5,6,8,10,11,12};
        Node root=createBST(arr,0,arr.length-1);
        preorder(root);
        System.out.println();
        balanceBST(root);
        preorder(root);
    }
}
