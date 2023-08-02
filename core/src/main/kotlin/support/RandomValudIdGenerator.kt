package support

import org.apache.commons.lang3.RandomStringUtils
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.IdentifierGenerator
import java.io.Serializable

class RandomValueIdGenerator : IdentifierGenerator {
    override fun generate(session: SharedSessionContractImplementor?, `object`: Any?): Serializable {
        return RandomStringUtils.randomAlphanumeric(ID_LENGTH)
    }

    companion object {
        const val ID_LENGTH = 8
    }
}