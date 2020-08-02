public class AVLTree
{
    AVLTreeNode root;

    public AVLTree()
    {
        root = null;
    }
    //Hard code the initial unbalanced tree
    public void createTestTree()
    {
        root = new  AVLTreeNode("4");
        root.right = new AVLTreeNode("6");
        root.left = new AVLTreeNode("2");
        root.right.right = new AVLTreeNode("7");
        root.right.left = new AVLTreeNode("5");
        root.left.right = new AVLTreeNode("3");
        root.left.left = new AVLTreeNode("1");
    }

    //print tree by using traversal from lecture starting at root
    public void print()
    {
        preOrderTraversal(root,0);
    }

    //will use the same traversal to go through each element and check if each element is equal to string e
    public boolean inTree(String e)
    {
        return search(e,root);
    }

    //will call recursion function from lecture which will compare element to be entered to left and right elements and insert it
    public void insert(String e)
    {
        root = insertRecursive(root, e);
    }

    //will call recursively the balanced insert function
    public void insertBalanced(String e)
    {
        root=insertBal(root,e);
    }

    void preOrderTraversal(AVLTreeNode cur,int height)
    {
        String space = "  ";
        if (cur == null)
        {
          return;
        }
        System.out.print(space.repeat(height));

        System.out.println(cur.key);// Process the current AVLTreeNode.
        height ++;
        preOrderTraversal(cur.left,height);     // Process AVLTreeNodes in left sub-tree.
        preOrderTraversal(cur.right,height);  // Process AVLTreeNodes in left sub-tree.
    }

    public static boolean search(String e, AVLTreeNode n)
    {
        if (n == null)
        {
            return false;
        }
        if (e.equals(n.key))
        {
            return true;
        }
        if (e.compareTo(n.key)<0)
        {
            return search(e, n.left);
        }
        return search(e, n.right);
    }

    AVLTreeNode insertRecursive(AVLTreeNode root, String e)
    {
        /* If the tree is empty, return a new AVLTreeNode */
        if (root == null)
        {
            root = new AVLTreeNode(e);
            return root;
        }
        if (e.compareTo(root.key)<0)
        {
          root.left = insertRecursive(root.left, e);
        }

        else if (e.compareTo(root.key)>0)
        {
          root.right = insertRecursive(root.right, e);
        }
        /* return the (unchanged) AVLTreeNode pointer */
        return root;
    }


    int getHeight(AVLTreeNode N)
    {
        int height = N.height;
        if(N == null)
        {
          return 0;
        }
        else
        {
          return height;
        }
    }

    //function to return balance of node, will return 0 if there is no node
    int getBalance(AVLTreeNode N)
    {
        if (N == null)
            return 0;

        return getHeight(N.left) - getHeight(N.right);
    }


    AVLTreeNode insertBal(AVLTreeNode AVLTreeNode, String e)
    {

        //insert as root if there is no nodes in the tree
        if (AVLTreeNode == null)
            return (new AVLTreeNode(e));
        //insert on the left
        if (e.compareTo( AVLTreeNode.key)<0)
            AVLTreeNode.left = insertBal(AVLTreeNode.left, e);
        //insert on the right
        else if (e.compareTo( AVLTreeNode.key)>0)
            AVLTreeNode.right = insertBal(AVLTreeNode.right, e);
        else
            return AVLTreeNode;

        //increase height of node
        AVLTreeNode.height = 1 + Math.max(getHeight(AVLTreeNode.left),getHeight(AVLTreeNode.right));

        //4 re-balancing cases
        // LR
        if (getBalance(AVLTreeNode) > 1 && e.compareTo( AVLTreeNode.left.key)>0)
        {
            AVLTreeNode.left = leftRotate(AVLTreeNode.left);
            return rightRotate(AVLTreeNode);
        }
        // RL
        if (getBalance(AVLTreeNode) < -1 && e.compareTo( AVLTreeNode.right.key)<0)
        {
            AVLTreeNode.right = rightRotate(AVLTreeNode.right);
            return leftRotate(AVLTreeNode);
        }
        // LL
        if (getBalance(AVLTreeNode) > 1 && e.compareTo( AVLTreeNode.left.key)<0)
        {
          return rightRotate(AVLTreeNode);
        }
        // RR
        if (getBalance(AVLTreeNode) < -1 && e.compareTo( AVLTreeNode.right.key)>0)
        {
          return leftRotate(AVLTreeNode);
        }

        return AVLTreeNode;
    }



    //function to carry out on a subtree right rotation
    AVLTreeNode rightRotate(AVLTreeNode toRotate)
    {
        AVLTreeNode leftNode = toRotate.left;
        AVLTreeNode temp = leftNode.right;

        // Perform rotation
        leftNode.right = toRotate;
        toRotate.left = temp;

        // Update heights
        toRotate.height = Math.max(getHeight(toRotate.left), getHeight(toRotate.right)) + 1;
        leftNode.height = Math.max(getHeight(leftNode.left), getHeight(leftNode.right)) + 1;

        // Return new root
        return leftNode;
    }

    //function to carry out on a subtree left rotation
    AVLTreeNode leftRotate(AVLTreeNode toRotate)
    {
        AVLTreeNode rightNode = toRotate.right;
        AVLTreeNode temp = rightNode.left;

        // Perform rotation
        rightNode.left = toRotate;
        toRotate.right = temp;

        //  Update heights
        toRotate.height = Math.max(getHeight(toRotate.left), getHeight(toRotate.right)) + 1;
        rightNode.height = Math.max(getHeight(rightNode.left), getHeight(rightNode.right)) + 1;

        // Return new root
        return rightNode;
    }
}
