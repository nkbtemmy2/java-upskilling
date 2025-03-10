class GenericStack<T> {
    private java.util.ArrayList<T> stack;
    private int maxSize;

    public GenericStack() {
        this(Integer.MAX_VALUE); // Unlimited stack by default
    }

    public GenericStack(int maxSize) {
        this.stack = new java.util.ArrayList<>();
        this.maxSize = maxSize;
    }

    public void push(T item) {
        if (stack.size() >= maxSize) {
            throw new IllegalStateException("Stack is full");
        }
        stack.add(item);
    }

    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return stack.remove(stack.size() - 1);
    }

    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return stack.get(stack.size() - 1);
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.size();
    }
}