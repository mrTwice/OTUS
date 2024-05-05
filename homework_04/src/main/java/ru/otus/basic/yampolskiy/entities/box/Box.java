package ru.otus.basic.yampolskiy.entities.box;

public class Box <T> implements Openable, Closeable{
    private double height;
    private double depth;
    private double width;
    private BoxColor color;
    private boolean isOpen;
    private T item;

    public Box(double height, double depth, double width, BoxColor color) {
        this.height = height;
        this.depth = depth;
        this.width = width;
        this.color = color;
        this.isOpen = true;
    }

    public Box(double height, double depth, double width, BoxColor color, T item) {
        this.height = height;
        this.depth = depth;
        this.width = width;
        this.color = color;
        this.item = item;
        this.isOpen = false;
    }
    

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }


    public BoxColor getBoxColor() {
        return color;
    }

    public void changeColor(BoxColor color) {
        this.color = color;
    }

    public boolean isOpen() {
        
        return isOpen;
    }

    public void printInfo() {
        System.out.println(this);
    }

    public void putItem(T item) {
        if (isOpen && this.item == null) {
            this.item = item;
            System.out.printf("%s помещен в коробку. \n", item.toString());
        } else {
            System.out.println("Нельзя поместить предмет, пока коробка закрыта или в ней лежит другой предмет.");
        }
    }

    public T takeItem() {
        if (isOpen && item != null) {
            T takedItem = item;
            System.out.printf("Вы достали предмет: %s\n", item.toString());
            item = null;
            return takedItem;
        } else {
            System.out.println("Нельзя извлечь предмет, пока коробка закрыта или пуста.");
            return null;
        }
    }

    @Override
    public String toString() {
        return "Высота: " + height + "\n" +
                "Глубина: " + depth + "\n" +
                "Ширина: " + width + "\n" +
                "Цвет: " + color.toString() + "\n";

    }

    @Override
    public void close(){
        if (isOpen) {
            System.out.println("Коробка закрыта.");
            isOpen = false;
        } else {
            System.out.println("Коробка уже закрыта.");
        }
    }

    @Override
    public void open() {
        if (!isOpen) {
            System.out.println("Коробка открыта.");
            if(item != null) {
                System.out.printf("Вы видите, что в ней лежит предмет: %s\n", item);
            }
            isOpen = true;
        } else {
            System.out.println("Коробка уже открыта.");
        }
    }
}
