import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Stack;

/**
 * Program class for an SRPN calculator.
 */

public class SRPN
{

  int randomCounter = 0;
  Stack<String> SRPNStack = new Stack<String>(); //Stack is created where the inputs can only be strings


  public String bigIntGenerator(String b)
  //this method will check if inputted string exceed the maximum value for an integer
  {
    String max = Integer.toString(2147483647);//max value for a positive Integer
		String min = Integer.toString(-2147483648);//minimum value for a negative Integer

    //These three new variables turn integers above into BigInteger
		BigInteger bg = new BigInteger(b);
		BigInteger maxInt = new BigInteger(max);
		BigInteger minInt = new BigInteger(min);

		if (bg.compareTo(maxInt) == 1)
    //compareto() will compare the input and Maxint and if the two strings are
    //equal it will return our max value and min value in the case of our string being negative
    {
			return max;
		}

		else if (bg.compareTo(minInt) == -1)
    {
			return min;
		}

		return b;
  }


  public boolean stackManager()
  /*This method will use stack.size() to make sure that the maximum number of
  elements in our stack wont exceed 23 and return true in the case of our stack
  overflowing*/
  {
    if (SRPNStack.size() >= 23)
    {
      System.out.println("Stack overflow.");
      return true;
    }

    else
    {
      //this will return false in the case of the number of elements in our
      //stack being less than 23
      return false;
    }
  }



	public String randomNumberGenerator()
  {
    //create an array with every possible value of our variable r
		String[] rArray = { "1804289383", "846930886", "1681692777", "1714636915", "1957747793", "424238335",
				"719885386", "1649760492", "596516649", "1189641421", "1025202362", "1350490027", "783368690",
				"1102520059", "2044897763", "1967513926", "1365180540", "1540383426", "304089172", "1303455736",
				"35005211", "521595368"};

    /*Define a counter (randomCounter) on line 14 which will be used to print
    out the corresponding value of r for the amount of times which r has been
    called prior to that within our stack*/
    if (randomCounter > 22)
    {
      /*if our counter reaches a value higher than 22, then we will have a
      stack overflow and we will run out of values in our array, therefore
      our counter will be resetted to 0, therefore when r is called again it
      will return the first value of our array*/
    	int randomCounter = 0;
    	return rArray[randomCounter];

    }

    else
    {
      return rArray[randomCounter];

    }
  }


  public void octalConverter(String o)
  /*This method will take in an octal number which will be detected as it startsWith
  with 0 and then turn it into an integer of decicmal value and then turn that into
  a string and append it to our stack*/
  {
    Integer octalInteger = Integer.parseInt(o);
		Integer decimal = 0;
		Double orderCounter = 0.0;

    /*we will use a very similar function to that used in lab3ex4 however we will
    change the base to 8 instead of 16*/
		while (octalInteger != 0)
    {

			decimal = decimal + (octalInteger % 10) * (int)Math.pow (8 , orderCounter);
			orderCounter=orderCounter+1;
			octalInteger /= 10;
		}

    String octalFinal = Integer.toString(decimal);
		if (stackManager() == false)
    {
			System.out.println(octalFinal);
		}

  }



  public void processOperators(String s)
    {
      /*We will input strings onto the command line which will then be checked wether they are Ints
      If the input in is an int, it will be pushed onto the Stack
      If the input is not a number, it is therefore an operator which will perform an operation (+,-,*,/,%)
      If the input is a letter like d or r it will perform its designated purpose*/
      try
      {
        int a = Integer.parseInt(s);
        if (stackManager() == false)
        {
          SRPNStack.push(s);
        }
      }

      catch (NumberFormatException e)
      {

        if (SRPNStack.size() > 1) //Checks if there are enough elements in the stack to perform the operation
        {

          String a, b, operation, result, saturationCheck;
          long pop1,pop2;

          //In each case, the two numbers that where popped are used in the operation to give a result
          //The result is converted from Integer to String
          //The result is pushed back into the SRPNStack

            if (s.charAt(s.length()-1) == '+')
            {

              //Converts them from String to Long
              pop2 = Long.parseLong(SRPNStack.pop());
              pop1 = Long.parseLong(SRPNStack.pop());

              operation = Long.toString(pop1 + pop2);
              saturationCheck = bigIntGenerator(operation);
              SRPNStack.push(saturationCheck);
            }

            else if (s.charAt(s.length()-1) == '-')
              {

                pop2 = Long.parseLong(SRPNStack.pop());
                pop1 = Long.parseLong(SRPNStack.pop());

                operation = Long.toString(pop1 - pop2);
                saturationCheck = bigIntGenerator(operation);
                SRPNStack.push(saturationCheck);
              }

            else if (s.charAt(s.length()-1) == '*')
              {
                pop2 = Long.parseLong(SRPNStack.pop());
                pop1 = Long.parseLong(SRPNStack.pop());

                operation = Long.toString(pop1 * pop2);
                saturationCheck = bigIntGenerator(operation);
                SRPNStack.push(saturationCheck);
              }

            else if (s.charAt(s.length()-1) == '/')
            {

              pop2 = Long.parseLong(SRPNStack.pop());
              pop1 = Long.parseLong(SRPNStack.pop());

              if(pop2 == 0)
              //A fraction can't be divisible by 0,
              //therefore numbers are pushed back into the stack
              {
                System.out.println("Divide by 0.");
                a = Long.toString(pop1);
                b = Long.toString(pop2);
                SRPNStack.push(b);
                SRPNStack.push(a);
              }

              else
              {
              operation = Long.toString(pop1 / pop2);
              SRPNStack.push(operation);
              }

            }

            else if (s.charAt(s.length()-1) == '%')
            {

              //Converts them from String to Integer
              pop2 = Long.parseLong(SRPNStack.pop());
              pop1 = Long.parseLong(SRPNStack.pop());

              operation = Long.toString(pop1 % pop2);
              //result=bigInt(operation);
              SRPNStack.push(operation);
            }

            else if (s.charAt(s.length()-1) == '^')
            {
              //Converts them from String to Integer
              pop2 = Long.parseLong(SRPNStack.pop());
              pop1 = Long.parseLong(SRPNStack.pop());


              if(pop2<0)
              {
                System.out.println("Negative power.");

                String push1=Long.toString(pop1);
                String push2=Long.toString(pop2);

                SRPNStack.push(push1);
                SRPNStack.push(push2);

              }

              else
              {
                operation = Long.toString((int) Math.pow(pop1, pop2));
                a = Long.toString(pop1);
                b = Long.toString(pop2);
                SRPNStack.push(operation);
              }
            }


            else if (s.charAt(s.length()-1) == '=')
            {
              System.out.println(SRPNStack.peek());
            }


            else if (s.charAt(s.length()-1) == 'd')
            //turn our stack into an array to find size and print every single item
            {
              Object[] Array = SRPNStack.toArray();
              //create a for loop which will cycle through each element and print them in different lines
    			    for (int counter = 0; counter < Array.length; counter++)
              {
                System.out.println(Array[counter]);
              }
            }


            else if (s.charAt(s.length()-1) == 'r')
            /*We created three different instances for 'r', as it needed to carry
             out the same function regardless of the size of the stack priorly*/
            {
              if (stackManager()==false)
              {
                SRPNStack.push(randomNumberGenerator());
                randomCounter=randomCounter+1;
              }
            }
        }


        else if(SRPNStack.size() == 1)
        /*create an if/else if statement for each operator, this statement will
        print out "stack underflow" if their is only one operand in the stack,
        as more than one needs to be present for any of these operations to take place*/
        {
          if (s.charAt(s.length()-1)=='=')
          {
            String popoct =SRPNStack.peek();
            if(popoct.charAt(0)=='0' && popoct.length()>1)
            {
              octalConverter(popoct);
            }

            else
            {
              System.out.println(SRPNStack.peek());
            }

          }

          else if (s.charAt(s.length()-1) == 'r')
          {
            if (stackManager()==false)
            {
              SRPNStack.push(randomNumberGenerator());
              randomCounter=randomCounter+1;
            }
          }

          else if (s.charAt(s.length()-1) == '+')
          {
            System.out.println("Stack underflow.");
          }

          else if (s.charAt(s.length()-1) == '-')
          {
            System.out.println("Stack underflow.");
          }

          else if (s.charAt(s.length()-1) == '*')
          {
            System.out.println("Stack underflow.");
          }

          else if (s.charAt(s.length()-1) == '/')
          {
            System.out.println("Stack underflow.");
          }

          else if (s.charAt(s.length()-1) == '%')
          {
            System.out.println("Stack underflow.");
          }

          else if (s.charAt(s.length()-1) == '^')
          {
            System.out.println("Stack underflow.");
          }

          else
          {
            System.out.println(SRPNStack.peek());
          }
        }

        else
        {
          if (s.charAt(s.length()-1) == 'r')
          {
            if (stackManager()==false)
            {
              SRPNStack.push(randomNumberGenerator());
              randomCounter=randomCounter+1;
            }
          }
        }
      }
    }


    public static void main(String[] args)
    {

        SRPN sprn = new SRPN();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try
        {
            //Keep on accepting input from the command-line
            while(true)
              {
                String command = reader.readLine();

                //Close on an End-of-file (EOF) (Ctrl-D on the terminal)
                if(command == null)
                {
                  //Exit code 0 for a graceful exit
                  System.exit(0);
                }

                //Otherwise, (attempt to) process the character
                if (command.contains(" "))
                /*this if statement will use .contains() to see if there is a
                space in our input, in which case it will detect it as an operation
                which will be carried out on the same line unlike the rest*/
                {
                  String noSpaces = command.replaceAll(" ","");
                  Integer stringLength = noSpaces.length();
                  for(Integer i = 0 ; i<stringLength ; i++)
                  /*if a space is detected it will be removed by .replaceall()
                  and then the if loop will cycle through all integers and
                  operators with the processOperator function*/
                  {
                    String infixOperation = Character.toString(noSpaces.charAt(i));
                    sprn.processOperators(infixOperation);
                  }

                }

                else
                {
                  sprn.processOperators(command);
                }

              }

        }
          catch(IOException e)
          {
          System.err.println(e.getMessage());
          System.exit(1);
          }
    }
}
