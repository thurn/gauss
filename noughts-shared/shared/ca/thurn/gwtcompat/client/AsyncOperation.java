package ca.thurn.gwtcompat.client;

/**
 * Runs operations asynchronously in a way that works both in GWT and in
 * regular java code. Relies on cooperative scheduling: tasks are expected
 * to periodically check if termination has been requested and yield
 * gracefully.
 */
public class AsyncOperation<T> {
  public static interface Task<T> {
    public T execute();
  }

  public static interface OnComplete<T> {
    public void onComplete(T result);
  }

  public AsyncOperation(final OnComplete<T> onComplete, final Task<T> task) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        onComplete.onComplete(task.execute());
      }
    }).start();
  }
}
