package com.holodniysvitanok.myblockingqueue;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class MySimpleLinkedBlockingQueue<T> implements BlockingQueue<T>, Serializable {

	/**
	 * Простая тестовая реализация блокирующей очереди основаной на двусвязном
	 * списке
	 */
	private static final long serialVersionUID = 1775340375351081884L;

	private Node firstNode;
	private Node lastNode;
	private int capacity;
	private volatile int size;

	private static class Node {
		Node nextNode;
		Node previousNode;
		Object object;

		Node(Object object) {
			this.object = object;
		}

	}

	public MySimpleLinkedBlockingQueue(int capacity) {
		this.capacity = capacity;
	}

	@Override
	public T element() {
		if (size == 0) {
			throw new NoSuchElementException();
		}
		return (T) firstNode.object;
	}

	@Override
	public T peek() {
		if (size == 0) {
			return null;
		}
		return (T) firstNode.object;
	}

	@Override
	public T poll() {
		if (size == 0) {
			return null;
		}
		return retrieveItems();
	}

	private synchronized T retrieveItems() { // Извлекает из головы элемент
												// (общий метод для poll(),
												// remove(), take())
		Node retrievable = firstNode;
		firstNode = firstNode.nextNode;
		if (firstNode != null)
			firstNode.previousNode = null;
		size--;
		return (T) retrievable.object;
	}

	@Override
	public T remove() {
		if (size == 0) {
			throw new NoSuchElementException();
		}
		return retrieveItems();
	}

	@Override
	public boolean addAll(Collection<? extends T> arg0) {
		// метод не поддерживается
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		firstNode = null;
		lastNode = null;
		size = 0;

	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		// метод не поддерживается
		return false;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return new Iterator<T>() {

			Node iterator = firstNode;

			@Override
			public boolean hasNext() {
				return iterator != null;
			}

			@Override
			public T next() {
				Node returnNode = iterator;
				iterator = iterator.nextNode;
				return (T) returnNode.object;
			}
		};
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		// метод не поддерживается
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		// метод не поддерживается
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public Object[] toArray() {
		Object arr[] = new Object[size];
		Iterator<T> iter = iterator();
		int i = 0;
		while (iter.hasNext()) {
			arr[i] = iter.next();
			i++;
		}
		return arr;
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public synchronized boolean add(T arg0) {
		if (size == capacity)
			throw new IllegalStateException(); // ??
		addToQueue(arg0);
		return true;
	}

	@Override
	public boolean contains(Object arg0) {
		// TODO Auto-generated method stub
		Iterator<T> it = iterator();
		while (it.hasNext()) {
			if (arg0.equals(it.next()))
				return true;
		}
		return false;
	}

	@Override
	public int drainTo(Collection<? super T> arg0) {
		// TODO Auto-generated method stub
		// метод не поддерживается
		return 0;
	}

	@Override
	public int drainTo(Collection<? super T> arg0, int arg1) {
		// TODO Auto-generated method stub
		// метод не поддерживается
		return 0;
	}

	@Override
	public boolean offer(T arg0) { // добовляем в хвост
		if (size == capacity)
			return false;
		addToQueue(arg0);
		return true;
	}

	@Override
	public boolean offer(T arg0, long arg1, TimeUnit arg2) throws InterruptedException {
		// TODO Auto-generated method stub
		// метод не поддерживается
		return false;
	}

	@Override
	public T poll(long arg0, TimeUnit arg1) throws InterruptedException {

		// метод не поддерживается
		return null;
	}

	@Override
	public synchronized void put(T arg0) throws InterruptedException {
		while (size == capacity) {
			this.wait();
		}
		this.notify();
		addToQueue(arg0);
	}

	private synchronized void addToQueue(Object object) { // добавляем в конец
															// очереди, общий
															// метод для put(),
															// add(), offer()
		Node addNode = new Node(object);
		if (size == 0) {
			firstNode = addNode;
			lastNode = addNode;
			size++;
			return;
		}
		lastNode.nextNode = addNode;
		addNode.previousNode = lastNode;
		lastNode = addNode;
		size++;
	}

	@Override
	public int remainingCapacity() {
		return capacity - size;
	}

	@Override
	public boolean remove(Object arg0) {
		Node current = firstNode;
		boolean del = false;
		while (current.nextNode != null) {
			if (arg0.equals(current.object)) {
				if (current.previousNode != null) {
					current.previousNode.nextNode = current.nextNode;
					current.nextNode.previousNode = current.previousNode;
					size--;
					del = true;
				}
			}
		}
		if (del)
			return true;
		else
			throw new NoSuchElementException();
	}

	@Override
	public synchronized T take() throws InterruptedException {
		while (size == 0)
			this.wait();

		this.notify();
		return retrieveItems();
	}

}
