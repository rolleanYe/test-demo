package com.gupiao;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by yejun on 18/5/18.
 */
public class Test003 {

    public static void main(String[] args) {

        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
        String a="a";
        String b="b";
        queue.offer(a);
        for(int i=0;;i++) {
            if (i % 1024 == 0) {
                System.out.println("i = " + i);
            }
            queue.offer(b);
            queue.remove(b);
            System.out.println(queue);
        }

    }


}
