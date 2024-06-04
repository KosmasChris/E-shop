package javaapplication2;

// import necesary libraries
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class JavaApplication2 {

    // variables declaration
    private JFrame frame;
    private DefaultListModel<Product> productList;
    private DefaultListModel<Product> cart;
    private JList<Product> productListUI;
    private JList<Product> cartUI;
    private JLabel totalPriceLabel;
    private JButton removeFromCartButton;
    private JButton checkoutButton;
    private JButton searchButton;
    private JTextField searchField;
    private List<Product> allProducts;


    public JavaApplication2() {
        frame = new JFrame("E-Shop");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);

        productList = new DefaultListModel<>();
        cart = new DefaultListModel<>();
        allProducts = new ArrayList<>();

        // Initialize some products
        Product product1 = new Product("Nike", "Men", 90.00, "Sports");
        Product product2 = new Product("Nike", "Kids", 30.00, "Chunky");
        Product product3 = new Product("Nike", "Women", 70.00, "Sneakers");
        Product product4 = new Product("Adidas", "Men", 110.00, "Chunky");
        Product product5 = new Product("Adidas", "Kids", 40.00, "Sports");
        Product product6 = new Product("Adidas", "Women", 60.00, "Sneakers");
        Product product7 = new Product("Puma", "Men", 100.00, "Sneakers");
        Product product8 = new Product("Puma", "Kids", 20.00, "Sports");
        Product product9 = new Product("Puma", "Women", 50.00, "Chunky");

        allProducts.add(product1);
        allProducts.add(product2);
        allProducts.add(product3);
        allProducts.add(product4);
        allProducts.add(product5);
        allProducts.add(product6);
        allProducts.add(product7);
        allProducts.add(product8);
        allProducts.add(product9);


        // Populate the product list
        populateProductList("");

        productListUI = new JList<>(productList);
        cartUI = new JList<>(cart);

        totalPriceLabel = new JLabel("Total: $0.00");

        // Creation Add to Cart button
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.setBackground(Color.black);
        addToCartButton.setForeground(Color.white);
        addToCartButton.setPreferredSize(new Dimension(0,35));
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product selectedProduct = productListUI.getSelectedValue();
                if (selectedProduct != null) {
                    removeFromCartButton.setEnabled(true);
                    removeFromCartButton.setBackground(Color.red);
                    removeFromCartButton.setForeground(Color.white);
                    cart.addElement(selectedProduct);
                    updateTotalPrice();

                    }
            }
        });

        // Creation Remove From Cart Button
        removeFromCartButton = new JButton("Remove from Cart");
        removeFromCartButton.setEnabled(false);
        removeFromCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product selectedProduct = cartUI.getSelectedValue();
                if (selectedProduct != null) {
                    removeFromCartButton.setEnabled(true);
                    cart.removeElement(selectedProduct);
                    updateTotalPrice();
                    if (cart.isEmpty()) {
                        removeFromCartButton.setEnabled(false);
                        removeFromCartButton.setBackground(Color.white);
                    }
                }
            }
        });

        // Creation Checkout Button
        checkoutButton = new JButton("Checkout");
        checkoutButton.setBackground(Color.gray);
        checkoutButton.setForeground(Color.white);
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double totalPrice = calculateTotalPrice();
                JOptionPane.showMessageDialog(frame, "Thank you for your purchase!\nTotal: $" + totalPrice);
                cart.clear();
                updateTotalPrice();
                removeFromCartButton.setEnabled(false);
                removeFromCartButton.setBackground(Color.white);
            }
        });

        // Creation Search Button
        searchButton = new JButton("Search");
        searchField = new JTextField(20);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = searchField.getText();
                populateProductList(keyword);
            }
        });

        //Create the frames and Design the Buttons
        JPanel productListPanel = new JPanel(new BorderLayout());
        productListPanel.setBorder(BorderFactory.createTitledBorder("Products"));
        productListPanel.add(new JScrollPane(productListUI), BorderLayout.CENTER);
        //productListPanel.add(addToCartButton, BorderLayout.SOUTH);

        JPanel addToCartPanel = new JPanel();
        addToCartButton.setPreferredSize(new Dimension(300, 38));
        addToCartPanel.add(addToCartButton);
        productListPanel.add(addToCartPanel, BorderLayout.SOUTH);

        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBorder(BorderFactory.createTitledBorder("Shopping Cart"));
        cartPanel.add(new JScrollPane(cartUI), BorderLayout.CENTER);

        JPanel cartButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cartButtonPanel.add(removeFromCartButton);
        cartButtonPanel.add(checkoutButton);
        removeFromCartButton.setPreferredSize(new Dimension(150, 38));
        checkoutButton.setPreferredSize(new Dimension(150, 38));

        cartPanel.add(cartButtonPanel, BorderLayout.SOUTH);

        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        JPanel contentPanel = new JPanel(new GridLayout(1, 2));
        contentPanel.add(productListPanel);
        contentPanel.add(cartPanel);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(totalPriceLabel);

        frame.add(searchPanel, BorderLayout.NORTH);
        frame.add(contentPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }


    private void updateTotalPrice() {
        double totalPrice = calculateTotalPrice();
        totalPriceLabel.setText("Total: $" + String.format("%.2f", totalPrice));
    }

    private double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (int i = 0; i < cart.size(); i++) {
            totalPrice += cart.get(i).getPrice();
        }
        return totalPrice;
    }

    private void populateProductList(String keyword) {
        productList.clear();
        for (Product product : allProducts) {
            if (keyword.isEmpty() || product.getName().toLowerCase().contains(keyword.toLowerCase()) || product.getCategory().toLowerCase().contains(keyword.toLowerCase()) || product.getDescription().toLowerCase().startsWith(keyword.toLowerCase())){
                productList.addElement(product);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JavaApplication2();
            }
        });
    }

    //Constructor
    private class Product {
        private String name;
        private String description;
        private double price;
        private String category;

        public Product(String name, String description, double price, String category) {
            this.name = name;
            this.description = description;
            this.price = price;
            this.category = category;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public String getCategory() {
            return category;
        }

        public String getDescription(){
            return description;
        }

       @Override
        public String toString() {
            return name + " - $" + price + " (" + category + " "+ description +")";
        } 
    }
}