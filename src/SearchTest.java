import static org.junit.Assert.*;

import org.junit.Test;

public class SearchTest {

    @Test 
    public void testNearestCheese() {
        char[][] maze = {
            {'o','o','o','o','c','w','c','o'},
            {'w','o','o','w','w','c','w','o'},
            {'o','o','o','o','R','w','o','o'},
            {'o','o','w','w','w','o','o','o'},
            {'o','o','o','o','c','o','o','o'}
        };

        int[] actual = Search.nearestCheese(maze);

        int[] expected = new int[]{0, 4};

        assertArrayEquals(expected, actual);
    }
    
}
