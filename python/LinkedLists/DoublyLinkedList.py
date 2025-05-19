from typing import Generic, TypeVar, Optional, Iterator

T = TypeVar('T')


class DoublyLinkedList(Generic[T]):
    class _Node:
        __slots__ = ('data', 'prev', 'next')

        def __init__(self, data: T, prev: Optional['DoublyLinkedList._Node'], next: Optional['DoublyLinkedList._Node']):
            self.data = data
            self.prev = prev
            self.next = next

        def __str__(self) -> str:
            return str(self.data)

    def __init__(self) -> None:
        self._size: int = 0
        self._head: Optional[DoublyLinkedList._Node] = None
        self._tail: Optional[DoublyLinkedList._Node] = None

    def clear(self) -> None:
        trav = self._head
        while trav is not None:
            next = trav.next
            trav.prev = trav.next = None
            trav.data = None  # type: ignore
            trav = next
        self._head = self._tail = None
        self._size = 0

    def size(self) -> int:
        return self._size

    def is_empty(self) -> bool:
        return self._size == 0

    def add(self, value: T) -> None:
        self.add_last(value)

    def add_first(self, value: T) -> None:
        if self.is_empty():
            node = DoublyLinkedList._Node(value, None, None)
            self._head = self._tail = node
        else:
            node = DoublyLinkedList._Node(value, None, self._head)
            self._head.prev = node  # type: ignore
            self._head = node
        self._size += 1

    def add_last(self, value: T) -> None:
        if self.is_empty():
            node = DoublyLinkedList._Node(value, None, None)
            self._head = self._tail = node
        else:
            node = DoublyLinkedList._Node(value, self._tail, None)
            self._tail.next = node  # type: ignore
            self._tail = node
        self._size += 1

    def add_at(self, index: int, value: T) -> None:
        if index < 0 or index > self._size:
            raise IndexError(f"Index {index} is out of bounds.")
        if index == 0:
            self.add_first(value)
            return
        if index == self._size:
            self.add_last(value)
            return
        if index < self._size // 2:
            trav = self._head
            for _ in range(index - 1):
                trav = trav.next  # type: ignore
        else:
            trav = self._tail
            for _ in range(self._size - 1, index - 1, -1):
                trav = trav.prev  # type: ignore
        new_node = DoublyLinkedList._Node(
            value, trav, trav.next)  # type: ignore
        trav.next.prev = new_node  # type: ignore
        trav.next = new_node  # type: ignore
        self._size += 1

    def get_first(self) -> T:
        if self.is_empty():
            raise IndexError("List is empty")
        return self._head.data  # type: ignore

    def get_last(self) -> T:
        if self.is_empty():
            raise IndexError("List is empty")
        return self._tail.data  # type: ignore

    def remove_first(self) -> T:
        if self.is_empty():
            raise IndexError("List is empty")
        data = self._head.data  # type: ignore
        self._head = self._head.next  # type: ignore
        self._size -= 1
        if self.is_empty():
            self._tail = None
        else:
            self._head.prev = None  # type: ignore
        return data

    def remove_last(self) -> T:
        if self.is_empty():
            raise IndexError("List is empty")
        data = self._tail.data  # type: ignore
        self._tail = self._tail.prev  # type: ignore
        self._size -= 1
        if self.is_empty():
            self._head = None
        else:
            self._tail.next = None  # type: ignore
        return data

    def _remove_node(self, node: _Node) -> T:
        if node.prev is None:
            return self.remove_first()
        if node.next is None:
            return self.remove_last()
        node.next.prev = node.prev
        node.prev.next = node.next
        data = node.data
        node.data = None  # type: ignore
        node.prev = node.next = None
        self._size -= 1
        return data  # type: ignore

    def remove_at(self, index: int) -> T:
        if index < 0 or index >= self._size:
            raise IndexError(f"Index {index} is out of bounds.")
        if index < self._size // 2:
            trav = self._head
            for _ in range(index):
                trav = trav.next  # type: ignore
        else:
            trav = self._tail
            for _ in range(self._size - 1, index, -1):
                trav = trav.prev  # type: ignore
        return self._remove_node(trav)  # type: ignore

    def remove(self, obj: object) -> bool:
        trav = self._head
        if obj is None:
            while trav is not None:
                if trav.data is None:
                    self._remove_node(trav)
                    return True
                trav = trav.next
        else:
            while trav is not None:
                if obj == trav.data:
                    self._remove_node(trav)
                    return True
                trav = trav.next
        return False

    def index_of(self, obj: object) -> int:
        trav = self._head
        idx = 0
        if obj is None:
            while trav is not None:
                if trav.data is None:
                    return idx
                trav = trav.next
                idx += 1
        else:
            while trav is not None:
                if obj == trav.data:
                    return idx
                trav = trav.next
                idx += 1
        return -1

    def contains(self, obj: object) -> bool:
        return self.index_of(obj) != -1

    def __str__(self) -> str:
        sb = []
        trav = self._head
        while trav is not None:
            sb.append(str(trav.data))
            trav = trav.next
        return "[" + ", ".join(sb) + "]"

    def __iter__(self) -> Iterator[T]:
        trav = self._head
        while trav is not None:
            yield trav.data  # type: ignore
            trav = trav.next


# ---------------- MAIN ----------------
if __name__ == "__main__":
    lst = DoublyLinkedList[int]()
    print("Initial list:", lst)

    # Add elements
    lst.add_first(10)
    lst.add_last(20)
    lst.add(30)
    lst.add_at(2, 49)
    print("After adds:", lst)

    # Access elements
    print("First:", lst.get_first())
    print("Last:", lst.get_last())

    # Remove by index
    print("remove_at(1):", lst.remove_at(1))
    print("After remove_at:", lst)

    # Remove first/last
    print("remove_first():", lst.remove_first())
    print("remove_last():", lst.remove_last())
    print("After remove_first/last:", lst)

    # Add more and test remove(obj)
    lst.add(100)
    lst.add(200)
    lst.add(300)
    print("Before remove(obj):", lst)
    lst.remove(200)
    print("After remove(200):", lst)

    # Iteration
    print("Iterating:", end=" ")
    for val in lst:
        print(val, end=" ")
    print()

    # Clear
    lst.clear()
    print("After clear, is_empty:", lst.is_empty())
