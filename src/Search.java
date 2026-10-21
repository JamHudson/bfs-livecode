import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class Search {

    public static void main(String[] args) {
        char[][] grid = {
            {'o', 'o', 'o', 'o', 'c', 'w', 'c', 'o'},
            {'w', 'o', 'o', 'w', 'w', 'c', 'w', 'o'},
            {'o', 'o', 'o', 'o', 'R', 'w', 'o', 'o'},
            {'o', 'o', 'w', 'w', 'w', 'o', 'o', 'o'},
            {'o', 'o', 'o', 'o', 'c', 'o', 'o', 'o'}
        };


    }
     /**
     * Finds the location of the nearest reachable cheese from the rat's position.
     * Returns a 2-element int array: [row, col] of the closest 'c'. If there are multiple
     * cheeses that are tied for the same shortest distance to reach, return
     * any one of them.
     * 
     * 'R' - the rat's starting position (exactly one)
     * 'o' - open space the rat can walk on
     * 'w' - wall the rat cannot pass through
     * 'c' - cheese that the rat wants to reach
     * 
     * If no rat is found, throws EscapedRatException.
     * If more than one rat is found, throws CrowdedMazeException.
     * If no cheese is reachable, throws HungryRatException
     *
     * oooocwco
     * woowwcwo
     * ooooRwoo
     * oowwwooo
     * oooocooo
     *
     * The method will return [0,4] as the nearest cheese.
     *
     * @param maze 2D char array representing the maze
     * @return int[] location of the closest cheese in row, column format
     * @throws EscapedRatException if there is no rat in the maze
     * @throws CrowdedMazeException if there is more than one rat in the maze
     * @throws HungryRatException if there is no reachable cheese
     */
    public static int[] nearestCheese(char[][] maze) throws EscapedRatException, CrowdedMazeException, HungryRatException {
        Location start = ratLocation(maze);
        Set<Location> visitedLocations = new HashSet<>();
        Queue<Location> q = new ArrayDeque<>();

        q.offer(start);

        while(!q.isEmpty()) {
            Location current = q.remove();
            visitedLocations.add(current);
            
            if (maze[current.row()][current.column()] == 'c') return new int[] {current.row(), current.column()};

            for (Location spot : possibleMoves(maze,current)) if (!visitedLocations.contains(spot)) q.offer(spot);
        }

        throw new HungryRatException();
    }

    
    public static List<Location> pathToNearestCheese(char[][] maze) throws EscapedRatException, CrowdedMazeException, HungryRatException {
        Location start = ratLocation(maze);
        Set<Location> visitedLocations = new HashSet<>();
        Queue<Location> q = new ArrayDeque<>();

        Map<Location, Location> previous = new HashMap<>();

        q.offer(start);

        while(!q.isEmpty()) {
            Location current = q.remove();
            visitedLocations.add(current);
            
            if (maze[current.row()][current.column()] == 'c') {
                List<Location> path = new LinkedList<>();
                Location step = current;
                while (step != null) {
                    path.addFirst(step);
                    step = previous.get(step);
                }
                // Return path
                // return new int[] {current.row(), current.column()};
            } 

            for (Location spot : possibleMoves(maze,current)) if (!visitedLocations.contains(spot)) {
                previous.put(spot, current);
                q.offer(spot);
            }
        }

        throw new HungryRatException();
    }

    public static List<Location> possibleMoves(char[][] maze, Location current) {
        int[][] moves = new int[][] {
            {-1,  0}, // UP
            { 1,  0}, // DOWN
            { 0, -1}, // LEFT
            { 0,  1}  // RIGHT
        };

        List<Location> possible = new ArrayList<>();

        for (int[] move : moves) {
            Location spot = new Location(current.row() + move[0], current.column() + move[1]);

            if (spot.row() >= 0 && spot.row() < maze.length && spot.column() >= 0 && spot.column() < maze[spot.row()].length && maze[spot.row()][spot.column()] != 'w') {
                possible.add(spot);
            }
        }
        return possible;
    }


    public static Location ratLocation(char[][] maze) throws EscapedRatException, CrowdedMazeException {
        Location ratPosition = null;
        for (int r=0; r<maze.length; r++) {
            for (int c=0; c<maze[r].length; c++) {
                if (maze[r][c] == 'R') {
                    if (ratPosition != null) throw new CrowdedMazeException();
                    ratPosition = new Location(r, c);
                }
            }
        }
        if (ratPosition == null) throw new EscapedRatException();
        return ratPosition;
    }
}