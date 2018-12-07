package binaryTree;

import java.util.LinkedList;
import java.util.Queue;

public class createTree {
	public static void main(String[] args) {
		createTree bst = new createTree();
        int[] arr = {2,4,5,1,3};
        
        /**
         *    2
         *   / \
         *  1   4
         *     / \
         *    3   5
         */
        
        bst.buildTree(arr);
        bst.printTreeLineByLine(bst.root);
        
//        System.out.println("----------------");
//        
//        bst.buildCompleteTree(arr);
//        bst.printTreeLineByLine(bst.root);
    }
	private TreeNode<Integer> root;
	
	public void buildTree(int[] arr) {
		for(int node: arr) {
			insert(node);
		}
	}
	
	private void insert(int data){
		root = insert(root,data);
	}
	
	private TreeNode<Integer> insert(TreeNode<Integer> root,int data){
		if(root == null) {
			return new TreeNode(data);
		}

		if (root.data > data)
			root.left = insert(root.left, data);
		else if (root.data < data)
			root.right = insert(root.right, data);
		else
			root.left = insert(root.left, data);
		
		return root;
	}
	
	public void printTreeLineByLine(TreeNode root){
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        
        int current = 1;//当前层未打印的结点个数
        int next = 0;//下一层待打印的结点个数
        
        queue.offer(root);
        TreeNode currentNode;
        while(!queue.isEmpty())
        {
            currentNode = queue.poll();
            current--;
            System.out.print(currentNode.data + " ");//打印当前节点
            
            if(currentNode.left != null)
            {
                queue.offer(currentNode.left);
                next++;
            }
            if(currentNode.right != null)
            {
                queue.offer(currentNode.right);
                next++;
            }
            
            if(current == 0)//表示本行所有的结点已经打印完了
            {
                System.out.println();//打印下一行
                current = next;
                next = 0;
            }
        }
	}

	public void buildCompleteTree(int[] nodes) {
		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		root = new TreeNode(nodes[0]);
		TreeNode currentNode = null;

		// queue.offer(root);
		int next = 0;// 标记当前结点的左右孩子是否已经构造完毕
		currentNode = root;// 保存当前正在"构造"的结点
		int count = 1;// 记录数组中的已经构造的元素
		while (count < nodes.length) {
			if (next == 2)// 某结点的左右孩子已经构造好了
			{
				currentNode = queue.poll();
				next = 0;
			}
			if (currentNode.left == null && count < nodes.length) {
				currentNode.left = new TreeNode(nodes[count++]);
				queue.offer(currentNode.left);
				next++;
			}
			if (currentNode.right == null && count < nodes.length) {
				currentNode.right = new TreeNode(nodes[count++]);
				queue.offer(currentNode.right);
				next++;
			}
		}
	}
	
}
