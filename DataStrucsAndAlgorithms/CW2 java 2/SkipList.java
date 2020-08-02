
class SkipList
{
    private SkipListNode[] head;
    private int n = 0; // list size
    private int maxLevel = 4;


    public SkipList()
    {
        this.head = new SkipListNode[5];
        for(int i = 0; i<maxLevel+1; i++)
        {
            head[i] = new SkipListNode(null,true);
        }
    }


    public void createTestList()
    {
        SkipListNode a = new SkipListNode("Anne",true);
        SkipListNode b = new SkipListNode("Ben",true);
        SkipListNode c = new SkipListNode("Charlie",true);
        SkipListNode d = new SkipListNode("Don",true);
        SkipListNode e = new SkipListNode("Ernie",true);
        a.next[0] = b;
        b.next[0] = c;
        c.next[0] = d;
        d.next[0] = e;
        e.next[0] = null;

        a.next[1] = c;
        c.next[1] = e;

        a.next[2] = e;

        int end = maxLevel +1;
        for(int i=0;i<3;i++)
        {
            if(i<3)
            {
                head[i].next[i] = a;
            }
            else
            {
                head[i].next[i] = null;
            }
        }
        n = end;
    }


    public void print()
    {
        for(int i = maxLevel; i >= 0 ; i--)
        {
            SkipListNode currentNode = head[i];
            while(currentNode != null)
            {
                if (currentNode.element != null)
                {
                    if (currentNode.next[i] == null)
                    {
                        System.out.print(currentNode.element);
                    }
                    else
                    {
                        System.out.print(currentNode.element + ",");
                    }
                }
                currentNode = currentNode.next[i];
            }
            System.out.println();
        }
    }

    public boolean inList(String s)
    {
        int i = maxLevel;
        boolean isFound = false;
        for (int nodeTransverse = maxLevel; nodeTransverse >= 0;)
        {
            if (head[nodeTransverse].next[nodeTransverse] != null)
            {
                i = nodeTransverse;
                break;
            }
            else if (head[nodeTransverse].next[nodeTransverse] == null)
            {
                nodeTransverse--;
            }
        }

        SkipListNode currentNode = head[i];
        while(i >= 0) {
            while (currentNode.next[i] != null && currentNode.next[i].element.compareTo(s) <= 0)
            {
                currentNode = currentNode.next[i];
                if (currentNode.element.compareTo(s) == 0)
                {
                    isFound = true;
                }
            }
            i--;
        }

        SkipListNode checkNode = head[0];
        for(int checkCounter =0;checkCounter<n; checkCounter++)
        {
            if (checkNode.next[0].element.compareTo(s) == 0)
            {
                isFound = true;
            }
            checkNode = checkNode.next[0];
        }
        if(isFound==true)
        {
            System.out.println(s + " was found");
        }
        else
        {
            System.out.println(s + " was not found in the list. Sorry");
        }

        return isFound;
    }


    public void insert(String s)
    {
        n++;
        SkipListNode newNode = new SkipListNode(s);
        int max = maxLevel;
        SkipListNode[] newList = new SkipListNode[maxLevel+1];
        for (int levelCounter = maxLevel; levelCounter >= 0;)
        {
            if (head[levelCounter].next[levelCounter] == null)
            {
                levelCounter--;
            }
            else if (head[levelCounter].next[levelCounter] != null)
            {
                max = levelCounter;
                break;
            }
        }

        SkipListNode currentNode = head[max];
        while(max >= 0)
        {
            while (currentNode.next[max] != null && currentNode.next[max].element.compareTo(s) <= 0)
            {
                currentNode = currentNode.next[max];
                if (max != 0)
                {
                    newList[max] = currentNode;
                }
            }
            if(max == 0)
            {
                if(currentNode.element != null)
                {
                    newNode.next[max] = currentNode.next[max];
                    currentNode.next[max] = newNode;

                }
                else
                {
                    for (int j = 0; j < maxLevel+1; j++)
                    {
                        if(j < newNode.l)
                        {
                            SkipListNode tempHead = head[j].next[j];
                            head[j].next[j] = newNode;
                            newNode.next[j] = tempHead;
                        }
                    }
                }
            }
            max--;
        }

        for (int levelCounter = 0; levelCounter < maxLevel+1; levelCounter++)
        {
            if(newList[levelCounter] != null && levelCounter < newNode.l)
            {
                SkipListNode temp = newList[levelCounter].next[levelCounter];
                newList[levelCounter].next[levelCounter] = newNode;
                newList[levelCounter] = newNode;
                newList [levelCounter].next[levelCounter] = temp;
            }
        }
    }
}
