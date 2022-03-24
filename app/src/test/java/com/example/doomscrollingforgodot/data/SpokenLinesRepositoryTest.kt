package com.example.doomscrollingforgodot.data

import org.junit.Assert.assertEquals

import org.junit.Test

class SpokenLinesRepositoryTest {

    @Test
    fun `lines property exposes fulltext`() {
        assertEquals(
            SpokenLinesRepository.getTestInstance().lines,
            listOf(
                SpokenLine(line = "Let's go.", speaker = "ESTRAGON"),
                SpokenLine(line = "We can't.", speaker = "VLADIMIR"),
                SpokenLine(line = "Why not?", speaker = "ESTRAGON"),
                SpokenLine(line = "We're waiting for Godot.", speaker = "VLADIMIR"),
            ),
        )
    }
}