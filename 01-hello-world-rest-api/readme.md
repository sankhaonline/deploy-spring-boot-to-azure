# Hello World Rest API

### Running the Application

Run com.in28minutes.rest.webservices.restfulwebservices.RestfulWebServicesApplication as a Java Application.

- http://localhost:8080/hello-world

```txt
Hello World
```

- http://localhost:8080/hello-world-bean

```json
{"message":"Hello World"}
```

- http://localhost:8080/hello-world/path-variable/in28minutes

```json
{"message":"Hello World, in28minutes"}
```

### Plugin configuration

```
<plugin>
	<groupId>com.microsoft.azure</groupId>
    <artifactId>azure-webapp-maven-plugin</artifactId>
    <version>2.12.0</version>
</plugin>
```

### Azure CLI

- https://docs.microsoft.com/en-us/cli/azure/install-azure-cli?view=azure-cli-latest

### Final Plugin Configuration

```
<plugin>
    <groupId>com.microsoft.azure</groupId>
    <artifactId>azure-webapp-maven-plugin</artifactId>
    <version>2.12.0</version>
    <configuration>
        <schemaVersion>v2</schemaVersion>
        <resourceGroup>hello-world-rest-api-rg</resourceGroup>
        <appName>hello-world-rest-api-sankhaonline</appName>
        <pricingTier>F1</pricingTier>
        <region>westeurope</region>
        <appSettings>
            <property>
                <name>JAVA_OPTS</name>
                <value>-Dserver.port=80</value>
            </property>
        </appSettings>
        <runtime>
            <os>Linux</os>
            <javaVersion>Java 17</javaVersion>
            <webContainer>Java SE</webContainer>
        </runtime>
        <deployment>
            <resources>
                <resource>
                    <directory>${project.basedir}/target</directory>
                    <includes>
                        <include>*.jar</include>
                    </includes>
                </resource>
            </resources>
        </deployment>
    </configuration>
</plugin>

```

az webapp log tail --name hello-world-rest-api-sankhaonline --resource-group hello-world-rest-api-rg

### Logging Configuration

```
-Dlogging.level.org.springframework=DEBUG
```