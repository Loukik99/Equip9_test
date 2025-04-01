import java.util.*;

public class EquipmentRental {

    // Method to find the shortest path to the nearest provider with the target equipment
    public static List<Integer> findShortestPath(int n, int[][] edges, Map<Integer, List<String>> availability, int startProvider, String targetEquipment) {
        // Building the graph from edges
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int[] edge : edges) {
            graph.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(edge[1]);
            graph.computeIfAbsent(edge[1], k -> new ArrayList<>()).add(edge[0]);
        }

        // BFS for shortest path search
        Queue<List<Integer>> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        queue.add(Arrays.asList(startProvider));
        visited.add(startProvider);

        while (!queue.isEmpty()) {
            List<Integer> path = queue.poll();
            int current = path.get(path.size() - 1);

            // Check if the current provider has the required equipment
            if (availability.getOrDefault(current, new ArrayList<>()).contains(targetEquipment)) {
                return path;
            }

            // Explore neighbors
            for (int neighbor : graph.getOrDefault(current, new ArrayList<>())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    List<Integer> newPath = new ArrayList<>(path);
                    newPath.add(neighbor);
                    queue.add(newPath);
                }
            }
        }

        // If no provider with the required equipment is found
        return Collections.singletonList(-1);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input: Number of rental providers
        System.out.print("Enter number of rental providers: ");
        int n = scanner.nextInt();

        // Input: Number of connections
        System.out.print("Enter number of connections: ");
        int m = scanner.nextInt();
        int[][] edges = new int[m][2];

        System.out.println("Enter connections (providerA providerB):");
        for (int i = 0; i < m; i++) {
            edges[i][0] = scanner.nextInt();
            edges[i][1] = scanner.nextInt();
        }

        // Input: Availability of equipment
        Map<Integer, List<String>> availability = new HashMap<>();
        System.out.println("Enter availability of equipment for each provider:");
        for (int i = 0; i < n; i++) {
            System.out.print("Provider ID: ");
            int provider = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter equipment types (comma separated): ");
            String[] equipment = scanner.nextLine().split(",");
            availability.put(provider, Arrays.asList(equipment));
        }

        // Input: Start provider
        System.out.print("Enter start provider: ");
        int startProvider = scanner.nextInt();

        // Input: Target equipment type
        System.out.print("Enter target equipment type: ");
        String targetEquipment = scanner.next();

        // Find and print the shortest path
        List<Integer> result = findShortestPath(n, edges, availability, startProvider, targetEquipment);
        System.out.println("Shortest path: " + result);

        scanner.close();
    }
}
