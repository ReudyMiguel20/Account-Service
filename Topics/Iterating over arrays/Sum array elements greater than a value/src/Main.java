import java.util.Arrays;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        String sizeInput = scanner.nextLine();
        int sizeArray = Integer.parseInt(sizeInput);
        int[] arr = new int[sizeArray];

        String input = "";
        input = scanner.nextLine();
        String[] numbers = input.split(" ");

        arr = Arrays.stream(numbers)
                .mapToInt(Integer::parseInt)
                .toArray();

        int numberN = scanner.nextInt();
        int sumNumbers = 0;

        for (int x : arr){
            if (x > numberN){
                sumNumbers += x;
            }
        }

        System.out.println(sumNumbers);
    }
}