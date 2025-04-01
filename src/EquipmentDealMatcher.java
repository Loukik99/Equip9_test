import java.util.*;

public class EquipmentDealMatcher {
    public static List<Integer> matchRequests(List<Request> requests, List<Seller> sellers) {
        Map<String, PriorityQueue<Integer>> equipmentPrices = new HashMap<>();

        // Populate the priority queue (min-heap) for each equipment type
        for (Seller seller : sellers) {
            equipmentPrices.putIfAbsent(seller.equipmentType, new PriorityQueue<>());
            equipmentPrices.get(seller.equipmentType).offer(seller.price);
        }

        List<Integer> result = new ArrayList<>();
        for (Request request : requests) {
            PriorityQueue<Integer> prices = equipmentPrices.get(request.equipmentType);

            // Find the lowest price within budget
            if (prices != null && !prices.isEmpty() && prices.peek() <= request.maxPrice) {
                result.add(prices.poll()); // Take the cheapest option
            } else {
                result.add(null); // No valid seller
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of buyer requests: ");
        int requestCount = scanner.nextInt();
        List<Request> requests = new ArrayList<>();

        System.out.println("Enter buyer requests (equipment_type max_price):");
        for (int i = 0; i < requestCount; i++) {
            String type = scanner.next();
            int maxPrice = scanner.nextInt();
            requests.add(new Request(type, maxPrice));
        }

        System.out.print("Enter number of sellers: ");
        int sellerCount = scanner.nextInt();
        List<Seller> sellers = new ArrayList<>();

        System.out.println("Enter seller inventory (equipment_type price):");
        for (int i = 0; i < sellerCount; i++) {
            String type = scanner.next();
            int price = scanner.nextInt();
            sellers.add(new Seller(type, price));
        }

        List<Integer> matches = matchRequests(requests, sellers);
        System.out.println("Matched prices: " + matches);

        scanner.close();
    }
}

class Request {
    String equipmentType;
    int maxPrice;

    public Request(String equipmentType, int maxPrice) {
        this.equipmentType = equipmentType;
        this.maxPrice = maxPrice;
    }
}

class Seller {
    String equipmentType;
    int price;

    public Seller(String equipmentType, int price) {
        this.equipmentType = equipmentType;
        this.price = price;
    }
}
