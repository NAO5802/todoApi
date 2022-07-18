import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.rohanprabhu.gradle.plugins.kdjooq.*

plugins {
	id("org.springframework.boot") version "2.7.1"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
    id("com.rohanprabhu.kotlin-dsl-jooq") version "0.4.6"
}

group = "com.example.todoApi"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_13

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-jooq")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("com.ninja-squad:springmockk:3.1.1")
	runtimeOnly("org.postgresql:postgresql:42.3.2")
    jooqGeneratorRuntime("org.postgresql:postgresql:42.3.2")
}

jooqGenerator {
    configuration("primary", project.the<SourceSetContainer>()["main"]) {
        configuration = jooqCodegenConfiguration {
            jdbc {
                username = "postgres"
                password = "docker"
                driver = "org.postgresql.Driver"
                url = "jdbc:postgresql://localhost:5433/todo"
            }

            generator {
                target {
                    packageName = "com.example.todoApi.todoApi.driver.gen"
                    directory = "${project.rootDir}/src/main/kotlin"
                }

                database {
                    name = "org.jooq.meta.postgres.PostgresDatabase"
                    inputSchema = "public"
                }
            }
        }
    }
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "13"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
