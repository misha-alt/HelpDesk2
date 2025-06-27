



# Use the Tomcat 9.0 image based on Alpine
FROM tomcat:9.0-alpine

# Copy your application WAR file to the webapps directory
COPY target/helpDesk-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/

# Set the working directory to Tomcat's bin directory
WORKDIR /usr/local/tomcat/bin

# Start Tomcat
CMD ["catalina.sh", "run"]