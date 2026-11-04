# DevOps Lab - Quote API
 
A minimal REST service used across all five DevOps experiments.
 
Version: 0.3.0-dev
 
## Endpoints
- `GET /quote` returns a random quote
- `GET /health` returns OK

---

## Build & Test Instructions

### Building with Gradle
```bash
./gradlew clean build
./gradlew test
```

### Building with Maven
```bash
mvn clean package
mvn test
```

---

## Experiment 4: Continuous Integration with Jenkins

This experiment demonstrates setting up automated Continuous Integration (CI) pipelines using Jenkins and the Jenkins CLI.

### 1. Installation on Ubuntu
```bash
# Step 1: Update system packages
sudo apt update && sudo apt upgrade -y

# Step 2: Install Java (Java 17/11 required for Jenkins)
sudo apt install openjdk-17-jdk -y
java -version

# Step 3: Add Jenkins repository and key
curl -fsSL https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key | sudo tee /etc/apt/keyrings/jenkins-keyring.asc > /dev/null
echo 'deb [signed-by=/etc/apt/keyrings/jenkins-keyring.asc] https://pkg.jenkins.io/debian-stable binary/' | sudo tee /etc/apt/sources.list.d/jenkins.list > /dev/null
sudo apt update

# Step 4: Install Jenkins
sudo apt install jenkins -y
```

### 2. Service Management
```bash
sudo systemctl start jenkins
sudo systemctl enable jenkins
sudo systemctl status jenkins
```
Access the dashboard at `http://localhost:8080` (or `http://<server-ip>:8080`).

### 3. Unlock Jenkins & Initial Password
```bash
sudo cat /var/lib/jenkins/secrets/initialAdminPassword
```

### 4. Jenkins CLI Setup
Download the Jenkins CLI jar:
```bash
wget http://localhost:8080/jnlpJars/jenkins-cli.jar
```

General CLI Syntax:
```bash
java -jar jenkins-cli.jar -s http://localhost:8080/ <command> [options]
```

### 5. Common CLI Commands for CI
- **Check Jenkins version:**
  ```bash
  java -jar jenkins-cli.jar -s http://localhost:8080/ version
  ```
- **List all jobs:**
  ```bash
  java -jar jenkins-cli.jar -s http://localhost:8080/ list-jobs
  ```
- **Create a job from XML:**
  ```bash
  java -jar jenkins-cli.jar -s http://localhost:8080/ create-job pipeline-job < jenkins/pipeline-job.xml
  java -jar jenkins-cli.jar -s http://localhost:8080/ create-job freestyle-job < jenkins/freestyle-job.xml
  ```
- **Build a job:**
  ```bash
  java -jar jenkins-cli.jar -s http://localhost:8080/ build pipeline-job
  ```
- **View console output:**
  ```bash
  java -jar jenkins-cli.jar -s http://localhost:8080/ console pipeline-job 1
  ```
- **Manage plugins:**
  ```bash
  java -jar jenkins-cli.jar -s http://localhost:8080/ install-plugin git
  java -jar jenkins-cli.jar -s http://localhost:8080/ safe-restart
  ```
- **Manage slave nodes:**
  ```bash
  java -jar jenkins-cli.jar -s http://localhost:8080/ create-node slave1 < jenkins/slave-node.xml
  java -jar jenkins-cli.jar -s http://localhost:8080/ list-nodes
  ```

### 6. Pipeline Definition (`Jenkinsfile`)
A declarative pipeline (`Jenkinsfile`) is provided in the repository root containing:
- **Checkout**: Pulls the repository source code.
- **Compile**: Compiles the Java Spring Boot application.
- **Test**: Executes unit test suite.
- **Package**: Produces executable JAR artifacts.
