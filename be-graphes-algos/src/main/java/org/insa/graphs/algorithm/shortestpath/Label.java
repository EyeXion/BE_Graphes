package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;
import org.insa.graphs.model.Arc;



public class Label {
	protected Node sommet_courant;
	
	protected boolean marque;
	
	protected double cout;
	
	protected Arc pere;
	
	public Label(Node sommet_courant) {
		this.sommet_courant = sommet_courant;
		this.marque = false;
		this.cout = Double.POSITIVE_INFINITY;
		this.pere = null;
	}
	
	public double getCost() {
		return this.cout;
	}
	
	public boolean isMarque() {
		return marque;
	}
	
	public void setCost(double cout) {
		this.cout = cout;
	}
	
	public void setMarqueTrue() {
		this.marque = true;
	}
	
	public void setPere(double cout) {
		this.marque = true;
	}
	
	public Node getSommet() {
		return sommet_courant;
	}
	
	public Arc getPere() {
		return pere;
	}
	
}
