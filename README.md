Skraper
========
~~Here should be some modern logo~~

[![Apache License 2](https://img.shields.io/badge/license-ASF2-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.txt)
[![](https://jitpack.io/v/sokomishalov/skraper.svg)](https://jitpack.io/#sokomishalov/skraper)

## Overview
Kotlin/JVM coroutine-based scrapers without any authorization and full page rendering

## Scrapers
List of implemented scrapers looks like this so far:
- [Reddit](https://www.reddit.com)
- [Facebook](https://www.facebook.com)
- [Instagram](https://www.instagram.com)
- [Twitter](https://twitter.com)
- [YouTube](https://youtube.com)
- [9gag](https://9gag.com)
- [Pinterest](https://www.pinterest.com)
- [VK](https://vk.com)
- [IFunny](https://ifunny.co)

## Usage
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
<dependencies>
    <dependency>
        <groupId>com.github.sokomishalov.skraper</groupId>
        <artifactId>skrapers</artifactId>
        <version>${skraper.version}</version>
    </dependency>
</dependencies>
```

Each scraper is a class which implements [Skraper](skraper-core/src/main/kotlin/ru/sokomishalov/skraper/Skraper.kt) interface:
```kotlin
interface Skraper {
    val baseUrl: String
    val client: SkraperClient get() = DefaultBlockingSkraperClient
    suspend fun getLatestPosts(uri: String, limit: Int = 100): List<Post>
    suspend fun getPageLogoUrl(uri: String, imageSize: ImageSize = ImageSize.SMALL): String?
    suspend fun getLogoUrl(imageSize: ImageSize = ImageSize.SMALL): String? = "${baseUrl}/favicon.ico"
}
```

Then you you are able to use provider like this:
```kotlin
fun main() = runBlocking {
    val skraper = FacebookSkraper()
    
    val posts = skraper.getLatestPosts(uri = "originaltrollfootball")
    posts.forEach { println(it) }
    
    val logo = skraper.getPageLogoUrl(uri = "originaltrollfootball")
    println(logo)
}
```
You can see the full model structure for posts and others [here](skraper-core/src/main/kotlin/ru/sokomishalov/skraper/model)

**Important moment:** it is highly recommended not to use [DefaultBlockingClient](skraper-core/src/main/kotlin/ru/sokomishalov/skraper/client/jdk/DefaultBlockingSkraperClient.kt).
There are some more efficient, non-blocking and resource-friendly implementations for [SkraperClient](skraper-core/src/main/kotlin/ru/sokomishalov/skraper/SkraperClient.kt).
To use them you just have to put required dependencies in the classpath.
After that usage as simple as is:
```kotlin
val skraper = FacebookSkraper(client = ReactorNettySkraperClient())
``` 

Current http-client implementation list:
- [DefaultBlockingClient](skraper-core/src/main/kotlin/ru/sokomishalov/skraper/client/jdk/DefaultBlockingSkraperClient.kt) - simple java.net.* blocking api implementation
- [ReactorNettySkraperClient](skraper-core/src/main/kotlin/ru/sokomishalov/skraper/client/reactornetty/ReactorNettySkraperClient.kt) - [reactor netty](https://mvnrepository.com/artifact/io.projectreactor.netty/reactor-netty) implementation
- [OkHttp3SkraperClient](skraper-core/src/main/kotlin/ru/sokomishalov/skraper/client/okhttp3/OkHttp3SkraperClient.kt) - [okhttp3](https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp) implementation
- [SpringReactiveSkraperClient](skraper-core/src/main/kotlin/ru/sokomishalov/skraper/client/spring/SpringReactiveSkraperClient.kt) - [spring webclient](https://mvnrepository.com/artifact/org.springframework/spring-webflux) implementation
- [KtorSkraperClient](skraper-core/src/main/kotlin/ru/sokomishalov/skraper/client/ktor/KtorSkraperClient.kt) - [ktor client jvm](https://mvnrepository.com/artifact/io.ktor/ktor-client-core-jvm) implementation
