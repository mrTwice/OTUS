package ru.otus.basic.yampolskiy.entities.user;

import java.time.LocalDate;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserFactory {
    private static final int ADULT = 18;
    private final Random rnd = new Random();

    private int getCurrentYear() {
        return LocalDate.now().getYear();
    }
    public User getUser() {
        String firstName = getRandomName();
        String lastName = getRandomSurname();
        String patronymic = getRandomPatronymic();
        int yearOfBirth = generateYearOfBirth();
        String email = generateEmail(firstName, lastName);
        return new User(firstName, lastName, patronymic, yearOfBirth, email);
    }

    private String getRandomName() {
        String[] names = {
                "Александр", "Михаил", "Иван", "Артем", "Дмитрий",
                "Егор", "Никита", "Сергей", "Павел", "Владимир",
                "Кирилл", "Андрей", "Алексей", "Илья", "Максим",
                "Даниил", "Тимофей", "Арсений", "Станислав", "Григорий",
                "Матвей", "Роман", "Владислав", "Евгений", "Василий",
                "Федор", "Денис", "Руслан", "Борис", "Игорь",
                "Олег", "Анатолий", "Николай", "Валерий", "Валентин",
                "Семен", "Леонид", "Данила", "Фёдор", "Марк",
                "Игнат", "Ефим", "Герман", "Виталий", "Евдоким",
                "Кузьма", "Мирослав", "Влад", "Родион", "Тарас",
                "Лука", "Георгий", "Константин", "Аркадий", "Давид",
                "Савва"
        };
        return names[rnd.nextInt(0, names.length)];
    }

    private String getRandomSurname() {
        String[] surnames = {
                "Иванов", "Петров", "Сидоров", "Смирнов", "Кузнецов",
                "Васильев", "Попов", "Соколов", "Михайлов", "Новиков",
                "Федоров", "Морозов", "Волков", "Алексеев", "Лебедев",
                "Семенов", "Егоров", "Павлов", "Козлов", "Степанов",
                "Николаев", "Орлов", "Андреев", "Макаров", "Никитин",
                "Захаров", "Зайцев", "Соловьев", "Борисов", "Яковлев",
                "Григорьев", "Романов", "Воробьев", "Сергеев", "Кузьмин",
                "Фролов", "Александров", "Дмитриев", "Королев", "Гусев",
                "Киселев", "Ильин", "Максимов", "Поляков", "Сорокин",
                "Виноградов", "Ковалев", "Белов", "Тарасов", "Медведев",
                "Антонов", "Тимофеев", "Филиппов", "Гаврилов", "Ефимов",
                "Денисов", "Суворов", "Тихонов", "Комаров", "Лапин",
                "Горбунов", "Кудрявцев", "Быков", "Гришин", "Трофимов",
                "Горбачев", "Кондратьев", "Головин", "Капустин", "Фомин",
                "Давыдов", "Белоусов", "Мельников", "Кольцов", "Пестов",
                "Тимошенко", "Афанасьев", "Фокин", "Богданов", "Селезнев",
                "Кочергин", "Гордеев", "Емельянов", "Маслов", "Родионов",
                "Калинин", "Лазарев", "Корнеев", "Жуков", "Блинов",
                "Исаев", "Терентьев", "Марков", "Устинов", "Федосеев",
                "Игнатьев", "Бычков", "Ларионов", "Никифоров", "Савельев"
        };
        return surnames[rnd.nextInt(0, surnames.length)];
    }

    private String getRandomPatronymic() {
        String[] patronymics = {
                "Александрович", "Михайлович", "Иванович", "Артемович", "Дмитриевич",
                "Егорович", "Никитич", "Сергеевич", "Павлович", "Владимирович",
                "Кириллович", "Андреевич", "Алексеевич", "Ильич", "Максимович",
                "Даниилович", "Тимофеевич", "Арсеньевич", "Станиславович", "Григорьевич",
                "Матвеевич", "Романович", "Владиславович", "Евгеньевич", "Васильевич",
                "Федорович", "Денисович", "Русланович", "Борисович", "Игоревич",
                "Олегович", "Анатольевич", "Николаевич", "Валерьевич", "Валентинович",
                "Семенович", "Леонидович", "Данилович", "Фёдорович", "Маркович",
                "Игнатьевич", "Ефимович", "Германович", "Витальевич", "Евдокимович",
                "Кузьмич", "Мирославович", "Владович", "Родионович", "Тарасович",
                "Лукич", "Георгиевич", "Константинович", "Аркадьевич", "Давидович",
                "Саввич"
        };
        return patronymics[rnd.nextInt(0, patronymics.length)];
    }

    private String convertToLatin(String cyrillicText) {
        char[] cyrillicChars = {'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', 'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я'};
        String[] latinChars = {"a", "b", "v", "g", "d", "e", "yo", "zh", "z", "i", "y", "k", "l", "m", "n", "o", "p", "r", "s", "t", "u", "f", "kh", "ts", "ch", "sh", "shch", "'", "y", "'", "e", "yu", "ya", "A", "B", "V", "G", "D", "E", "Yo", "Zh", "Z", "I", "Y", "K", "L", "M", "N", "O", "P", "R", "S", "T", "U", "F", "Kh", "Ts", "Ch", "Sh", "Shch", "'", "Y", "'", "E", "Yu", "Ya"};

        StringBuilder latinTextBuilder = new StringBuilder();
        for (char c : cyrillicText.toCharArray()) {
            boolean isFound = false;
            for (int i = 0; i < cyrillicChars.length; i++) {
                if (c == cyrillicChars[i]) {
                    latinTextBuilder.append(latinChars[i]);
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                latinTextBuilder.append(c);
            }
        }
        return latinTextBuilder.toString();
    }

    private String generateEmail(String firstName, String lastName) {

        return removeInvalidCharacters(convertToLatin(firstName) + "_" + convertToLatin(lastName) + "@mail.ru").toLowerCase();
    }

    private int generateYearOfBirth(){
        return rnd.nextInt(1940, LocalDate.now().getYear()-ADULT);
    }

    public  String removeInvalidCharacters(String input) {
        Pattern pattern = Pattern.compile("[^а-яА-Яa-zA-Z0-9\\s_@.]");
        Matcher matcher = pattern.matcher(input);
        return matcher.replaceAll("");
    }
}
