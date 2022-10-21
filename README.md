## Small application to create a CV based on xml file and photo

##### To download the required dependencies (bootstrap, jquery, moment)
###### run: 
```shell
mvn clean package
```
###### Run aplication with  parametres:
```shell
mvn spring-boot:run -D spring-boot.run.arguments=--photo=./sample/photo.jpeg,--data=./sample/cv.ru.xml
```

###### For example run: 
* build.cmd (once)
* cv.cmd --data=sample/cv.xml --photo=sample/photo.jpeg 

* The result will be located in the folder **/out** 



