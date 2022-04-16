package practice.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.concurrent.ExecutionException;

public class main {
    public static void main(String[] args) {
        // runs producer and consumer with 2 diff threads parellely.
        main m = new main();
        m.threadProducer();
        m.threadConsumer();
    }

    public void threadConsumer() {
        Thread t = new Thread(() -> {
            runConsumer();
        });
        t.start();
    }

    public void threadProducer() {
        Thread t = new Thread(() -> {
            runProducer();
        });
        t.start();
    }

    void runConsumer() {
        Consumer<Long, String> consumer = ConsumerCreator.createConsumer();

        int noMessageFound = 0;

        while (true) {
            ConsumerRecords<Long, String> consumerRecords = consumer.poll(100);
            // 1000 is the time in milliseconds consumer will wait if no record is found at broker.
            if (consumerRecords.count() == 0) {
                noMessageFound++;
                if (noMessageFound > IKafkaConstants.MAX_NO_MESSAGE_FOUND_COUNT)
                // If no message found count is reached to threshold exit loop.
                {
                    break;
                } else {
                    continue;
                }
            }

            //print each record.
            consumerRecords.forEach(record -> {
                System.out.println("Consumer Record Key " + record.key());
                System.out.println("Consumer Record value " + record.value());
                System.out.println("Consumer Record partition " + record.partition());
                System.out.println("Consumer Record offset " + record.offset());
            });

            // commits the offset of record to broker.
            consumer.commitAsync();
        }
        consumer.close();
    }

    void runProducer() {
        Producer<Long, String> producer = ProducerCreator.createProducer();

        for (int index = 0; index < IKafkaConstants.MESSAGE_COUNT; index++) {
            try {
//                Thread.sleep(100);
                ProducerRecord<Long, String> record = new ProducerRecord<>(IKafkaConstants.TOPIC_NAME,
                        "This is record " + index);
                RecordMetadata metadata = producer.send(record).get();
                System.out.println("Producer Record sent with key " + index + " to partition " + metadata.partition()
                        + " with offset " + metadata.offset());
            } catch (ExecutionException | InterruptedException e) {
                System.out.println("Error in sending record");
                System.out.println(e);
            }
        }
    }
}
