package com.demo

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonTokenId
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer
import com.fasterxml.jackson.databind.type.LogicalType
import java.math.BigDecimal

@JsonSerialize(using = MoneySerializer::class)
@JsonDeserialize(using = MoneyDeserializer::class)
class Money(val amount: BigDecimal) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Money) return false
        return amount.compareTo(other.amount) == 0
    }

    override fun hashCode(): Int {
        return amount.hashCode()
    }

    override fun toString(): String {
        return "Money(amount=$amount)"
    }
}

class MoneySerializer : JsonSerializer<Money>() {
    override fun serialize(value: Money, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeNumber(value.amount)
    }
}

class MoneyDeserializer : StdScalarDeserializer<Money>(BigDecimal::class.java) {
    override fun getEmptyValue(ctxt: DeserializationContext?): Any {
        return BigDecimal.ZERO
    }

    override fun logicalType(): LogicalType {
        return LogicalType.Float
    }

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Money {
        return when (p.currentTokenId()) {
            JsonTokenId.ID_NUMBER_INT, JsonTokenId.ID_NUMBER_FLOAT -> p.decimalValue
            JsonTokenId.ID_STRING -> p.text
            JsonTokenId.ID_START_OBJECT -> ctxt.extractScalarFromObject(p, this, _valueClass)
            JsonTokenId.ID_START_ARRAY -> _deserializeFromArray(p, ctxt)
            else -> {
                ctxt.handleUnexpectedToken(getValueType(ctxt), p)
            }
        }.let {
            when (it) {
                is BigDecimal -> Money(it)
                is String -> {
                    Money(BigDecimal(it))
                }
                else -> {
                    getNullValue(ctxt)
                }
            }
        }
    }
}
