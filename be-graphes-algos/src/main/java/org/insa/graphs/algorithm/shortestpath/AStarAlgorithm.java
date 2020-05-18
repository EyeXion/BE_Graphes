package org.insa.graphs.algorithm.shortestpath;

import java.util.List;

import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {
	
	public void init(List<Node> list, Node origine, Node destination,double max_speed) {
		System.out.println("Algo de A*");
    	heap = new BinaryHeap<Label>();
    	labels = new LabelStar[list.size()];
    	Node first = list.get(0) ;
    	LabelStar f = new LabelStar(first, destination,max_speed);
        for (Node node : list) {
        	LabelStar aux = new LabelStar(node);
        	labels[node.getId()] = aux;
        	if (node.getId() == origine.getId()) {
        		labels[node.getId()].setCost(0);
        		heap.insert(labels[node.getId()]);
        	}
        }
    }
    
    public void solution(ShortestPathData data) {
    	last = labels[data.getDestination().getId()];
    }
	

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    protected ShortestPathSolution doRun() {
    	ShortestPathSolution solution = super.doRun();
    	return solution;
    }

}
