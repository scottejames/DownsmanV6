
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