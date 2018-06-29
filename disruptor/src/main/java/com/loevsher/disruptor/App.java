package com.loevsher.disruptor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.InsufficientCapacityException;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		EventFactory<LongEvent> eventFactory = new LongEventFactory();
		ExecutorService executor = Executors.newSingleThreadExecutor();
		int ringBufferSize = 2; // RingBuffer 大小，必须是 2 的 N 次方；

		ThreadFactory threadFactory = new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r);
			}
		};
		Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(eventFactory, ringBufferSize, threadFactory);

		EventHandler<LongEvent> eventHandler = new LongEventHandler();
		disruptor.handleEventsWith(eventHandler);

		disruptor.start();

		for (int i = 0; i < 10000; i++) {
			publishEvent2(disruptor);
		}

	}

	private static int index = 0;

	private static long getEventData() {
		return index++;
	}

	static class Translator implements EventTranslatorOneArg<LongEvent, Long> {
		@Override
		public void translateTo(LongEvent event, long sequence, Long data) {
			event.setValue(data);
		}
	}

	public static Translator TRANSLATOR = new Translator();

	public static void publishEvent2(Disruptor<LongEvent> disruptor) {
		// 发布事件；
		RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
		System.out.println("remainingCapacity:" + ringBuffer.remainingCapacity());
		long sequence = ringBuffer.next();
		try {
			LongEvent e = ringBuffer.get(sequence);
		} finally {
			ringBuffer.publishEvent(TRANSLATOR, getEventData());
		}
	}
}
