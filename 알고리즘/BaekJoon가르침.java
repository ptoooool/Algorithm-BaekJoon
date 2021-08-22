import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;


class BaekJoon_15683{
    static class point{
        int row;
        int col;
        point(int row, int col){
            this.row = row;
            this.col = col;
        }
    }
    static int test = 0;
    static ArrayList<point> cctv_List;
    
    static int[][]dir = {{-1,0},{0,1},{1,0},{0,-1}}; // 상 - 우 - 하 - 좌
    static int MIN = Integer.MAX_VALUE;
    public static void main(String[] args) throws IOException{
        /*
        0 - 빈칸, 6 - 벽, 1~5 - CCTV번호
        */
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        cctv_List = new ArrayList<>();

        int[][]Map = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++){
                Map[i][j] = Integer.parseInt(st.nextToken());
                //cctv의 위치를 넣어준다.
                if(Map[i][j] >= 1 && Map[i][j] <= 5){
                    cctv_List.add(new point(i, j));
                }
            }
        }

        recurse(0, cctv_List.size(), new boolean[4], new point(0,0), Map); 

        System.out.println(MIN);
    }

    public static void recurse(int start, int end, boolean[] dir_visit, point p, int[][] Map){
        if(start == end){
            int cnt = 0;
            for(int i = 0; i < Map.length; i++){
                for (int j = 0; j < Map[i].length; j++) {
                    if(Map[i][j] == 0){
                        cnt++;
                    }
                }
            }
            test++;
            MIN = Math.min(MIN, cnt);
            return;
        }
        else{
            int[][] copyList = new int[Map.length][Map[0].length];

            // cctv종류 5가지에 대해서 각각 다른 경우들을 넣어준다.
            int row = cctv_List.get(start).row;
            int col = cctv_List.get(start).col;
            dir_visit = new boolean[4];
            
            //cctv 마다 가능한 모든 방향을 너어준다.
            //dir_visit -> 0 : 상, 1 : 우, 2 : 하, 3 : 좌
            if(Map[row][col] == 1){
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < Map.length; j++) {
                        copyList[j] = Arrays.copyOf(Map[j], Map[j].length);
                    }
                    dir_visit[i] = true;
                    dirCctv(dir_visit, copyList, new point(row, col));
                    recurse(start+1, end, dir_visit, new point(row, col), copyList);
                    dir_visit[i] = false;
                }
            }
            else if(Map[row][col] == 2){
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < Map.length; j++) {
                        copyList[j] = Arrays.copyOf(Map[j], Map[j].length);
                    }
                    dir_visit[i] = true;
                    dir_visit[i+2] = true;
                    dirCctv(dir_visit, copyList, new point(row, col));
                    recurse(start+1, end, dir_visit, new point(row, col), copyList);
                    dir_visit[i] = false;
                    dir_visit[i+2] = false;
                }
            }
            else if(Map[row][col] == 3){
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < Map.length; j++) {
                        copyList[j] = Arrays.copyOf(Map[j], Map[j].length);
                    }
                    dir_visit[i] = true;
                    dir_visit[(i +1) % 4] = true;
                    dirCctv(dir_visit, copyList, new point(row, col));
                    recurse(start+1, end, dir_visit, new point(row, col), copyList);
                    dir_visit[i] = false;
                    dir_visit[(i +1) % 4] = false;
                }
            }
            else if(Map[row][col] == 4){
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < Map.length; j++) {
                        copyList[j] = Arrays.copyOf(Map[j], Map[j].length);
                    }
                    dir_visit[i] = true;
                    dir_visit[(i +1) % 4] = true;
                    dir_visit[(i +2) % 4] = true;
                    dirCctv(dir_visit, copyList, new point(row, col));
                    recurse(start+1, end, dir_visit, new point(row, col), copyList);
                    dir_visit[i] = false;
                    dir_visit[(i +1) % 4] = false;
                    dir_visit[(i +2) % 4] = false;
                }
            }
            else if(Map[row][col] == 5){
                for (int i = 0; i < Map.length; i++) {
                    copyList[i] = Arrays.copyOf(Map[i], Map[i].length);
                }
                dir_visit[0] = true;
                dir_visit[1] = true;
                dir_visit[2] = true;
                dir_visit[3] = true;
                dirCctv(dir_visit, copyList, new point(row, col));
                recurse(start+1, end, dir_visit, new point(row, col), copyList);
                
            }
        }
    }

    public static void dirCctv(boolean[] dir_visit, int[][]Map, point p){
        for (int i = 0; i < dir_visit.length; i++) {
            // 해당 방향으로 CCTV사정범위를 모두 탐색한다.
            if(dir_visit[i]){
                if(i == 0){ // 상
                    for (int j = p.row; j >= 0; j--) {
                        if(Map[j][p.col] == 0){
                            Map[j][p.col] = -1; // 빈칸만 채워준다.
                        }
                        if(Map[j][p.col] == 6){
                            break;
                        }
                    }
                }
                else if(i == 1){ // 우
                    for (int j = p.col; j < Map[p.row].length; j++) {
                        if(Map[p.row][j] == 0){
                            Map[p.row][j] = -1; // 빈칸만 채워준다.
                        }
                        if(Map[p.row][j] == 6){
                            break;
                        }
                    }
                }
                else if(i == 2){ // 하
                    for (int j = p.row; j < Map.length; j++) {
                        if(Map[j][p.col] == 0){
                            Map[j][p.col] = -1; // 빈칸만 채워준다.
                        }
                        if(Map[j][p.col] == 6){
                            break;
                        }
                    }
                }
                else if(i == 3){ // 좌
                    for (int j = p.col; j >= 0; j--) {
                        if(Map[p.row][j] == 0){
                            Map[p.row][j] = -1; // 빈칸만 채워준다.
                        }
                        if(Map[p.row][j]== 6){
                            break;
                        }
                    }
                }
            }
        }
    }
}