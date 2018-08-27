# Sample - Arquillian - Thorntail

## Purpose of this sample

The purpose of this sample is to show you how test a REST endpoint with Arquillian and Thorntail.


## Compile and package

Being Maven centric, you can compile and package it with `mvn clean compile`, `mvn clean package` or `mvn clean install`. The `package` and `install` phase will automatically trigger the unit tests. Once you have your war file, you can deploy it.

## Execute it

Don't execute the test and just package the Uber jar with :

```
$ mvn clean install -Dmaven.test.skip=true
```

Then, if you execute the Uber Jar without any parameter it won't work (because it's looking for Consul at a wrong host/port):

```
$ $ java -jar target/sampleArquilianThorntail-thorntail.jar
```

Go to the following URLs :

* [http://localhost:8080/sampleArquilianThorntail/api/numbers/book]()

## Execute the test

The purpose of this sample is to execute unit and integration tests. So to execute it you can run :

* `mvn test` : this will execute the unit test ItemEJBTest which uses Mockito
* `mvn integration-test` : this will execute both integration tests `ItemEJBWithArquillianIT` and `ItemEJBWithoutArquillianIT`

<div class="footer">
    <span class="footerTitle"><span class="uc">a</span>ntonio <span class="uc">g</span>oncalves</span>
</div>