
import java.util.Random;
public class SkipListNode
{
    public String element;
    public SkipListNode[] next;
    public int l = 1;

    public SkipListNode(String e)
    {
        element = e;
        Random r = new Random();
        while (r.nextFloat() < 0.5 && l<=5)
        {
            l++;
        }
        next = new SkipListNode[l];
    }

    public SkipListNode(String e, boolean test)
    {
        this.element = e;
        this.next = new SkipListNode[5];
    }
}
