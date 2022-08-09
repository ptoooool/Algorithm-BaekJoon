import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BaekJoon요세푸스문제 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= N; i++) {
            queue.add(i);
        }
        System.out.print("<");
        while(!queue.isEmpty()){
            for(int i = 0; i < K-1; i++){
                int q = queue.poll();
                queue.add(q); // 삭제한걸 맨 뒤로 보낸다.
            }
            int answer = queue.poll();
            if(queue.isEmpty()) {
                System.out.print(answer);
            }else System.out.print(answer+", ");
        }
        System.out.print(">");
    }
}
