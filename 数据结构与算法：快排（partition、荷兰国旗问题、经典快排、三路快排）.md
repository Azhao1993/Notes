

## 一、快排 ##
### 1.partition
#### 1）问题
给定数组arr,给定num,要求将数组中小于等于num的数放在数组的左边，大于num的数放在数组的右边。

要求额外空间复杂度O（1），时间复杂度O（N）
#### 2）代码
	public static void partition(int[] arr, int num) {
		partition(arr, num, 0, arr.length - 1);
		return;
	}

	private static void partition(int[] arr, int num, int l, int r) {
		int less = l-1;
		int more = r+1;
		while(l<more) {
			if(arr[l]<=num) {
				swap(arr,++less,l);
			}else {
				swap(arr,--more,l);
			}
		}
	}

#### 3）分析
时间复杂度：O(N)；

额外空间复杂度：O（1）；

不能保证稳定性。

### 2.荷兰国旗问题
#### 1）问题
给定数组arr,给定num,要求将数组中小于num的放在数组的左边，等于num的放在数组的中间，大于num的放在数组的右边。
#### 2）代码
	public static int[] netherLand(int[] arr, int num, int l, int r) {
		int less = l - 1;// <num区域的最后一个索引
		int more = r + 1;// >num区域的第一索引
		// l:待定区域的第一个索引
		// 终止条件是l==more
		while (l < more) {
			if (arr[l] < num) {
				// 交换，<num区域扩大一个索引
				swap(arr, ++less, l++);// 注意++less
			} else if (arr[l] == num) {
				// 待定区域向后移动
				l++;
			} else {
				// 交换，>num区域向前移动一个索引
				swap(arr, --more, l);
			}
		}
		// 返回=num区域开始和结束的索引位置
		return new int[] { less + 1, more - 1 };
	}

#### 3）分析
时间复杂度：O(N^2)；

空间复杂度：O（1）；

不能保证稳定性。

### 3.快排
#### 1）思想
选一个基准值key，一般选择数组中的最后位置的数。

小于等于基准值的数放在数组的左边，大于基准值的的数放在右边，并将最后一个数与中间的数交换位置。数组被分为左右两部分，分别左右两部分用上述步骤实现排序。

#### 2）改进
经典快排的时间复杂度与数据状况有关。利用随机数消除数据状况对算法的影响。
快排每次只能排好一个数，利用荷兰国旗问题对快排进行改进。（三路快排）。

#### 3）代码
	//三路快排
	public static void quickSort(int[] arr) {
		quickSort(arr, 0, arr.length - 1);
	}

	private static void quickSort(int[] arr, int l, int r) {
		// 需要排序的范围是[l,r]
		if (l < r) {
			// 随机快排
			swap(arr, l + (int) (Math.random() * (r - l + 1)), r);
			// 等于arr[r]区域的起始和终止的索引位置
			int[] p = netherLand(arr, l, r);
			quickSort(arr, l, p[0] - 1);
			quickSort(arr, p[1] + 1, r);
		}

	}
	//经典快排下面的应该用partition的代码
	private static int[] netherLand(int[] arr, int l, int r) {
		int less = l - 1;
		int more = r + 1;
		while (l < more) {
			if (arr[l] < arr[r]) {
				swap(arr, ++less, l++);
			} else if (arr[l] > arr[r]) {
				swap(arr, --more, l);
			} else {
				l++;
			}
		}
		swap(arr, more, r);// 注意把最后一个数arr[r]放在=key的区域
		return new int[] { less + 1, more - 1 };
	}
