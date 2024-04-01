package at.kocmana.loomplayground.structuredconcurrency.solution;

import at.kocmana.loomplayground.structuredconcurrency.support.MockMethods;
import at.kocmana.loomplayground.structuredconcurrency.support.model.Response;
import java.util.concurrent.StructuredTaskScope;
import java.util.function.Supplier;

public class ProblemSolution1StructuredTaskScope {

  void main() throws Throwable {
    var response = handle();
    System.out.println(response);
  }

  Response handle() throws Throwable {
    try (var scope = new MyVeryFirstTaskScope<>()) {
      Supplier<String> user = scope.fork(MockMethods::failUserRequest);
      Supplier<Integer> order = scope.fork(MockMethods::fetchOrder);

      scope.join()
          .andGetException();

      // Here, both subtasks have succeeded, so compose their results
      return new Response(user.get(), order.get());
    }

  }
}

class MyVeryFirstTaskScope<T> extends StructuredTaskScope<T> {

  private Throwable exception;

  @Override
  protected void handleComplete(Subtask subtask) {
    if (subtask.state() == Subtask.State.FAILED) {
      exception = subtask.exception();
      this.shutdown();
    }
  }

  @Override
  public MyVeryFirstTaskScope<T> join() throws InterruptedException {
    super.join();
    return this;
  }

  public Throwable andGetException() throws Throwable {
    throw exception;
  }
}