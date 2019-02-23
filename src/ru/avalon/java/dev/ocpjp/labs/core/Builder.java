package ru.avalon.java.dev.ocpjp.labs.core;

/**
 * Абстрактное представление об эталоне проектирования "Строитель".
 *
 * @param <E> тип, создаваемых строителем объектов.
 */
public interface Builder<E> {

    /**
     * Возвращает экземпляр типа {@link E}.
     *
     * @return экземпляр типа {@link E}
     */
    E build();
}
