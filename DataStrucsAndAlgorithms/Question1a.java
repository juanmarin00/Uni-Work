public class Question1a {
    StringRepeater s = new StringRepeater();

    private double inaccurateTime(int n){

        s.repeatString("Hello", n);

        long start = System.nanoTime();
        s.repeatString("Hello", n);
        long end = System.nanoTime();

        long timeTaken = end - start;
        double timeInSeconds = (double) timeTaken / 1000000000;
        return timeInSeconds;
    }

    public static void main(String[] args) {
        Question1a q1a = new Question1a();

        //Call function once to remove JIT
        q1a.inaccurateTime(1);

        System.out.println("T(1) = " + q1a.inaccurateTime(1) + " seconds");
        System.out.println("T(100) = " + q1a.inaccurateTime(100) + " seconds");
        System.out.println("T(1000) = " + q1a.inaccurateTime(1000) + " seconds");
        System.out.println("T(10000) = " + q1a.inaccurateTime(10000) + " seconds");
    }
}
