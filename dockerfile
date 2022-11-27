FROM osangenis/openjdk-18-go-1.16-alpine

COPY out/artifacts/printer_jar/printer.jar /printer.jar

CMD ["java", "-jar", "/printer.jar"]