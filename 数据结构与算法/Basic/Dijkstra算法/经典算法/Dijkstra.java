package dijkstra;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

// no negative weight
public class Dijkstra {
	// Դ�ڵ� head
	public static HashMap<Node, Integer> dijkstra1(Node head) {
		HashMap<Node, Integer> distanceMap = new HashMap<>();// head�������ڵ����̾��� key -> �ڵ� value -> ����
		distanceMap.put(head, 0);
		HashSet<Node> selectedNodes = new HashSet<>();// s����
		// �ӿɴ�Ľڵ����ҵ�һ��·����̵Ľڵ�
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

	// ���·��distanceMap,s���� touchedNodes
	public static Node getMinDistanceAndUnselectedNode(HashMap<Node, Integer> distanceMap, HashSet<Node> touchedNodes) {
		// ���ؽ�� minNode
		Node minNode = null;
		int minDistance = Integer.MAX_VALUE;
		// ����Map
		for (Entry<Node, Integer> entry : distanceMap.entrySet()) {
			Node node = entry.getKey();
			int distance = entry.getValue();
			// ��ǰ�ڵ㲻��s�У����Ҿ���С����С�ڵ�
			if (!touchedNodes.contains(node) && distance < minDistance) {
				// ��������Ϊ���ؽ��
				minNode = node;
				minDistance = distance;
			}
		}
		return minNode;
	}
	


}
