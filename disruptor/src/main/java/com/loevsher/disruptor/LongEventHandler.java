package com.loevsher.disruptor;
import com.lmax.disruptor.EventHandler;

public class LongEventHandler implements EventHandler<LongEvent>
{
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch)
    {
        System.out.println("Event: " + event.getValue());
        try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
    }
}