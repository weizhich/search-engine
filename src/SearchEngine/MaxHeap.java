package SearchEngine;
/**
 * Created by cwz on 2015/6/21.
 */
import java.util.LinkedList;

public class MaxHeap {
	 private LinkedList<HeapItem> heap = new LinkedList<HeapItem>();
	 
	 //交换
	 public void swapItem(int x, int y){
		 HeapItem temp = new HeapItem(heap.get(x).docID, heap.get(y).vectorSim);
		 heap.set(x, heap.get(y));
		 heap.set(y, temp);
	 }
	 
	 //插入一个堆元素
	 public void insert(HeapItem vector){
		 //数组下标为0的位置不放元素
		 if(heap.size() == 0) heap.add(new HeapItem(-1, 0));
		 heap.add(vector);
		 //开始上升操作
		 this.heapUp(heap.size() - 1);
	 }
	 
	 //向上调整 
	 public void heapUp(int index){
		 //从下标大于1开始
		 if(index > 1){
			 //求父亲节点
			 int parent = index / 2;
			 HeapItem parentItem = heap.get(parent);
			 HeapItem indexItem = heap.get(index);
			 //如果父亲节点的值比index的小
			 if(parentItem.vectorSim < indexItem.vectorSim){
				 //交换节点
				 this.swapItem(parent, index);
				 //递归调用
				 this.heapUp(parent);
			 }
		 }
	 }
	 
	 //实现堆的调整
	 public void adjust(int i, int n){
		 int child;
		 for(; i <= n / 2 ;){
			 child = i * 2;
			 //使child指向较大的儿子
			 if((child + 1 <= n) && (heap.get(child).vectorSim < heap.get(child + 1).vectorSim)) 
				 child += 1;
			 if(heap.get(i).vectorSim < heap.get(child).vectorSim){
				 this.swapItem(i, child);
				 i = child;
			 }else break;
		 }
	 }
	 
	 public void heapSort(){
		 int TopNum = 0;
		 if(heap.size() - 1 <= 0){
			 System.out.println("No such files founded!!!");
		 }
		 for(int i = heap.size() - 1; i > 0; i--){
			 TopNum++;
			 if(TopNum > 6) return;
			 System.out.println("[Top " + TopNum + "]: docId = " + heap.get(1).docID + "      Value = " + heap.get(1).vectorSim);
			 //把第一个节点与最后一个节点调换一下，调整剩余节点
			 this.swapItem(1, i);
			 this.adjust(1, i - 1);
		 }
	 }

}
