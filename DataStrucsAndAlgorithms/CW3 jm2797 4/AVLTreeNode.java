class AVLTreeNode
{
    int height;
    String key;
    AVLTreeNode left, right;

    AVLTreeNode(String d)
    {
        key = d;
        height = 1;
    }
}
