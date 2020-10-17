# Transformers-Quora
Backend for a social Q&amp;A web-app like Quora 

Team : Sonal Sharma, Anitha Rajamuthu , Aparna , Sandeep

UserController Owner : Anitha Rajamuthu

signup - "/user/signup"
signin - "/user/signin" 
signout - "/user/signout" 

Common,Admin Controller : Sonal Sharma

<TBD>

Question Controller 

<TBD>

Answer Controller

<TBD>

====DB Installation Steps start====

##1.Install postgres and postgres admin
##2.Create DB 'quora'
##3.goto quora-db folder path and run maven command "mvn clean install -Psetup"

##rajamuthua-a01:quora-db rajamuthua$ mvn clean install -Psetup
[INFO] Scanning for projects...
[WARNING] 
[WARNING] Some problems were encountered while building the effective model for com.upgrad.quora:quora-db:jar:1.0-SNAPSHOT
[WARNING] 'dependencies.dependency.(groupId:artifactId:type:classifier)' must be unique: com.fasterxml.jackson.core:jackson-annotations:jar -> duplicate declaration of version 2.9.6 @ com.upgrad.quora:quora:1.0-SNAPSHOT, /Users/rajamuthua/Documents/STS_SCDF/Git/Transformers-Quora/pom.xml, line 26, column 21
[WARNING] 'dependencies.dependency.(groupId:artifactId:type:classifier)' must be unique: com.fasterxml.jackson.core:jackson-annotations:jar -> duplicate declaration of version 2.9.6 @ com.upgrad.quora:quora:1.0-SNAPSHOT, /Users/rajamuthua/Documents/STS_SCDF/Git/Transformers-Quora/pom.xml, line 32, column 21
[WARNING] 
[WARNING] It is highly recommended to fix these problems because they threaten the stability of your build.
[WARNING] 
[WARNING] For this reason, future Maven versions might no longer support building such malformed projects.
[WARNING] 
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] Building quora-db 1.0-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:3.0.0:clean (default-clean) @ quora-db ---
[INFO] Deleting /Users/rajamuthua/Documents/STS_SCDF/Git/Transformers-Quora/quora-db/target
[INFO] 
[INFO] --- properties-maven-plugin:1.0-alpha-2:read-project-properties (default) @ quora-db ---
[INFO] 
[INFO] --- maven-resources-plugin:2.7:resources (default-resources) @ quora-db ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 3 resources
[INFO] 
[INFO] --- sql-maven-plugin:1.5:execute (db-setup) @ quora-db ---
[INFO] Executing file: /var/folders/fb/c3t3kcwd1gq_2md6xx3kr56h0000gp/T/quora.2116121059sql
[INFO] Executing file: /var/folders/fb/c3t3kcwd1gq_2md6xx3kr56h0000gp/T/quora_test.183163189sql
[INFO] 21 of 21 SQL statements executed successfully
[INFO] 
[INFO] --- maven-compiler-plugin:3.7.0:compile (default-compile) @ quora-db ---
[INFO] No sources to compile
[INFO] 
[INFO] --- maven-resources-plugin:2.7:testResources (default-testResources) @ quora-db ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /Users/rajamuthua/Documents/STS_SCDF/Git/Transformers-Quora/quora-db/src/test/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.7.0:testCompile (default-testCompile) @ quora-db ---
[INFO] No sources to compile
[INFO] 
[INFO] --- maven-surefire-plugin:2.21.0:test (default-test) @ quora-db ---
[INFO] No tests to run.
[INFO] 
[INFO] --- maven-jar-plugin:3.0.2:jar (default-jar) @ quora-db ---
[INFO] Building jar: /Users/rajamuthua/Documents/STS_SCDF/Git/Transformers-Quora/quora-db/target/quora-db.jar
[INFO] 
[INFO] --- maven-install-plugin:2.5.2:install (default-install) @ quora-db ---
[INFO] Installing /Users/rajamuthua/Documents/STS_SCDF/Git/Transformers-Quora/quora-db/target/quora-db.jar to /Users/rajamuthua/.m2/repository/com/upgrad/quora/quora-db/1.0-SNAPSHOT/quora-db-1.0-SNAPSHOT.jar
[INFO] Installing /Users/rajamuthua/Documents/STS_SCDF/Git/Transformers-Quora/quora-db/pom.xml to /Users/rajamuthua/.m2/repository/com/upgrad/quora/quora-db/1.0-SNAPSHOT/quora-db-1.0-SNAPSHOT.pom
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 10.270 s
[INFO] Finished at: 2020-10-17T22:15:23+05:30
[INFO] Final Memory: 17M/201M
[INFO] ------------------------------------------------------------------------


====DB Installation Steps end====
