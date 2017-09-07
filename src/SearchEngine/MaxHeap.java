package SearchEngine;
/**
 * Created by cwz on 2015/6/21.
 */
import java.util.LinkedList;

public class MaxHeap {
	 private LinkedList<HeapItem> heap = new LinkedList<HeapItem>();
	 
	 //����
	 public void swapItem(int x, int y){
		 HeapItem temp = new HeapItem(heap.get(x).docID, heap.get(y).vectorSim);
		 heap.set(x, heap.get(y));
		 heap.set(y, temp);
	 }
	 
	 //����һ����Ԫ��
	 public void insert(HeapItem vector){
		 //�����±�Ϊ0��λ�ò���Ԫ��
		 if(heap.size() == 0) heap.add(new HeapItem(-1, 0));
		 heap.add(vector);
		 //��ʼ��������
		 this.heapUp(heap.size() - 1);
	 }
	 
	 //���ϵ��� 
	 public void heapUp(int index){
		 //���±����1��ʼ
		 if(index > 1){
			 //���׽ڵ�
			 int parent = index / 2;
			 HeapItem parentItem = heap.get(parent);
			 HeapItem indexItem = heap.get(index);
			 //������׽ڵ��ֵ��index��С
			 if(parentItem.vectorSim < indexItem.vectorSim){
				 //�����ڵ�
				 this.swapItem(parent, index);
				 //�ݹ����
				 this.heapUp(parent);
			 }
		 }
	 }
	 
	 //ʵ�ֶѵĵ���
	 public void adjust(int i, int n){
		 int child;
		 for(; i <= n / 2 ;){
			 child = i * 2;
			 //ʹchildָ��ϴ�Ķ���
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
			 //�ѵ�һ���ڵ������һ���ڵ����һ�£�����ʣ��ڵ�
			 this.swapItem(1, i);
			 this.adjust(1, i - 1);
		 }
	 }

}
