public class Question1b {

        public static double timeInSeconds, squareDifferenceSum;
        public static long start, end, timeTaken, totalTime, averageTime;
        public static int numberOfRepetitions = 20000;
        public static double[] times = new double[numberOfRepetitions];
        StringRepeater s = new StringRepeater();

        public double accurateTime(String word,int n)
        {

            totalTime = 0;
            for(int i = 0; i<numberOfRepetitions; i++)
            {
                start = System.nanoTime();
                s.repeatString(word, n);
                end = System.nanoTime();

                timeTaken = end - start;
                times[i] = timeTaken;
                totalTime += timeTaken;
            }

            averageTime = totalTime/numberOfRepetitions;
            squareDifferenceSum = 0.0;
            for (int i = 0; i < numberOfRepetitions; i++)
            {
            squareDifferenceSum += (times[i] - averageTime) * (times[i] - averageTime);
            }
            double variance = squareDifferenceSum / numberOfRepetitions;
            double standardDeviation = Math.sqrt(variance);
            double confidenceLevel = 1.96;
            double temp = confidenceLevel * standardDeviation / Math.sqrt(numberOfRepetitions);
            timeInSeconds = (double) averageTime / 1000000000;
            return timeInSeconds;
        }


        public double inaccurateTime(String word,int n){

            start = System.nanoTime();
            s.repeatString(word, n);
            end = System.nanoTime();

            timeTaken = end - start;
            timeInSeconds = (double) timeTaken / 1000000000;
            return timeInSeconds;
        }


        public static void main(String[] args) {
            Question1b q1b = new Question1b();

            //Call function once to remove JIT

            q1b.inaccurateTime("hello",1);
            System.out.println("T(1) = " + q1b.inaccurateTime("Hello",1) + " seconds");
            System.out.println("T(100) = " + q1b.inaccurateTime("Hello",100) + " seconds");
            System.out.println("T(1000) = " + q1b.inaccurateTime("Hello",1000) + " seconds");
            System.out.println("T(10000) = " + q1b.inaccurateTime("Hello",10000) + " seconds");

            //Call function once to remove JIT

            q1b.accurateTime("Hello",1);
            System.out.println("T(1) = " + q1b.accurateTime("Hello",1) + " seconds");
            System.out.println("T(100) = " + q1b.accurateTime("Hello",100) + " seconds");
            System.out.println("T(1000) = " + q1b.accurateTime("Hello",1000) + " seconds");
            System.out.println("T(10000) = " + q1b.accurateTime("Hello",10000) + " seconds");
            
        }
}
