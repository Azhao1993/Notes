package dijkstra;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

// no negative weight
public class Dijkstra {
	// 源节点 head
	public static HashMap<Node, Integer> dijkstra1(Node head) {
		HashMap<Node, Integer> distanceMap = new HashMap<>();// head到其他节点的最短距离 key -> 节点 value -> 距离
		distanceMap.put(head, 0);
		HashSet<Node> selectedNodes = new HashSet<>();// s集合
		// 从可达的节点中找到一个路径最短的节点
		Node minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
		while (minNode != null) {
			int distance = distanceMap.get(minNode);
			for (Edge edge : minNode.edges) {
				Node toNode = edge.to;
				if (!distanceMap.containsKey(toNode)) {
					distanceMap.put(toNode, distance + edge.weight);
				}
				distanceMap.put(edge.to, Math.min(distanceMap.get(toNode), distance + edge.weight));
			}
			selectedNodes.add(minNode);
			minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
		}
		return distanceMap;
	}

	// 最短路径distanceMap,s集合 touchedNodes
	public static Node getMinDistanceAndUnselectedNode(HashMap<Node, Integer> distanceMap, HashSet<Node> touchedNodes) {
		// 返回结果 minNode
		Node minNode = null;
		int minDistance = Integer.MAX_VALUE;
		// 遍历Map
		for (Entry<Node, Integer> entry : distanceMap.entrySet()) {
			Node node = entry.getKey();
			int distance = entry.getValue();
			// 当前节点不在s中，并且距离小于最小节点
			if (!touchedNodes.contains(node) && distance < minDistance) {
				// 将其设置为返回结果
				minNode = node;
				minDistance = distance;
			}
		}
		return minNode;
	}
	


}
