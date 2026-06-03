import java.util.Scanner;

public class VehicleMaintenanceScheduler {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of vehicles: ");
        int n = sc.nextInt();

        int[] duration = new int[n];
        int[] impact = new int[n];

        System.out.println("Enter duration of each vehicle:");

        for (int i = 0; i < n; i++) {
            duration[i] = sc.nextInt();
        }

        System.out.println("Enter impact of each vehicle:");

        for (int i = 0; i < n; i++) {
            impact[i] = sc.nextInt();
        }

        System.out.print("Enter available mechanic hours: ");
        int hours = sc.nextInt();

        int[][] dp = new int[n + 1][hours + 1];

        for (int i = 1; i <= n; i++) {

            for (int j = 1; j <= hours; j++) {

                if (duration[i - 1] <= j) {

                    dp[i][j] = Math.max(
                            impact[i - 1] + dp[i - 1][j - duration[i - 1]],
                            dp[i - 1][j]
                    );

                } else {

                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        System.out.println("Maximum Impact: " + dp[n][hours]);
    }
}