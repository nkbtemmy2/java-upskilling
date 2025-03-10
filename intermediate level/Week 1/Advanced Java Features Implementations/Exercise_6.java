class Product {
    private String name;
    private double price;
    private String category;

    public Product(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                '}';
    }
}

class ProductProcessor {
    public static void main(String[] args) {
        java.util.List<Product> products = java.util.Arrays.asList(
                new Product("Laptop", 1200.0, "Electronics"),
                new Product("Smartphone", 800.0, "Electronics"),
                new Product("Desk", 350.0, "Furniture"),
                new Product("Chair", 150.0, "Furniture"),
                new Product("Tablet", 500.0, "Electronics"),
                new Product("Bookshelf", 280.0, "Furniture")
        );

        // Custom comparator to sort by category then by price (ascending)
        java.util.Comparator<Product> categoryThenPriceComparator =
                java.util.Comparator.comparing(Product::getCategory)
                        .thenComparing(Product::getPrice);

        // Process the collection using Stream API
        System.out.println("Products sorted by category then price:");
        products.stream()
                .sorted(categoryThenPriceComparator)
                .forEach(System.out::println);

        System.out.println("\nElectronic products with price over $600:");
        products.stream()
                .filter(p -> p.getCategory().equals("Electronics"))
                .filter(p -> p.getPrice() > 600)
                .forEach(System.out::println);

        double averageElectronicsPrice = products.stream()
                .filter(p -> p.getCategory().equals("Electronics"))
                .mapToDouble(Product::getPrice)
                .average()
                .orElse(0.0);

        System.out.println("\nAverage price of electronics: $" + averageElectronicsPrice);

        // Group products by category
        java.util.Map<String, java.util.List<Product>> productsByCategory =
                products.stream()
                        .collect(java.util.stream.Collectors.groupingBy(Product::getCategory));

        System.out.println("\nProducts grouped by category:");
        productsByCategory.forEach((category, productList) -> {
            System.out.println(category + ":");
            productList.forEach(p -> System.out.println("  " + p.getName() + " - $" + p.getPrice()));
        });
    }
}