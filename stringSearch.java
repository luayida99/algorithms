
public class stringSearch {
    public final static int inputSize = 256;

    public static void rabinKarp(String pattern, String text, int prime) {
        int M = pattern.length();
        int N = text.length();
        int patternHash = 0;
        int rollingHash = 0; 
        int h = 1;

        for (int i = 0; i < M-1; i++) {
            h = (h * inputSize) % prime;
        }
     
        for (int i = 0; i < M; i++) {
            patternHash = (inputSize * patternHash + pattern.charAt(i)) % prime;
            rollingHash = (inputSize * rollingHash + text.charAt(i)) % prime;
        }

        for (int i = 0; i <= N - M; i++) {
            if (patternHash == rollingHash) {
                int j;
                for (j = 0; j < M; j++) {
                    if (text.charAt(i+j) != pattern.charAt(j)) {
                        break;
                    }
                }
                if (j == M) {
                    System.out.println("Pattern found at index " + i);
                    break;
                }
            }

            if (i == N - M) {
                System.out.println("Pattern not found");
                break;
            }
     
            if (i < N - M) {
                rollingHash = (inputSize * (rollingHash - text.charAt(i) * h) + text.charAt(i + M)) % prime;

                if (rollingHash < 0) {
                rollingHash += prime;
                }
            }
        }
    }

    public static void main(String[] args) {
        rabinKarp("O", "HELLO WORLD", 101);
    }
}