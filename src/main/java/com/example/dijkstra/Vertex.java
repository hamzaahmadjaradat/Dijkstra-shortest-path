package com.example.dijkstra;

import java.util.LinkedList;
import java.util.List;

public class Vertex implements  Comparable<Vertex> {

  public String name;
  public int x;
  public int y;
  public boolean known;
  public double distance;
  public Vertex prev;
  public List<Edge> adjacentEdges;

  public Vertex(String name, int x, int y) {
    this.name = name;
    this.x = x;
    this.y = y;
    // by default java sets uninitialized boolean to false and double to 0
    // hence known == false and dist == 0.0
    adjacentEdges = new LinkedList<Edge>();
    prev = null;
  }

  @Override
  public int hashCode() {
    // we assume that each vertex has a unique name
    return name.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (!(o instanceof Vertex)) {
      return false;
    }
    Vertex oVertex = (Vertex) o;

    return name.equals(oVertex.name) && x == oVertex.x && y == oVertex.y;
  }

  public void addEdge(Edge edge) {
    adjacentEdges.add(edge);
  }

  public String toString() {
    return name + " (" + x + ", " + y + ")";
  }

@Override
public int compareTo(Vertex vertex2) {
	if (this.distance>vertex2.distance)
		return 1 ;
	else if (this.distance<vertex2.distance)
		return -1 ;
	else
		return 0 ;
}

}