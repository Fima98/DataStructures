package structures.Stack;

public class Stack<T> implements Iterable<T> {
  private java.util.LinkedList<T> list = new java.util.LinkedList<T>();

  public Stack() {
  }

  public Stack(T firstElem) {
    push(firstElem);
  }

  public int size() {
    return list.size();
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  public void push(T elem) {
    list.addLast(elem);
  }

  public T pop() {
    if (isEmpty())
      throw new IllegalStateException("Stack is empty");
    return list.removeLast();
  }

  public T peek() {
    if (isEmpty())
      throw new IllegalStateException("Stack is empty");
    return list.getLast();
  }

  @Override
  public java.util.Iterator<T> iterator() {
    return list.iterator();
  }
}