/**
 * Copyright 2019-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.sokomishalov.skraper

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import ru.sokomishalov.skraper.internal.net.openStreamForRedirectable
import java.io.InputStream
import java.net.URL

/**
 * @author sokomishalov
 */
interface SkraperClient {

    suspend fun fetch(url: String): ByteArray? = withContext(IO) { openStream(url) }.use { it?.readBytes() }

    suspend fun openStream(url: String): InputStream? = withContext(IO) { URL(url).openStreamForRedirectable() }

}