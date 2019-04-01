package dijkstra;

import java.util.HashMap;

import Basic08.Node;

public class NodeHeap {
	private Node[] nodes;
	private HashMap<Node, Integer> heapIndexMap;
	private HashMap<Node, Integer> distanceMap;
	private int size;

	// ����С���ѣ�size��ʾ�ڵ�ĸ���������ĳ��ȣ�
	public NodeHeap(int size) {
		nodes = new Node[size];// ��֪�����ýڵ�
		heapIndexMap = new HashMap<>();// ��֪�ڵ�������
		// �ڵ㵽Դ�ڵ�ľ���
		distanceMap = new HashMap<>();
		this.size = 0;
	}

	// �ж�heap���Ƿ��Ѿ�û��Ԫ����
	public boolean isEmpty() {
		return size == 0;
	}

	// ���¶�
	public void addOrUpdateOrIgnore(Node node, int distance) {
		// ��node�ڶ���ʱ
		if (inHeap(node)) {
			// ����distanceMap�еľ��룺ԭ���ľ����뵱ǰ�ľ��루�ȵ����Ž�ľ���+�ߵ�Ȩ�أ�
			distanceMap.put(node, Math.min(distanceMap.get(node), distance));
			// ���¶��е�λ��
			insertHeapify(node, heapIndexMap.get(node));
		}
		// ���ڶ��У�����Ҳδ���ɴ�
		if (!isEntered(node)) {
			// ���ڵ���ڶѵĺ���
			nodes[size] = node;
			heapIndexMap.put(node, size);
			// ����distanceMap
			distanceMap.put(node, distance);
			// ����С���ѣ������ѵĴ�С+1
			insertHeapify(node, size++);
		}
	}

	// �����Ѷ��Ľڵ�;���
	public NodeRecord popMinDistance() {
		NodeRecord nodeRecord = new NodeRecord(nodes[0], distanceMap.get(nodes[0]));
		// ���һ��λ�ú�0λ�ý���
		swap(0, size - 1);
		heapIndexMap.put(nodes[size - 1], -1);
		// �Ƴ���С�ڵ�
		distanceMap.remove(nodes[size - 1]);
		// ɾ�������е���С�ڵ�
		nodes[size - 1] = null;
		// ����С���ѣ��ѵĴ�С-1
		heapify(0, --size);
		return nodeRecord;
	}

	// ���¶��еľ��룬����С���ѵľ����㷨
	private void insertHeapify(Node node, int index) {
		// ���丸�ڵ�ľ�����бȽϣ����С����ǰ����
		// ��index�õ��ڵ㣬��node�õ�����
		while (distanceMap.get(nodes[index]) < distanceMap.get(nodes[(index - 1) / 2])) {
			swap(index, (index - 1) / 2);
			index = (index - 1) / 2;
		}
	}

	private void heapify(int index, int size) {
		int left = index * 2 + 1;
		while (left < size) {
			int smallest = left + 1 < size && distanceMap.get(nodes[left + 1]) < distanceMap.get(nodes[left]) ? left + 1
					: left;
			smallest = distanceMap.get(nodes[smallest]) < distanceMap.get(nodes[index]) ? smallest : index;
			if (smallest == index) {
				break;
			}
			swap(smallest, index);
			index = smallest;
			left = index * 2 + 1;
		}
	}

	// �жϵ�ǰ�ڵ��Ƿ�ɴ�
	// �ɴ�˵����indexMap�д��ڶ�Ӧ��keyNode
	private boolean isEntered(Node node) {
		return heapIndexMap.containsKey(node);
	}

	// �ж�node�Ƿ��ڶ���
	// 1.û��ӹ�����indexmap��û�м�¼��
	// 2.��indexmap���м�¼Ϊ-1��˵���Ѿ��õ����Ž�
	private boolean inHeap(Node node) {
		return isEntered(node) && heapIndexMap.get(node) != -1;
	}

	// �����ڵ㽻��λ��
	private void swap(int index1, int index2) {
		// ��indexmap��Ҫ����
		heapIndexMap.put(nodes[index1], index2);
		heapIndexMap.put(nodes[index2], index1);
		// �������е�λ��ҲҪ����
		Node tmp = nodes[index1];
		nodes[index1] = nodes[index2];
		nodes[index2] = tmp;
	}
}
