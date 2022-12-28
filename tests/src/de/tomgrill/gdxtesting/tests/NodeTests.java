package de.tomgrill.gdxtesting.tests;

import com.mygdx.game.Node;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class NodeTests {

    @Test
    public void testFCalculation(){
        //This node should have an F value of 3 + 1 = 4
        Node node = new Node(0, 0, false);
        node.setG(3);
        node.setH(1);
        //Assert the F value is equal to 4, with a floating point error allowance of 1e^-9
        assertEquals(node.getF(), 4, 1e-9);

        //This node should have an F value of 2 + 5 = 7
        node.setG(2);
        node.setH(5);
        //Assert the F value is equal to 7, with a floating point error allowance of 1e^-9
        assertEquals(node.getF(), 7, 1e-9);
    }

    @Test
    public void testNodeComparison(){

        /*
        * Create 3 Nodes
        * Node 1 : F Value -> 0
        * Node 2 : F Value -> 2
        * Node 3 : F Value -> 2
         */

        Node node1 = new Node(0,0, false);
        node1.setH(0);
        node1.setG(0);

        Node node2 = new Node(1, 1, false);
        node2.setH(1);
        node2.setG(1);

        Node node3 = new Node(2,2, true);
        node3.setH(1);
        node3.setG(1);

        //Assert that the F Value of node 1 is less than the F Value of Node 2
        assertEquals(node1.compareTo(node2), -1);

        //Assert that the F Value of node 2 is greater than the F Value of Node 1
        assertEquals(node2.compareTo(node1), 1);

        //Assert that the F Value of node 3 is equal to the F Value of Node 2
        assertEquals(node2.compareTo(node3), 0);
    }
}
