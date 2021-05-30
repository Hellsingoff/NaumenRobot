import java.util.LinkedList;
import java.util.Queue;

public class NaumenRobot implements RouteFinder {
    private int[][] intMap;
    private int startX, startY, endX, endY, found;
    private Queue<Integer[]> steps = new LinkedList<>();

    NaumenRobot() {
        this.startX = -1;
        this.startY = -1;
        this.endX = -1;
        this.endY = -1;
        this.found = -1;
    }

    @Override
    public char[][] findRoute(char[][] charMap) {
        intMatrixCreate(charMap);
        steps.offer(new Integer[]{startY, startX});
        Integer[] coords;
        while (found == -1 && (coords = steps.poll()) != null) {
            doStep(coords);
        }
        if (found == -1)
            return null;
        else
            return topographer(charMap);
    }

    private char[][] topographer(char[][] charMap) {
        while (--found > 0) {
            if (endY > 0 && intMap[endY-1][endX] == found)
                charMap[--endY][endX] = '+';
            else if (endX < intMap[0].length-1 && intMap[endY][endX+1] == found)
                charMap[endY][++endX] = '+';
            else if (endY < intMap.length-1 && intMap[endY+1][endX] == found)
                charMap[++endY][endX] = '+';
            else
                charMap[endY][--endX] = '+';
        }
        return charMap;
    }

    private void doStep(Integer[] coords) {
        int len = intMap[coords[0]][coords[1]] + 1;
        if (coords[0] > 0)
            moveUp(len, coords);
        if (coords[1] < intMap[0].length-1)
            moveRight(len, coords);
        if (coords[0] < intMap.length-1)
            moveDown(len, coords);
        if (coords[1] > 0)
            moveLeft(len, coords);
    }

    private void moveLeft(int len, Integer[] coords) {
        int oldRouteLen = intMap[coords[0]][coords[1]-1];
        if (oldRouteLen == -2)
            found = len;
        else if (oldRouteLen > len) {
            intMap[coords[0]][coords[1]-1] = len;
            steps.offer(new Integer[]{coords[0], coords[1]-1});
        }
    }

    private void moveDown(int len, Integer[] coords) {
        int oldRouteLen = intMap[coords[0]+1][coords[1]];
        if (oldRouteLen == -2)
            found = len;
        else if (oldRouteLen > len) {
            intMap[coords[0]+1][coords[1]] = len;
            steps.offer(new Integer[]{coords[0]+1, coords[1]});
        }
    }

    private void moveRight(int len, Integer[] coords) {
        int oldRouteLen = intMap[coords[0]][coords[1]+1];
        if (oldRouteLen == -2)
            found = len;
        else if (oldRouteLen > len) {
            intMap[coords[0]][coords[1]+1] = len;
            steps.offer(new Integer[]{coords[0], coords[1]+1});
        }
    }

    private void moveUp(int len, Integer[] coords) {
        int oldRouteLen = intMap[coords[0]-1][coords[1]];
        if (oldRouteLen == -2)
            found = len;
        else if (oldRouteLen > len) {
            intMap[coords[0]-1][coords[1]] = len;
            steps.offer(new Integer[]{coords[0]-1, coords[1]});
        }
    }

    private void intMatrixCreate(char[][] charMap) {
        intMap = new int[charMap.length][charMap[0].length];
        for (int y = 0; y < charMap.length; y++) {
            for (int x = 0; x < charMap[0].length; x++) {
                if (charMap[y][x] == '@') {
                    intMap[y][x] = 0;
                    startX = x;
                    startY = y;
                }
                else if (charMap[y][x] == 'X') {
                    intMap[y][x] = -2;
                    endX = x;
                    endY = y;
                }
                else if (charMap[y][x] == '.')
                    intMap[y][x] = 0x7fffffff;
                else if (charMap[y][x] == '#')
                    intMap[y][x] = -1;
            }
        }
    }

}