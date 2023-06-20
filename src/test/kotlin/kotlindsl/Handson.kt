package kotlindsl

import io.kotest.matchers.shouldBe
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class HandsOnDslTest {
    @ValueSource(strings = ["홍길동", "남상윤"])
    @ParameterizedTest
    fun introduce(name: String) {
        val person: Person2 = omg {
            name(name)
        }
        person.name shouldBe name
    }

    @ValueSource(strings = ["회사1", "회사2"])
    @ParameterizedTest
    fun company(company: String) {
        val person: Person2 = omg {
            name("이름")
            company(company)
        }
        person.company shouldBe company
    }
}

fun omg(block: Person2.() -> Unit): Person2 {
    return Person2().apply(block)
}

class Person2 {
    lateinit var name: String
    var company: String? = null

    fun name(value: String) {
        name = value
    }

    fun company(value: String) {
        company = value
    }
}
