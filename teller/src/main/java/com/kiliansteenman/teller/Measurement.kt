package com.kiliansteenman.teller

data class Measurement(
    val framework: String,
    val type: String,
    val name: String,
    val params: Map<String, Any>
) {

    fun newBuilder(): Builder =
        Builder(framework, type, name, params.toMutableMap())

    open class Builder(
        val framework: String,
        val type: String,
        var _name: String = "",
        private val params: MutableMap<String, Any> = mutableMapOf()
    ) {

        fun setName(name: String) = this.apply { _name = name }

        fun addParam(param: Pair<String, String>) = this.apply {
            params[param.first] = param.second
        }

        fun addParam(key: String, value: String) = this.apply {
            params[key] = value
        }

        fun build() = Measurement(
            framework = framework,
            type = type,
            name = _name,
            params = params
        )
    }
}