import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class BaekJoon_2573 {
    static int start_row = 0; // BFS를 돌때 필요한 시작점
    static int start_col = 0; 
    static int year = 1; // 빙산이 둘 이상으로 나뉘어지는데 거리는 년도 
    static int cnt = 0; // 빙산개수
    static int[][] dir = {{1,0}, {-1,0}, {0,1}, {0,-1} }; 
    static int[][] Map; 
    static int N, M;
    static class point {
        int row; int col; 
        point(int row, int col) {
            this.row = row; this.col = col;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); 
        M = Integer.parseInt(st.nextToken()); 
        Map = new int[N][M]; 
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine()); 
            for (int j = 0; j < M; j++) {
                Map[i][j] = Integer.parseInt(st.nextToken()); 
                if (Map[i][j] != 0) {
                    start_row = i; 
                    start_col = j; 
                    cnt++;
                }
            }
        }

        BFS(new point(start_row, start_col)); 
        System.out.println(year);
    }

    static void BFS(point p) {
        int prev_ice = 0; // 이전의 빙산개수
        int present_ice = 0; 
        int next_row = 0; 
        int next_col = 0; 
        boolean[][] visit = new boolean[N][M]; 
        Queue<point> queue = new LinkedList();

        queue.add(p); 
        visit[p.row][p.col] = true; // 첫 방문
        while (!queue.isEmpty()) {
            point temp = queue.poll();
            
            int round_ice = 0;
            for (int i = 0; i < dir.length; i++) { // 빙하의 4방향의 물의 개수를 구해준다.
                int round_row = temp.row + dir[i][0]; 
                int round_col = temp.col + dir[i][1]; 
                if (check(round_row, round_col) && Map[round_row][round_col] == 0
                    && !visit[round_row][round_col]) { // 방문안한곳이면서 물로 일루어져 있는곳이어야됨 그래야 0이 된 빙하를 포함안시킬 수 있음
                        round_ice++;
                }
            }
            //0 밑으로 내려가면 그냥 물로 쳐준다. 
            
            Map[temp.row][temp.col] =
                (Map[temp.row][temp.col] - round_ice <= 0 ? 0 : Map[temp.row][temp.col] - round_ice); 
            if (Map[temp.row][temp.col] != 0) {
                prev_ice++; 
                next_row = temp.row; 
                next_col = temp.col;
            }
            //녹은뒤에 물이아닌 빙하로 남아있는 녀석의 개수를 더해준다.
            for (int i = 0; i < dir.length; i++) {
                int row = temp.row + dir[i][0]; 
                int col = temp.col + dir[i][1];

                if (check(row, col) && !visit[row][col] && Map[row][col] != 0) { // 방문안한곳이며 물이아닌곳
                    //present_ice++; 
                    visit[row][col] = true; 
                    queue.add(new point(row, col));
                }
            }
        
        }

        visit = new boolean[N][M]; 
        queue.add(new point(next_row, next_col));
        while(!queue.isEmpty()){
            point temp = queue.poll();
            for (int i = 0; i < dir.length; i++) {
                int row = temp.row + dir[i][0]; 
                int col = temp.col + dir[i][1];

                if (check(row, col) && !visit[row][col] && Map[row][col] != 0) { // 방문안한곳이며 물이아닌곳
                    present_ice++; 
                    visit[row][col] = true; 
                    queue.add(new point(row, col));
                }
            }
        }
        for (int i = 0; i < Map.length; i++) {
            for (int j = 0; j < Map[i].length; j++) {
                System.out.print(Map[i][j] + " ");
            }
            System.out.println();
        } 
        System.out.println("==================" + cnt + " " + present_ice + " pre" + prev_ice);
        if(prev_ice == 0){ 
            year = 0;
            return;
        }
        if (prev_ice != present_ice) {
            return;
        }
            
        year++; 
        cnt = prev_ice; 
        BFS(new point(next_row, next_col));

    }

    private static boolean check(int row, int col) { 
        if (row < 0 || row > N - 1 || col < 0 || col > M - 1) {
            return false; 
        } else {
            return true;
        }
    }
}