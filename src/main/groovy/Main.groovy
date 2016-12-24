import groovy.sql.Sql

class Main {
    static void main(String... args) {
        def url = 'jdbc:awsathena://athena.us-east-1.amazonaws.com:443'

        def properties = new Properties();
        properties.put('s3_staging_dir', 's3://my-athena-result-bucket/test/');
        properties.put('aws_credentials_provider_class', 'com.amazonaws.auth.DefaultAWSCredentialsProviderChain');

        def driver = 'com.amazonaws.athena.jdbc.AthenaDriver';

        Sql.withInstance(url, properties, driver) { sql ->
            sql.eachRow('SELECT elbresponsecode, COUNT(*) FROM sampledb.elb_logs GROUP BY elbresponsecode') { row ->
                println("${row[0]}\t${row[1]}")
            }
        }
    }
}
