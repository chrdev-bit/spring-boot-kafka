<h3>Spring-Boot with Kafka</h3>

This application can run multiple Spring-Boot servers that integrate with Kafka messaging to check the used heap.
<p>
To run, start-up Kafka (on port 9092) then as a 4 node cluster for example:
<p>
java -Dserver.port=8090 -jar target/spring-boot-kafka-1.jar<br/>
java -Dserver.port=8091 -jar target/spring-boot-kafka-1.jar<br/>
java -Dserver.port=8092 -jar target/spring-boot-kafka-1.jar<br/>
java -Dserver.port=8093 -jar target/spring-boot-kafka-1.jar<br/>
<p>
Visit http://localhost:8090/status (or 8091,8092 etc.) and you should see status updates of the cluster you're running.
