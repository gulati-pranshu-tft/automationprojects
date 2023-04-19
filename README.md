# GenixVentures-Automation

# General Overview

Page Object Model framework in selenium using Java as scripting language. Maven is used for dependency management and
continuous development. TestNG is used to maintain test cases.

JAVA - Scripting Language

Selenium - Browser automation framework

Maven - Dependency management

TestNG - Testing framework

ExtentReports - Library used for reporting

Mozilla Sops - Encryption and Decryption

## File Structure

`reports` - Test Execution reports with date and time are available in `reports` folder in html format. They can be
viewed easily with any browser.

`script-resources` - This folder includes important information regarding URLs and drivers for multiple browsers.

`Src` - This folder including a main folder which is further divided into a java and resources folder which contains all
the code used to execute the tests.

`main/java` - This is the main directory which contains all the implementation code for executing the tests. It contains
a package named as `com.genixVentures` which is further divided into several packages.

* config.properties - This package contains `SEOConfig` file containing xpath values for different elements of each
  domain and `TestConfig` file containing test configuration information like mailing and test waits.

* Core - This is the package where TestCore.java is placed which contains the main code which is used before the
  execution of tests starts like initializing the driver instance or killing the driver instance after the tests are
  executed.

* Pages - Page classes consist of page functions and xpaths for that particular domain.

* Services - This package contains classes `ApiUtil` containing function for fetching response code of a page
  and `MongoDB` class containing db utility functions.

* Testcases.smokes - Test Cases are maintained in the `testcases` folder which leverage page functionalities stored
  in `pages`.

* Utils - This folder consists of utilities/helper files which has generic methods designed to help with scripting.

`resources` - This folder includes xml suite files that are used to run the test cases. To run any domain test cases
there is a specific xml file from the domain name so that user can easily run the tests directly using it
`Pom.xml` - contains all the dependencies used in the project to run the tests.

## Software to install

There are few software's required to install to run the tests on local setup.

1. We can use any IDE which supports java development but currently we are using `Intellij`, find out how to
   install [here](https://www.jetbrains.com/idea/download/#section=windows).
2. Chrome browser
3. Chrome browser driver, find out how to install [here](https://chromedriver.chromium.org/downloads).
4. JDK, link for downloading is [here](https://www.oracle.com/java/technologies/downloads/).
5. Apache Maven, link for downloading is [here](https://www.oracle.com/java/technologies/downloads/).
6. GIT bash, link for downloading is [here](https://git-scm.com/downloads).
7. GoLang, link for downloading is [here](https://go.dev/dl/)
8. GCloud, link for downloading is [here](https://cloud.google.com/sdk/docs/install)
9. Mozilla Sops, link for downloading is [here](https://github.com/mozilla/sops/releases)

**NOTE** - You have to download chrome driver version according to chrome browser version installed in your machine.

## Local Set-Up

### GCP Access
1. Obtain access to GCP.
2. Follow the steps provided in Mozilla SOPS [documentation](https://github.com/mozilla/sops#stable-release) for encrypting using GCP KMS, but do not create a new key or key ring.
3. Following are the commands to run from the above documentation:
   * `$ gcloud auth login` to login to GCP KMS
   * `$ gcloud auth application-default login` enable application default credentials using the sdk
   * To get the list of available keys:
    ```shell
    $ gcloud kms keys list --location global --keyring sops

    # you should see
    NAME                                                                   PURPOSE          PRIMARY_STATE
    projects/my-project/locations/global/keyRings/sops/cryptoKeys/sops-key ENCRYPT_DECRYPT  ENABLED
    ```
4. New users can be assigned specific roles by the GCP Project owner.

### Java Installation / Update

Check your system to see if you have the latest Java version installed.

* Command:

    ```shell
    $ java –version
    ```

If you do not have the latest Java installed, find out how to install
Java [here](https://www.java.com/en/download/help/download_options.xml).

Ensure your JAVA_HOME environment to the location of the installed JDK. Find out how to do
that [here](https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t/).

### Setting up Maven

1. Download Maven [here](https://maven.apache.org/download.cgi).

2. Unzip the distribution archive to the directory you wish to install Maven.
3. Add Maven to the `PATH`. More information can be found in the `README.txt` in the zip folder.
4. Verify Maven was correctly installed.

   Command:

    ```shell
    $ mvn –version
    ```

Maven dependencies contains key references to libraries that a Maven project needs to execute. The `pom.xml` in the root
of a Maven project file stores the dependencies (similar to the `package.json` for Node and `gemfile` for Ruby) for a
project.

## After Cloning
1. Clone the repository.
2. fetch submodule after first clone of the repo
`git submodule update --init --recursive`
`./toolbox/local_hooks_setup.sh`
3. Check if hooks from sub-modules re copied to local `.git/hooks`. If not, copy the hooks from the `hooks/` directory of the repository to your local `.git/hooks` folder.

## How to Run
### Running tests on Grid Chrome Headless
mvn clean test -DgridUrl=GRIDURL -Dbrowser=browsername for eg
mvn clean test -DgridUrl=http://localhost:4444 -Dbrowser=chrome

### Running tests on Local machine
1. change the value of deviceName from 'grid' to 'Desktop' on line number `201` of TestCore.java.
2. create a credentials.properties file in root folder with your credentials according to credentials.properties.template file.

## Steps to add a new test for a new domain

1. Add a new class in `pages` package starting with the name of domain abbreviation. For instance - 'home page' domain
   there is a class named as `CBLAHomePage` in the pages folder.
2. Add all the xpath elements and necessary functions required to perform several operations on the domain in that
   class.
3. Add a new test class in `testcases.smokes` folder starting with domain name like 'EpicName' which clearly indicates
   that this file is related to that Epic.
4. Add a new xml file in the `resources` folder starting with the domain name to run the tests.

## Steps to add a new test in existing domain

1. Add any extra functions if required for the test in the domain specific class.
2. Add a new test class in `testcases.smokes` folder starting with the domain name.

## Commit Rules

1. The encrypted `data.properties.enc` file will reside in the repository, not the decrypted `data.properties` file. NEVER COMMIT the `data.properties` file.
2. You can modify any file without worrying about the `data.properties` file as it is `.gitignore` and will never be committed. Use regular Git commands to execute all necessary actions.
3. If you want to commit ONLY changes to `data.properties`, execute an empty commit using `git commit --allow-empty -m 'Your Message'`. This will encrypt the `data.properties` changes and commit the `data.properties.enc` file.
4. If someone removes `data.properties` from the `.gitignore`, the hooks will prevent it from being committed by providing an error message.

## Codding standard to be followed

1. Follow three tier architecture
2. Add comments in al the test steps
3. Do not create any locator instead of page file
4. Add more and more reusable functions
5. Add logging statements in the report
6. Run suite with invocation count 3 to be merge in master
7. Take care of indentation
