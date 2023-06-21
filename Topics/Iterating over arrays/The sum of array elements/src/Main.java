import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        int size = Integer.parseInt(scanner.nextLine());
        String numbers = scanner.nextLine();
        String[] splitter = numbers.split(" ");

        int[] arrayOfNumbers = new int[size];

        for (int i = 0; i < arrayOfNumbers.length; i++) {
            arrayOfNumbers[i] = Integer.parseInt(splitter[i]);
        }


        int sumOfNumbers = 0;
        for (int x : arrayOfNumbers) {
            sumOfNumbers += x;
        }

        System.out.println(sumOfNumbers);




    }
}