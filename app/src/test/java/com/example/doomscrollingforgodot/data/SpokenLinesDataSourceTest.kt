package com.example.doomscrollingforgodot.data

import org.junit.Assert.assertEquals
import org.junit.Test

class SpokenLinesDataSourceTest {

    @Test
    fun `consume() parses list of SpokenLines from resource file`() {
        assertEquals(
            SpokenLinesDataSource.getTestInstance().consume(),
            listOf(
                SpokenLine(line = "Let's go.", speaker = "ESTRAGON"),
                SpokenLine(line = "We can't.", speaker = "VLADIMIR"),
                SpokenLine(line = "Why not?", speaker = "ESTRAGON"),
                SpokenLine(line = "We're waiting for Godot.", speaker = "VLADIMIR"),
            ),
        )
    }
}