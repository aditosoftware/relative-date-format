version = "1.1.0"

plugins {
    `java-library`
    `maven-publish`
    id("org.hibernate.build.maven-repo-auth") version "3.0.0" apply false
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("org.jetbrains:annotations:19.0.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.5.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.2")
}

val test by tasks.getting(Test::class) {
    // Use junit platform for unit tests
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = "de.adito"
            artifactId = "relative-date-format"

            pom {
                name.set("Relative Date Expression")
                url.set("https://github.com/aditosoftware/relative-date-format")

                scm {
                    connection.set("https://github.com/aditosoftware/relative-date-format.git")
                    developerConnection.set("git@github.com:aditosoftware/relative-date-format.git")
                    url.set("https://github.com/aditosoftware/relative-date-format")
                }

                issueManagement {
                    url.set("https://github.com/aditosoftware/relative-date-format/issues")
                    system.set("github")
                }

                organization {
                    name.set("ADITO Software GmbH")
                    url.set("http://www.adito.de")
                }
            }
        }
        repositories {
            maven {
                name = "adito.m2"

                // If snapshot version publish it to snapshot repository
                url = if (version.toString().endsWith("-SNAPSHOT"))
                    uri("http://nexus.aditosoftware.local/repository/snapshots/")
                else
                    uri("http://nexus.aditosoftware.local/repository/releases/")

            }
        }
    }
}
