import ctypes


class DynamicArray:
    def __init__(self):
        self._capacity = 10
        self._size = 0
        self._data = self._make_array(self._capacity)

    def add(self, value):
        self._ensure_capacity()
        self._data[self._size] = value
        self._size += 1

    def get(self, index):
        self._check_bounds(index)
        return self._data[index]

    def set(self, index, value):
        self._check_bounds(index)
        self._data[index] = value

    def remove_at(self, index):
        self._check_bounds(index)
        removed = self._data[index]
        for i in range(index, self._size - 1):
            self._data[i] = self._data[i + 1]
        self._data[self._size - 1] = None
        self._size -= 1
        return removed

    def remove(self, obj):
        index = self.index_of(obj)
        if index == -1:
            return False
        self.remove_at(index)
        return True

    def index_of(self, obj):
        for i in range(self._size):
            if obj is None:
                if self._data[i] is None:
                    return i
            else:
                if self._data[i] == obj:
                    return i
        return -1

    def contains(self, obj):
        return self.index_of(obj) != -1

    def size(self):
        return self._size

    def is_empty(self):
        return self._size == 0

    def print(self):
        print("DynamicArray: [", end="")
        for i in range(self._size):
            print(self._data[i], end=", " if i < self._size - 1 else "")
        print("]")

    def _ensure_capacity(self):
        if self._size == self._capacity:
            self._capacity *= 2
            new_data = self._make_array(self._capacity)
            for i in range(self._size):
                new_data[i] = self._data[i]
            self._data = new_data

    def _check_bounds(self, index):
        if index < 0 or index >= self._size:
            raise IndexError(f"Index {index} is out of bounds.")

    @staticmethod
    def _make_array(capacity):
        return (capacity * ctypes.py_object)()

    def __iter__(self):
        for i in range(self._size):
            yield self._data[i]

    def __str__(self):
        return "[" + ", ".join(str(self._data[i]) for i in range(self._size)) + "]"


# ---------------- MAIN ----------------
if __name__ == "__main__":
    array = DynamicArray()

    array.add(10)
    array.add(20)
    array.add(30)

    array.print()  # [10, 20, 30]

    array.set(1, 99)
    array.print()  # [10, 99, 30]

    print("Element at index 0:", array.get(0))
    print("Size:", array.size())

    array.remove_at(1)
    array.print()  # [10, 30]

    print("Contains 30?", array.contains(30))  # True
    print("Index of 10:", array.index_of(10))  # 0
    print("Remove 30:", array.remove(30))      # True
    array.print()  # [10]
    print("Is empty?", array.is_empty())       # False

    print("Iterating:", end=" ")
    for val in array:
        print(val, end=" ")
    print()
