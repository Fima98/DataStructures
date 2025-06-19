package structures.Queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class StaticArrayQueue<T> implements Iterable<T> {

  private T[] data;
  private int head = 0;
  private int tail = 0;
  private int size = 0;

  @SuppressWarnings("unchecked")
  public StaticArrayQueue(int capacity) {
    data = (T[]) new Object[capacity];
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public boolean isFull() {
    return size == data.length;
  }

  public void enqueue(T elem) {
    if (isFull())
      throw new IllegalStateException("Queue is full");
    data[tail] = elem;
    tail = (tail + 1) % data.length;
    size++;
  }

  public T dequeue() {
    if (isEmpty())
      throw new NoSuchElementException("Queue is empty");
    T elem = data[head];
    data[head] = null; // очищення для збору сміття
    head = (head + 1) % data.length;
    size--;
    return elem;
  }

  public T peek() {
    if (isEmpty())
      throw new NoSuchElementException("Queue is empty");
    return data[head];
  }

  @Override
  public Iterator<T> iterator() {
    return new Iterator<T>() {
      int count = 0;
      int current = head;

      public boolean hasNext() {
        return count < size;
      }

      public T next() {
        if (!hasNext())
          throw new NoSuchElementException();
        T val = data[current];
        current = (current + 1) % data.length;
        count++;
        return val;
      }
    };
  }

  public static void main(String[] args) {
    StaticArrayQueue<Integer> queue = new StaticArrayQueue<>(5);
    queue.enqueue(1);
    queue.enqueue(2);
    queue.enqueue(3);
    System.out.println("Size: " + queue.size()); // Size: 3
    System.out.println("Peek: " + queue.peek()); // Peek: 1
    System.out.println("Dequeue: " + queue.dequeue());
    System.out.println("Size after dequeue: " + queue.size()); // Size after dequeue: 2

    for (Integer elem : queue) {
      System.out.print(elem + " "); // 2 3
    }
  }
}