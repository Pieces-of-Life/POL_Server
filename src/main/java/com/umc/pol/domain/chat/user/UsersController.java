package com.umc.pol.domain.chat.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

// https://firework-ham.tistory.com/111
// https://www.youtube.com/watch?v=clH7SxG-Vdc&list=PLS1QulWo1RIYMQcf1y2bqQZbpXLzpKDUL&index=1

@RestController
@RequiredArgsConstructor
@RequestMapping("/exam/svc/v1")
public class UsersController {

    private final UserServiceImpl userService;

    @GetMapping("/users")
    public ResponseEntity<Object> getUsers() throws ExecutionException, InterruptedException {
        List<User> list = userService.getUsers();
        return ResponseEntity.ok().body(list);

    }

    ////

    @PostMapping("/create")
    public String createUser(@RequestBody User user) throws InterruptedException, ExecutionException {
        return userService.createUser(user);
    }

    @GetMapping("/get")
    public User getUser(@RequestParam String id) throws InterruptedException, ExecutionException {
        return userService.getUser(id);
    }

    @PutMapping("/update")
    public String updateUser(@RequestBody User user) throws InterruptedException, ExecutionException {
        return userService.updateUser(user);
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam String id) throws InterruptedException, ExecutionException {
        return userService.deleteUser(id);
    }
}