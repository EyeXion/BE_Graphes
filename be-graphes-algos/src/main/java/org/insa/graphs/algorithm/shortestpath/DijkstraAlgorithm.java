package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.algorithm.utils.ElementNotFoundException;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.algorithm.AbstractSolution;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        List<Node> list = data.getGraph().getNodes();
        Label[]  labels = new Label[list.size()];
        Node origine = data.getOrigin();
        BinaryHeap<Label> heap = new BinaryHeap<Label>();
        boolean updated = false;
        boolean not_finished = true;
        boolean first = true;
        
        for (Node node : list) {
        	Label aux = new Label(node);
        	labels[node.getId()] = aux;
        	if (node.getId() == origine.getId()) {
        		labels[node.getId()].setCost(0);
        		heap.insert(labels[node.getId()]);
        		System.out.println(heap.size());
        		System.out.println(heap);
        	}
        }
        
        while (not_finished) {
        	System.out.println(heap.size());
        	Label min = heap.deleteMin();
        	notifyNodeMarked(min.getSommet());
        	labels[min.getSommet().getId()].setMarqueTrue();
        	List<Arc> successors = min.getSommet().getSuccessors();
        	
        	for (Arc y : successors) {
        		if (!labels[y.getDestination().getId()].isMarque() && data.isAllowed(y)) {
        			if (labels[y.getDestination().getId()].getCost() > min.getCost() + y.getMinimumTravelTime()) {
        				updated = true;
        				labels[y.getDestination().getId()].setPere(y);
        				labels[y.getDestination().getId()].setCost(min.getCost() + y.getMinimumTravelTime());
        			}
        			if (updated) {
        				updated = false;
            			try {
            				heap.remove(labels[y.getDestination().getId()]);
            			}
            			catch (ElementNotFoundException e) {
            				notifyNodeReached(y.getDestination());
            			}
        			}
        			heap.insert(labels[y.getDestination().getId()]);
        		}
        	}
	        if (min.getSommet().getId() == data.getDestination().getId()) {
	        	not_finished = false;
	        	notifyDestinationReached(min.getSommet());
	        }
	        if (first) {
	        	notifyOriginProcessed(min.getSommet());
	        	first = false;
	        }
        }
        not_finished = true;
        ArrayList<Arc> final_list = new ArrayList<Arc>();
        Label last = labels[data.getDestination().getId()];
        
        while (not_finished) {
        	System.out.println(last.getSommet().getId());
        	final_list.add(0,last.getPere());
        	if (last.getPere() == null) {
        		not_finished = false;
        	}
        	else {
        		last = labels[last.getPere().getOrigin().getId()];
        	}
        }
        final_list.remove(0);
        System.out.println(final_list);
        solution = new ShortestPathSolution(data,AbstractSolution.Status.FEASIBLE,new Path(data.getGraph(),final_list));
        return solution;
    }
}
