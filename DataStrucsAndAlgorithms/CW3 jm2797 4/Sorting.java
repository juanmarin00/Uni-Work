import java.util.Random;

public class Sorting
{
    public static int[][] hybridSort (int[][] data,int threshold)
    {
        int n = data.length;
        if(n<threshold)
        {
            insertionSort(data);
            return data;
        }
        // to complete
        int mid = data.length/2;
        int[][] l= new int[mid][];
        int[][] r= new int[data.length-mid][];

        for (int i = 0; i < mid; i++)
        {
            l[i] = data[i];
        }

        for (int i = mid; i < data.length; i++)
        {
            r[i - mid] = data[i];
        }
        mergeSort(l);
        mergeSort(r);

        merge(data, l, r, mid, data.length - mid);
        return data;

    }



    public static int[][] mergeSort (int[][] data)
    {
        int n = data.length;
        if(n<2)
        {
            return data;
        }
        // to complete
        int mid = data.length/2;
        int[][] l= new int[mid][];
        int[][] r= new int[data.length-mid][];

        for (int i = 0; i < mid; i++)
        {
            l[i] = data[i];
        }

        for (int i = mid; i < data.length; i++)
        {
            r[i - mid] = data[i];
        }
        mergeSort(l);
        mergeSort(r);

        merge(data, l, r, mid, data.length - mid);


        return data;
    }


    public static void merge(int[][] a, int[][] l, int[][] r, int left, int right)
    {

        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (compareLines(l[i],r[j])==-1)
            {
                a[k++] = l[i++];
            }
            else
            {
                a[k++] = r[j++];
            }
        }
        while (i < left)
        {
            a[k++] = l[i++];
        }
        while (j < right)
        {
            a[k++] = r[j++];
        }
    }


    public static void insertionSort(int[][] input)
    {

        int []temp;
        for (int i = 1; i < input.length; i++)
        {
            for(int j = i ; j > 0 ; j--)
            {
                if(compareLines(input[j],input[j-1])==-1)
                {
                    temp = input[j];
                    input[j] = input[j-1];
                    input[j-1] = temp;
                }
            }
        }
    }


    public static int compareLines(int[] a,int[] b)
    {
        int n=a.length;
        if (n != b.length)
        {
            return (a[b.length-1]+b[a.length-1]);  // this gives an error
        }
        int i=0;
        while (i<n && a[i]==b[i])
        {
            i++;   // skip equal elements
        }
        if (i==n)
        {
            return 0;
        }
        if (a[i]<b[i])
        {
            return -1;
        }
        else
        {
            return 1;
        }
    }


    public static void printMatrix(int m[][])
    {
        for (int i = 0; i < m.length; i++)
        {
            for (int j = 0; j < m[i].length; j++)
                System.out.print(m[i][j] + " ");
            System.out.println();
        }
    }


    public static int[][] createArray(int Dimension1, int Dimension2)
    {
        Random rand = new Random();
        int [][]myArray = new int[Dimension1][Dimension2];
        for(int i=0;i<myArray.length;i++)
        {
            for(int j =0;j<myArray[i].length;j++)
            {
                int rand_int3 = rand.nextInt(10);
                myArray[i][j] = rand_int3;
            }
        }

        System.out.println("Array to be sorted:");
        printMatrix(myArray);

        return myArray;
    }
}
