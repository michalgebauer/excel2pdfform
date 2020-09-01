Requires JDK 14

[Gradle plugin](https://badass-runtime-plugin.beryx.org/releases/latest/)
```gradle
plugins {
    id 'org.beryx.runtime' version '1.1.3'
}
```

Create fat-jar before running jdeps in order to be able to locate and analyse external dependencies.
```gradle
plugins {
    id 'com.github.johnrengelman.shadow' version '6.0.0'
}
```

```bash
alias jdeps='pathToJDK14/bin/jdeps'
```

Find out which modules are needed in custom runtime
```bash
jdeps -s excel2pdfform/build/lib/excel2pdfform-all.jar
```

```gradle
runtime {
    modules = [
            'java.base',
            'java.desktop',
            'java.logging',
            'java.naming',
            'java.security.jgss',
            'java.sql',
            'java.xml',
            'java.xml.crypto']
}
```

```bash
gradle clean build runtimeZip
```

```bash
excel2pdfform/build/image/bin/java -jar ../lib/excel2pdfform-all.jar
```