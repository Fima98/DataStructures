package structures.LinkedLists;

public class SinglyLinkedList<T> implements Iterable<T> {

  private int size = 0;
  private Node<T> head = null;

  private static class Node<T> {
    T data;
    Node<T> next;

    Node(T data, Node<T> next) {
      this.data = data;
      this.next = next;
    }
  }

  public void clear() {
    head = null;
    size = 0;
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public void add(T value) {
    addLast(value);
  }

  public void addFirst(T value) {
    head = new Node<>(value, head);
    size++;
  }

  public void addLast(T value) {
    Node<T> newNode = new Node<>(value, null);
    if (isEmpty()) {
      head = newNode;
    } else {
      Node<T> trav = head;
      while (trav.next != null)
        trav = trav.next;
      trav.next = newNode;
    }
    size++;
  }

  public void addAt(int index, T value) {
    if (index < 0 || index > size)
      throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
    if (index == 0) {
      addFirst(value);
      return;
    }
    Node<T> prev = head;
    for (int i = 1; i < index; i++)
      prev = prev.next;
    prev.next = new Node<>(value, prev.next);
    size++;
  }

  public T getFirst() {
    if (isEmpty())
      throw new IllegalStateException("List is empty");
    return head.data;
  }

  public T getLast() {
    if (isEmpty())
      throw new IllegalStateException("List is empty");
    Node<T> trav = head;
    while (trav.next != null)
      trav = trav.next;
    return trav.data;
  }

  public T removeFirst() {
    if (isEmpty())
      throw new IllegalStateException("List is empty");
    T data = head.data;
    head = head.next;
    size--;
    return data;
  }

  public T removeLast() {
    if (isEmpty())
      throw new IllegalStateException("List is empty");
    if (size == 1)
      return removeFirst();
    Node<T> prev = head;
    while (prev.next.next != null)
      prev = prev.next;
    T data = prev.next.data;
    prev.next = null;
    size--;
    return data;
  }

  public T removeAt(int index) {
    if (index < 0 || index >= size)
      throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
    if (index == 0)
      return removeFirst();
    Node<T> prev = head;
    for (int i = 1; i < index; i++)
      prev = prev.next;
    T data = prev.next.data;
    prev.next = prev.next.next;
    size--;
    return data;
  }

  public boolean remove(Object obj) {
    if (isEmpty())
      return false;
    if (obj == null) {
      if (head.data == null) {
        removeFirst();
        return true;
      }
      Node<T> prev = head;
      while (prev.next != null) {
        if (prev.next.data == null) {
          prev.next = prev.next.next;
          size--;
          return true;
        }
        prev = prev.next;
      }
    } else {
      if (obj.equals(head.data)) {
        removeFirst();
        return true;
      }
      Node<T> prev = head;
      while (prev.next != null) {
        if (obj.equals(prev.next.data)) {
          prev.next = prev.next.next;
          size--;
          return true;
        }
        prev = prev.next;
      }
    }
    return false;
  }

  public int indexOf(Object obj) {
    Node<T> trav = head;
    int index = 0;
    if (obj == null) {
      while (trav != null) {
        if (trav.data == null)
          return index;
        trav = trav.next;
        index++;
      }
    } else {
      while (trav != null) {
        if (obj.equals(trav.data))
          return index;
        trav = trav.next;
        index++;
      }
    }
    return -1;
  }

  public boolean contains(Object obj) {
    return indexOf(obj) != -1;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("[");
    Node<T> trav = head;
    while (trav != null) {
      sb.append(trav.data);
      if (trav.next != null)
        sb.append(", ");
      trav = trav.next;
    }
    sb.append("]");
    return sb.toString();
  }

  @Override
  public java.util.Iterator<T> iterator() {
    return new java.util.Iterator<T>() {
      private Node<T> current = head;

      @Override
      public boolean hasNext() {
        return current != null;
      }

      @Override
      public T next() {
        if (!hasNext())
          throw new java.util.NoSuchElementException();
        T data = current.data;
        current = current.next;
        return data;
      }
    };
  }

  // -------------- MAIN --------------
  public static void main(String[] args) {
    SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
    System.out.println("Initial: " + list); // Print the initial (empty) list

    // Add an element at the beginning
    list.addFirst(10);

    // Add an element at the end
    list.addLast(20);

    // Add an element at the end (same as addLast)
    list.add(30);

    // Add an element at index 1 (between 10 and 20)
    list.addAt(1, 15);
    System.out.println("After adds: " + list); // Expected: [10, 15, 20, 30]

    // Get first and last elements
    System.out.println("First: " + list.getFirst() + ", Last: " + list.getLast()); // 10, 30

    // Remove the element at index 2 (value 20)
    System.out.println("removeAt(2): " + list.removeAt(2));
    System.out.println("After removeAt: " + list); // Expected: [10, 15, 30]

    // Remove the element with value 15
    list.remove((Integer) 15);
    System.out.println("After remove(15): " + list); // Expected: [10, 30]

    // Iterate through the list
    System.out.print("Iterating: ");
    for (int v : list)
      System.out.print(v + " ");
    System.out.println(); // Output: 10 30

    // Clear the list
    list.clear();
    System.out.println("After clear, isEmpty: " + list.isEmpty()); // true
  }
}
