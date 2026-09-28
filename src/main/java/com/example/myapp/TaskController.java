package com.example.myapp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
//GetMappingを使うので必要//
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class TaskController {
    //Taskを複数入れておく箱　「List<Task>」はTaskを複数入れられるリスト　<Task>は「このListにはTaskを入れます」と指定している。　tasksは箱の名前//
    //new ArrayList<>()空のリストを作ります//
     private List<Task> tasks = new ArrayList<>();

    ///tasks にタスクが送られてきたら、この処理をする//
    @PostMapping("/tasks")
    //addTask というメソッドを作って、Task型のデータを task という名前で受け取ります。「Task task」はTaskクラスを使って、taskという名前のものを作って扱います。という意味//
    public Task addTask(@RequestBody Task task) {
    //ブラウザで入力して追加した内容をパワーシェルに表示させる//
        System.out.println(task.getName());

        tasks.add(task);

        return task;
    }

    //画面に表示するために、ブラウザなどから /tasks にGETでアクセスされたら、この処理を実行する//
    @GetMapping("/tasks")
        public List<Task> getTasks() {
        return tasks;
    }

}