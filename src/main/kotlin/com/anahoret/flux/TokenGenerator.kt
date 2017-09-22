package com.anahoret.flux

interface TokenGenerator<T> {
  fun createToken(): T
}

class IntTokenGenerator: TokenGenerator<Int> {
  private var lastToken = 0
  override fun createToken(): Int {
    return lastToken++
  }
}
