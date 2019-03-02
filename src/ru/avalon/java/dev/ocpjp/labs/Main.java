package ru.avalon.java.dev.ocpjp.labs;

import ru.avalon.java.dev.ocpjp.labs.models.Commodity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import ru.avalon.java.dev.ocpjp.labs.core.io.RandomFileReader;
import ru.avalon.java.dev.ocpjp.labs.core.io.RandomFileReaderImpl;
import ru.avalon.java.dev.ocpjp.labs.models.CommodityImpl;

public class Main {

    public static void main(String[] args) throws IOException {
        final Collection<Commodity> commodities = Commodity.random(100);
        
        /*1. Выполнить поиск самого дорого товара*/
        System.out.println("Самый дорогой товар это: ");
        OptionalDouble max = commodities
                .stream()
                .mapToDouble(Commodity::getPrice)
                .max(); 
        for(Commodity comm : commodities){
            if(comm.getPrice() == max.getAsDouble()){
                System.out.println(comm.getName() + " и его цена " + max.getAsDouble());
            }
            
        }
        
        /* 2. Найти товары с минимальным остатком*/
        System.out.println();
        System.out.println("Меньше всего осталось товара : ");
        
        OptionalInt min = commodities
                .stream()
                .mapToInt(Commodity::getResidue)
                .min(); 
        
        Consumer<Commodity> action2 = (Commodity) -> {
                if (Commodity.getResidue() <= min.getAsInt()) {
                System.out.println("Товар - " + Commodity.getName() + "с минимальным остатком " + Commodity.getResidue());                
            }
        };
        commodities.forEach(action2);
        
        
//        for(Commodity comm : commodities){
//            if(comm.getResidue() == min.getAsInt()){
//                System.out.println(comm.getName() + " и его осталось " + min.getAsInt());
//            }
//        }

        /* 3. Найти товары с остатком меньшим 5 и вывести в консоль их названия*/
        Collection<Commodity> residueLessFive = commodities .stream()
                                                            .filter(Commodity -> Commodity.getResidue() <= 5)
                                                            .collect(Collectors.toCollection(ArrayList::new));

        residueLessFive.forEach(x -> System.out.println(x.getResidue()));
        
        
        /* 4. Подсчитать общую стоимость товаров с учётом их остатка*/
        double sum = commodities    .stream()
                                    .mapToDouble(x -> x.getPrice()*x.getResidue())
                                    .sum();        
        System.out.println();
        System.out.println("Общая стоимость товаров на складе: " + sum);
    

        
            
        /* 5. Найти товар, с самым длинным названием и вывести его на экран*/
        System.out.println();
        System.out.println("Товар с самым длинным названием это: ");
        String longest = commodities
                .stream()
                .map(x-> x.getName())
                .max(Comparator.comparingInt(String::length))
                .get();
        System.out.println(longest);
        
        
        /* 6. Выполнить сортировку коллекции commodities*/
        commodities.stream().sorted(Comparator.comparing(Commodity::getName)) 
                .forEach(x -> System.out.println(x.getName()));
        
        /* 7. Найти среднюю стоимость товаров*/
        
        OptionalDouble average = commodities.stream()
                .mapToDouble(Commodity::getPrice)
                .average();
        System.out.println("Средняя цена товаров: " + average.getAsDouble());
        
         /* 8. Найти моду (медиану) стоимости товаров*/
         double[] arr = commodities.stream()
                .mapToDouble(Commodity::getPrice)
                .sorted()
                .toArray();
         System.out.println("Медиана: " + getMedian(arr));   
    }
        
       
        static double getMedian(double[] arr) {
        double median;
        if (arr.length % 2 == 0) {
            median = arr[(arr.length/2) + 1];
        } else {
            median = arr[arr.length/2];

        }
        return median;
    }

                
                
}

    
            
        

                                                           
    

        /*
         * TODO(Студент): С использованием Java Stream API выполнить задачи из списка:
         * 1. Выполнить поиск самого дорого товара
         * 2. Найти товары с минимальным остатком
         * 3. Найти товары с остатком меньшим 5 и вывести в консоль их названия
         * 4. Подсчитать общую стоимость товаров с учётом их остатка
         * 5. Найти товар, с самым длинным названием и вывести его на экран
         * 6. Выполнить сортировку коллекции commodities
         * 7. Найти среднюю стоимость товаров
         * 8. Найти моду (медиану) стоимости товаров
         */
    

