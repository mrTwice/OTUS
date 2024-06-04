package ru.otus.basic.yampolskiy;

import ru.otus.basic.yampolskiy.entities.Contact;
import ru.otus.basic.yampolskiy.entities.PhoneNumber;

import java.util.List;

public class Presenter {
    private final PhoneBook phoneBook;
    private final View view;

    public Presenter(PhoneBook phoneBook, View view) {
        this.phoneBook = phoneBook;
        this.view = view;
    }

    public void create() {
        boolean flag = true;
        while (flag) {
            view.printMenu();
            switch (view.getOperation()) {
                case 1 -> view.printContacts(phoneBook.getContacts());
                case 2 -> {
                    String firstname = view.getInput("Введите имя: ");
                    String lastname = view.getInput("Введите фамилию: ");
                    String phoneNumber = view.getInput("Введите номер телефона: ");
                    String titlePhoneNumber = view.getInput("Укажите тип телефона: ");
                    PhoneNumber newPhonenumber = new PhoneNumber(titlePhoneNumber, phoneNumber);
                    Contact contact = new Contact(firstname, lastname);
                    contact.addNewPhoneNumber(newPhonenumber);
                    view.printInfo("Контакт " + phoneBook.addNew(contact).getFullname() + " успешно добавлен.");
                }
                case 3 -> {
                    List<Contact> contacts = phoneBook.getContacts();
                    view.printContacts(contacts);
                    int choice = view.getChoice("Укажите номер контакта для удаления: ") -1 ;
                    Contact contactForDelete = contacts.get(choice);
                    if(phoneBook.remove(contactForDelete))
                        view.printInfo("Контакт успешно удален.");
                }
                case 4 -> {
                    String query = view.getInput("Введите слова запрос для поиска контактов: ");
                    List<Contact> contacts = phoneBook.findContacts(query);
                    view.printInfo("Результаты поиска: ");
                    view.printContacts(contacts);
                }
                case 5 -> {
                    String phoneNumber = view.getInput("Введите номер телефона для поиска: ");
                    Contact contact = phoneBook.findContactByPhoneNumber( phoneNumber);
                    if(contact != null)
                        view.printInfo(contact.toString());
                }
                case 6 -> flag = false;
                default -> System.out.println("Такого пункта нет.");
            }
        }
    }

}
