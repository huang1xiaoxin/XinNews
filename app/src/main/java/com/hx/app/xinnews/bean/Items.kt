package com.hx.app.xinnews.bean

import java.util.*

class Items : ArrayList<Any?> {
    constructor(initialCapacity: Int) : super(initialCapacity)
    constructor() : super()
    constructor(c: MutableCollection<out Any?>) : super(c)
}
