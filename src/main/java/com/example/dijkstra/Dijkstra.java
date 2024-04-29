package com.example.dijkstra;

import java.util.*;

public class Dijkstra {

	private Map<String, Vertex> vertexNames;

	public Dijkstra() {
		vertexNames = new HashMap<String, Vertex>();
	}

	public void addVertex(Vertex v) {
		if (vertexNames.containsKey(v.name))
			throw new IllegalArgumentException("Cannot create new vertex with existing name.");
		vertexNames.put(v.name, v);
	}

	public void addEdge(String nameU, String nameV, Double cost) {
		if (!vertexNames.containsKey(nameU))
			throw new IllegalArgumentException(nameU + " does not exist. Cannot create edge.");
		if (!vertexNames.containsKey(nameV))
			throw new IllegalArgumentException(nameV + " does not exist. Cannot create edge.");
		Vertex sourceVertex = vertexNames.get(nameU);
		Vertex targetVertex = vertexNames.get(nameV);
		Edge newEdge = new Edge(sourceVertex, targetVertex, cost);
		sourceVertex.addEdge(newEdge);
	}

	public void addUndirectedEdge(String nameU, String nameV, double cost) {
		addEdge(nameU, nameV, cost);
		addEdge(nameV, nameU, cost);
	}

	public void doDijkstra(String s, String t) {
		double MAX_VALUE = Double.MAX_VALUE;

		for (String u : vertexNames.keySet()) {
			Vertex node = vertexNames.get(u);
			node.distance = MAX_VALUE;
			node.prev = null;
			node.known = false;
		}
		vertexNames.get(s).distance = 0;

		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<>(Comparator.comparingDouble(vertex -> vertex.distance));
		vertexQueue.addAll(vertexNames.values()); // add the vertices from the map to the heap

		while (!vertexQueue.isEmpty()) {
			Vertex v = vertexQueue.poll();
			v.known = true;

			System.out.println(v.name);
			if (v.name.equals(t)) {
				break;
			}

			for (Edge e : v.adjacentEdges) {
				if (!e.target.known) {  //source previous is always null and the known is true at first so
										// it will never enter the if statment
					double distanceCost = e.distance;
					if (v.distance + distanceCost < e.target.distance) {
						e.target.distance = v.distance + distanceCost;
						e.target.prev = v;								//going to the adjacents of the vertix and fill the distance and prev of every adjacent
						vertexQueue.remove(e.target);
						vertexQueue.add(e.target);
					}
				}
			}
		}
	}

	public List<Edge> getDijkstraPath(String s, String t) {
		doDijkstra(s,t);

		Vertex v = vertexNames.get(t);
		LinkedList<Edge> shortestPathEdges = new LinkedList<>();

		while (v.prev != null) {
			List<Edge> adjacencyList = v.adjacentEdges;
			for (Edge e : adjacencyList) {
				if (e.target.equals(v.prev)) {
					shortestPathEdges.addFirst(e);
				}
			}
			v = v.prev;
		}

		return shortestPathEdges;
	}
}