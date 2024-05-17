plugins {
    id("java")
    id("jacoco") // Add JaCoCo plugin
    id("com.github.kt3k.coveralls") version "2.12.2"

}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

// Configure JaCoCo
configure<JacocoPluginExtension> {
    toolVersion = "0.8.7" // Use desired JaCoCo version
}

// Create code coverage check task
val customJacocoTestReport = tasks.register<JacocoReport>("customJacocoTestReport") {
    dependsOn("test")
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco"))
    }
}


tasks.named("check") {
    dependsOn(customJacocoTestReport)
}