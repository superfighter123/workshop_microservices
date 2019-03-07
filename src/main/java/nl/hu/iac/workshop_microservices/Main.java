package nl.hu.iac.workshop_microservices;

public class Main {

	public static void main(String[] args) throws Exception {
		Producer producer = new Producer();
		producer.create();
		
		Consumer consumer1 = new Consumer();
		consumer1.create("consumer1");
		
		Consumer consumer2 = new Consumer();
		consumer2.create("consumer2");
		
		producer.sendMessage("Dit is een test.");
		
		System.out.println("Consumer1 received: \"" + consumer1.getMessage(1000) + "\"");
		System.out.println("Consumer2 received: \"" + consumer2.getMessage(1000) + "\"");
	}

}
