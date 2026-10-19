public class Task03 {
    public static void main(String[] args) {
        int[][] parking = new int[2][2];
        int row = -1;
        int col = -1;
        for (int i = 0; i < parking.length; i++) {
            for (int j = 0; j < parking[i].length; j++) {
                if (parking[i][j] == 0) {
                    row = i;
                    col = j;
                    parking[i][j] = 1;
                    break;
                }
            }
            if (row != -1) {
                break;
            }
        }
        if (row != -1) {
            System.out.println("Slot allocated: " + row + ", " + col);
        } else {
            System.out.println("Parking full");
        }
    }
}
