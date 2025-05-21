from typing import Generic, TypeVar, Optional, Iterator

T = TypeVar('T')


class SinglyLinkedList(Generic[T]):
    class _Node:
        __slots__ = ('data', 'next')

        def __init__(self, data: T, next: Optional['SinglyLinkedList._Node']):
            self.data = data
            self.next = next

        def __str__(self) -> str:
            return str(self.data)

    def __init__(self):
        self._size: int = 0
        self._head: Optional[SinglyLinkedList._Node] = None

    def clear(self) -> None:
        trav = self._head
        while trav is not None:
            nxt = trav.next
            trav.next = None
            trav.data = None  # type: ignore
            trav = nxt
        self._head = None
        self._size = 0

    def size(self) -> int:
        return self._size

    def is_empty(self) -> bool:
        return self._size == 0

    def add(self, value: T) -> None:
        self.add_last(value)

    def add_first(self, value: T) -> None:
        new_node = SinglyLinkedList._Node(value, self._head)
        self._head = new_node
        self._size += 1

    def add_last(self, value: T) -> None:
        new_node = SinglyLinkedList._Node(value, None)
        if self.is_empty():
            self._head = new_node
        else:
            trav = self._head
            while trav.next is not None:
                trav = trav.next
            trav.next = new_node
        self._size += 1

    def add_at(self, index: int, value: T) -> None:
        if index < 0 or index > self._size:
            raise IndexError(f"Index {index} out of bounds")
        if index == 0:
            self.add_first(value)
            return
        prev = self._head
        for _ in range(1, index):
            prev = prev.next  # type: ignore
        node = SinglyLinkedList._Node(value, prev.next)  # type: ignore
        prev.next = node  # type: ignore
        self._size += 1

    def get_first(self) -> T:
        if self.is_empty():
            raise IndexError("List is empty")
        return self._head.data  # type: ignore

    def get_last(self) -> T:
        if self.is_empty():
            raise IndexError("List is empty")
        trav = self._head
        while trav.next is not None:  # type: ignore
            trav = trav.next  # type: ignore
        return trav.data  # type: ignore

    def remove_first(self) -> T:
        if self.is_empty():
            raise IndexError("List is empty")
        data = self._head.data  # type: ignore
        self._head = self._head.next  # type: ignore
        self._size -= 1
        return data

    def remove_last(self) -> T:
        if self.is_empty():
            raise IndexError("List is empty")
        if self._size == 1:
            return self.remove_first()
        prev = self._head
        while prev.next.next is not None:  # type: ignore
            prev = prev.next  # type: ignore
        data = prev.next.data  # type: ignore
        prev.next = None  # type: ignore
        self._size -= 1
        return data

    def remove_at(self, index: int) -> T:
        if index < 0 or index >= self._size:
            raise IndexError(f"Index {index} out of bounds")
        if index == 0:
            return self.remove_first()
        prev = self._head
        for _ in range(1, index):
            prev = prev.next  # type: ignore
        data = prev.next.data  # type: ignore
        prev.next = prev.next.next  # type: ignore
        self._size -= 1
        return data

    def remove(self, value: T) -> bool:
        if self.is_empty():
            return False
        if value is None:
            if self._head.data is None:  # type: ignore
                self.remove_first()
                return True
            prev = self._head
            while prev.next is not None:
                if prev.next.data is None:  # type: ignore
                    prev.next = prev.next.next
                    self._size -= 1
                    return True
                prev = prev.next  # type: ignore
        else:
            if value == self._head.data:  # type: ignore
                self.remove_first()
                return True
            prev = self._head
            while prev.next is not None:
                if value == prev.next.data:  # type: ignore
                    prev.next = prev.next.next
                    self._size -= 1
                    return True
                prev = prev.next  # type: ignore
        return False

    def index_of(self, value: T) -> int:
        trav = self._head
        index = 0
        if value is None:
            while trav is not None:
                if trav.data is None:
                    return index
                trav = trav.next
                index += 1
        else:
            while trav is not None:
                if value == trav.data:
                    return index
                trav = trav.next
                index += 1
        return -1

    def contains(self, value: T) -> bool:
        return self.index_of(value) != -1

    def __iter__(self) -> Iterator[T]:
        trav = self._head
        while trav is not None:
            yield trav.data  # type: ignore
            trav = trav.next  # type: ignore

    def __str__(self) -> str:
        elements = []
        trav = self._head
        while trav is not None:
            elements.append(str(trav.data))
            trav = trav.next
        return f"[{', '.join(elements)}]"


if __name__ == "__main__":
    lst = SinglyLinkedList[int]()
    print("Initial:", lst)  # []

    # Add an element at the beginning
    lst.add_first(10)

    # Add an element at the end
    lst.add_last(20)

    # Add an element at the end (same as add)
    lst.add(30)

    # Add an element at index 1 (between 10 and 20)
    lst.add_at(1, 15)
    print("After adds:", lst)  # [10, 15, 20, 30]

    # Get first and last elements
    # First: 10, Last: 30
    print(f"First: {lst.get_first()}, Last: {lst.get_last()}")

    # Remove the element at index 2 (value 20)
    print("remove_at(2):", lst.remove_at(2))
    print("After remove_at:", lst)  # [10, 15, 30]

    # Remove the element with value 15
    lst.remove(15)
    print("After remove(15):", lst)  # [10, 30]

    # Iterate through the list
    print("Iterating:", end=" ")
    for v in lst:
        print(v, end=" ")
    print()

    # Clear the list
    lst.clear()
    print("After clear, is_empty():", lst.is_empty())  # True
