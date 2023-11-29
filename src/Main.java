public class Main {
    public static void main(String[] args) {
        BlockingStack<Integer> blockingStack = new BlockingStack<>(10);
        Thread producerThread = new Thread(() -> {
            for (int i = 0; i <= 10; i++) {
                try {
                    blockingStack.push(i);
                    System.out.println(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        Thread consumerThread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Thread 1 " + blockingStack.pop());
            }
        });
        Thread consumerThread2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Thread 2 " + blockingStack.pop());
            }
        });
        producerThread.start();
        consumerThread.start();
        consumerThread2.start();


    }
}