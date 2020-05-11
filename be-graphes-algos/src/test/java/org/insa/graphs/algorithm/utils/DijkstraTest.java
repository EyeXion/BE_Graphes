package org.insa.graphs.algorithm.utils;


import static org.junit.Assert.assertEquals;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.BeforeClass;
import org.junit.Test;


public class DijkstraTest {

    	// Visit these directory to see the list of available files on Commetud.
	    static String mapName = "/home/elies/3A/Cartes_Graphes/haute-garonne.mapgr";
	
	    // Create a graph reader.
	    static GraphReader reader;

    	// TODO: Read the graph.
    	static Graph graph;
    	
    	/* list of origins*/
    	static Node[] ori;
    	
    	/* list of destinations */
    	static Node[] dest;
    	
    	
	    @BeforeClass
	    public static void initAll() throws IOException {
	    	
	    	try {
	    	reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
	    	}
	    	catch (FileNotFoundException e) {
	    		System.out.println("File not found");
	    	}
	    	graph = reader.read();
	        // Create nodes
	        ori = new Node[5];
	        dest = new Node[5];
	        
	        // Prendre les points ici pour que ce soit cohérant
	        ori[0] = graph.get(49966);
	        dest[0] = graph.get(56156);
	        ori[1] = graph.get(107731);
	        dest[1] = graph.get(54862);
	        ori[2] = graph.get(113283);
	        dest[2] = graph.get(33699);
	        

	    }


	    @SuppressWarnings("deprecation")
		@Test
	    public void testHauteGaronne() {
	    	
	    	ArcInspector arcinspc1 = ArcInspectorFactory.getAllFilters().get(0);
	    	ArcInspector arcinspc3 = ArcInspectorFactory.getAllFilters().get(2);
	    	ShortestPathData[] listData = new ShortestPathData[3];
	    	DijkstraAlgorithm[] algos = new DijkstraAlgorithm[3];
	    	BellmanFordAlgorithm[] algo_opt = new BellmanFordAlgorithm[3];
	    	AStarAlgorithm[] algo_star = new AStarAlgorithm[3];
	    	Path[] solutions_Djik = new Path[3];
	    	Path[] solutions_Belleman = new Path[3];
	    	Path[] solutions_star = new Path[3];
	    	
	    	for (int i = 0; i< 3; i++) {
	    		listData[i] = new ShortestPathData(graph, ori[i], dest[i], arcinspc1);
	    		algos[i] = new DijkstraAlgorithm(listData[i]);
	    		algo_opt[i] = new BellmanFordAlgorithm(listData[i]);
	    		algo_star[i] = new AStarAlgorithm(listData[i]);
	    		solutions_Belleman[i] = algo_opt[i].run().getPath();
	    		solutions_Djik[i] = algos[i].run().getPath();
	    		solutions_star[i] = algo_star[i].run().getPath();
		    	assertEquals(solutions_Belleman[i].getLength(), solutions_Djik[i].getLength(), 0.0001);
		    	assertEquals(solutions_Belleman[i].getMinimumTravelTime(), solutions_Djik[i].getMinimumTravelTime(), 0.0001);
		    	assertEquals(solutions_Belleman[i].getLength(), solutions_star[i].getLength(), 0.0001);
		    	assertEquals(solutions_Belleman[i].getMinimumTravelTime(), solutions_star[i].getMinimumTravelTime(), 0.0001);
	    	}	    
	    	
	    	for (int i = 0; i< 3; i++) {
	    		listData[i] = new ShortestPathData(graph, ori[i], dest[i], arcinspc1);
	    		algos[i] = new DijkstraAlgorithm(listData[i]);
	    		algo_opt[i] = new BellmanFordAlgorithm(listData[i]);
	    		algo_star[i] = new AStarAlgorithm(listData[i]);
	    		solutions_Belleman[i] = algo_opt[i].run().getPath();
	    		solutions_Djik[i] = algos[i].run().getPath();
	    		solutions_star[i] = algo_star[i].run().getPath();
		    	assertEquals(solutions_Belleman[i].getLength(), solutions_Djik[i].getLength(), 0.0001);
		    	assertEquals(solutions_Belleman[i].getMinimumTravelTime(), solutions_Djik[i].getMinimumTravelTime(), 0.0001);
		    	assertEquals(solutions_Belleman[i].getLength(), solutions_star[i].getLength(), 0.0001);
		    	assertEquals(solutions_Belleman[i].getMinimumTravelTime(), solutions_star[i].getMinimumTravelTime(), 0.0001);
	    	}

	    }
	}
