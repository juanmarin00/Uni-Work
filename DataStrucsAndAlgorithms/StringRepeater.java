public class StringRepeater {
    StringBuffer result = new StringBuffer();

    public  StringBuffer repeatString(String s, int n){
        for(int i = 0; i < n; i++) {
            result.append(s);
        }
        return result;
    }
}
