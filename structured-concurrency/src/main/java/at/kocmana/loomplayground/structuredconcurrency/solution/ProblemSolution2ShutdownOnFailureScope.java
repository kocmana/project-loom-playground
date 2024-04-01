import at.kocmana.loomplayground.structuredconcurrency.support.MockMethods;
import at.kocmana.loomplayground.structuredconcurrency.support.model.Response;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.function.Supplier;

void main() throws ExecutionException, InterruptedException {
  var response = handle();
  System.out.println(response);
}

Response handle() throws ExecutionException, InterruptedException {
  try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
    Supplier<String> user = scope.fork(MockMethods::failUserRequest);
    Supplier<Integer> order = scope.fork(MockMethods::fetchOrder);

    scope.join()            // Join both subtasks
        .throwIfFailed();  // ... and propagate errors

    // Here, both subtasks have succeeded, so compose their results
    return new Response(user.get(), order.get());
  }
}
