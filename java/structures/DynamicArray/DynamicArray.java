package structures.DynamicArray;

public class DynamicArray<T> implements Iterable<T> {
    private T[] data;
    private int size;
    private int capacity;

    @SuppressWarnings("unchecked")
    public DynamicArray() {
        capacity = 10;
        size = 0;
        data = (T[]) new Object[capacity];
    }

    public void add(T value) {
        ensureCapacity();
        data[size++] = value;
    }

    public T get(int index) {
        checkBounds(index);
        return data[index];
    }

    public void set(int index, T value) {
        checkBounds(index);
        data[index] = value;
    }

    public T removeAt(int index) {
        checkBounds(index);
        T removed = data[index];
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        data[--size] = null;
        return removed;
    }

    public boolean remove(Object obj) {
        int index = indexOf(obj);
        if (index == -1)
            return false;
        removeAt(index);
        return true;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int indexOf(Object obj) {
        for (int i = 0; i < size; i++) {
            if (obj == null) {
                if (data[i] == null)
                    return i;
            } else {
                if (obj.equals(data[i]))
                    return i;
            }
        }
        return -1;
    }

    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @SuppressWarnings("unchecked")
    private void ensureCapacity() {
        if (size == capacity) {
            capacity *= 2;
            T[] newData = (T[]) new Object[capacity];
            for (int i = 0; i < size; i++) {
                newData[i] = data[i];
            }
            data = newData;
        }
    }

    private void checkBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds.");
        }
    }

    public void print() {
        System.out.print("[");
        for (int i = 0; i < size; i++) {
            System.out.print(data[i]);
            if (i < size - 1)
                System.out.print(", ");
        }
        System.out.println("]");
    }

    @Override
    public java.util.Iterator<T> iterator() {
        return new java.util.Iterator<T>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public T next() {
                return data[index++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1)
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {
        DynamicArray<Integer> array = new DynamicArray<>();

        array.add(10);
        array.add(20);
        array.add(30);

        array.print(); // [10, 20, 30]

        array.set(1, 99);
        array.print(); // [10, 99, 30]

        System.out.println("Element at index 0: " + array.get(0));
        System.out.println("Size: " + array.size());

        array.removeAt(1);
        array.print(); // [10, 30]

        System.out.println("Is empty? " + array.isEmpty());
    }
}
