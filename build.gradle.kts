plugins {
    kotlin("jvm") version "1.9.22"
    id("io.papermc.paperweight.userdev") version "1.5.11"
}

group = "xyz.r2turntrue"
version = "1.0.0"

repositories {
    mavenCentral()
    //maven("https://jitpack.io")
    mavenLocal()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    compileOnly(kotlin("stdlib"))
    paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    //implementation("com.github.R2turnTrue:chzzk4j:0.0.6")
    //implementation("io.github.R2turnTrue:chzzk4j:1.0-SNAPSHOT")
    compileOnly("io.github.R2turnTrue:chzzk4j:0.0.2")
}


val shade = configurations.create("shade")
shade.extendsFrom(configurations.implementation.get())

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {

    javadoc {
        options.encoding = "UTF-8"
    }

    compileJava {
        options.encoding = "UTF-8"
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
    
    compileTestKotlin {
        kotlinOptions.jvmTarget = "17"
    }
    
    processResources {
        filesMatching("*.yml") {
            expand(project.properties)
        }
    }
    
    create<Jar>("sourceJar") {
        archiveClassifier.set("source")
        from(sourceSets["main"].allSource)
    }

    jar {
        from (shade.map { if (it.isDirectory) it else zipTree(it) })
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    assemble {
        dependsOn(reobfJar)
    }
}

