import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingStack<T> {
    private Stack<T> stack;
    private Lock locker;
    private Condition isFull;
    private Condition isEmpty;
    private static int CAPACITY;

    public BlockingStack(int capacity) {
        this.stack = new Stack<>();
        this.locker = new ReentrantLock();
        this.isFull = locker.newCondition();
        this.isEmpty = locker.newCondition();
        this.CAPACITY = capacity;
    }

    public void push(T t) throws InterruptedException {
        locker.lock();
        while (stack.size() == CAPACITY) {
            isFull.await();
        }
        stack.push(t);
        isEmpty.signalAll();
        Thread.currentThread().interrupt();
        locker.unlock();
    }

    public T pop() {
        try {
            locker.lock();
            while (stack.isEmpty()) {
                isEmpty.await();
            }
            T item = stack.pop();
            isFull.signalAll();
            return item;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } finally {
            locker.unlock();
        }
    }

}
