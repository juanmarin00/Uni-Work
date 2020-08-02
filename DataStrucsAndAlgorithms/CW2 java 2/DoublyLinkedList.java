class DoublyLinkedList
{
    public ListNode2 head = null;
    public ListNode2 tail = null;
    public int count = 0; // list size


    public void print() {
        // no elements to print for empty list
        if (head == null) {
            System.out.println("list empty.");
            return;
        }

        // follow next references to list elements from the front to the back of the list
        System.out.print("front to back: ");
        ListNode2 node = head;
        System.out.print(node.element + " ");
        while (node.next != null) {
            node = node.next;
            System.out.print(node.element + " ");
        }

        // follow prev references to list elements from the back to the front of the list
        System.out.print("-- and back to front: ");
        while (node != null) {
            System.out.print(node.element + " ");
            node = node.prev;
        }
        System.out.println();
    }


    public void addFirst(Object o)
    {
      ListNode2 newNode = new ListNode2(o,null,head);
      if(count>0)
      {
        head.prev = newNode;
        head = newNode;
      }

      else
      {
        head = newNode;
        tail = newNode;
      }
      count++;
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
        ListNode2 node = head;
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

      else if(head == null)
      {
        addFirst(o);
      }

      else if(i==0)
      {
        ListNode2 newNode = new ListNode2(o,null,null);
        newNode.next = head;
        head.prev = newNode;
        head = newNode;
        count++;

      }

      else if (i == count)
      {
        ListNode2 newNode = new ListNode2(o,null,null);
        newNode.prev = tail;
        tail.next = newNode;
        tail = newNode;
        count++;

      }

      else
      {
        ListNode2 newNode = new ListNode2(o,null,null);
        ListNode2 nodeReference = head;
        for (int j=0; j<i-1; j++)
        {
          nodeReference = nodeReference.next;
          //node.next = new ListNode2(o,node,node.next);
        }
        newNode.next = nodeReference.next;
        nodeReference.next = newNode;
        newNode.prev = nodeReference;
        newNode.next.prev = newNode;


        count++;
      }
    }


    public void remove(int i)
    {
      if(i<0||i>=count)
      {
        System.out.println("Sorry couldnt remove anything here");
      }

      else if(i==0 && count != 1)//delete head
      {
        head.next.prev = null;
        head = head.next;
        count--;
      }
      else if(i==0 && count == 1)
      {
        head = null;
        tail = null;
        count--;
      }
      else
      {
        ListNode2 node = head;
        for (int j=0; j<i-1; j++)
        {
          node = node.next;
        }

        if(node.next != null && node.prev == null)
        {
          if(count>2)
          {
            node.next = node.next.next;
            node.next.prev = node;
          }
          else
          {
            node.next = null;
          }

        }

        else if(node.next != null && node.prev != null)
        {
          node.next = node.next.next;
          node.next.next.prev = node;
        }
        count--;
      }
    }
}
