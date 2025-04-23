# Лаборатори ажил 13 - IntQueue Тестийн Тайлан

- Семинар 3|2
- B241940029 А.Эрхэмтөр

## IntQueueTest Тестийн Тайлбар

`IntQueueTest` класс нь `LinkedIntQueue` болон `ArrayIntQueue` классуудын хэрэгжүүлэлтүүдийг шалгах JUnit тестүүдийг агуулсан. Бүх тестүүд нь Log4j ашиглан логгинг хийдэг.

### Тестийн Төрлүүд

1. **testIsEmpty()** - Шинээр үүсгэсэн дараалал хоосон эсэхийг шалгана

   ```java
   @Test
   public void testIsEmpty() {
       logger.info("Running testIsEmpty");
       assertTrue(mQueue.isEmpty());
       logger.debug("Queue is empty as expected");
   }
   ```

2. **testNotEmpty()** - Элементтэй дараалал хоосон биш эсэхийг шалгана

   ```java
   @Test
   public void testNotEmpty() {
       logger.info("Running testNotEmpty");
       IntQueue queue = new ArrayIntQueue();
       assertTrue("New queue should be empty", queue.isEmpty());
       queue.enqueue(10);
       assertFalse("Queue with elements should not be empty", queue.isEmpty());
   }
   ```

3. **testPeekEmptyQueue()** - Хоосон дарааллын peek() функц null буцаах эсэхийг шалгана

   ```java
   @Test
   public void testPeekEmptyQueue() {
       logger.info("Running testPeekEmptyQueue");
       IntQueue queue = new ArrayIntQueue();
       assertNull("Peek on empty queue should return null", queue.peek());
   }
   ```

4. **testPeekNoEmptyQueue()** - Дарааллын peek() функц эхний элементийг буцаах эсэхийг шалгана

   ```java
   @Test
   public void testPeekNoEmptyQueue() {
       // Код нь peek() функц элементийг устгахгүйгээр эхний элементийг буцаах эсэхийг шалгана
   }
   ```

5. **testEnqueue()** - Дараалалд элемент нэмэх үйлдлийг шалгана

   ```java
   @Test
   public void testEnqueue() {
       // Код нь enqueue() функц элементүүдийг зөв хадгалж байгаа эсэхийг шалгана
   }
   ```

6. **testDequeue()** - Дарааллаас элемент гаргах үйлдлийг шалгана

   ```java
   @Test
   public void testDequeue() {
       // Код нь dequeue() функц элементүүдийг зөв дарааллаар буцааж байгааг шалгана
   }
   ```

7. **testContent()** - Файлаас уншсан өгөгдлүүдийг дараалалд хадгалах, гаргахыг шалгана

   ```java
   @Test
   public void testContent() throws IOException {
       // Код нь файлаас уншсан элементүүдийг дараалалд оруулж, гаргаж байгааг шалгана
   }
   ```

8. **testClear()** - Дараалал цэвэрлэх үйлдлийг шалгана

   ```java
   @Test
   public void testClear() {
       // Код нь clear() функц дарааллыг зөв цэвэрлэж байгааг шалгана
   }
   ```

9. **testEnsureCapacity()** - Дарааллын багтаамж автоматаар өргөжих эсэхийг шалгана

   ```java
   @Test
   public void testEnsureCapacity() {
       // Код нь дараалал нь багтаамжаас хэтрэх үед автоматаар өргөжих эсэхийг шалгана
   }
   ```

10. **testWrappingQueue()** - Дараалал тойрч эргэх зан төлөвийг шалгана
    ```java
    @Test
    public void testWrappingQueue() {
        // Код нь дараалалд элементүүд эхлэл, төгсгөлд нэмэгдэж, гарахад зөв ажиллах эсэхийг шалгана
    }
    ```

### Log4j Ашиглалт

`IntQueueTest` классын бүх тестүүд нь Log4j ашиглан үйл ажиллагаагаа бүртгэдэг. Тестийн функц бүрд:

1. INFO түвшинд тест эхэлсэн талаар мэдээлдэг

   ```java
   logger.info("Running testMethodName");
   ```

2. DEBUG түвшинд тестийн үйл явцыг дэлгэрэнгүй бүртгэдэг

   ```java
   logger.debug("Operation result details: {}", value);
   ```

3. DEBUG түвшинд тестийн үр дүнг бүртгэдэг
   ```java
   logger.debug("Test assertion passed as expected");
   ```

### Тестийн Хамрах Хүрээ

Эдгээр тестүүд нь `IntQueue` интерфейсийн бүх методуудыг хамарсан бөгөөд:

1. Хэвийн тохиолдлуудыг шалгана (элемент нэмэх, гаргах, харах)
2. Хязгаарын тохиолдлуудыг шалгана (хоосон дараалал, бүрэн дараалал)
3. Онцгой тохиолдлуудыг шалгана (хоосон дарааллаас dequeue/peek хийх)
4. Дарааллын багтаамж өргөжих үеийн зан төлөвийг шалгана
5. Дарааллын элементүүд тойрч эргэх үеийн зан төлөвийг шалгана

Эдгээр тестүүд нь `ArrayIntQueue` классын бүх мөрийг хамрах зорилготой.

## Тестүүдийг Ажиллуулах

Бүх тестүүдийг ажиллуулахын тулд:

```
mvn test
```

Зөвхөн `IntQueueTest`-ийг ажиллуулахын тулд:

```
mvn test -Dtest=IntQueueTest
```

## Тестийн Үр Дүн

Тестийн үр дүн болон хамрах хүрээний тайланг `target/site/jacoco/index.html` файлд үзэх боломжтой.
