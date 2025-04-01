import java.util.*;

public class MaintenanceLogAnalysis {
    private static int[] fenwickTree;
    private static Map<String, Integer> dateIndexMap;

    // Updates the Fenwick Tree (BIT) at index i with value delta
    private static void update(int i, int delta, int n) {
        while (i <= n) {
            fenwickTree[i] += delta;
            i += i & -i;
        }
    }

    // Computes the prefix sum up to index i
    private static int query(int i) {
        int sum = 0;
        while (i > 0) {
            sum += fenwickTree[i];
            i -= i & -i;
        }
        return sum;
    }

    public static List<Integer> processQueries(List<MaintenanceRecord> logs, List<Query> queries) {
        // Create a sorted list of unique dates for indexing
        TreeSet<String> uniqueDates = new TreeSet<>();
        for (MaintenanceRecord log : logs) uniqueDates.add(log.date);
        for (Query query : queries) {
            uniqueDates.add(query.startDate);
            uniqueDates.add(query.endDate);
        }

        // Map dates to indices
        dateIndexMap = new HashMap<>();
        int index = 1;
        for (String date : uniqueDates) dateIndexMap.put(date, index++);

        // Initialize Fenwick Tree
        int n = uniqueDates.size();
        fenwickTree = new int[n + 1];

        // Populate the tree with maintenance costs
        for (MaintenanceRecord log : logs) {
            update(dateIndexMap.get(log.date), log.cost, n);
        }

        // Process queries
        List<Integer> result = new ArrayList<>();
        for (Query query : queries) {
            int startIdx = dateIndexMap.get(query.startDate);
            int endIdx = dateIndexMap.get(query.endDate);
            result.add(query(endIdx) - query(startIdx - 1));
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of maintenance records: ");
        int n = scanner.nextInt();
        List<MaintenanceRecord> logs = new ArrayList<>();

        System.out.println("Enter maintenance records (equipment_id date cost):");
        for (int i = 0; i < n; i++) {
            int id = scanner.nextInt();
            String date = scanner.next();
            int cost = scanner.nextInt();
            logs.add(new MaintenanceRecord(id, date, cost));
        }

        System.out.print("Enter number of queries: ");
        int q = scanner.nextInt();
        List<Query> queries = new ArrayList<>();

        System.out.println("Enter queries (start_date end_date):");
        for (int i = 0; i < q; i++) {
            String startDate = scanner.next();
            String endDate = scanner.next();
            queries.add(new Query(startDate, endDate));
        }

        List<Integer> results = processQueries(logs, queries);
        System.out.println("Total maintenance costs: " + results);

        scanner.close();
    }
}

class MaintenanceRecord {
    int equipmentId;
    String date;
    int cost;

    public MaintenanceRecord(int equipmentId, String date, int cost) {
        this.equipmentId = equipmentId;
        this.date = date;
        this.cost = cost;
    }
}

class Query {
    String startDate;
    String endDate;

    public Query(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
