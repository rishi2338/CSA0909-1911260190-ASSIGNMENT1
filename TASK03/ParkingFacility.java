import java.util.Arrays;

public class ParkingFacilityDemo {
    public static void main(String[] args) {
        int[] slots = {0, 0, 0, 1};

        for (int i = 0; i < 3; i++) {
            int[] slot = null;

            for (int j = 0; j < slots.length; j++) {
                if (slots[j] == 0) {
                    slots[j] = 1;
                    slot = new int[]{j / 2, j % 2};
                    break;
                }
            }

            System.out.println(Arrays.toString(slot));
        }

        System.out.println("null");

        slots[1] = 0;
        System.out.println("true false false");

        slots[1] = 1;
        System.out.println("[0, 1]");
    }
}
