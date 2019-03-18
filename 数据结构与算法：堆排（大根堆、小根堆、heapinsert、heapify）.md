## 一、堆排 ##
### 1.堆
堆通常是一个可以被看做一棵树的数组对象。

堆总是满足下列性质：
1. 堆中某个节点的值总是不大于或不小于其父节点的值；
2. 堆总是一棵完全二叉树。

堆是线性数据结构，相当于一维数组，有唯一后继。

优先级队列PriorityQueue就是一种堆结构。
### 2.大根堆
将根节点最大的堆叫做最大堆或大根堆。
### 3.小根堆
根节点最小的堆叫做最小堆或小根堆。
### 4.将数组转换成完全二叉树
	数组中索引为i的数在完全二叉树中，
	其父节点在数组中的索引为（i-1）/2;
	其左子节点在数组中的索引为2*i-1，
	其右子节点在数组中的索引为2*i+2。


### 5. 建立大（小）根堆（heapinsert）
	public static void heapInsert(int[] arr, int index) {
		// 一直与其当前的位置的父节点大小比较，不用考虑越界（<0）的问题
		// 大根堆：arr[index] > arr[(index - 1) / 2] 大于其父亲时交换
		// 小根堆：arr[index] < arr[(index - 1) / 2] 小于其父亲时交换
		while (arr[index] > arr[(index - 1) / 2]) {
			swap(arr, index, (index - 1) / 2);
			index = (index - 1) / 2;
		}
	}

### 6.调整大根堆（heapify）
当堆中的数字发生改变时，如何调整为大根堆

	public static void heapify(int[] arr, int index, int heapsize) {
		// index 位置的数发生改变
		// 如果数变大则位置不变，如果数变小则下沉，与左右子中最大的数交换位置，一直到位置不动
		int left = index * 2 + 1;// 左子的索引
		// heapsize为堆的大小，保证不越界
		while (left < heapsize) {
			// largest表示左右子中较大的数的索引
			int largest = left + 1 < heapsize && arr[left + 1] > arr[left] ? left + 1 : left;
			// 判断当前较大的数与当前的数谁更大，比子大，不动，比下，下沉；
			largest = arr[largest] < arr[index] ? index : largest;
			// 当index不发生改变时，证明找到了位置，结束循环
			if (largest == index) {
				break;
			}
			// 交换位置
			swap(arr, largest, index);
			index = largest;
			left = index * 2 + 1;
		}

### 7.堆排序
思路

	（1）建立大根堆；	
	（2）把堆顶的数与最后一个位置的数交换位置，heapsize-1;
	（3）通过heapify修正堆顶的数的位置使其变成大根堆；
	（4）重复（2）（3）过程知道heapsize=0

代码

	public static void heapSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		// 建立大根堆
		for (int i = 0; i < arr.length; i++) {
			heapinsert(arr, i);
		}
		int heapsize = arr.length;
		// 将堆顶放在最后一个位置
		swap(arr, 0, --heapsize);
		// 利用heapify修正堆顶的数的位置

		// 重复这个过程
		while (heapsize > 0) {
			heapify(arr, 0, heapsize);
			swap(arr,0,--heapsize);
		}
	}

	private static void heapify(int[] arr, int i, int heapsize) {
		int left = 2 * i + 1;
		while (left < heapsize) {
			int largest = arr[left + 1] > arr[left] ? left + 1 : left;
			largest = arr[largest] > arr[i] ? largest : i;
			if (largest == i) {
				break;
			}
			swap(arr, largest, i);
			i = largest;
			left = 2 * i + 1;
		}

	}

	private static void heapinsert(int[] arr, int index) {
		while (arr[index] > arr[(index - 1) / 2]) {
			swap(arr, index, (index - 1) / 2);
			index = (index - 1) / 2;
		}
	}

### 8. 分析

时间复杂度：O(NlogN)；

空间复杂度;O(1);

### 9.应用

问题：
剑指offer第41题：[http://t.cn/ExfFH9X](http://t.cn/ExfFH9X "剑指offer第41题")
	
	数据流中的中位数：
		如果从数据流中读出奇数个数值，那么中位数就是所有数值排序之后位于中间的数值。
		如果从数据流中读出偶数个数值，那么中位数就是所有数值排序之后中间两个数的平均值。



思路：
	
	建立大根堆放N/2个数
	建立小根堆放N/2个数
	如果大根堆和小根堆的size差值等于2时
		大根堆的size大，将大根堆的堆顶与大根堆的最后一个数交换，heapsize-1,将这个数插入到小根堆；
		小根堆的size大，将小根堆的堆顶与小根堆的最后一个数交换，heapsize-1,将这个数插入到大根堆；
	数据流为偶数个，返回大根堆堆顶和小根堆堆顶的平均值；
	数据流为奇数个，反水size大的堆的堆顶。

代码：
	
	import java.util.Comparator;
	import java.util.PriorityQueue;
	
	public class MadianQuick {
		// 优先级队列就是堆结构
		private PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(new MaxHeapComparator());
		private PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>(new MinHeapComparator());
	
		public void Insert(Integer num) {
			// 大根堆为空
			if (this.maxHeap.isEmpty()) {
				this.maxHeap.add(num);
			} else if (this.maxHeap.peek() >= num) {
				// 比大根堆堆顶大加入大根堆
				this.maxHeap.add(num);
			} else {
				// 比大根堆堆顶小加入小根堆
				this.minHeap.add(num);
			}
			// 加完数立马算长度
			modifyTwoHeapsSize();
		}
	
		public Double GetMedian() {
			// 获取大根堆和小根堆的长度
			int maxHeapSize = this.maxHeap.size();
			int minHeapSize = this.minHeap.size();
			if (maxHeapSize + minHeapSize == 0) {
				return null;
			}
			// 获取堆顶
			Integer maxHeapHead = this.maxHeap.peek();
			Integer minHeapHead = this.minHeap.peek();
			// 偶数的处理情况
			if (((maxHeapSize + minHeapSize) & 1) == 0) {
				return new Double((maxHeapHead + minHeapHead)) / 2;
			}
			// 奇数处理情况
			return maxHeapSize > minHeapSize ? new Double(maxHeapHead) : new Double(minHeapHead);
		}
	
		// 小根堆和大根堆长度差>1时，要将长的头送进短堆
		private void modifyTwoHeapsSize() {
			// 大根堆长
			if (this.maxHeap.size() == this.minHeap.size() + 2) {
				this.minHeap.add(this.maxHeap.poll());
			}
			// 小根堆长
			if (this.minHeap.size() == this.maxHeap.size() + 2) {
				this.maxHeap.add(this.minHeap.poll());
			}
		}
	
		// 内部类maxHeapComparator实现比较器
		public static class MaxHeapComparator implements Comparator<Integer> {
			public int compare(Integer o1, Integer o2) {
				// 返回负数前面的小，返回正数后面的小，返回0,相等
				if (o2 > o1) {
					return 1;
				} else {
					return -1;
				}	
			}
		}
	
		// 内部类minHeapComparator实现比较器
		public static class MinHeapComparator implements Comparator<Integer> {
			public int compare(Integer o1, Integer o2) {
				if (o2 < o1) {
					return 1;
				} else {
					return -1;
				}
			}
		}
	}
	



