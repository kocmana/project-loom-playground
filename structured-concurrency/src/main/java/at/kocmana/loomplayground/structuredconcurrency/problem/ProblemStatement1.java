import static at.kocmana.loomplayground.structuredconcurrency.support.MockMethods.fetchOrder;
import static at.kocmana.loomplayground.structuredconcurrency.support.MockMethods.findUser;

import at.kocmana.loomplayground.structuredconcurrency.support.model.Response;

void main() {
    var response = handle();
    System.out.println(response);
}

public Response handle () {
    String theUser = findUser();
    int theOrder = fetchOrder();
    return new Response(theUser, theOrder);
}

