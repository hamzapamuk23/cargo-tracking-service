FROM ubuntu:latest

RUN apt-get update && apt-get install -y wget curl unzip gnupg2

# Install OpenJDK 17
RUN apt-get update && apt-get install -y openjdk-17-jdk

# Install Chrome browser
RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add -
RUN echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list
RUN apt-get update && apt-get install -y google-chrome-stable=92.0.4515.107

# Install ChromeDriver
ARG CHROME_DRIVER_VERSION=92.0.4515.107
RUN wget -q https://chromedriver.storage.googleapis.com/$CHROME_DRIVER_VERSION/chromedriver_linux64.zip && \
    unzip chromedriver_linux64.zip && \
    chmod +x chromedriver && \
    mv chromedriver /usr/local/bin/chromedriver

# Set display port to avoid crash
ENV DISPLAY=:99

# Install dependencies
RUN apt-get update && apt-get install -y libglib2.0-0 libnss3 libgconf-2-4 libfontconfig1

WORKDIR /app
COPY target/cargo-tracking-service-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-Dwebdriver.chrome.driver=/usr/local/bin/chromedriver", "-jar", "app.jar"]
