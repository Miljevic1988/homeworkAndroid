package zeljkomiljevic.bitcamp.ba.homework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static UserList users = new UserList();
    private EditText nameText;
    private EditText surnameText;
    private Button addButton;
    private final ThreadLocal<RecyclerView> recyclerView = new ThreadLocal<>();
    private UserAdapter userAdapter;
    private RadioButton sortByName;
    private RadioButton sortBySurname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameText = (EditText) findViewById(R.id.name);
        surnameText = (EditText) findViewById(R.id.surname);
        addButton = (Button) findViewById(R.id.addButton);
        sortByName = (RadioButton) findViewById(R.id.sort_by_name);
        sortBySurname = (RadioButton) findViewById(R.id.sort_by_surname);
        recyclerView.set((RecyclerView) findViewById(R.id.recycler));
        userAdapter = new UserAdapter(users);
        recyclerView.get().setAdapter(userAdapter);
        recyclerView.get().setLayoutManager(new LinearLayoutManager(this));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable name = nameText.getText();
                Editable surname = surnameText.getText();
                users.addUser(name.toString(), surname.toString());
                userAdapter.notifyDataSetChanged();
                nameText.setText("");
                surnameText.setText("");
            }
        });

        sortByName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    sortBySurname.setChecked(false);
                    Collections.sort(UserList.getUsers(), new Comparator<User>() {
                        @Override
                        public int compare(User u1, User u2) {
                            return u1.getName().compareTo(u2.getName());
                        }
                    });
                    userAdapter.notifyDataSetChanged();
                    updateUI();
                }
            }
        });
        sortBySurname.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    sortByName.setChecked(false);
                    Collections.sort(UserList.getUsers(), new Comparator<User>() {
                        @Override
                        public int compare(User u1, User u2) {
                            return u1.getSurname().compareTo(u2.getSurname());
                        }
                    });
                    userAdapter.notifyDataSetChanged();
                    updateUI();
                }
            }
        });
    }

    private class UserView extends RecyclerView.ViewHolder {

        private TextView nameText;
        private TextView surnameText;
        private TextView dateText;
        private Button deleteButton;
        private Button editButton;

        public UserView(View itemView) {
            super(itemView);

            nameText = (TextView) itemView.findViewById(R.id.user_name);
            surnameText = (TextView) itemView.findViewById(R.id.user_surname);
            dateText = (TextView) itemView.findViewById(R.id.user_date);
            deleteButton = (Button) itemView.findViewById(R.id.delete);
            editButton = (Button) itemView.findViewById(R.id.edit_button);
        }
    }

    private class UserAdapter extends RecyclerView.Adapter<UserView> {

        private UserList users;

        public UserAdapter(UserList users) {
            this.users = users;
        }

        @Override
        public UserView onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);

            View view = layoutInflater.inflate(R.layout.user_layout, parent, false);

            return new UserView(view);
        }

        @Override
        public void onBindViewHolder(UserView holder, int position) {
            final User user = users.getUser(position);

            holder.nameText.setText(user.getName());
            holder.surnameText.setText(user.getSurname());
            holder.dateText.setText(user.getDate().toString());
            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserList.deleteUser(user.getUUID());
                    updateUI();
                }
            });

            holder.editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, EditUserActivity.class);
                    i.putExtra("abcd", user.getUUID());
                    startActivity(i);
                }
            });
        }

        @Override
        public int getItemCount() {
            return users.getSize();
        }
    }

    public void updateUI() {
        List<User> users = UserList.getUsers();
        userAdapter.notifyDataSetChanged();
        recyclerView.get().setAdapter(userAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}