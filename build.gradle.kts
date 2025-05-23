plugins {
	java
	id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "io.github.joeweh"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// DB Connection Pool Impl
	implementation("com.zaxxer:HikariCP:6.3.0")

	// HikariCP Metrics
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation ("io.micrometer:micrometer-core")
	implementation ("io.micrometer:micrometer-registry-prometheus") // Optional

	implementation("org.springframework.boot:spring-boot-starter-mail")
	implementation("org.springframework.boot:spring-boot-starter-web")
	runtimeOnly("com.mysql:mysql-connector-j")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
