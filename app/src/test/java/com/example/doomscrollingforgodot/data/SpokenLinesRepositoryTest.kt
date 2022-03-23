package com.example.doomscrollingforgodot.data

import org.junit.Assert.assertEquals

import org.junit.Test

class SpokenLinesRepositoryTest {

    @Test
    fun `lines property exposes fulltext`() {
        val lines = SpokenLinesRepository.getTestInstance().lines.subList(0,3)
        assertEquals(
            lines,
            listOf(
                SpokenLine(
                    line= "(giving up again). Nothing to be done. ",
                    speaker = "ESTRAGON",
                ),
                SpokenLine(
                    line = "(advancing with short, stiff strides, legs wide apart). I'm beginning to come round to that opinion. All my life I've tried to put it from me, saying Vladimir, be reasonable, you haven't yet tried everything. And I resumed the struggle. (He broods, musing on the struggle. Turning to Estragon.) So there you are again. ",
                    speaker= "VLADIMIR",
                ),
                SpokenLine(
                    line= "Am I? ",
                    speaker = "ESTRAGON",
                ),
            ),
        )
    }
}