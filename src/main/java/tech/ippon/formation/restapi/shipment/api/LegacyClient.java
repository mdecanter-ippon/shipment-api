package tech.ippon.formation.restapi.shipment.api;

import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class LegacyClient {

    private final HttpClient httpClient;
    // Defined in docker-compose ("http://external-legacy-api:8080")
    private final String legacyUrl = "http://localhost:8081/legacy/shipments";

    public LegacyClient() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(2)) // Fail fast if not reachable
                .build();
    }

    public String notifyShipment(String destination, Double weight) {
        try {
            // TASK 1: Build the JSON Payload (Manually for simplicity or using Jackson)
            String jsonBody = """
                {
                    "destination": "%s",
                    "weight": %s
                }
                """.formatted(destination, weight);

            // TASK 2: Create the Request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(legacyUrl))
                    .timeout(Duration.ofSeconds(3)) // Global Request Timeout
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            // Send and wait (Synchronous for this lab)
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // TASK 4: Error Handling
            if (response.statusCode() >= 400) {
                throw new RuntimeException("Legacy System Error: " + response.statusCode());
            }

            return response.body();

        } catch (Exception e) {
            // In a real world, we would queue this for retry (Kafka/RabbitMQ)
            // For now, we just log and return a fallback message.
            System.err.println("Legacy System Unreachable: " + e.getMessage());
            return "Legacy notification pending (System Unavailable)";
        }
    }
}