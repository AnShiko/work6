package com.geekbrains.work6;

public class PoolOfNumber {
    static final int size = 10000000;
    static final int h = size / 2;

    public void metodOne()
    {
        float[] arr = new float[size];
        for (int i = 0; i < arr.length; i++) arr[i] = 1;
        long a = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++)
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        System.out.printf("Время работы первого метода = %d",(System.currentTimeMillis() - a));
        System.out.println();
    }

    public void metodTwo()
    {
        float[] arr = new float[size];
        for (int i = 0; i < arr.length; i++) arr[i] = 1;
        final long a = System.currentTimeMillis();

        final float[] a1 = new float[h];
        final float[] a2 = new float[h];
        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);
        System.out.printf("Время разбивки = %d, ",(System.currentTimeMillis() - a));

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < a1.length; i++)
                    a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                System.out.printf("Время обработки первого потока = %d, ",(System.currentTimeMillis() - a));

            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < a2.length; i++)
                    a2[i] = (float) (a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                System.out.printf("Время обработки второго потока = %d, ",(System.currentTimeMillis() - a));
            }
        });

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);
        System.out.printf("Время склейки = %d, ",(System.currentTimeMillis() - a));
        System.out.printf("Время работы второго метода = %d.",(System.currentTimeMillis() - a));
        System.out.println();
    }



    public static void main(String[] args) {
        PoolOfNumber pool = new PoolOfNumber();
        System.out.println("Первый метод");
        pool.metodOne();
        System.out.println("Второй метод");
        pool.metodTwo();

    }
}

