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
	    
	    static String mapNameBretagne = "/home/elies/3A/Cartes_Graphes/bretagne.mapgr";
	
	    // Create a graph reader for haute garonne.
	    static GraphReader reader;
	    
	    // Create a graph reader for Bretagne.
	    static GraphReader readerB;

    	// TODO: Read the graph of haute garonne.
    	static Graph graph;
    	
    	// Read the graph of Bretagne
    	static Graph graphB;
    	
    	/* list of origins for haute garonne*/
    	static Node[] ori;
    	
    	/* list of destinations for haute garonne*/
    	static Node[] dest;
    	
    	/* list of origins for Bretagne*/
    	static Node[] oriB;
    	
    	/* list of destinations for Bretagne*/
    	static Node[] destB;
    	
    	
	    @BeforeClass
	    public static void initAll() throws IOException {
	    	
	    	try {
	    		reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
	    		readerB = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapNameBretagne))));
	    	}
	    	catch (FileNotFoundException e) {
	    		System.out.println("File not found");
	    	}
	    	graph = reader.read();
	    	graphB = readerB.read();
	        // Create nodes
	        ori = new Node[3];
	        dest = new Node[3];
	        oriB = new Node[3];
	        destB = new Node[3];
	        
	        // Prendre les points ici pour que ce soit cohérant
	        ori[0] = graph.get(49966);
	        dest[0] = graph.get(56156);
	        ori[1] = graph.get(107731);
	        dest[1] = graph.get(54862);
	        ori[2] = graph.get(113283);
	        dest[2] = graph.get(33699);
	        
	        oriB[0] = graphB.get(534197); //Non connexe, impossible
	        destB[0] = graphB.get(126452);
	        oriB[1] = graphB.get(604130);
	        destB[1] = graphB.get(498422);
	        oriB[2] = graphB.get(326393);
	        destB[2] = graphB.get(357080);
	        
	        

	    }


		@Test
	    public void testHauteGaronne() {
	    	
	    	ArcInspector arcinspc1 = ArcInspectorFactory.getAllFilters().get(0);
	    	ArcInspector arcinspc3 = ArcInspectorFactory.getAllFilters().get(2);
	    	//ArcInspector arcinspc5 = ArcInspectorFactory.getAllFilters().get(4);
	    	ShortestPathData[] listData = new ShortestPathData[3];
	    	DijkstraAlgorithm[] algos = new DijkstraAlgorithm[3];
	    	BellmanFordAlgorithm[] algo_opt = new BellmanFordAlgorithm[3];
	    	AStarAlgorithm[] algo_star = new AStarAlgorithm[3];
	    	Path[] solutions_Djik = new Path[3];
	    	Path[] solutions_Belleman = new Path[3];
	    	Path[] solutions_star = new Path[3];
	    	
	    	for (int i = 0; i< 3; i++) {
	    		System.out.println("Itération : "+i+" dans HG, arcinspec1");
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
	    		System.out.println("Itération : "+i+" dans HG, arcinspec3");
	    		listData[i] = new ShortestPathData(graph, ori[i], dest[i], arcinspc3);
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
	    	/*for (int i = 0; i< 3; i++) {
	    		System.out.println("Itération : "+i+" dans HG, arcinspec5");
	    		listData[i] = new ShortestPathData(graph, ori[i], dest[i], arcinspc5);
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
	    	*/
	    }
		@Test
	    public void testBretagne() {
	    	
	    	ArcInspector arcinspc2 = ArcInspectorFactory.getAllFilters().get(1);
	    	ArcInspector arcinspc4 = ArcInspectorFactory.getAllFilters().get(3);
	    	ShortestPathData[] listData = new ShortestPathData[3];
	    	DijkstraAlgorithm[] algos = new DijkstraAlgorithm[3];
	    	BellmanFordAlgorithm[] algo_opt = new BellmanFordAlgorithm[3];
	    	AStarAlgorithm[] algo_star = new AStarAlgorithm[3];
	    	Path[] solutions_Djik = new Path[3];
	    	Path[] solutions_Belleman = new Path[3];
	    	Path[] solutions_star = new Path[3];
	    	
	    	for (int i = 0; i< 3; i++) {
	    		System.out.println("Itération : "+i+" dans Bretagne, arcinspec2");
	    		listData[i] = new ShortestPathData(graphB, oriB[i], destB[i], arcinspc2);
	    		algos[i] = new DijkstraAlgorithm(listData[i]);
	    		algo_opt[i] = new BellmanFordAlgorithm(listData[i]);
	    		algo_star[i] = new AStarAlgorithm(listData[i]);
	    		if (i == 0) { //Noeuds qui donne solution impossible
	    			assertEquals(algos[i].run().isFeasible(), false);
	    			assertEquals(algo_star[i].run().isFeasible(), false);
	    		}
	    		else {
		    		solutions_Belleman[i] = algo_opt[i].run().getPath();
		    		solutions_Djik[i] = algos[i].run().getPath();
		    		solutions_star[i] = algo_star[i].run().getPath();
			    	assertEquals(solutions_Belleman[i].getLength(), solutions_Djik[i].getLength(), 0.0001);
			    	assertEquals(solutions_Belleman[i].getMinimumTravelTime(), solutions_Djik[i].getMinimumTravelTime(), 0.0001);
			    	assertEquals(solutions_Belleman[i].getLength(), solutions_star[i].getLength(), 0.0001);
			    	assertEquals(solutions_Belleman[i].getMinimumTravelTime(), solutions_star[i].getMinimumTravelTime(), 0.0001);
	    		}
	    	}
	    	for (int i = 0; i< 3; i++) {
	    		System.out.println("Itération : "+i+" dans Bretagne, arcinspec4");
	    		listData[i] = new ShortestPathData(graphB, oriB[i], destB[i], arcinspc4);
	    		algos[i] = new DijkstraAlgorithm(listData[i]);
	    		algo_opt[i] = new BellmanFordAlgorithm(listData[i]);
	    		algo_star[i] = new AStarAlgorithm(listData[i]);
	    		if (i == 0) { //Noeuds qui donne solution impossible
	    			assertEquals(algos[i].run().isFeasible(), false);
	    			assertEquals(algo_star[i].run().isFeasible(), false);
	    		}
	    		else {
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
	}
