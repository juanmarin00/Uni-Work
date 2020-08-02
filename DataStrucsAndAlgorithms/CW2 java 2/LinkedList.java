class LinkedList {
	// Any header stuff
    public ListNode head = null;
    public ListNode tail = null;
    public int count = 0;


    public void addFirst(Object o)
    {
      if(count == 0)
      {
        head = new ListNode(o, head);
        tail = head;
        count++;
      }
      else
      {
        head = new ListNode(o, head);
        count++;
      }
    }


    public Object get(int i)
    {
      if(i<0||i>=count)
      {
        System.out.println("Sorry there is no element here");
        return null;
      }
      else
      {
        ListNode node = head;
        for (int j=0; j<i; j++)
        {
          node = node.next;
        }
        return node.element;
      }
    }


    public void insert(Object o, int i)
    {
      if(i<0||i>count)
      {
        System.out.println("Sorry nothing could be added here");
      }
      else if(i==0)
      {
        addFirst(o);
      }
      else if(i==count)
      {
        add(o);
      }
      else
      {
        ListNode node = head;
        for (int j=0; j<i-1; j++)
        {
          node = node.next;
          node.next = new ListNode(o,node.next);
        }
        count++;
      }
    }


    public void remove(int i)
    {
      if(i<0||i>=count)
      {
        System.out.println("Sorry couldnt remove anything here");
      }
      else if(i==0)
      {
        head = head.next;
        count--;
      }
      else if(i==count-1)
      {
        ListNode node = head;
        for (int j=0; j<i-1; j++)
        {
          node = node.next;

        }
        node.next = node.next.next;
        tail = node;
        count--;
      }
      else
      {
        ListNode node = head;
        for (int j=0; j<i-1; j++)
        {
          node = node.next;

        }
        node.next = node.next.next;
        count--;
      }
    }


    public void add(Object o)
    {
      if(count==0)
      {
        head = new ListNode(o, head);
        tail = head;
        count++;
      }
      else
      {
        ListNode newNode = new ListNode(o,null);
        tail.next = newNode;
        tail = newNode;
        count++;
      }
    }
}
