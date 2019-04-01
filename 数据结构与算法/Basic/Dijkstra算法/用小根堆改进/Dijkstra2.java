package dijkstra;

import java.util.HashMap;

import Basic08.Edge;
import Basic08.Node;
import Basic08.Code_07_Dijkstra.NodeHeap;
import Basic08.Code_07_Dijkstra.NodeRecord;

// no negative weight
public class Dijkstra2 {

	public static HashMap<Node, Integer> dijkstra2(Node head, int size) {
		// 构建一个小根堆
		NodeHeap nodeHeap = new NodeHeap(size);
		// 添加一个节点
		nodeHeap.addOrUpdateOrIgnore(head, 0);

		HashMap<Node, Integer> result = new HashMap<>();
		while (!nodeHeap.isEmpty()) {
			NodeRecord record = nodeHeap.popMinDistance();
			Node cur = record.node;
			int distance = record.distance;
			for (Edge edge : cur.edges) {
				nodeHeap.addOrUpdateOrIgnore(edge.to, edge.weight + distance);
			}
			result.put(cur, distance);
		}
		return result;
	}

}
