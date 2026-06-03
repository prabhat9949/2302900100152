package com.project.notificationapp.vehiclescheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class VehicleSchedulerService implements CommandLineRunner {

    @Value("${affordmed.token}")
    private String token;

    @Override
    public void run(String... args) {

        try {

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);

            HttpEntity<String> entity =
                    new HttpEntity<>(headers);

            ResponseEntity<Map> depotResponse =
                    restTemplate.exchange(
                            "http://4.224.186.213/evaluation-service/depots",
                            HttpMethod.GET,
                            entity,
                            Map.class
                    );

            ResponseEntity<Map> vehicleResponse =
                    restTemplate.exchange(
                            "http://4.224.186.213/evaluation-service/vehicles",
                            HttpMethod.GET,
                            entity,
                            Map.class
                    );

            List<Map<String, Object>> depots =
                    (List<Map<String, Object>>) depotResponse
                            .getBody()
                            .get("depots");

            List<Map<String, Object>> vehiclesData =
                    (List<Map<String, Object>>) vehicleResponse
                            .getBody()
                            .get("vehicles");

            int mechanicHours =
                    ((Number) depots.get(0)
                            .get("MechanicHours"))
                            .intValue();

            List<Vehicle> vehicles =
                    new ArrayList<>();

            for (Map<String, Object> item : vehiclesData) {

                vehicles.add(
                        new Vehicle(
                                (String) item.get("TaskID"),
                                ((Number) item.get("Duration")).intValue(),
                                ((Number) item.get("Impact")).intValue()
                        )
                );
            }

            solveKnapsack(vehicles, mechanicHours);

        } catch (Exception e) {

            System.out.println(
                    "Vehicle Scheduler Error: "
                            + e.getMessage()
            );
        }
    }

    private void solveKnapsack(
            List<Vehicle> vehicles,
            int capacity
    ) {

        int n = vehicles.size();

        int[][] dp =
                new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {

            for (int w = 0; w <= capacity; w++) {

                dp[i][w] = dp[i - 1][w];

                if (vehicles.get(i - 1)
                        .getDuration() <= w) {

                    dp[i][w] = Math.max(
                            dp[i][w],
                            dp[i - 1][
                                    w - vehicles.get(i - 1)
                                            .getDuration()
                                    ]
                                    + vehicles.get(i - 1)
                                    .getImpact()
                    );
                }
            }
        }

        System.out.println("\n====================");
        System.out.println("VEHICLE SCHEDULER");
        System.out.println("====================");

        System.out.println(
                "Maximum Operational Impact = "
                        + dp[n][capacity]
        );

        List<String> selectedTasks =
                new ArrayList<>();

        int w = capacity;

        for (int i = n; i > 0; i--) {

            if (dp[i][w]
                    != dp[i - 1][w]) {

                selectedTasks.add(
                        vehicles.get(i - 1)
                                .getTaskId()
                );

                w -= vehicles.get(i - 1)
                        .getDuration();
            }
        }

        Collections.reverse(selectedTasks);

        System.out.println(
                "\nSelected Task IDs:"
        );

        selectedTasks.forEach(
                System.out::println
        );
    }
}