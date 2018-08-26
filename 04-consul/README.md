# Sample - Arquilian - Consul

## Purpose of this sample

The purpose of this sample is to show you how test a REST endpoint that needs Consul to register. So this sample shows:

* Integration testing the REST endpoint and mocking Consul with Mockito
* Integration testing the REST endpoint with Consul up and running

[Read more on my blog](http://agoncal.wordpress.com/2012/01/16/wytiwyr-what-you-test-is-what-you-run/)

## Compile and package

Being Maven centric, you can compile and package it with `mvn clean compile`, `mvn clean package` or `mvn clean install`. The `package` and `install` phase will automatically trigger the unit tests. Once you have your war file, you can deploy it.

## Execute it

Don't execute the test and just package the Uber jar with :

```
$ mvn clean install -Dmaven.test.skip=true
```

Then, if you execute the Uber Jar without any parameter it won't work (because it's looking for Consul at a wrong host/port):

```
$ java -jar target/sampleArquilianConsul-thorntail.jar
...
Caused by: javax.ejb.EJBException: com.orbitz.consul.ConsulException: Error connecting to Consul
```

What you have to do is to pass parameters as follow to setup the correct Consul host and port:

```
$ $ java -DCONSUL_HOST=http://localhost -DCONSUL_PORT=8500 -jar target/sampleArquilianConsul-thorntail.jar 
```

Go to the following URLs :

* [http://localhost:8080/sampleArquilianConsul/api/numbers/book]()
* [http://localhost:8080/sampleArquilianConsul/api/numbers/health]()
* [http://localhost:8500/ui/dc1/services/CONSUL_NUMBER_RESOURCE]()

## Execute the test

The purpose of this sample is to execute unit and integration tests. So to execute it you can run :

* `mvn test` : this will execute the unit test ItemEJBTest which uses Mockito
* `mvn integration-test` : this will execute both integration tests `ItemEJBWithArquillianIT` and `ItemEJBWithoutArquillianIT`

<div class="footer">
    <span class="footerTitle"><span class="uc">a</span>ntonio <span class="uc">g</span>oncalves</span>
</div>