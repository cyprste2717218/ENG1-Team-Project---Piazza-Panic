package de.tomgrill.gdxtesting.tests;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.mygdx.game.Node;
import com.mygdx.game.enums.Facing;
import com.mygdx.game.enums.NodeType;
import com.mygdx.game.utils.TileMapUtils;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

@RunWith(GdxTestRunner.class)
public class TileMapUtilsTests {


    //A test to see if the tileMapToArray function properly converts a tileMap into an array
    @Test
    public void testTiledMapToArray(){
        TiledMap tiledMap = createTestTiledMap();
        Node[][] grid = TileMapUtils.tileMapToArray(tiledMap);
        Node[][] expectedGrid = createTestNodeArray();
        //tileMapToString() is used for the comparison here because junits array comparison will check memory addresses rather than actual node values
        assertEquals(TileMapUtils.tileMapToString(grid), TileMapUtils.tileMapToString(expectedGrid));
    }

    //Tests the tileMapToString function is working as intended
    @Test
    public void testMapString(){

        Node[][] grid = createTestNodeArray();
        grid[1][1].setNodeType(NodeType.FOOD);
        grid[5][5].setNodeType(NodeType.CHEF);
        grid[1][5].setNodeType(NodeType.STATION);
        grid[5][1].setNodeType(NodeType.CUSTOMER);

        String expectedOutputString = "XXXXXXX\n" +
                                      "XS   CX\n" +
                                      "X  X  X\n" +
                                      "X  X  X\n" +
                                      "X  X  X\n" +
                                      "XF   BX\n" +
                                      "XXXXXXX";

        assertEquals(TileMapUtils.tileMapToString(grid), expectedOutputString);
    }

    //Tests the getNodeAtFacing function performs as expected, including returning null for invalid Nodes
    @Test
    public void testNodeAtFacing(){
        Node[][] grid = createTestNodeArray();
        assertEquals(TileMapUtils.getNodeAtFacing(Facing.UP, grid, grid[2][2]), grid[2][3]);
        assertEquals(TileMapUtils.getNodeAtFacing(Facing.DOWN, grid, grid[2][2]), grid[2][1]);
        assertEquals(TileMapUtils.getNodeAtFacing(Facing.LEFT, grid, grid[2][2]), grid[1][2]);
        assertEquals(TileMapUtils.getNodeAtFacing(Facing.RIGHT, grid, grid[2][2]), grid[3][2]);
        assertNull(TileMapUtils.getNodeAtFacing(Facing.UP, grid, grid[6][6]));
        assertNull(TileMapUtils.getNodeAtFacing(Facing.DOWN, grid, grid[0][0]));
    }

    //This tests that world coordinates are correctly converted into grid coordinates
    @Test
    public void testPositionToCoord(){
    }

    @Test
    public void testCoordToPosition(){

    }

    public void testGetCollisionAtSprite(){

    }


    /*Creates a 7x7 test tileMap
    * Appearance is as follows, with X representing walls and blank space representing empty nodes
    *
    *  X X X X X X X
    *  X           X
    *  X     X     X
    *  X     X     X
    *  X     X     X
    *  X           X
    *  X X X X X X X
    *
     */
    private TiledMap createTestTiledMap() {
        TiledMap tiledMap = new TiledMap();
        TiledMapTileLayer layer = new TiledMapTileLayer(7, 7, 32, 32);
        layer.setName("Walls");

        for (int x = 0; x < layer.getWidth(); x++) {
            layer.setCell(x, 0, createWallCell());
            layer.setCell(x, layer.getHeight() - 1, createWallCell());
            layer.setCell(0, x, createWallCell());
            layer.setCell(layer.getWidth() - 1, x, createWallCell());
        }
        layer.setCell(3, 2, createWallCell());
        layer.setCell(3, 3, createWallCell());
        layer.setCell(3, 4, createWallCell());

        tiledMap.getLayers().add(layer);
        return tiledMap;
    }

    //Makes a cell a wall in the tileMap
    private TiledMapTileLayer.Cell createWallCell() {
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(new StaticTiledMapTile(new TextureRegion()));
        return cell;
    }


    //A helper class that manually creates the grid we would expect to see from createTestTiledMap() as well as being useful for other testing
    private Node[][] createTestNodeArray(){
        Node[][] expectedGrid = new Node[7][7];

        for(int y = 0; y < expectedGrid.length; y++){
            for(int x = 0; x < expectedGrid.length; x++){
                boolean atEdge = (x == 0 || y == 0 || x == expectedGrid.length - 1 || y == expectedGrid.length - 1);
                expectedGrid[x][y] = atEdge ? new Node(x, y, NodeType.WALL) : new Node(x, y);
            }
        }
        expectedGrid[3][2].setNodeType(NodeType.WALL);
        expectedGrid[3][3].setNodeType(NodeType.WALL);
        expectedGrid[3][4].setNodeType(NodeType.WALL);

        return expectedGrid;
    }

}
