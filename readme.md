<h3>Spring-Boot with Kafka</h3>

This application can run multiple Spring-Boot servers that integrate with Kafka messaging to check the used heap.

To run, start-up Kafka (on port 8092) then as a 4 node cluster for example:

java -Dserver.port=8090 -jar target/spring-boot-kafka-1.jar
java -Dserver.port=8091 -jar target/spring-boot-kafka-1.jar
java -Dserver.port=8092 -jar target/spring-boot-kafka-1.jar
java -Dserver.port=8093 -jar target/spring-boot-kafka-1.jar

Visit http://localhost:8090/status (or 8091,8092 etc) and you should see status updates of the cluster you're running.
