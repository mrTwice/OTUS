package ru.otus.basic.yampolskiy;

import ru.otus.basic.yampolskiy.entities.box.Box;
import ru.otus.basic.yampolskiy.entities.box.BoxColor;
import ru.otus.basic.yampolskiy.entities.user.User;
import ru.otus.basic.yampolskiy.entities.user.UserFactory;

import java.time.LocalDate;

public class Main {
    private static final int CURRENT_YEAR = LocalDate.now().getYear();
    private static final int AGE_BORDER = 40;
    private static final UserFactory userFactory = new UserFactory();

    public static void main(String[] args) {
        createUsers();
        createBox();
    }

    private static void createBox() {
        Box<Object> box = new Box<>(2.0, 2.0, 2.0, BoxColor.GREEN, "item");
        box.open();
        box.putItem("whiskey");
        Object objectFromBox = box.takeItem();
        System.out.printf("Объект из коробки: %s\nКласс объекта из коробки: %s\n",objectFromBox, objectFromBox.getClass().getSimpleName());
        box.putItem("whiskey");
        box.close();
    }

    private static void createUsers() {
        User[] users = new User[10];
        for (int i = 0; i < users.length; i++) {
            users[i] = userFactory.getUser();
        }

        for (int i = 0; i < users.length; i++) {
            if (CURRENT_YEAR - users[i].getYearOfBirth() > AGE_BORDER) {
                System.out.println(users[i].toString());
            }
        }
    }


}