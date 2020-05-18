package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.algorithm.utils.ElementNotFoundException;
import org.insa.graphs.algorithm.utils.EmptyPriorityQueueException;

import java.util.List;
import java.util.ArrayList;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.algorithm.AbstractInputData.Mode;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.AbstractSolution;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    protected Label last;
    
    protected BinaryHeap<Label> heap;
    
    protected Label[] labels;
    
    public void init(List<Node> list, Node origine, Node destination, double max_speed) {
    	System.out.println("Algo de Dijkstra");
    	heap = new BinaryHeap<Label>();
    	labels = new Label[list.size()];
        for (Node node : list) {
        	Label aux = new Label(node);
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

    @Override
    protected ShortestPathSolution doRun() {
    	long start = System.currentTimeMillis();
        final ShortestPathData data = getInputData();
        double max_speed;
        if (data.getMode() == Mode.LENGTH) {
        	max_speed = 1;
        }
        else {
        	max_speed = data.getMaximumSpeed();
        	if (max_speed == -1) {
        		max_speed = data.getGraph().getGraphInformation().getMaximumSpeed();
        	}
        	max_speed = max_speed/3.6;
        }
        int max_size_heap = 0;
        ShortestPathSolution solution = null;
        List<Node> list = data.getGraph().getNodes();
        Node origine = data.getOrigin();
        boolean updated = false;
        boolean not_finished = true;
        boolean first = true;
        double cout_precedent = 0;
        boolean doable = true;
        this.init(list, origine,data.getDestination(),max_speed);
        int compteur = 0;
        
        while (not_finished) {
        	if (max_size_heap < heap.size()) {
        		max_size_heap = heap.size();
        	}
        	try {
        		compteur++;
	            Label min = heap.deleteMin();
	        	notifyNodeMarked(min.getSommet());
	        	if (cout_precedent > min.getTotalCost()) {
	        		System.out.println("Cout marqués pas croissant !");
	        	}
	        	cout_precedent = min.getCost();
	        	labels[min.getSommet().getId()].setMarqueTrue();
	        	List<Arc> successors = min.getSommet().getSuccessors();
	       
	        	for (Arc y : successors) {
	        		if ((!(labels[y.getDestination().getId()].isMarque())) && data.isAllowed(y)) {
	        			if (labels[y.getDestination().getId()].getCost() > (min.getCost() + data.getCost(y))) {
	        				updated = true;
	        			}
	        			if (updated) {
	        				updated = false;
	            			try {
	            				heap.remove(labels[y.getDestination().getId()]);
	            			}
	            			catch (ElementNotFoundException e) {
	            				notifyNodeReached(y.getDestination());
	            			}
	            			finally {
	        				labels[y.getDestination().getId()].setPere(y);
	        				labels[y.getDestination().getId()].setCost(min.getCost() + data.getCost(y));
	        				heap.insert(labels[y.getDestination().getId()]);
	            			}
	        			}
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
        	catch (EmptyPriorityQueueException x) {
        		doable = false;
        		break;
        	}
        }
        
        if (doable == false) {
        	solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {	
	        not_finished = true;
	        ArrayList<Arc> final_list = new ArrayList<Arc>();
	        this.solution(data);
	        
	        while (not_finished) {
	        	final_list.add(0,last.getPere());
	        	if (last.getPere() == null) {
	        		not_finished = false;
	        	}
	        	else {
	        		last = labels[last.getPere().getOrigin().getId()];
	        	}
	        }
	        final_list.remove(0);
	        //System.out.println(final_list);
	        Path path_solution = new Path(data.getGraph(),final_list);
	        if (path_solution.isValid()) {
	        	//System.out.println("Le path de solution est valide");
	        }
	        if (data.getMode() == Mode.LENGTH) {
	        	int cout_final;
	        	cout_final =  (int) path_solution.getLength() * 1000; 
	        	//System.out.println("Cout path de base :" + labels[data.getDestination().getId()].getCost() + "Cout solution" + cout_final);
	        	if (cout_final ==(int) labels[data.getDestination().getId()].getCost()*1000) {
	        		System.out.println("C'est le bon cout !");
	        	}
	        }
	        else if (data.getMode() == Mode.TIME) {
	        	int cout_final;
	        	cout_final = (int) path_solution.getMinimumTravelTime()*1000;
	        	//System.out.println("Cout path de base :" + labels[data.getDestination().getId()].getCost() + " Cout solution" + cout_final);
	        	if (cout_final == (int) labels[data.getDestination().getId()].getCost()*1000) {
	        		System.out.println("C'est le bon cout !");
	        	}
	        }
	        solution = new ShortestPathSolution(data,AbstractSolution.Status.FEASIBLE,path_solution);
        }
        System.out.println("Taille max du tas : "+max_size_heap);
        System.out.println("Nb de sommets marqués : " + compteur);
        long time = System.currentTimeMillis() - start;
        System.out.println("Temps de calcul : "+time+"   ms");
        return solution;
    }
}
