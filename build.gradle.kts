plugins {
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.spring") version "2.2.21"
    id("org.springframework.boot") version "4.0.7"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.jpa") version "2.2.21"
    id("jacoco")
    id("org.jlleitschuh.gradle.ktlint") version "14.2.0"
}

group = "com.marcioos"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-flyway")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.flywaydb:flyway-database-postgresql")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("tools.jackson.module:jackson-module-kotlin")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
    testImplementation("org.springframework.boot:spring-boot-starter-flyway-test")
    testImplementation("org.springframework.boot:spring-boot-starter-validation-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.mockito.kotlin:mockito-kotlin:6.3.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.testcontainers:testcontainers-junit-jupiter")
    testImplementation("org.testcontainers:testcontainers-postgresql")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    jvmArgs("-XX:+EnableDynamicAgentLoading")
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.check {
    dependsOn(tasks.jacocoTestCoverageVerification)
}

afterEvaluate {
    tasks.jacocoTestReport {
        reports {
            xml.required.set(true)
            html.required.set(true)
        }
        classDirectories.setFrom(
            files(
                classDirectories.files.map {
                    fileTree(it) {
                        exclude(coverageExcludes)
                    }
                },
            ),
        )
    }

    tasks.jacocoTestCoverageVerification {
        violationRules {
            rule {
                limit {
                    minimum = "0.80".toBigDecimal()
                }
            }
        }
        classDirectories.setFrom(
            files(
                classDirectories.files.map {
                    fileTree(it) {
                        exclude(coverageExcludes)
                    }
                },
            ),
        )
    }
}

val coverageExcludes =
    listOf(
        "**/config/**",
        "**/*Application*",
        "**/*Dto*",
        "**/Bet.class",
        "**/StakeAllocation.class",
        "**/BetStatus.class",
        "**/DateRange.class",
        "**/Math*",
        "**/Sportsbook.class",
        "**/Outcome.class",
        "**/MoneylineOutcome.class",
        "**/Market.class",
        "**/MoneylineMarket.class",
        "**/Sport.class",
        "**/League.class",
        "**/Season.class",
        "**/Competitor.class",
    )
