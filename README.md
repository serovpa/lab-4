# Лабораторная работа №4

*по курсу: "DEV-OCPJP. Подготовка к сдаче сертификационных экзаменов серии Oracle Certified Professional Java Programmer"*

**Рассматриваемые темы:**

1. Java Stream API:
   - создание потока с использованием метода `generate()`;
   - отбор данных с использованием метода `filter()`;
   - преобразование потоков с использованием метода
   `map()`;
   - сортировка потоков;
   - объединение элементов потока в коллекцию с 
   использованием коллекторов и метода `collect()`.
2. Эталоны проектирования:
   - "Строитель";
   - "Фабрика".
  
## Эталоны проектирования

### Эталон проектирования "Строитель" / Builder

Эталон проектирования "Строитель" используют для создания сложных объектов.
Речь идёт о таких объектах, для которых сложно предусмотреть все необходимые
конструкторы, или создание такого количества конструкторов затруднительно.

Эталон "Строитель" также может быть использован для упрощения процесса 
инициализации объектов классов, в случае, когда для инициализации используется
большое количество параметров, что делает использование конструктора
неудобным.

![Эталон проектирования "Строитель"](https://upload.wikimedia.org/wikipedia/ru/2/28/Builder.gif)

Смысл подхода состоит в том, чтобы описать класс, способный разделить во 
времени моменты инициализации различных атрибутов сложного объекта, либо
разделить весь процесс определения параметров и момент инициализации.

Например, есть класс следующего вида, описывающий представление о человеке, 
которое может быть достаточно сложным:
```java
/**
  * Представление о человеке
  */  
class Person {
    /**
      * Bмя
      */
    private String name;
    /**
      * Фамилия
      */
    private String lastName;
    /**
      * Отчество  
      */
    private String surname;
    /**
      * Дата рождения
      */
    private Date birthDate;
    /**
      * Рост 
      */
    private float height;
    /**
      * Вес
      */
    private float weight;
    
    // ...
}
```
Даже в предложенном варианте получается большое количество конструкторов
для различных вариантов использования описанного класса. Ведь человек
может не иметь отчества или нам может быть не важен вес человека и мы хотим
его опустить при инициализации.

Для выхода из ситуации мы можем использовать эталон проектирования 
"Строитель" и описать в классе `Person` вложенный класс `PersonBuilder`:
```java
/**
  * Представление о человеке
  */
class Person {
    /**
      * "Строитель"
      */
    class PersonBuilder {
        private PersonBuilder() {}
        
        // Объект, инициализация которого выполняется в строителе
        private Person dummy = new Person();
        
        /**
          * Устанавливает имя человека 
          *  
          * @param name имя человека
          * @return ссылку на себя самого, чтобы обеспечить возможность
          * вызова методов по цепочке
          */
        public PersonBuilder name(String name) {
            dummy.name = name;
            return this;
        }
        
        /**
          * Устанавливает фамилию человека 
          *  
          * @param lastName фамилия человека
          * @return ссылку на себя самого, чтобы обеспечить возможность
          * вызова методов по цепочке
          */
        public PersonBuilder lastName(String lastName) {
            dummy.lastName = lastName;
            return this;
        }
        
        // ...
        
        /**
          * Выполняет создание экземпляра класса {@link Person}
          * 
          * @return экземпляр класса {@link Person}
          */
        public Person build() {
            Person result = dummy;
            dummy = new Person();
            return result;
        }
    }
    
    private String name;
    
    private String lastName;
    
    // ...
    
    /**
      * Создаёт "Строитель" класса {@link Person}.
      * 
      * @return экземпляр класса {@link PersonBuilder}.
      */
    public static PersonBuilder builder() {
        return new PersonBuilder();
    }
}
```
Тогда создание экземпляра класса `Person` может быть выполнено следуюшим
образом:
```java
Persin person = Person.builder()
      .name("Иван")
      .lastName("Петров")
      .birthDate(Date.valueOf("01.02.1990"))
      .build(); 
```
Подробнее об эталоне проектирования можно почитать в [Wikipedia](https://ru.wikipedia.org/wiki/%D0%A1%D1%82%D1%80%D0%BE%D0%B8%D1%82%D0%B5%D0%BB%D1%8C_(%D1%88%D0%B0%D0%B1%D0%BB%D0%BE%D0%BD_%D0%BF%D1%80%D0%BE%D0%B5%D0%BA%D1%82%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D1%8F)#Java).

### Эталон проектирования "Фабрика" / Factory

Существует много вариантов реализации данного эталона проектирования. 
Основная идея состоит том, чтобы вынести процесс создания экземпляров
за пределы контекста их использования.

Например, есть некоторый интерфейс:
```java
/**
  * Сервис приложения, отвечающий за аутентификацию и авторизацию 
  * пользователей системы.
  */
public interface UserService {
    /**
      * Выполняет аутентификацию пользователя в системе с использованием
      * переданных учётных данных.
      * 
      * @param credentials учётные дынные пользователя
      * @throws CredentialException если учётные данные были указаны 
      * неполно, или произошла ошибка валидации учётных данных.
      * @throws SecurityException если в системе отсутствует пользователь
      * с указанными учётными данными.
      */
    void signIn(Credentials credentials) throws CredentialException, SecurityException;
    
    /**
      * Завершает пользовательский сеанс.
      * 
      * @param user описатель пользователя системы
      */
    void signOut(User user);
    
    // ...
}
```
У предложенного интерфейса может быть предусмотрено множество 
реализаций, которые мы можем хотеть инкапсулировать на уровне модуля.
Но, при этом, мы хотим предоставить возможность создавать экземпляры
сервиса в контекстах, в которых они могут быть полезны.

Для этих случаев и придуман эталон проектирования "Фабрика". 

В самой простой реализации мы можем создать статический метод, который 
возвращает реализацию обобщая её до описанного интерфейса.
```java
/**
  * Сервис приложения, отвечающий за аутентификацию и авторизацию 
  * пользователей системы.
  */
public interface UserService {
    /**
      * Выполняет аутентификацию пользоателя в сисстеме с использованием
      * переданных учётных данных.
      * 
      * @param credentials учётные дынные пользователя
      * @throws CredentialException если учётные дынне были указаны 
      * неполно, или произошла ошибка валидации учётных данных.
      * @throws SecurityException если в системе отсутствует пользователь
      * с указанными учётными данными.
      */
    void signIn(Credentials credentials) throws CredentialException, SecurityException;
    
    /**
      * Завершает пользовательский сеанс.
      * 
      * @param user описатель пользователя системы
      */
    void signOut(User user);
    
    // ...
    
    /**
      * Абстрактная файрика, возвращющая реализацию серсиса аутентификации
      * и авторизации пользователей.
      * @return экземплр типа {@link UserService}
      */
    static UserService getInstance() {
        return new UserServiceBean();
    }
}
```
С другой стороны нам может требоваться использовать разные реализации, в 
зависимости от того, какие данные нам доступны. Но нам, также, может хотеться
вынести процесс определения того, какая реализация нужна за пределы
контекста её использования.

Примером может послужить конфигурация приложения. Допустим конфигурация
описана следующим интерфейсом:
```java
/**
  * Конфигурация приложения.
  */
public interface Configuration {
    /**
      * Возвращяет имя базы данных.
      * @return имя базы данных в виде строки.
      */
    String getDatabaseName();
    /**
      * Возвращает имя пользователя базы данных, от имени которого
      * будет осуществляться подключение к базе данных.
      * @return имя пользователя в виде строки.
      */
    String getDatabaseUserName();
    /**
      * Возвращает пароль пользователя от имени которого будет 
      * осуществлятся подключение к базе данных.
      * @return пароль в виде строки.
      */
    Stirng getDatabasePassword();
    
    // ...
}
```
Мы можем хотеть в различных ситуациях хранить конфигурацию в различных 
файлах. Например, в виде текстовых файлах, или на специальном сервере, или
в реестре операционной системы. В таком случае мы можем создать несколько
фабричных методов, с одинаковым названием и различными параметрами. Каждый
метод будет возвращать разные реализации, имеющие общий интерфейс.
```java
/**
  * Конфигурация приложения.
  */
public interface Configuration {
    /**
      * Возвращает имя базы данных.
      * @return имя базы данных в виде строки.
      */
    String getDatabaseName();
    /**
      * Возвращает имя пользователя базы данных, от имени которого
      * будет осуществляться подключение к базе данных.
      * @return имя пользователя в виде строки.
      */
    String getDatabaseUserName();
    /**
      * Возвращает пароль пользователя от имени которого будет 
      * осуществляется подключение к базе данных.
      * @return пароль в виде строки.
      */
    Stirng getDatabasePassword();
    
    // ...
    
    /**
      * Возвращает конфигурацию, хранимую в файле.
      * 
      * @param pathname путь к файлу конфигурации
      * @return экземпляр конфигурации.
      */
    static Configuration getInstance(String pathname) {
        return new FileBasedConfiguration(pathname);
    }

    /**
      * Возвращает конфигурацию, хранимую на сервере конфигураций.
      * 
      * @param address адрес сетевого ресурса
      * @return экземпляр конфигурации.
      */
    static Configuration getInstance(SocketAddress address) {
        return new NetworkBasedConfiguration(address);
    }
}
```

## Java Stream API

Поток - представляет собой последовательность элементов, поддерживающая
агрегационные операции над элементами.

### Создание потока

Существует несколько способов создания потока. Наиболее распространёнными
способами создания потоков можно считать:
- создание потока на основе коллекции;
- создание потока на основе массива;
- создание потока с использованием метода `generate()` класса `Stream`.

Кроме перечисленных, существуют низкоуровневые подходы к созданию потоков,
которые используются в реализации вышеперечисленных методов.

#### Создание потока из коллекции элементов

Чтобы создать поток из коллекции достаточно вызвать от коллекции метод 
`stream()`. Данный метод доступен в любом экземпляре коллекции, поскольку 
определён в интерфейсе `Collection` следующим образом:

```java
public interface Collection {
    
    // ...
    
    /**
     * Returns a sequential {@code Stream} with this collection as its source.
     *
     * <p>This method should be overridden when the {@link #spliterator()}
     * method cannot return a spliterator that is {@code IMMUTABLE},
     * {@code CONCURRENT}, or <em>late-binding</em>. (See {@link #spliterator()}
     * for details.)
     *
     * @implSpec
     * The default implementation creates a sequential {@code Stream} from the
     * collection's {@code Spliterator}.
     *
     * @return a sequential {@code Stream} over the elements in this collection
     * @since 1.8
     */
    default Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }
    
    // ...
}
```

Как можно видеть, поток из коллекции создаётся на основе итератора
с использованием вспомогательного метода `stream()` класса `StreamSupport`. 
Класс `StreamSupport` представляет собой набор вспомогательных 
низкоуровневых методов, предназначенных для создания потоков на основе
итераторов.

#### Создание потока на основе массива

Создание потока из массива элементов выполняется с использованием 
вспомогательного метода `stream()` класса `Arrays`.

Допустим мы имеем некоторый массив строк, названный нами `colors`. В 
таком случае получить поток, состоящий из элементов данного массива
можно следующим образом:
```java
Stream<String> stream = Arrays.stream(colors);
```

Методы, участвующие в вызове, показанном в предыдущем примере, описаны
следующим образом:

```java
/**
 * This class contains various methods for manipulating arrays (such as
 * sorting and searching). This class also contains a static factory
 * that allows arrays to be viewed as lists.
 *
 * <p>The methods in this class all throw a {@code NullPointerException},
 * if the specified array reference is null, except where noted.
 *
 * <p>The documentation for the methods contained in this class includes
 * brief descriptions of the <i>implementations</i>. Such descriptions should
 * be regarded as <i>implementation notes</i>, rather than parts of the
 * <i>specification</i>. Implementors should feel free to substitute other
 * algorithms, so long as the specification itself is adhered to. (For
 * example, the algorithm used by {@code sort(Object[])} does not have to be
 * a MergeSort, but it does have to be <i>stable</i>.)
 *
 * <p>This class is a member of the
 * <a href="{@docRoot}/java/util/package-summary.html#CollectionsFramework">
 * Java Collections Framework</a>.
 *
 * @author Josh Bloch
 * @author Neal Gafter
 * @author John Rose
 * @since  1.2
 */
public class Arrays {
    
    // ...
    
    /**
     * Returns a sequential {@link Stream} with the specified array as its
     * source.
     *
     * @param <T> The type of the array elements
     * @param array The array, assumed to be unmodified during use
     * @return a {@code Stream} for the array
     * @since 1.8
     */
    public static <T> Stream<T> stream(T[] array) {
        return stream(array, 0, array.length);
    }

    /**
     * Returns a sequential {@link Stream} with the specified range of the
     * specified array as its source.
     *
     * @param <T> the type of the array elements
     * @param array the array, assumed to be unmodified during use
     * @param startInclusive the first index to cover, inclusive
     * @param endExclusive index immediately past the last index to cover
     * @return a {@code Stream} for the array range
     * @throws ArrayIndexOutOfBoundsException if {@code startInclusive} is
     *         negative, {@code endExclusive} is less than
     *         {@code startInclusive}, or {@code endExclusive} is greater than
     *         the array size
     * @since 1.8
     */
    public static <T> Stream<T> stream(T[] array, int startInclusive, int endExclusive) {
        return StreamSupport.stream(spliterator(array, startInclusive, endExclusive), false);
    }
    
    // ...
}
```

#### Создание потока с использованием метода `generate()` интерфейса `Stream`

Для создания более сложных массивов, можно использовать метод `generate()`
интерфейса `Stream`.

Данный метод описан в интерфейсе `Stream` следующим образом:

```java
public interface Stream<T> extends BaseStream<T, Stream<T>> {
    
    // ...
    
    /**
     * Returns an infinite sequential unordered stream where each element is
     * generated by the provided {@code Supplier}.  This is suitable for
     * generating constant streams, streams of random elements, etc.
     *
     * @param <T> the type of stream elements
     * @param s the {@code Supplier} of generated elements
     * @return a new infinite sequential unordered {@code Stream}
     */
    public static<T> Stream<T> generate(Supplier<? extends T> s) {
        Objects.requireNonNull(s);
        return StreamSupport.stream(
                new StreamSpliterators.InfiniteSupplyingSpliterator.OfRef<>(Long.MAX_VALUE, s), false);
    }
    
    // ...
}
```

Как можно видеть, в этом случае, для создания потока, тоже используется 
итератор, только условно бесконечный. Также можно видеть, что метод
принимает в качестве параметра экземпляр функционального интерфейса
с использованием которого будут получены конкретные значения элементов
результирующего потока.

Как известно, проинициализировать экземпляр функционального интерфейса
можно проинициализировать чеытрмя возможными способами:
- при помощи экземпляра класса, реализующего необходимый интерфейс;
- с использованием анонимного класса;
- с использованием лямбда-выражения;
- передав ссылку на метод.

Например, нам требуется создать поток, содержащий случайные числа.
В таком случае создание потока с использованием метода `generate()` может
выглядеть следующим образом:

```java
Random random = new Random();
Stream<Integer> numbers = Stream.generate(random::nextInt);
```

Важно помнить, что полученный поток будет условно бесконечным. Ограничить
размер потока можно, к примеру, с использованием метода `limit()` 
интерфейса `Stream`. Данный метод объявлен в интерфейсе `Stream` следующим 
образом:

```java
public interface Stream<T> extends BaseStream<T, Stream<T>> {
    
    // ...
    
    /**
     * Returns a stream consisting of the elements of this stream, truncated
     * to be no longer than {@code maxSize} in length.
     *
     * <p>This is a <a href="package-summary.html#StreamOps">short-circuiting
     * stateful intermediate operation</a>.
     *
     * @apiNote
     * While {@code limit()} is generally a cheap operation on sequential
     * stream pipelines, it can be quite expensive on ordered parallel pipelines,
     * especially for large values of {@code maxSize}, since {@code limit(n)}
     * is constrained to return not just any <em>n</em> elements, but the
     * <em>first n</em> elements in the encounter order.  Using an unordered
     * stream source (such as {@link #generate(Supplier)}) or removing the
     * ordering constraint with {@link #unordered()} may result in significant
     * speedups of {@code limit()} in parallel pipelines, if the semantics of
     * your situation permit.  If consistency with encounter order is required,
     * and you are experiencing poor performance or memory utilization with
     * {@code limit()} in parallel pipelines, switching to sequential execution
     * with {@link #sequential()} may improve performance.
     *
     * @param maxSize the number of elements the stream should be limited to
     * @return the new stream
     * @throws IllegalArgumentException if {@code maxSize} is negative
     */
    Stream<T> limit(long maxSize);
    
    // ...
}
```

Поток ведёт себя подобно классам эталона проектирования "Строитель".
Всякий метод потока возврящает ссылку на сам поток (за исключением методов, 
потребляющих элементы потока). Такая конфигурая интерфейса `Stream` 
предоставляет нам возможность вызывать все "настраивающие" поток методы 
"по цепочке", как показано в нижеследующем примере:

```java
Random random = new Random();
Stream<Integer> numbers = Stream.generate(random::nextInt)
                                .limit(100);
```

Подробную информацию можно посмотреть в [официальной документации](https://www.oracle.com/technetwork/articles/java/ma14-java-se-8-streams-2177646.html).