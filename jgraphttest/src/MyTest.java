import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.SimpleGraph;


public class MyTest {

	public static void main(String[] args) throws Exception {
		SimpleGraph<String, String> graph = new SimpleGraph<String, String>(
				new ClassBasedEdgeFactory<String, String>(String.class));
		graph.addVertex("v0");
		graph.addVertex("v1");
		graph.addVertex("v2");
		graph.addEdge("v0", "v1", "e0");
		graph.addEdge("v0", "v2", "e1");
		JGraphModelAdapter<String, String> jma = new JGraphModelAdapter<String, String>(graph);
		JGraph jgraph = new JGraph(jma);
		positionVertexAt(jma, "v0", 100, 100);
		positionVertexAt(jma, "v1", 50, 200);
		positionVertexAt(jma, "v2", 150, 200);
	
		BufferedImage bi = jgraph.getImage(Color.WHITE, 0);
		ImageIO.write(bi, "JPG", new File("E:\\test.jpg"));
	}
	
    private static void positionVertexAt(JGraphModelAdapter<String, String> jma, Object vertex, int x, int y) {
        DefaultGraphCell cell = jma.getVertexCell(vertex);
        AttributeMap attr = cell.getAttributes();
        Rectangle2D bounds = GraphConstants.getBounds(attr);

        Rectangle2D newBounds =
            new Rectangle2D.Double(
                x,
                y,
                bounds.getWidth(),
                bounds.getHeight());

        GraphConstants.setBounds(attr, newBounds);

        // TODO: Clean up generics once JGraph goes generic
        AttributeMap cellAttr = new AttributeMap();
        cellAttr.put(cell, attr);
        jma.edit(cellAttr, null, null, null);
    }

}
