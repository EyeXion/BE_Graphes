package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;

public class LabelStar extends Label{
	
	protected double cout_estime;
	
	protected static Node destination;
	
	protected static double max_speed;
	
	public LabelStar(Node sommet_courant, Node destination, double max_speed) {
		super(sommet_courant);
		LabelStar.destination = destination;
		LabelStar.max_speed = max_speed;
		cout_estime = this.calculCoutEstime();
	}
	
	public LabelStar(Node sommet_courant) {
		super(sommet_courant);
		cout_estime = this.calculCoutEstime();
	}
	
	public double calculCoutEstime() {
		return this.getSommet().getPoint().distanceTo(LabelStar.destination.getPoint())/max_speed;
	}
	
	public double getCoutEstime() {
		return this.cout_estime;
	}
	
	public double getTotalCost() {
		return this.getCost() + this.getCoutEstime();
	}
	
}