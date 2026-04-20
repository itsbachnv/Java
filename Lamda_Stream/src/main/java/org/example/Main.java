package org.example;

import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println("---- LAMDA -----");
        List<Integer> listLamda = Arrays.asList(1, 2, 3, 4);
        listLamda.forEach(l -> System.out.println(l));
        System.out.println("---- STREAM -----");
        listLamda.stream().filter(x-> x % 2 ==0).map(x -> x * 2).forEach(System.out::println);
        System.out.println("---- collect -----");
        List<Integer> collects = listLamda.stream().filter(x-> x > 2).collect(Collectors.toList());
        System.out.println(collects);

        System.out.println("---- Bài tập -----");
        List<Integer> list = Arrays.asList(5, 12, 20, 3, 12, 25, 30);
        //output: Lọc + bỏ trùng + sắp xếp [12, 20, 25, 30].
        List<Integer> listReduce = list.stream().filter(x->x>10).distinct().sorted().collect(Collectors.toList());
        System.out.println(listReduce);

        //Nhân đôi giá trị: [10, 24, 40, 6, 24, 50, 60]
        List<Integer> listDup = list.stream().map(x->x*2).distinct().collect(Collectors.toList());
        System.out.println(listDup);
        //Yêu cầu 3: Tính tổng
        System.out.println(list.stream().mapToInt(x->x).sum());
        //Yêu cầu 4: Tìm số lớn nhất
        System.out.println(list.stream().max(Integer::compareTo));

        class Product {
            int id;
            String name;
            String category;
            double price;

            public Product(int i, String macBook, String laptop, int i1) {
                this.id = i;
                this.name = macBook;
                this.category = laptop;
                this.price = i1;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }
        }

        List<Product> productList = Arrays.asList(
                new Product(1, "iPhone", "Phone", 2000),
                new Product(2, "Samsung", "Phone", 1500),
                new Product(3, "MacBook", "Laptop", 3000),
                new Product(4, "Dell", "Laptop", 2500)
        );
        //1. Lấy danh sách tên sản phẩm
        System.out.println();
        System.out.println(productList.stream().map(Product::getName).collect(Collectors.toList()));
        //2. Lọc sản phẩm giá > 2000
        System.out.println(productList.stream().filter(x->x.price > 2000).map(Product::getName).collect(Collectors.toList()));

        //3. Group theo category (SIÊU QUAN TRỌNG)
        Map<String, List<Product>> result = productList.stream().collect(Collectors.groupingBy(Product::getCategory));
        System.out.println(result);
        //dat nhat
        String result2 = productList.stream().max(Comparator.comparing(Product::getPrice)).map(Product::getName).orElse("NULL");
        System.out.println(result2);
    }
}