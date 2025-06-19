package structures.Queue;

public class Queue<T> implements Iterable<T> {
  private java.util.LinkedList<T> list = new java.util.LinkedList<T>();

  public Queue() {
  }

  public Queue(T firstElem) {
    enqueue(firstElem);
  }

  public int size() {
    return list.size();
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  public T peek() {
    if (isEmpty())
      throw new IllegalStateException("Queue is empty");
    return list.peekFirst();
  }

  public void enqueue(T elem) {
    list.addLast(elem);
  }

  public T dequeue() {
    if (isEmpty())
      throw new IllegalStateException("Queue is empty");
    return list.removeFirst();
  }

  @Override
  public java.util.Iterator<T> iterator() {
    return list.iterator();
  }

  @Override
  public String toString() {
    return list.toString();
  }

  public static void main(String[] args) {
    Queue<Integer> queue = new Queue<>();
    queue.enqueue(1);
    queue.enqueue(2);
    queue.enqueue(3);

    System.out.println("Queue: " + queue); // Output: Queue: [1, 2, 3]
    System.out.println("Queue size: " + queue.size()); // Output: Queue size: 3
    System.out.println("Peek: " + queue.peek()); // Output: Peek: 1

    while (!queue.isEmpty()) {
      System.out.println("Dequeue: " + queue.dequeue());
    }
  }

}
