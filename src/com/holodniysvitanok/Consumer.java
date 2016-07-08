package com.holodniysvitanok;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable {

	BlockingQueue<String> bq;

	public Consumer(BlockingQueue<String> bq) {
		this.bq = bq;
	}

	@Override
	public void run() {
		try {
			while (true) {

				consume(bq.take());
				TimeUnit.MILLISECONDS.sleep(100);
			}
		} catch (InterruptedException e) {
		}
	}

	void consume(Object object) {
		String form = new SimpleDateFormat("ss S").format(new Date());
		System.out.println("поток  id " + Thread.currentThread().getId() + " \t\"" + object + "\"\t время = " + form);

	}
}
