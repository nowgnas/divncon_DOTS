import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Main
 */
public class Main {
    static int dist(Point p1, Point p2) {
        return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
    }

    public static int mindist(List<Point> a, int x, int y) {
        int ans = -1;
        for (int i = x; i <= y - 1; i++) {
            for (int j = i + 1; j <= y; j++) {
                int k = dist(a.get(i), a.get(j));
                if (ans == -1 || ans > k)
                    ans = k;
            }
        }
        return ans;
    }

    public static int Dots(List<Point> a, int x, int y) {
        int n = y - x + 1;
        if (n <= 3) {
            // n이 3이하면 완전탐색으로 x부터 y까지 가장 가까운 두 점 사이의 거리를 찾는다.
            return mindist(a, x, y);
        }
        int mid = (x + y) / 2;
        int left = Dots(a, x, mid);
        int right = Dots(a, mid + 1, y);
        int ans = Math.min(left, right);
        List<Point> b = new ArrayList<>(); // 왼쪽과 오른쪽 사이의 점들을 저장할 List

        for (int i = x; i <= y; i++) {
            int d = a.get(i).x - a.get(mid).x;
            if (d * d < ans) {
                b.add(a.get(i));
            }
        }
        Collections.sort(b, new PointComparator()); // y좌표순으로 정렬
        int m = b.size();
        for (int i = 0; i < m - 1; i++) {
            for (int j = i + 1; j < m; j++) {
                int k = b.get(j).y - b.get(i).y;
                if (k * k < ans) {
                    int d = dist(b.get(i), b.get(j));
                    if (d < ans) {
                        ans = d;
                    }
                } else { // y좌표 순으로 정렬했기 때문에 앞의 가장 가까운 점이 ans보다 크면 더 볼필요가 없다.
                    break;
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        List<Point> a = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            a.add(new Point(x, y));
        }
        Collections.sort(a);
        System.out.println(Dots(a, 0, n - 1));
    }
}

/* Point */
class Point implements Comparable<Point> {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Point o) {
        int r = this.x - o.x;
        if (r == 0)
            r = this.y - o.y;
        return r;
    }
}

class PointComparator implements Comparator<Point> {
    // y좌표 순으로 정렬하기 위한 Comparator

    @Override
    public int compare(Point o1, Point o2) {
        return o1.y - o2.y;
    }

}