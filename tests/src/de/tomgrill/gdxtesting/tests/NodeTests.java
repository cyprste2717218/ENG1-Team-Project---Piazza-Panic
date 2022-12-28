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
        Node node = new Node(0, 0, false);
        node.setG(3);
        node.setH(1);
        assertEquals(node.getF(), 4, 0.000000001);

        node.setG(2);
        node.setH(5);
        assertEquals(node.getF(), 7, 0.000000001);
    }

    @Test
    public void testNodeComparison(){
        Node node1 = new Node(0,0, false);
        node1.setH(0);
        node1.setG(0);

        Node node2 = new Node(1, 1, false);
        node2.setH(1);
        node2.setG(1);

        Node node3 = new Node(2,2, true);
        node3.setH(1);
        node3.setG(1);

        assertEquals(node1.compareTo(node2), -1);
        assertEquals(node2.compareTo(node1), 1);
        assertEquals(node2.compareTo(node3), 0);
    }
}
