package ba.unsa.sportevents.sealed

sealed class DataState {
    class Success(val data: MutableList<kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType.Object>) : DataState()
    class Failure(val message: String) : DataState()
    object Loading : DataState()
    object Empty : DataState()
}