import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class BaekJoon_1541 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        String str = br.readLine();
        
        // 문자열 형식으로 받아주고, 띄워진 칸은 존재하지않는다.
        // +,- 같은 기호의 경우에는 split에서 \\를 붙여줘야 가능하다.
        String[] lost_list = str.split("\\-");
        int result = 0;
        for (int i = 0; i < lost_list.length; i++) {
            int ans = 0;
            String[] temp =lost_list[i].split("\\+");
            for (String t : temp) {
                ans += Integer.parseInt(t);
            }
            if(i == 0){
                result += ans;
            }else{
                result -= ans;
            }
        }

        System.out.println(result);

    }
}
