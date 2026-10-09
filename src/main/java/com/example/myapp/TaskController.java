package com.example.myapp;


import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.web.bind.annotation.DeleteMapping;
//GetMappingを使うので必要//
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TaskController {
    private final JdbcTemplate jdbcTemplate;
    public TaskController(JdbcTemplate jdbcTemplate) {
        //JdbcTemplate = JavaからSQLを実行するための道具//
    this.jdbcTemplate = jdbcTemplate;
}


    //Taskを複数入れておく箱　「List<Task>」はTaskを複数入れられるリスト　<Task>は「このListにはTaskを入れます」と指定している。　tasksは箱の名前//
    //new ArrayList<>()空のリストを作ります//

    ///tasks にタスクが送られてきたら、この処理をする//
    @PostMapping("/tasks")
    //addTask というメソッドを作って、Task型のデータを task という名前で受け取ります。「Task task」はTaskクラスを使って、taskという名前のものを作って扱います。という意味//
    public Task addTask(@RequestBody Task task) {
    //ブラウザで入力して追加した内容をパワーシェルに表示させる//
        System.out.println(task.getName());

        //今tasksに入っている数を使って番号を設定する。1は0から始まるため。追加されるたびにタスクに番号が１プラスされた番号が付く//
            
        Integer nextNumber = jdbcTemplate.queryForObject(
            "SELECT COALESCE(MAX(number), 0) + 1 FROM tasks",
            Integer.class
        );

        task.setNumber(nextNumber);
        jdbcTemplate.update(
        "INSERT INTO tasks (number, name, completed) VALUES (?, ?, ?)",
            task.getNumber(),
            task.getName(),
            task.isCompleted()
        );
        return task;
    }

    //画面に表示するために、Javascriptから /tasks にGETでアクセスされたら、この処理を実行する//
    @GetMapping("/tasks")
       public List<Task> getTasks() {

    String sql = "SELECT number, name, completed FROM tasks";

    return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Task task = new Task();
            task.setNumber(rs.getInt("number"));
            task.setName(rs.getString("name"));
            task.setCompleted(rs.getBoolean("completed"));
            return task;
        });
    }

    //完了処理を受け付けるURLを決める//
    @PostMapping("/tasks/complete")
    public void completeTask(@RequestBody Task task) {
        jdbcTemplate.update(
            //DBの完了状態を変更する。　　指定した番号のタスクだけを変更する//
            "UPDATE tasks SET completed = ? WHERE number = ?",
            //完了状態にする//
            true,
            task.getNumber()
        );
    }

    @DeleteMapping("/tasks")
       public void deleteTask(@RequestBody Task task) {
        jdbcTemplate.update(
            "DELETE FROM tasks WHERE number = ?",
            task.getNumber()
        );
    }
}

   
