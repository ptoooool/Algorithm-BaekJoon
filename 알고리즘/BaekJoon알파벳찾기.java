import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BaekJoon알파벳찾기 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String str = br.readLine();
        int[] answer = new int[26];
        char[] strArr = str.toCharArray();
        Arrays.fill(answer, -1);

        for(int i = 0; i < strArr.length; i++){
            if(answer[strArr[i]-97] == -1)
                answer[strArr[i]-97] = i;
        }

        for(int a : answer)
            System.out.print(a + " ");


    }
}
