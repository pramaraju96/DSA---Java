package DSAinJava.BinaryTree;
import java.util.*;
public class BasicOperations {
    static class Node {
        int data;
        Node left;
        Node right;
        Node(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }
    static class binaryTree {
        static int idx = -1;
        public static Node buildTree(int[] nodes) {
            idx++;
            if (nodes[idx] == -1) {
                return null;
            }
            Node newNode = new Node(nodes[idx]);
            newNode.left = buildTree(nodes);
            newNode.right = buildTree(nodes);
            return newNode;
        }

        public static void preorder(Node root) {
            if (root == null) {
                return;
            }
            System.out.print(root.data + " ");
            preorder(root.left);
            preorder(root.right);
        }

        public static void inorder(Node root) {
            if (root == null) {
                return;
            }
            inorder(root.left);
            System.out.print(root.data + " ");
            inorder(root.right);
        }

        public static void postorder(Node root) {
            if (root == null) {
                return;
            }
            postorder(root.left);
            postorder(root.right);
            System.out.print(root.data + " ");
        }
        public static void levelOrder(Node root) {
            if (root == null) {
                return;
            }
            Queue<Node> q = new LinkedList<>();
            q.add(root);
            q.add(null);//for next line
            while (!q.isEmpty()) {
                Node currNode = q.remove();
                if (currNode == null) {
                    System.out.println();
                    if (q.isEmpty()) {
                        break;
                    } else {
                        q.add(null);
                    }
                } else {
                    System.out.print(currNode.data + " ");
                    if (currNode.left != null) {
                        q.add(currNode.left);
                    }
                    if (currNode.right != null) {
                        q.add(currNode.right);
                    }
                }
            }
        }
        public static int height(Node root) {
            if (root == null) {
                return 0;
            }
            int lh = height(root.left);
            int rh = height(root.right);
            int h = Math.max(lh, rh) + 1;
            return h;
        }
        public static int nodeCount(Node root) {
            if (root == null) {
                return 0;
            }
            int lc = nodeCount(root.left);
            int rc = nodeCount(root.right);
            int c = lc + rc + 1;
            return c;
        }
        public static int nodeSum(Node root) {
            if (root == null) {
                return 0;
            }
            int ls = nodeSum(root.left);
            int rs = nodeSum(root.right);
            int s = ls + rs + root.data;
            return s;
        }
        static int max = 0;
        public static int diameter(Node root) {
            maxDepth(root);
            return max;
        }
        private static int maxDepth(Node root) {
            if (root == null) return 0;

            int left = maxDepth(root.left);
            int right = maxDepth(root.right);

            max = Math.max(max, left + right);

            return Math.max(left, right) + 1;
        }
        public int minDepth(Node root) {
            if(root==null){
                return 0;
            }
            int left=minDepth(root.left);
            int right=minDepth(root.right);
            if(left==0||right==0){
                return Math.max(left,right)+1;
            }
            return Math.min(left,right)+1;
        }

        //approach 2
         static class Info {
            int diam;
            int height;

            public Info(int diam, int height) {
                this.diam = diam;
                this.height = height;
            }
        }
        public static Info diameter1(Node root) {
            if (root == null) {
                return new Info(0, 0);
            }
            Info leftInfo = diameter1(root.left);
            Info rightInfo = diameter1(root.right);
            int di = Math.max(Math.max(leftInfo.diam, rightInfo.diam), leftInfo.height + rightInfo.height + 1);
            int ht = Math.max(leftInfo.height, rightInfo.height) + 1;
            return new Info(di, ht);
        }
        public static boolean isIdentical(Node node, Node subroot) {
            if (node == null && subroot == null) {
                return true;
            } else if (node == null || subroot == null || node.data != subroot.data) {
                return false;
            }
            if (!isIdentical(node.left, subroot.left)) {
                return false;
            }
            if (!isIdentical(node.right, subroot.right)) {
                return false;
            }
            return true;
        }
        public static boolean isSubtree(Node root, Node subRoot) {
            if (root == null) {
                return false;
            }
            if (root.data == subRoot.data) {
                if (isIdentical(root, subRoot)) {
                    return true;
                }
            }
//            boolean leftAns=isSubtree(root.left,subRoot);
//            boolean rightAns=isSubtree(root.left,subRoot);
//            return leftAns||rightAns;
            return isSubtree(root.left, subRoot) || isSubtree(root.left, subRoot);
        }
        static class Info1 {
            Node node;
            int hd;
            public Info1(Node node, int hd) {
                this.node = node;
                this.hd = hd;
            }
        }
        public static void topView(Node root) {
            //level order traversal
            Queue<Info1> q = new LinkedList<>();
            HashMap<Integer, Node> map = new HashMap<>();
            int min = 0, max = 0;
            q.add(new Info1(root, 0));
            q.add(null);
            while (!q.isEmpty()) {
                Info1 curr = q.remove();
                if (curr == null) {
                    if (q.isEmpty()) {
                        break;
                    } else {
                        q.add(null);
                    }
                } else {
                    //first time the horizontal distance is occurring
                    //comment this condition to get bottom view
                    if (!map.containsKey(curr.hd)) {
                        map.put(curr.hd, curr.node);
                    }
                    if (curr.node.left != null) {
                        q.add(new Info1(curr.node.left, curr.hd - 1));
                        min = Math.min(min, curr.hd - 1);
                    }
                    if (curr.node.right != null) {
                        q.add(new Info1(curr.node.right, curr.hd + 1));
                        max = Math.max(max, curr.hd + 1);
                    }
                }
            }
            for (int i = min; i <= max; i++) {
                System.out.print(map.get(i).data + " ");
            }
            System.out.println();
        }
        public static void KthLevel(Node root, int level, int k) {
            if (root == null) {
                return;
            }
            if (level == k) {
                System.out.print(root.data + " ");
                return;
            }
            KthLevel(root.left, level + 1, k);
            KthLevel(root.right, level + 1, k);
        }
        public static Node lca(Node root, int n1, int n2) {//O(n)
            ArrayList<Node> path1 = new ArrayList<>();
            ArrayList<Node> path2 = new ArrayList<>();

            getPath(root, n1, path1);
            getPath(root, n2, path2);

            //last common ancestor
            int i = 0;
            for (; i < path1.size() && i < path2.size(); i++) {
                if (path1.get(i) != path2.get(i)) {
                    break;
                }
            }
            //last equal node is i-1
            Node lca = path1.get(i - 1);
            return lca;
        }
        public static boolean getPath(Node root, int n, ArrayList<Node> path) {
            if (root == null) {
                return false;
            }
            path.add(root);
            if (root.data == n) {
                return true;
            }
            boolean foundLeft = getPath(root.left, n, path);
            boolean foundRight = getPath(root.right, n, path);
            if (foundLeft || foundRight) {
                return true;
            }
            path.remove(path.size() - 1);
            return false;
        }
        public static Node lca2(Node root, int n1, int n2) {
            if (root==null||root.data == n1 || root.data == n2) {
                return root;
            }
            Node leftlca=lca2(root.left,n1,n2);
            Node rightlca=lca2(root.right, n1, n2);
            if(rightlca==null){
                return leftlca;
            }
            if(leftlca==null){
                return rightlca;
            }
            return root;
        }
        public static int distance(Node root,int n1,int n2){
            Node lca=lca2(root, n1, n2);
            int dis1=lcaDis(lca,n1);
            int dis2=lcaDis(lca,n2);
            return dis1+dis2;
        }
        public static int lcaDis(Node root,int n){
            if(root==null){
                return -1;
            }
            if(root.data==n){
                return 0;
            }
            int leftDis=lcaDis(root.left,n);
            int rightDis=lcaDis(root.right,n);
            if(leftDis==-1&&rightDis==-1){
                return -1;
            }
            else if(leftDis==-1){
                return rightDis+1;
            }
            else{
                return leftDis+1;
            }
        }
        public static int Kancestor(Node root,int n,int k){
            if(root==null){
                return -1;
            }
            if(root.data==n){
                return 0;
            }
            int leftDis=Kancestor(root.left,n,k);
            int rightDis=Kancestor(root.right,n,k);
            if(leftDis==-1&&rightDis==-1){
                return -1;
            }
            //valid distance will be greater than -1
            int max=Math.max(leftDis,rightDis);
            if(max+1==k){//node exists in subtree
                System.out.println(root.data);
            }
            return max+1;
        }
        public static int Sumtree(Node root){
            if(root==null){
                return 0;
            }
            int data= root.data;
            root.data=Sumtree(root.left)+Sumtree(root.right);
            return data+root.data;
        }
        public static boolean uniValued(Node root){
            if(root==null){
                return false;
            }
            if(root.left!=null&&root.data!=root.left.data){
                return true;
            }
            if(root.right!=null&&root.data!=root.right.data){
                return true;
            }
            boolean leftCheck=uniValued(root.left);
            boolean rightCheck=uniValued(root.right);
            return leftCheck&&rightCheck;
        }
        public static Node mirror(Node root){
            if(root==null){
                return null;
            }
            Node left=mirror(root.left);
            Node right=mirror(root.right);
            //swapping nodes
            root.left=right;
            root.right=left;
            return root;
        }
        public static Node delete(Node root,int x){
            if(root==null){
                return null;
            }
            root.left=delete(root.left,x);
            root.right=delete(root.right, x);
            if(root.data==x&&root.left==null&&root.right==null){
                return null;
            }
            return root;
        }
        public static int HouseRobber(Node root){
            int[] ans=HouseRobberHelper(root);
            return Math.max(ans[0],ans[1]);
        }
        public static int[] HouseRobberHelper(Node root){
            if(root==null){
                return new int[2];
            }
            int[] left=HouseRobberHelper(root.left);
            int[] right=HouseRobberHelper(root.right);
            int[] ans=new int[2];
            //0->excluded.....1->included
            ans[0]=Math.max(left[0],left[1])+Math.max(right[0],right[1]);
            ans[1]=root.data+left[0]+right[0];
            return ans;
        }
        public int maxPathSum(Node root){
//array take since variable can't be passed as reference
            int[] max =new int[1];
            max[0]=Integer.MIN_VALUE;
            maxPathHelper(root,max);
            return max[0];
        }
        public int maxPathHelper(Node node, int[] max){
            if(node==null){
                return 0;
            }
            //in case of negative outputs
            int left=Math.max(0,maxPathHelper(node.left,max));
            int right=Math.max(0,maxPathHelper(node.right,max));
            max[0]=Math.max(max[0],left+right+node.data);
            return Math.max(left,right)+node.data;
        }
    }
        public static void main(String[] args) {
            int[] nodes = {1, 2, 4, -1, -1, 5, -1, -1, 3, -1, 6, -1, -1};
            binaryTree tree = new binaryTree();
            Node root = tree.buildTree(nodes);
            System.out.println(root.data);

            Node subRoot = new Node(2);
            subRoot.left = new Node(4);
            subRoot.right = new Node(5);

            tree.preorder(root);
            System.out.println();
            tree.inorder(root);
            System.out.println();
            tree.postorder(root);
            System.out.println();
            tree.levelOrder(root);
            System.out.println(tree.height(root));
            System.out.println(tree.nodeCount(root));
            System.out.println(tree.nodeSum(root));
            System.out.println(tree.diameter(root));
            System.out.println(tree.diameter1(root).diam);
            System.out.println(tree.isSubtree(root, subRoot));
            tree.topView(root);
            tree.KthLevel(root,1,2);
            System.out.println();
            System.out.println(tree.lca(root,2,3).data);
            System.out.println(tree.lca2(root,4,5).data);
            System.out.println(tree.distance(root,4,6));
            tree.Kancestor(root,5,2);
            tree.Sumtree(root);
            tree.preorder(root);
            System.out.println();
            System.out.println(tree.uniValued(root));
            tree.mirror(root);
            tree.preorder(root);
            System.out.println();
            tree.delete(root,0);
            tree.preorder(root);
            System.out.println();
            System.out.println(tree.maxPathSum(root));
        }

}

