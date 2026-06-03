import java.util.*;

public class VehicleMaintenanceScheduler {

```
static class Vehicle {
    String taskId;
    int duration;
    int impact;

    Vehicle(String taskId,int duration,int impact){
        this.taskId = taskId;
        this.duration = duration;
        this.impact = impact;
    }
}

public static void main(String[] args) {

    List<Vehicle> vehicles = new ArrayList<>();

    vehicles.add(new Vehicle("ddcb068f",3,3));
    vehicles.add(new Vehicle("bfea3f66",4,9));
    vehicles.add(new Vehicle("4b9670a1",3,10));
    vehicles.add(new Vehicle("2dd97aea",5,9));

    int mechanicHours = 60;

    int n = vehicles.size();

    int[][] dp =
            new int[n + 1][mechanicHours + 1];

    for(int i=1;i<=n;i++){

        for(int w=0;w<=mechanicHours;w++){

            dp[i][w]=dp[i-1][w];

            if(vehicles.get(i-1).duration<=w){

                dp[i][w]=Math.max(
                        dp[i][w],
                        dp[i-1][w-
                                vehicles.get(i-1).duration]
                                + vehicles.get(i-1).impact
                );
            }
        }
    }

    System.out.println(
            "Maximum Operational Impact = "
                    + dp[n][mechanicHours]
    );

    List<String> selectedTasks =
            new ArrayList<>();

    int w = mechanicHours;

    for(int i=n;i>0;i--){

        if(dp[i][w] != dp[i-1][w]){

            selectedTasks.add(
                    vehicles.get(i-1).taskId
            );

            w -= vehicles.get(i-1).duration;
        }
    }

    Collections.reverse(selectedTasks);

    System.out.println(
            "\nSelected Task IDs:"
    );

    selectedTasks.forEach(System.out::println);
}
```

}
