package structures.Stack;

public class BracketsProblem {
  public static boolean isBalanced(String expression) {
    Stack<Character> stack = new Stack<>(); // Створення об'єкта стеку для символів

    for (char c : expression.toCharArray()) {
      if (c == '{' || c == '[' || c == '(') {
        stack.push(c);
      } else if (c == '}' || c == ']' || c == ')') {
        if (stack.isEmpty()) {
          return false; // Закриваюча дужка без відповідної відкриваючої
        }
        char top = stack.pop();
        if (!isMatchingPair(top, c)) {
          return false; // Неправильна пара дужок
        }
      }
    }

    return stack.isEmpty(); // Якщо стек порожній, дужки збалансовані
  }

  private static boolean isMatchingPair(char open, char close) {
    return (open == '(' && close == ')') ||
        (open == '[' && close == ']') ||
        (open == '{' && close == '}');
  }

  public static void main(String[] args) {
    String expression = "{[()]}";
    System.out.println("Is the expression balanced? " + isBalanced(expression));
  }
}
