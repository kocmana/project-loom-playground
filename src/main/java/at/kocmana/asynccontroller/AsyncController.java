package at.kocmana.asynccontroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/tasks")
@Slf4j
public class AsyncController {

    @GetMapping("/async")
    CompletableFuture<String> getRequestAsync() {
        return CompletableFuture.supplyAsync(this::performSomeTask);
    }

    @GetMapping("/blocking")
    String getRequestBlocking() {
        return performSomeTask();
    }

    private String performSomeTask() {
        var rand = new Random();
        var duration = rand.nextInt(5000);
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        var currentTask = "Thread: %s, Duration: %s".formatted(
                Thread.currentThread().getName(),
                duration);
        log.info(currentTask);
        return currentTask;
    }
}
