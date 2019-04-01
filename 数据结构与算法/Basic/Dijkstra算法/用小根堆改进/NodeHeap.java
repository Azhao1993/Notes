package dijkstra;

import java.util.HashMap;

import Basic08.Node;

public class NodeHeap {
	private Node[] nodes;
	private HashMap<Node, Integer> heapIndexMap;
	private HashMap<Node, Integer> distanceMap;
	private int size;

	// 构造小根堆（size表示节点的个数，数组的长度）
	public NodeHeap(int size) {
		nodes = new Node[size];// 已知索引拿节点
		heapIndexMap = new HashMap<>();// 已知节点拿索引
		// 节点到源节点的距离
		distanceMap = new HashMap<>();
		this.size = 0;
	}

	// 判断heap中是否已经没有元素了
	public boolean isEmpty() {
		return size == 0;
	}

	// 更新堆
	public void addOrUpdateOrIgnore(Node node, int distance) {
		// 当node在堆中时
		if (inHeap(node)) {
			// 更新distanceMap中的距离：原来的距离与当前的距离（等到最优解的距离+边的权重）
			distanceMap.put(node, Math.min(distanceMap.get(node), distance));
			// 更新堆中的位置
			insertHeapify(node, heapIndexMap.get(node));
		}
		// 不在堆中，但是也未曾可达
		if (!isEntered(node)) {
			// 将节点放在堆的后面
			nodes[size] = node;
			heapIndexMap.put(node, size);
			// 放入distanceMap
			distanceMap.put(node, distance);
			// 更新小根堆，并将堆的大小+1
			insertHeapify(node, size++);
		}
	}

	// 弹出堆顶的节点和距离
	public NodeRecord popMinDistance() {
		NodeRecord nodeRecord = new NodeRecord(nodes[0], distanceMap.get(nodes[0]));
		// 最后一个位置和0位置交换
		swap(0, size - 1);
		heapIndexMap.put(nodes[size - 1], -1);
		// 移除最小节点
		distanceMap.remove(nodes[size - 1]);
		// 删除数组中的最小节点
		nodes[size - 1] = null;
		// 调整小根堆，堆的大小-1
		heapify(0, --size);
		return nodeRecord;
	}

	// 更新堆中的距离，调整小根堆的经典算法
	private void insertHeapify(Node node, int index) {
		// 与其父节点的距离进行比较，如果小就向前交换
		// 用index拿到节点，用node拿到距离
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

	// 判断当前节点是否可达
	// 可达说明在indexMap中存在对应的keyNode
	private boolean isEntered(Node node) {
		return heapIndexMap.containsKey(node);
	}

	// 判断node是否在堆中
	// 1.没添加过（在indexmap中没有记录）
	// 2.在indexmap中有记录为-1，说明已经得到最优解
	private boolean inHeap(Node node) {
		return isEntered(node) && heapIndexMap.get(node) != -1;
	}

	// 两个节点交换位置
	private void swap(int index1, int index2) {
		// 在indexmap中要交换
		heapIndexMap.put(nodes[index1], index2);
		heapIndexMap.put(nodes[index2], index1);
		// 在数组中的位置也要交换
		Node tmp = nodes[index1];
		nodes[index1] = nodes[index2];
		nodes[index2] = tmp;
	}
}
