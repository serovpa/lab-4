package ru.avalon.java.dev.ocpjp.labs.models;

import ru.avalon.java.dev.ocpjp.labs.core.Builder;
import ru.avalon.java.dev.ocpjp.labs.core.io.RandomFileReader;

import java.io.IOException;
import java.util.Collection;

/**
 * Абстрактное представлене о товаре.
 */
public interface Commodity {
    /**
     * Возвращяет код товара.
     *
     * @return код товара в виде строки.
     */
    String getCode();

    /**
     * Возвращает артикул товара
     *
     * @return артикул товаа в виде строки.
     */
    String getVendorCode();

    /**
     * Возвращает наименование товара.
     *
     * @return наименование товара в виде строки.
     */
    String getName();

    /**
     * Возвращает стоимость товара.
     *
     * @return стоимость товара.
     */
    double getPrice();

    /**
     * Возвращает остаток товара на складе.
     *
     * @return остаток товара.
     */
    int getResidue();

    /**
     * Абстрактное представление о реализации эталона
     * проектирования "Строитель" для типа данных {@link Commodity}.
     */
    interface CommodityBuilder extends Builder<Commodity> {
        /**
         * Устанавливает код товара.
         *
         * @param code код товара
         * @return экземпояр типа {@link CommodityBuilder}
         */
        CommodityBuilder code(String code);

        /**
         * Устанавливает артикул товара.
         *
         * @param vendorCode артикул товара
         * @return экземпояр типа {@link CommodityBuilder}
         */
        CommodityBuilder vendorCode(String vendorCode);

        /**
         * Устанавливает наименование товара.
         *
         * @param name наименование товара
         * @return экземпояр типа {@link CommodityBuilder}
         */
        CommodityBuilder name(String name);

        /**
         * Устанавливает стоимость товара.
         *
         * @param price стоимость товара
         * @return экземпояр типа {@link CommodityBuilder}
         */
        CommodityBuilder price(double price);

        /**
         * Устанавливает остаток товара на складе.
         *
         * @param residue остаток товара
         * @return экземпояр типа {@link CommodityBuilder}
         */
        CommodityBuilder residue(int residue);

        /**
         * Возвращает экземпляр типа {@link Commodity}
         * проинициализированный согласно заданной
         * конфигурации.
         *
         * @return экземпояр типа {@link Commodity}
         */
        @Override
        Commodity build();
    }

    /**
     * Возвращает "Строитель", с использованием которого
     * можно создавать экземпляры типа {@link Commodity}.
     *
     * @return экземпляр типа {@link CommodityBuilder}
     */
    static CommodityBuilder builder() {
        /*
         * TODO(Студент): Реализовать метод 'builder()' типа 'Commodity'
         * В рамках задачи потребубется создать реализацию
         * интерфейса CommodityBuilder, что в свою очередь
         * потребует создаиня реализации для интерфейса Commodity.
         *
         * Созданные реализации случше всего инкапсулировать
         * на уровне пакета.
         */
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    /**
     * Выполняет создание заданного количества случайных
     * товаров.
     *
     * @param limit количество товаров в результирующей
     *              коллекции
     * @return коллекцию экземпляров {@link Commodity}
     * @throws IOException в случае ошибки ввода-вывода
     */
    static Collection<Commodity> random(int limit) throws IOException {
        try (RandomFileReader reader = RandomFileReader.fromSystemResource("resources/household.csv")) {
            /*
             * TODO(Студент): Реализовать создание случайных объектов типа 'Commodity'
             * 1. Для создания коллекции следует использовать метод 'generate()' класса 'Stream'
             * 2. Для получения коллекции следует использовать метод 'collect()' класса 'Stream'
             */
            throw new UnsupportedOperationException("Not implemented yet!");
        }
    }

    /**
     * Выполняет создание экземпляра типа {@link Commodity}
     * из строки.
     *
     * @param string строка, содержащая данные о товаре
     * @return экземпляр типа {@link Commodity}
     */
    static Commodity valueOf(String string) {
        /*
         * TODO(Студент): реализовать метод 'parse()' класса 'Commodity'
         * Реализация метода должна быть основана на формате
         * файла 'resources/household.csv'.
         */
        throw new UnsupportedOperationException("Not implemented yet!");
    }
}
