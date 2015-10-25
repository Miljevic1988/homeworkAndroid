package zeljkomiljevic.bitcamp.ba.homework;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.UUID;


public class EditUserActivity extends Activity {

    private User user;
    private UUID id;
    private EditText userName;
    private EditText userSurname;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_layout);

        userName = (EditText) findViewById(R.id.edit_name);
        userSurname = (EditText) findViewById(R.id.edit_surname);
        saveButton = (Button) findViewById(R.id.save_button);
        id = (UUID) getIntent().getExtras().getSerializable("abcd");
        user = UserList.getUserByUUID(id);
        userName.setText(user.getName().toString());
        userSurname.setText(user.getSurname().toString());

        UserList.updateUser(user.getUUID(), user.getName().toString(), user.getSurname().toString());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setName(userName.getText().toString());
                user.setSurname(userSurname.getText().toString());

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}
