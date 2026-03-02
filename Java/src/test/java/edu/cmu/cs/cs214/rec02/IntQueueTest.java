package edu.cmu.cs.cs214.rec02;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class IntQueueTest {

    private IntQueue mQueue;
    private List<Integer> testList;

    /**
     * Тест эхлэхийн өмнө ажиллана.
     */
    @Before
    public void setUp() {
        // Одоо ArrayIntQueue-г шалгахаар тохируулсан байна
        mQueue = new ArrayIntQueue();
        testList = new ArrayList<>(List.of(1, 2, 3));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testNotEmpty() {
        mQueue.enqueue(10);
        assertFalse(mQueue.isEmpty());
    }

    @Test
    public void testPeekEmptyQueue() {
        // Хоосон үед peek хийхэд null ирэх ёстой (Interface-ийн заавар ёсоор)
        assertNull(mQueue.peek());
    }

    @Test
    public void testPeekNoEmptyQueue() {
        mQueue.enqueue(10);
        mQueue.enqueue(20);
        assertEquals(Integer.valueOf(10), mQueue.peek());
    }

    @Test
    public void testEnqueue() {
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
            assertEquals(testList.get(0), mQueue.peek());
            assertEquals(i + 1, mQueue.size());
        }
    }

    @Test
    public void testDequeue() {
        mQueue.enqueue(10);
        mQueue.enqueue(20);
        assertEquals(Integer.valueOf(10), mQueue.dequeue());
        assertEquals(1, mQueue.size());
        assertEquals(Integer.valueOf(20), mQueue.dequeue());
        assertTrue(mQueue.isEmpty());
    }

    /**
     * ArrayIntQueue-ийн ensureCapacity() доторх алдааг илрүүлэх тест.
     * Анхны хэмжээ 10 байгаа тул 11 элемент нэмэхэд массив томрох ёстой.
     */
    @Test
    public void testEnsureCapacity() {
        for (int i = 0; i < 12; i++) {
            mQueue.enqueue(i);
        }
        assertEquals(12, mQueue.size());
        for (int i = 0; i < 12; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }
    }

    @Test
    public void testContent() throws IOException {
        InputStream in = new FileInputStream("src/test/resources/data.txt");
        try (Scanner scanner = new Scanner(in)) {
            scanner.useDelimiter("\\s*fish\\s*");

            List<Integer> correctResult = new ArrayList<>();
            while (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                correctResult.add(input);
                mQueue.enqueue(input);
            }

            for (Integer result : correctResult) {
                assertEquals(result, mQueue.dequeue());
            }
        }
    }
}