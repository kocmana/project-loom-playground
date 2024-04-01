import at.kocmana.loomplayground.structuredconcurrency.support.MockMethods;
import at.kocmana.loomplayground.structuredconcurrency.support.model.Response;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

void main() throws ExecutionException, InterruptedException {
  var response = handle();
  System.out.println(response);
}

Response handle() throws ExecutionException, InterruptedException {
  Future<String> user = EXECUTOR_SERVICE.submit(MockMethods::findUser);
  Future<Integer> order = EXECUTOR_SERVICE.submit(MockMethods::fetchOrder);
  String theUser = user.get();   // Join findUser
  int theOrder = order.get();  // Join fetchOrder
  return new Response(theUser, theOrder);
}

