package ru.otus.basic.yampolskiy;

import java.util.ArrayList;
import java.util.List;

class TreePrinter {

    public static void print(Tree tree) {
        Tree.Node root = tree.getRoot(); // Получаем корень дерева
        Tree.Node NIL = tree.getNilNode(); // Получаем NIL-узел

        List<List<String>> lines = new ArrayList<>(); // Список строк для хранения уровней дерева
        List<Tree.Node> level = new ArrayList<>(); // Список текущего уровня узлов
        List<Tree.Node> next = new ArrayList<>(); // Список следующего уровня узлов

        level.add(root); // Начинаем с корня
        int nn = 1; // Количество узлов на текущем уровне, начальное значение 1 (корень)
        int widest = 0; // Ширина самой широкой строки

        // Пока nn не равно 0, то есть есть узлы для обработки
        while (nn != 0) {
            List<String> line = new ArrayList<>(); // Текущая строка

            nn = 0; // Сбрасываем счетчик узлов на текущем уровне

            // Проходим по всем узлам текущего уровня
            for (Tree.Node n : level) {
                if (n == NIL) { // Если узел NIL, добавляем пустые строки для следующего уровня
                    line.add(null);
                    next.add(NIL);
                    next.add(NIL);
                } else {
                    // Форматируем значение узла с его цветом
                    String aa = n.getValue() + "(" + n.getColor() + ")";
                    line.add(aa);
                    if (aa.length() > widest) widest = aa.length(); // Обновляем ширину самой широкой строки

                    next.add(n.getLeft() != null ? n.getLeft() : NIL); // Добавляем левого потомка
                    next.add(n.getRight() != null ? n.getRight() : NIL); // Добавляем правого потомка

                    if (n.getLeft() != NIL) nn++; // Увеличиваем счетчик, если есть левый потомок
                    if (n.getRight() != NIL) nn++; // Увеличиваем счетчик, если есть правый потомок
                }
            }

            if (widest % 2 == 1) widest++; // Если ширина нечетная, делаем ее четной

            lines.add(line); // Добавляем текущую строку в список строк

            List<Tree.Node> tmp = level;
            level = next; // Переходим на следующий уровень
            next = tmp; // Очищаем список следующего уровня
            next.clear();
        }

        // Уменьшаем расстояние между узлами на 20%
        widest = (int) (widest * 0.6);

        int perpiece = lines.get(lines.size() - 1).size() * (widest + 4); // Вычисляем количество символов на узел на последнем уровне
        for (int i = 0; i < lines.size(); i++) { // Проходим по всем строкам
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1; // Половина ширины узла

            if (i > 0) { // Не для первого уровня
                for (int j = 0; j < line.size(); j++) {
                    char c = ' ';
                    if (j % 2 == 1) { // Если узел является правым потомком
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (line.get(j) != null) c = '└';
                        }
                    }
                    System.out.print(c);

                    if (line.get(j) == null) { // Если узел пустой
                        for (int k = 0; k < perpiece - 1; k++) {
                            System.out.print(" ");
                        }
                    } else {
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? " " : "─");
                        }
                        System.out.print(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                System.out.println();
            }

            // Печатаем значение узлов
            for (String f : line) {
                if (f == null) f = ""; // Если значение null, заменяем на пустую строку
                float a = f.length(); // Длина строки узла
                float b = (float) Math.floor(perpiece / 2f - a / 2f); // Пробелы слева
                float c = (float) Math.floor(perpiece / 2f - a / 2f); // Пробелы справа

                if (a % 2 == 1) { // Если длина строки нечетная
                    b += 0.5;
                    c -= 0.5;
                }

                // Печатаем пробелы слева
                for (int j = 0; j < b; j++) {
                    System.out.print(" ");
                }
                System.out.print(f); // Печатаем значение узла
                // Печатаем пробелы справа
                for (int j = 0; j < c; j++) {
                    System.out.print(" ");
                }
            }
            System.out.println();

            perpiece /= 2; // Уменьшаем количество символов на узел для следующего уровня
        }
    }
}
