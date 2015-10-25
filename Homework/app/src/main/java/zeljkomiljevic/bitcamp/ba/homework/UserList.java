package zeljkomiljevic.bitcamp.ba.homework;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserList {

    private static List<User> Users;

    public UserList() {
        Users = new ArrayList<>();
    }

    public void addUser(String name, String surname) {
        Users.add(new User(name.toString(), surname.toString()));
    }

    public static List<User> getUsers() {
        return Users;
    }

    public User getUser(Integer index) {
        if(Users.size() > index) {
            return Users.get(index);
        } else {
            return null;
        }
    }

    public Integer getSize() {
        return Users.size();
    }

    public static User getUserByUUID(UUID id) {
        for(int i = 0; i < Users.size(); i++) {
            if(Users.get(i).getUUID().equals(id)) {
                return Users.get(i);
            }
        }
        return null;
    }

    public static void updateUser(UUID id, String name, String surname) {
        User u = getUserByUUID(id);
        u.setName(name);
        u.setSurname(surname);
        return;
    }

    public static void deleteUser(UUID id) {
        for(int i = 0; i < Users.size(); i++) {
            if(Users.get(i).getUUID().equals(id)) {
                Users.remove(i);
                return;
            }
        }
    }
}
