
# introduction

# Guide to the code

This software uses AWS to for infrastructure 

## DB Setup

Assuming an empty database to create the tables run the code in services.CreateDynamoTables
Register a user using the UI wonce the app starts up.

If you want to make this user an admin then login to the DynamoDB database look in the users table 
Change the admin field from 0 to 1.

## To prepare for a release

Update the version number in the pom.xml     

`<version>1.13-SNAPSHOT</version>`

Rebuild and rerun from inside intellij will find and use the new WAR file (and will update the target URI)
However this is not a deployable target as it still has all the vardin development stuff in it.

Create an elastic beanstalk instance with a tomcat target,  upload new war ../target/

## Running dynamoDB locally

`docker pull amazon/dynamodb-local`

`docker run -p 8000:8000 amazon/dynamodb-local`

Check things are connected 

`aws dynamodb list-tables --endpoint-url http://localhost:8000`

To run the app against this local store update the client in services/DatabaseService

 `client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder
                .EndpointConfiguration("http://localhost:8000","us-east-1"))
                .build();`
                
 to return to normal comment out everything after .stanard() again to put tables in run CreateDynamoTables per above
 to check tables exists run the commmand 
 
 `$ aws dynamodb list-tables --endpoint-url http://localhost:8000 --region="us-east-1"`
 
 To use the admin browser to see data run the following:
 
 `npm install dynamodb-admin -g
  export DYNAMO_ENDPOINT=http://localhost:8000
  dynamodb-admin
`

Found from https://github.com/aaronshaf/dynamodb-admin note you will need to update the region

`export AWS_REGION="us-east-1"`

## Persistant local storage

`$ docker run -v ~/tmp/data:/data -p 8000:8000 amazon/dynamodb-local -jar DynamoDBLocal.jar -sharedDb -dbPath /data`

THis will run dyn in a mode that puts its storage into local file system so will remain past reboots.

## Create ECS cluster ready for recieving our docker image
$ ecs-cli up --force --keypair SJMBP --capability-iam --size 2 --instance-type t2.medium --cluster-config downsman

Create security group
aws ec2 create-security-group --group-name my-ecs-sg --description my-ecs-sg

##2021 installation Log

updage version     
`<version>4.0-SNAPSHOT</version>` 

in POM

`cd /Users/scottejames/IdeaProjects/DownsmanV6 ; mvn clean package -Pproduction`

Dev mode controlled by env variable DM_DEV; lets get it running locally.  Install local dynamoDB
Run this up with persistant storage :

`docker run -v ~/tmp/data:/data -p 8000:8000 amazon/dynamodb-local -jar DynamoDBLocal.jar -sharedDb -dbPath /data`

Validate its running and list tables

`aws dynamodb list-tables --endpoint-url http://localhost:8000 --region="us-east-1"`

No tables WOOT.  Create tables there are some handy dandy scripts `...\scripts` firstly
rebuild the classpath to ensure that the scripts can see the build files `./updateClasspath.sh"
` to build tables `./createTables.sh` and now we are pro note there is `./listTables.sh`

to start `java -jar .../target/downsman-4.0-SNAPSHOT.jar`

point browser at `http://localhost:8080/` and login

To look in the database. install admin tool `npm install -g dynamodb-admin` (-g means its installed globally)
tell it to connect to local end point `export DYNAMO_ENDPOINT=http://localhost:8000` the run it
`dynamodb-admin` connect to web 'http://localhost:8001/tables/User' to see the users you have created woot all working.

Now lets do it ALL again but using docker.

There is a docker file in scripts

```
FROM openjdk:11
MAINTAINER scottejames@gmail.com
ENV DM_DEV false

RUN mkdir /usr/src/myapp
COPY ./target/downsman-4.0-SNAPSHOT.jar /usr/src/myapp
WORKDIR /usr/src/myapp

CMD ["java", "-jar", "downsman-4.0-SNAPSHOT.jar"]
```

login to dockerhub `docker login` and then run the script (from the scripts dir).  It will rebuild
the docker imagine and then push to docker hub.  NOTE this does NOT contrain your AWS credentials this is
important!

##Tinker with lightsail

install docker `sudo yum install docker; sudo service docker start'
and then add user to docker group `sudo usermod -aG docker scottejames

Switch to user and run up downsman 
`docker login`
`docker run scottejames/downsman:4.0`

Ok at this point you will realise that building docker images on an M1 mmac cant run on amazon sooooo.
this helps : https://blog.jaimyn.dev/how-to-build-multi-architecture-docker-images-on-an-m1-mac/

`docker buildx ls` will show you the current builder instances you have create a new builder instance (so we can
build for more than one arch at at time `docker buildx create --use`)

Lets check all is well by kicking off a build by hand:

`docker buildx build --platform linux/amd64,linux/arm64 -f scripts/Dockerfile -t scott/downsman:4.0 .`

This will take a bit the first time as it pulls down moby build kit.

Then to build : 

`docker buildx build --platform linux/amd64,linux/arm64 -f scripts/Dockerfile --push -t scottejames/downsman:4.1 .`

(make sure you login to docker hub first!)

Login to your AWS host and then 

'docker run -it -p 80:8080 -v $HOME/.aws:/root/.aws:ro scottejames/downsman:4.1'

NOTE this assumes that /root is home and that you have prepopulated your .aws dir on lightsail.

NOW .. how to https!

instructions here gets me a cert https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/SSL-on-amazon-linux-2.html#letsencrypt

moved server.xml into jar but server is not using it.

## Getting a cert working

ok this was a lot harder than it should be.   firstly we need some certificates.  Lets encrypt are favourate.   You will 
need to proove you have control over the domain.   Now note certs expire in a few months so the approach i am about to 
describe is totally the wrong way.    Use a technique called DNS challenge.  This allows you to create certs anywhere.

install certbot using homebrew 'brew install certbot' then run:

` sudo certbot -d signup.downsman.com --manual --preferred-challenges dns certonly`

This will ultimatly ask you to set a TXT record in word press go do that:

acme-challenge.signup.downsman.com. wait a second click go and you will have shiny certs on your local machine.  Setup DNS to point
signup.downsman.com to 127.0.0.1 this is BAD DONT DO THIS (except its ok for the use case i have).  Better option would be 
a self signed cert that is trusted.  i will investigate this next but for now.   HACKING.

Server will be different, come to that in a bit.   It will be different so we can renew the certs.

Right so it seems that the server does not want pems so need to convert to a p12 key store as root:

`openssl pkcs12 -export -in fullchain.pem -inkey privkey.pem -out keystore.p12 -name downsman -CAfile chain.pem -caname root`

This needs to be put in .../src/main/resources/ create file in same dir keystore.yml:

server:
  ssl:
    key-store: classpath:keystore/keystore.p12
    key-store-password: knot8gen
    key-store-type: pkcs12
    enabled=true:
  port: 443

SO now to release prod build

`mvn clean package -Pproduction`
`docker buildx build --platform linux/amd64,linux/arm64 -f scripts/Dockerfile --push -t scottejames/downsman:4.2 .`


This pushes a new to docker hub goto server to run and run 'docker run -it -p 443:443 -v $HOME/.aws:/root/.aws:ro scottejames/downsman:4.2` all is well


Dont forget to insert the  -e DM_BANKDETS to indicate where to pay.