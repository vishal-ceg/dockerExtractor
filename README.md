# docker's image extractor
1. Clone this repo
2. Update docker credential in config.properties
3. mvn clean package
4. java -jar target/dockerExtractor-1.0-SNAPSHOT-jar-with-dependencies.jar docker-image-name
5. Note- We can see the extracted info in folder target/docker-image-name

