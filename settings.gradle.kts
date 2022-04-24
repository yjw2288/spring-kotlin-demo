rootProject.name = "spring-kotlin-demo"

include (
	"db:db-core",

	"domain:sample",

	"web:web-service",
	"web:web-controller",
)

pluginManagement {
	val kotlinVersion: String by settings
	val springBootVersion: String by settings
	val dependencyManagementVersion: String by settings
	val asciiDoctorConverterVersion: String by settings
	val kotlinterVersion: String by settings

	resolutionStrategy {
		eachPlugin {
			when(requested.id.id) {
				"org.jetbrains.kotlin.jvm" -> useVersion(kotlinVersion)
				"org.jetbrains.kotlin.kapt" -> useVersion(kotlinVersion)
				"org.jetbrains.kotlin.plugin.spring" -> useVersion(kotlinVersion)
				"org.jetbrains.kotlin.plugin.jpa" -> useVersion(kotlinVersion)
				"org.jetbrains.kotlin.plugin.allopen" -> useVersion(kotlinVersion)
				"org.springframework.boot" -> useVersion(springBootVersion)
				"io.spring.dependency-management" -> useVersion(dependencyManagementVersion)
				"org.asciidoctor.jvm.convert" -> useVersion(asciiDoctorConverterVersion)
				"org.jmailen.kotlinter" -> useVersion(kotlinterVersion)
			}
		}
	}

}
