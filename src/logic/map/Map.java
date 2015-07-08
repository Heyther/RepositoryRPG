
package logic.map;

/**
 * Represents the overworld map, which is navigated outside of combat.
 * 
 * @author Robert Ferguson
 * 
 */
public final class Map
{
    private Tile[][] mapData;
    private int startingX;
    private int startingY;
    private int width;
    private int height;

    private Map(Tile[][] tiles, int startX, int startY, int w, int h)
    {
        startingX = startX;
        startingY = startY;
        width = w;
        height = h;
        mapData = tiles;
    }

    public static Map TestMap()
    {
        int mapHeight = 3;
        int mapWidth = 3;
        Tile[][] t = new Tile[mapWidth][mapHeight];
        t[0][0] = Tile.MOUNTAIN_IMPASSABLE;
        t[0][1] = Tile.MOUNTAIN_IMPASSABLE;
        t[0][2] = Tile.MOUNTAIN_IMPASSABLE;

        t[1][0] = Tile.MOUNTAIN_IMPASSABLE;
        t[1][1] = Tile.GRASS_PASSABLE;
        t[1][2] = Tile.MOUNTAIN_IMPASSABLE;

        t[2][0] = Tile.MOUNTAIN_IMPASSABLE;
        t[2][1] = Tile.MOUNTAIN_IMPASSABLE;
        t[2][2] = Tile.MOUNTAIN_IMPASSABLE;

        return new Map(t, 0, 0, mapWidth, mapHeight);
    }

    public static void printChars(final int low, final int high)
    {
        for (int i = low; i < high; i++)
        {
            System.out.println((char) i);
        }
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for (int row = 0; row < height; row++)
        {
            for (int col = 0; col < width; col++)
            {
                sb.append(mapData[row][col]);
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    /**
     * Represents a single cell in the map.
     * 
     * @author Robert Ferguson
     *
     */
    private final static class Tile
    {
        private final char displayChar;
        private final int flag;

        public static final Tile MOUNTAIN_IMPASSABLE = new Tile('^', 0);
        public static final Tile GRASS_PASSABLE = new Tile('#', 1);

        public Tile(final char c, final int f)
        {
            displayChar = c;
            flag = f;
        }

        public String toString()
        {
            return "" + displayChar;
        }

    }
}
