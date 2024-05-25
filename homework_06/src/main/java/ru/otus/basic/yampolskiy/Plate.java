package ru.otus.basic.yampolskiy;

public class Plate {
    private final int maxAmountFood;
    private int currentAmountFood;

    public Plate(int maxAmountFood) {
        this.maxAmountFood = maxAmountFood;
        this.currentAmountFood = maxAmountFood;
    }

    public int getMaxAmountFood() {
        return maxAmountFood;
    }

    public boolean getFood(int amountFood) {
        if(amountFood > currentAmountFood) {
            System.out.println("В тарелке не хватает " + amountFood + " ед. еды");
            return false;
        }

        currentAmountFood -= amountFood;
        System.out.println("В тарелке осталось " + currentAmountFood + " ед. еды.");
        return true;
    }

    public void addFoodToPlate(int amountFood) {
        if( amountFood + currentAmountFood > maxAmountFood) {
            System.out.print("Невозможно поместить в тарелку" + amountFood + " ед. еды.");
            return;
        }
        currentAmountFood += amountFood;
    }

    public int getCurrentAmountFoodOnPlate() {
        return currentAmountFood;
    }

    public void reFillPlate() {
        this.currentAmountFood = maxAmountFood;
    }
}
