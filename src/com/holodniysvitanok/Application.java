package com.holodniysvitanok;

import java.util.concurrent.BlockingQueue;

import com.holodniysvitanok.myblockingqueue.MySimpleLinkedBlockingQueue;

public class Application {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		BlockingQueue<String> bq = new MySimpleLinkedBlockingQueue<>(2);

		new Thread(new Producer(bq)).start();

		new Thread(new Consumer(bq)).start();
		new Thread(new Consumer(bq)).start();

		// bq.offer("q");
		// bq.offer("2");
		// bq.offer("3");
		// bq.offer("aa");
		// Object[] array = bq.toArray();
		// for (Object str : array)
		// System.out.println(str);
	}
}
