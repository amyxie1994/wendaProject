/**
 * Created by amyxie in 2018
 * MultiThreadTest.java
 * 25 Feb. 2018
 */
package com.example.snsProject;

import org.apache.ibatis.javassist.expr.NewArray;
import org.mockito.asm.tree.IntInsnNode;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;




/**
 * @author amyxie
 *
 */

class Consumer implements Runnable{

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	
	private BlockingQueue< String > q;
	
	public Consumer(BlockingQueue q) {
		this.q = q;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			
			while(true) {
				System.out.println(Thread.currentThread().getName()+":"+q.take());
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}


class Producer implements Runnable{

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	
	private BlockingQueue<String> q;
	
	public Producer(BlockingQueue<String> q) {
		this.q = q;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			
			
			for(int i =0;i<10;i++) {
				Thread.sleep(100);
				q.put(String.valueOf(i));
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}


class MyThread extends Thread{
	private int tid;
	
	public MyThread(int tid) {
		this.tid = tid;
	}
	
	@Override
	public void run() {
		
		try {
			
			for(int i = 0;i<10;i++) {
				sleep(1000);
				System.out.println(String.format("Thread %d:%d",this.tid,i));
			}
				
		}
		catch(Exception e) {
				e.printStackTrace();
		}
	}
}


public class MultiThreadTest {

	
	public static void testThread() {
		
		for(int i = 0;i<10;i++) {
			MyThread mThread = new MyThread(i);
			mThread.start();
		}
		
	}
	
	
	public static void testThread2() {
		for(int i = 0;i<10;i++) {
			final int finalI = i;
			new Thread (new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							
							for(int i = 0;i<10;i++) {
								Thread.sleep(1000);
								System.out.println(String.format("Thread %d:%d",finalI,i));
							}
								
						}
						catch(Exception e) {
								e.printStackTrace();
						}
						
					}
				}
				
				
			).start();
		}
		
	}
	
	private static Object obj = new Object();
	public static void synchronizedThread1() {
		synchronized(obj) {
			for(int i = 0;i<10;i++) {
				  try {
						Thread.sleep(1000);
						System.out.println(String.format("T3:%d", i));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
	}
	
	
	public static void synchronizedThread2() {
		synchronized(new Object()) {
			for(int i = 0;i<10;i++) {
			    try {
					Thread.sleep(1000);
					System.out.println(String.format("T4:%d", i));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	
	public static void testSynchronized() {
		for(int i = 0;i<10;i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					synchronizedThread1();
					synchronizedThread2();
				}
			}).start();
		}
	}
	
	public static void testBlockingQueue() {
		BlockingQueue<String> q = new ArrayBlockingQueue<>(10);
		new Thread(new Producer(q)).start();
        new Thread(new Consumer(q), "Consumer1").start();
        new Thread(new Consumer(q), "Consumer2").start();
	}
	 private static ThreadLocal<Integer> threadLocalUserIds = new ThreadLocal<>();
	 private static int userId;
	public static void testThreadLocal() {
		
		for(int i=0;i<10;i++) {
			final int FINALI = i;
			new Thread(new Runnable() {
				
				@Override
				public void run() {
				try {
					threadLocalUserIds.set(FINALI);
					//userId = FINALI;
					Thread.sleep(100);
					//System.out.println(String.format("%d ",userId));
					
					System.out.println("Thread"+threadLocalUserIds.get());
					// TODO Auto-generated method stub
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
					
				}
			}).start();
		}
	}
	
	
	public static void testExecutor() {
		ExecutorService service = Executors.newFixedThreadPool(2);
		service.submit(new Runnable() {
			
			@Override
			public void run() {
				
				for(int i = 0;i<10;i++) {
				    try {
						Thread.sleep(100);
						System.out.println(String.format("T4:%d", i));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				// TODO Auto-generated method stub
				
			}
		});
		
		service.submit(new Runnable() {
			
			@Override
			public void run() {
				
				for(int i = 0;i<10;i++) {
				    try {
						Thread.sleep(100);
						System.out.println(String.format("T5:%d", i));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				// TODO Auto-generated method stub
				
			}
		});
		
		service.shutdown();
		
		while(!service.isTerminated()) {
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("wait for termination");
		}
		
		

		
	}
	
	
	private static int count=0;
	private static AtomicInteger atomCount  = new AtomicInteger(0);
	public static void testWithoutAutomic() {
		
		for(int j = 0;j<10;j++) {
			new Thread(new Runnable() {

			@Override
			public void run() {
				for(int i = 0;i<10;i++) {
				    try {
						Thread.sleep(100);
						System.out.println(String.format("%d", count));
						count++;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
			}
		}).start();
		
	}	
	}
	
	
	public static void testWithAutomic() {
		
		for(int j = 0;j<10;j++) {
			new Thread(new Runnable() {

			@Override
			public void run() {
				for(int i = 0;i<10;i++) {
				    try {
						Thread.sleep(100);
						System.out.println(String.format("%d", atomCount.incrementAndGet()));
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
			}
		}).start();
		
	}	
	}
	
	public static void testFuture() {
		ExecutorService service = Executors.newSingleThreadExecutor();
		Future<Integer> future = service.submit(new Callable<Integer>() {
			
			

			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				return 1;
			}
		});
		
		service.shutdown();
		try {
			System.out.println(future.get());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//testThread2();
		//testSynchronized();
		
		//testBlockingQueue();
		//testThreadLocal();
		//testExecutor();
		//testWithAutomic();
		
		testFuture();
	}
}
