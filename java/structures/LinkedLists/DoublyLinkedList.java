package structures.LinkedLists;

public class DoublyLinkedList<T> implements Iterable<T> {

  private int size = 0;
  private Node<T> head = null;
  private Node<T> tail = null;

  private static class Node<T> {
    T data;
    Node<T> prev, next;

    public Node(T data, Node<T> prev, Node<T> next) {
      this.data = data;
      this.prev = prev;
      this.next = next;
    }

    @Override
    public String toString() {
      return data.toString();
    }
  }

  public void clear() {
    Node<T> trav = head;
    while (trav != null) {
      Node<T> next = trav.next;
      trav.prev = trav.next = null;
      trav.data = null;
      trav = next;
    }
    head = tail = null;
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
    if (isEmpty()) {
      head = tail = new Node<>(value, null, null);
    } else {
      Node<T> newNode = new Node<>(value, null, head);
      head.prev = newNode;
      head = newNode;
    }
    size++;
  }

  public void addLast(T value) {
    if (isEmpty()) {
      head = tail = new Node<>(value, null, null);
    } else {
      Node<T> newNode = new Node<>(value, tail, null);
      tail.next = newNode;
      tail = newNode;
    }
    size++;
  }

  public void addAt(int index, T value) {
    if (index < 0 || index > size)
      throw new IndexOutOfBoundsException("Index " + index + " is out of bounds.");

    if (index == 0) {
      addFirst(value);
      return;
    }

    if (index == size) {
      addLast(value);
      return;
    }

    Node<T> trav;
    if (index < size / 2) {
      trav = head;
      for (int i = 0; i < index - 1; i++)
        trav = trav.next;
    } else {
      trav = tail;
      for (int i = size - 1; i >= index; i--)
        trav = trav.prev;
    }

    Node<T> newNode = new Node<>(value, trav, trav.next);
    trav.next.prev = newNode;
    trav.next = newNode;
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
    return tail.data;
  }

  public T removeFirst() {
    if (isEmpty())
      throw new IllegalStateException("List is empty");
    T data = head.data;
    head = head.next;
    size--;
    if (isEmpty())
      tail = null;
    else
      head.prev = null;
    return data;
  }

  public T removeLast() {
    if (isEmpty())
      throw new IllegalStateException("List is empty");
    T data = tail.data;
    tail = tail.prev;
    size--;
    if (isEmpty())
      head = null;
    else
      tail.next = null;
    return data;
  }

  private T remove(Node<T> node) {
    if (node.prev == null)
      return removeFirst();
    if (node.next == null)
      return removeLast();

    node.next.prev = node.prev;
    node.prev.next = node.next;
    T data = node.data;
    node.data = null;
    node.prev = node.next = null;
    size--;
    return data;
  }

  public T removeAt(int index) {
    if (index < 0 || index >= size)
      throw new IndexOutOfBoundsException("Index " + index + " is out of bounds.");

    Node<T> trav;
    if (index < size / 2) {
      trav = head;
      for (int i = 0; i < index; i++)
        trav = trav.next;
    } else {
      trav = tail;
      for (int i = size - 1; i > index; i--)
        trav = trav.prev;
    }
    return remove(trav);
  }

  public boolean remove(Object obj) {
    Node<T> trav = head;
    if (obj == null) {
      while (trav != null) {
        if (trav.data == null) {
          remove(trav);
          return true;
        }
        trav = trav.next;
      }
    } else {
      while (trav != null) {
        if (obj.equals(trav.data)) {
          remove(trav);
          return true;
        }
        trav = trav.next;
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
      private Node<T> trav = head;

      @Override
      public boolean hasNext() {
        return trav != null;
      }

      @Override
      public T next() {
        T data = trav.data;
        trav = trav.next;
        return data;
      }
    };
  }

  // ---------------- MAIN ----------------
  public static void main(String[] args) {
    DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
    System.out.println("Initial list: " + list);

    // Add elements
    list.addFirst(10);
    list.addLast(20);
    list.add(30);
    list.addAt(2, 49);
    System.out.println("After adds: " + list);

    // Access elements
    System.out.println("First: " + list.getFirst());
    System.out.println("Last: " + list.getLast());

    // Remove by index
    System.out.println("removeAt(1): " + list.removeAt(1));
    System.out.println("After removeAt: " + list);

    // Remove first/last
    System.out.println("removeFirst(): " + list.removeFirst());
    System.out.println("removeLast(): " + list.removeLast());
    System.out.println("After removeFirst/Last: " + list);

    // Add more and test remove(Object)
    list.add(100);
    list.add(200);
    list.add(300);
    System.out.println("Before remove(obj): " + list);
    list.remove((Integer) 200);
    System.out.println("After remove(200): " + list);

    // Iteration
    System.out.print("Iterating: ");
    for (int val : list)
      System.out.print(val + " ");
    System.out.println();

    // Clear
    list.clear();
    System.out.println("After clear, isEmpty: " + list.isEmpty());
  }
}
