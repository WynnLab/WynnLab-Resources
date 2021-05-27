package com.wynnlab.registry

abstract class Registry<T> {
    abstract val entries: List<T>
}