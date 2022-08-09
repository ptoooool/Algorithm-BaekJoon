import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class BaekJoon괄호{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            String str = br.readLine();
            String[] vps = str.split("");

            boolean check = true;
            Stack<String> stack = new Stack<>();
            // 괄호를 스택에 넣어준다.
            for(String s : vps){
                if(s.equals("(")){
                    stack.add(s);
                }else {
                    if(stack.size() == 0) {
                        check = false;
                        break;
                    }else{
                        stack.pop();
                    }
                }
            }

            if(check && stack.size() == 0) System.out.println("YES");
            else System.out.println("NO");

        }
    }
}
