package libpapercube.desktop.kotlin

inline fun <T> lazyInit(noinline objSupplier: () -> T, crossinline init: T.() -> Unit): Lazy<T> {
    return lazy { objSupplier().with(init) }
}

inline fun <T> lazyInit(obj: T, crossinline init: T.() -> Unit): Lazy<T> {
    return lazy { obj.with(init) }
}

inline fun <T> T.with(init: T.() -> Unit): T {
    this.init()
    return this
}