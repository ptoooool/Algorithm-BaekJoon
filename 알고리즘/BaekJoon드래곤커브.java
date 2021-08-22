import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;


class BaekJoon_15685 {
    static int N;
    static int[][] Map = new int[101][101];
    static int cnt = 0;
    static ArrayList<point> pointList;

    static class point{
        int row;
        int col;

        point(int row, int col){
            this.row = row;
            this.col = col;
        }
    }
    public static void main(String[] args) throws IOException {
        // 드래곤커브
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        // x좌표, y좌표, d방향, g세대
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 방향 0 -> 우, 1 -> 상, 2 -> 좌, 3 -> 하
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int g = Integer.parseInt(st.nextToken());

            dragonCurv(x, y, d, g);
        }

        for (int i = 0; i < Map.length; i++) {
            for (int j = 0; j < Map[i].length; j++) {
                if(check_rect(i, j)){ // 사각형을 생성하는지 확인
                    cnt++;
                }
            }
        }

        System.out.println(cnt);
    }

    public static void dragonCurv(int col, int row, int dir, int gen) {
        pointList = new ArrayList<point>();
        ArrayList<Integer> dirList = new ArrayList<>();
        pointList.add(new point(row, col)); // 시작되는 좌표 추가
        dirList.add(dir); // 방향 추가
        Map[row][col] = 1; // 첫 시작점 표시

        if(gen >= 0){ // 첫 시작점에서 0세대이더라도 직선점을 하나 넣어줘야하기떄문에 먼저 추가
            addDir(dir, row, col);
        }
        while(gen > 0){
            //방향점이 현재의 끝점에서만 이동해야되는데 계속 추가되기 떄문에
            //시작 끝점을 미리 지정해준다.
            int length = dirList.size() - 1;

            // 커브를 돌기 위해서는 진행해왔던 저장된 방향들의 역순으로
            // 방향이 1씩 더해지기 때문에 dirList의 마지막부터 지속적으로 진행한다.
            for (int i = length; i >= 0; i--) {
                int temp_dir = (dirList.get(i) + 1) % 4; // 다음방향 설정

                // 시작될 포인트점을 뽑아낸다.
                point start_point = pointList.get(pointList.size()-1);
                dirList.add(temp_dir); // 해당 start점이 진행될 방향 추가

                int s_row = start_point.row;
                int s_col = start_point.col;

                addDir(temp_dir, s_row, s_col);

            }

            gen--;
            
        }
        // 좌표점 찍어주기
        for (point p : pointList) {
            if(check(p.row, p.col)){
                Map[p.row][p.col] = 1;
            }
        }
    }
    // 커브 방향에 따라 움직인 좌표를 추가해주는 함수
    public static void addDir(int dir, int row, int col){
        if(dir == 0){
            pointList.add(new point(row, col+1));
        }else if(dir == 1){
            pointList.add(new point(row-1, col));
        }else if(dir == 2){
            pointList.add(new point(row, col-1));
        }else if(dir == 3){
            pointList.add(new point(row +1, col));
        }
    }
    //해당 좌표가 사각형의 왼쪽 위 점이라 가정하고 나머지 3개의 꼭지점 확인
    public static boolean check_rect(int row, int col) {
        if((check(row, col) && Map[row][col] == 1) && (check(row+1, col) && Map[row+1][col] == 1)
            && (check(row, col+1) && Map[row][col+1] == 1) && (check(row+1, col+1) && Map[row+1][col+1] == 1)){
                return true;
        }else {
            return false;
        }
    }
    // 좌표의 범위가 Map을 넘어가는지 확인하는 함수
    public static boolean check(int row, int col){
        if(row < 0 || col < 0 || row > 100 || col > 100){
            return false;
        }else{
            return true;
        }
    }
}
