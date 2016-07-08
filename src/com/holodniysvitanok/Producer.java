package com.holodniysvitanok;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

	BlockingQueue<String> bq;
	int i;

	public Producer(BlockingQueue<String> bq) {
		super();
		this.bq = bq;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		try {
			while (true) {
				bq.put(produce());
			}
		} catch (InterruptedException e) {
		}
	}

	String produce() {
		return String.valueOf(i++);
	}
}
