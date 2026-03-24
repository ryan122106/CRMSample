package com.example.crm.app.miniExercise

class FizzBuzzExercise {

    /**
     * Generate FizzBuzz result from 1 to n
     *
     * Rules:
     * - Multiple of 3 → "Fizz"
     * - Multiple of 5 → "Buzz"
     * - Multiple of both → "FizzBuzz"
     */
    fun generateFizzBuzz(n: Int): List<String> {
        if(n <= 0) throw IllegalArgumentException()
        val result = mutableListOf<String>()

        for (i in 1..n) {
            when {
                i % 3 == 0 && i % 5 == 0 -> result.add("FizzBuzz")
                i % 3 == 0 -> result.add("Fizz")
                i % 5 == 0 -> result.add("Buzz")
                else -> result.add(i.toString())
            }
        }
        return result
    }
}