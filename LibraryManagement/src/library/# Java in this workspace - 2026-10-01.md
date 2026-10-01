# Java in this workspace - 2026-10-01

Oracle JDK License Gate read 0 files here against 18 checks. Everything below was read on this machine; nothing was sent anywhere.

## 1. Oracle-licensed Java pulls on 2026-10-01: 0

None of the files read pulls an Oracle-licensed Java build.

## 2. Where this workspace chooses its Java: 0 places

No file here names a Java vendor (setup-java distribution, Docker base image, Gradle or Maven toolchain vendor, SDKMAN, asdf, dev container). The build then uses whatever JDK the machine or the CI runner happens to have.

## 3. Java on this machine

No JAVA_HOME is set and no Java runtime is configured in the VS Code settings, so there is nothing to read here.

## 4. The free line for each place

| Place | Eclipse Temurin | Amazon Corretto |
| --- | --- | --- |
| GitHub Actions setup-java | `distribution: temurin` | `distribution: corretto` |
| Docker base image | `FROM eclipse-temurin:21-jdk` | `FROM amazoncorretto:21` |
| Gradle toolchain | `vendor = JvmVendorSpec.ADOPTIUM` | `vendor = JvmVendorSpec.AMAZON` |
| SDKMAN (.sdkmanrc) | `java=<version>-tem` | `java=<version>-amzn` |
| asdf / mise (.tool-versions) | `java temurin-<version>` | `java corretto-<version>` |
| Dev container | `"jdkDistro": "tem"` | `"jdkDistro": "amzn"` |
| This machine (Windows) | `winget install EclipseAdoptium.Temurin.21.JDK` |  |
| This machine (macOS) | `brew install --cask temurin@21` |  |
| This machine (SDKMAN) | `sdk install java <version>-tem` | `sdk install java <version>-amzn` |

Oracle's No-Fee Terms cover a Java LTS release until one year after the next LTS ships: the free window for JDK 21 closed on 2026-09-16, for JDK 17 on 2024-09-19. OpenJDK builds such as Temurin and Corretto are not under those terms.

---
This map is free and stays on this machine. The written workspace report (every line, as a file for your team, an auditor or CI) is the paid part: run `Oracle JDK License Gate: Sweep workspace and write report`.