/** A stack abstract data type that contains Strings. */
public class StringStack
{
    // TODO add variables for data here
    public int counter=0;
    public boolean canAdd = true;
    public int size;
    public int position;
    public String[] stringStack;
    /**
     * Constructor for creating a new StringStack with a certain capacity.
     * @param capacity the maximum number of strings the stack can hold
     */
    public StringStack(int capacity)
    {
        stringStack= new String[capacity];
        size = capacity;
        position = size - 1;
    }

    /**
     * Puts the given String on top of the stack (if there is enough space).
     * @param s the String to add to the top of the stack
     * @return false if there was not enough space in the stack to add the string;
     *         otherwise true
     */
    public boolean push(String s) {

        if(counter==size)
        {
            canAdd = false;
        }
        else
        {
            //System.out.println(position);
            stringStack[position] = s;
            counter++;
            position--;
        }
        return canAdd;
    }

    /**
     * Removes the String on top of the stack from the stack and returns it.
     * @return the String on top of the stack, or null if the stack is empty.
     */
    public String pop() {
        if(counter==0)
        {
            return null;
        }
        else
        {
            String popped = stringStack[position+1];
            stringStack[position+1] = null;
            counter=counter -1;
            position++;
            return popped;
        }
    }

    /**
     * Returns the number of Strings in the stack.
     * @return the number of Strings in the stack
     */
    public int count()
    {
        return size;
    }

}
