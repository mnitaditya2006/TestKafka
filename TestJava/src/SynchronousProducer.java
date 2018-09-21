import java.util.*;
import org.apache.kafka.clients.producer.*;
public class SynchronousProducer {

    public static void main(String[] args) throws Exception{

        String topicName = "SynchronousProducerTopic";
        String key = "Key-1";
        String value = "Value-1";

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092,localhost:9093");
        props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");



        ProducerRecord<String, String> record =  null;

        for (int i=0; i<100 ; i++) {

            Producer<String, String> producer = new KafkaProducer<>(props);

            key = "Key-"+i;
            value = "Value-"+i ;
           record =  new ProducerRecord<>(topicName, key, value);

            try {
                RecordMetadata metadata = producer.send(record).get();
                System.out.println("Message is sent to Partition no " + metadata.partition() + " and offset " + metadata.offset());
                System.out.println("SynchronousProducer Completed with success.");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("SynchronousProducer failed with an exception");
            } finally {
                producer.close();
            }

        }
    }
}