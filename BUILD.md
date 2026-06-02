# Build & Run Instructions

## Prerequisites

- **Java 11+** (JDK, not just JRE — `javac` must be available)
- **Maven 3.6+**
- **DynamoDB** — either AWS-hosted or local via Docker

## Environment Variables

| Variable | Description | Example |
|---|---|---|
| `DM_DEV` | Enable dev mode (`true`/`false`) | `false` |
| `DM_BANKDETS` | Bank payment details string | — |
| `DM_LOCK` | Lock registrations (`true`/`false`) | `false` |

## Building

```bash
# Standard build (skips tests)
mvn clean package -DskipTests

# Production build (optimised Vaadin frontend)
mvn clean package -Pproduction

# Run tests
mvn test
```

The output JAR is at `target/downsman-4.3-SNAPSHOT.jar`.

## Running

### Local Development

1. Start a local DynamoDB:

```bash
docker run -v ~/tmp/data:/data -p 8000:8000 amazon/dynamodb-local \
  -jar DynamoDBLocal.jar -sharedDb -dbPath /data
```

2. Update `src/main/resources/application.yml` — comment out the SSL block and set port to 8080.

3. Create tables (first run only):

```bash
./scripts/createTables.sh
```

4. Run the app:

```bash
export DM_DEV=true
java -jar target/downsman-4.3-SNAPSHOT.jar
```

5. Open http://localhost:8080

### Production (Lightsail)

1. Set up an iptables redirect so the app can bind to an unprivileged port:

```bash
sudo iptables -A PREROUTING -t nat -p tcp --dport 443 -j REDIRECT --to-port 1443
```

2. Ensure `~/keystore.p12` exists with a valid TLS certificate.

3. Run the app:

```bash
export DM_DEV=false
export DM_BANKDETS="<payment details>"
export DM_LOCK=false
java -jar downsman-4.3-SNAPSHOT.jar
```

4. Open port 443 in the Lightsail firewall.

## DynamoDB Admin (optional)

To browse the local DynamoDB:

```bash
npm install -g dynamodb-admin
export DYNAMO_ENDPOINT=http://localhost:8000
export AWS_REGION=us-east-1
dynamodb-admin
```

Then open http://localhost:8001.

## TLS Certificate Renewal

Generate a new cert with Let's Encrypt DNS challenge:

```bash
sudo certbot -d signup.downsman.com --manual --preferred-challenges dns certonly
```

Convert to PKCS12 keystore:

```bash
sudo openssl pkcs12 -export \
  -in /etc/letsencrypt/live/signup.downsman.com/fullchain.pem \
  -inkey /etc/letsencrypt/live/signup.downsman.com/privkey.pem \
  -out keystore.p12 -name downsman -CAfile chain.pem -caname root
```

Copy `keystore.p12` to the home directory on the server.
