/************************************************************
 *  Name:         
 *  Date:         12/4/20
 *  Assignment:
 *  Class Number: 
 *  Description:
 ***********************************************************/

import java.io.File
import java.lang.Exception
import java.util.*

fun main() {

    var puzzle = Puzzle(30, 30, "words.txt")

    puzzle.createPuzzle();
    println(puzzle.displayPuzzleKey());
    println(puzzle.displayWordList());
    println(puzzle.displayPuzzle());
    println(puzzle.displayWordList());
    println(puzzle.time());

    // ----------------------------------------------------
    File("puzzle.txt").printWriter().use { out ->
        out.write(puzzle.displayPuzzle());
        out.write("\n");
        out.write(puzzle.displayWordList());
    }
    File("puzzleKey.txt").printWriter().use { out ->
        out.write(puzzle.puzzleKey);
        out.write("\n");
        out.write(puzzle.displayWordList());
    }

    // ----------------------------------------------------
}

class Puzzle(var rows: Int, var cols: Int, var fileName: String) {
    var puzzleKey = "";
    val grid = Array(rows) { CharArray(cols) }
    val wordsList = mutableListOf<String>()
    var sortedwordsList = mutableListOf<String>()
    fun createPuzzle() {
        populateBlank(grid)
        val bufferedReader = File(fileName).bufferedReader()
        bufferedReader.useLines { lines -> lines.forEach { wordsList.add(it.toUpperCase()) } }

        sortedwordsList.addAll(wordsList)
        sortedwordsList.sortByDescending { it.length }
        sortedwordsList.forEach {
            placeWord(grid, it);
        }
    }
    fun time(){
        val start = System.currentTimeMillis()
        for( i in 1..10) {
            var puzzle = Puzzle(30, 30, "words.txt")
            puzzle.createPuzzle()
        }
        var totalTime = System.currentTimeMillis() - start
        println( "Total Time to create 10 puzzles: $totalTime milli-seconds" )
        println( "Average Time to create 1 puzzle: ${totalTime / 10} milli-seconds" )
    }
    fun displayPuzzleKey(): String {
        puzzleKey = printMatrix();
        return puzzleKey;
    }

    fun setEqualWidth(input: String, maxCharacters: Int): String {
        var finalWord = input;
        var wordLength = input.length;
        var spacesCount = maxCharacters - wordLength;
        for (i in 0..spacesCount) {
            finalWord += " ";
        }
        return finalWord;
    }

    fun displayWordList(): String {
        var builder = java.lang.StringBuilder();
        builder.append("Find The Following ${wordsList.size} Words:\n\n")
        var counter = 0;
        builder.append("\t\t");
        wordsList.forEach {
            builder.append(setEqualWidth(it, 12));

            builder.append("\t\t");
            ++counter;
            if ((counter % 3) == 0) {
                builder.append("\n");
                builder.append("\t\t");

            }
        }
        return builder.toString();
    }

    fun displayPuzzle(): String {
        populateForPuzzle(grid);
        return printMatrix()
    }

    fun getRandomDirection(list: List<Direction>): Direction {
        val rand = Random()
        return list[rand.nextInt(list.size)]
    }

    fun getRandomCharForPuzzle(): Char {
        val charList = mutableListOf<Char>()
        val rand = Random()
        wordsList.forEach {
            it.toCharArray().forEach {
                if (it.equals(' ') == false && it.isLetter()) {
                    charList.add(it)
                }
            }
        }

        return charList[rand.nextInt(charList.size)]
    }

    enum class Direction {
        NORTH, SOUTH, EAST, WEST, NE, SE, NW, SW
    }

    fun placeWord(grid: Array<CharArray>, _word: String) {
        var word = _word
        var directionsList = arrayListOf<Direction>()
        directionsList.addAll(
            listOf(
                Direction.NORTH,
                Direction.SOUTH,
                Direction.EAST,
                Direction.WEST,
                Direction.NE,
                Direction.SE,
                Direction.NW,
                Direction.SW,
            )
        )

        val direction = getRandomDirection(directionsList);

        when {
            direction.equals(Direction.WEST) ->
                placeWordInWest(grid, word, reverseIt = false)
            direction.equals(Direction.EAST) -> {
                placeWordInWest(grid, word, reverseIt = true);
            }
            direction.equals(Direction.SOUTH) -> {
                placeWordInSouth(grid, word, reverseIt = false)
            }
            direction.equals(Direction.NORTH) -> {
                placeWordInSouth(grid, word, reverseIt = true)
            }
            direction.equals(Direction.NW) -> {
                placeWordDiagonalLeft(grid, word, reverseIt = false)
            }
            direction.equals(Direction.SE) -> {
                placeWordDiagonalLeft(grid, word, reverseIt = true)
            }
            direction.equals(Direction.NE) -> {
                placeWordDiagonalRight(grid, word, reverseIt = false)
            }
            direction.equals(Direction.SW) -> {
                placeWordInWest(grid, word, reverseIt = true)
            }
        }
    }

    private fun placeWordDiagonalLeft(grid: Array<CharArray>, word: String, reverseIt: Boolean = false) {
        val loc = randomLocationInGrid
        val row = loc.row
        val col = loc.col
        if (testPlaceDiagonalLeft(grid, word, loc, reverseIt) == false) {
            placeWordDiagonalLeft(grid, word, reverseIt)
            return
        }
        if (reverseIt == false) {
            for (i in 0 until word.length) {
                grid[row - i][col - i] = word[i]
            }
        } else {
            for (i in 0 until word.length) {
                grid[row + i][col + i] = word[i]
            }
        }
    }

    private fun placeWordDiagonalRight(grid: Array<CharArray>, word: String, reverseIt: Boolean = false) {
        val loc = randomLocationInGrid
        val row = loc.row
        val col = loc.col
        if (testPlaceDiagonalRight(grid, word, loc, reverseIt) == false) {
            placeWordDiagonalRight(grid, word, reverseIt)
            return
        }
        if (reverseIt == false) {
            for (i in 0 until word.length) {
                grid[row - i][col + i] = word[i]
            }
        } else {
            for (i in 0 until word.length) {
                grid[row + i][col - i] = word[i]
            }
        }
    }

    private fun testPlaceDiagonalLeft(
        grid: Array<CharArray>,
        word: String,
        loc: Location,
        reverseIt: Boolean = false
    ): Boolean {
        val row = loc.row
        val col = loc.col
        for (i in 0 until word.length) {
            try {
                if (reverseIt == false) {
                    if (col - i >= grid[col].size && row - i >= grid[row].size) {
                        return false
                    } else if (grid[row - i][col - i] != '.') {
                        val charAtLocation = grid[row - i][col - i]
                        val expectedForOverlap = word[i]
                        if (charAtLocation != expectedForOverlap) {
                            return false
                        }
                    }
                } else {
                    if (col + i >= grid[col].size && row + i >= grid[row].size) {
                        return false
                    } else if (grid[row + i][col + i] != '.') {
                        val charAtLocation = grid[row - i][col - i]
                        val expectedForOverlap = word[i]
                        if (charAtLocation != expectedForOverlap) {
                            return false
                        }
                    }
                }
            } catch (e: Exception) {
                return false
            }
        }
        return true
    }

    private fun testPlaceDiagonalRight(
        grid: Array<CharArray>,
        word: String,
        loc: Location,
        reverseIt: Boolean = false
    ): Boolean {
        val row = loc.row
        val col = loc.col
        for (i in 0 until word.length) {
            try {
                if (reverseIt == false) {
                    if (col + i >= grid[col].size && row - i >= grid[row].size) {
                        return false
                    } else if (grid[row - i][col + i] != '.') {
                        val charAtLocation = grid[row - i][col + i]
                        val expectedForOverlap = word[i]
                        if (charAtLocation != expectedForOverlap) {
                            return false
                        }
                    }
                } else {
                    if (col - i >= grid[col].size && row + i >= grid[row].size) {
                        return false
                    } else if (grid[row + i][col - i] != '.') {
                        val charAtLocation = grid[row + i][col - i]
                        val expectedForOverlap = word[i]
                        if (charAtLocation != expectedForOverlap) {
                            return false
                        }
                    }
                }
            } catch (e: Exception) {
                return false
            }
        }
        return true
    }

    private fun placeWordInWest(grid: Array<CharArray>, word: String, reverseIt: Boolean = false) {
        val loc = randomLocationInGrid
        val row = loc.row
        val col = loc.col
        if (testPlaceHorizontally(grid, word, loc, reverseIt) == false) {
            placeWordInWest(grid, word, reverseIt)
            return
        }
        if (reverseIt == false) {
            for (i in 0 until word.length) {
                grid[row][col + i] = word[i]
            }
        } else {
            for (i in 0 until word.length) {
                grid[row][col - i] = word[i]
            }
        }
    }

    private fun testPlaceVertically(
        grid: Array<CharArray>, word: String, loc: Location, reverseIt: Boolean = false
    ): Boolean {
        val row = loc.row
        val col = loc.col
        try {
            if (reverseIt == false) {
                for (i in 0 until word.length) {
                    if (row + i >= grid[row].size) {
                        return false
                    } else if (grid[row + i][col] != '.') {
                        val charAtLocation = grid[row + i][col]
                        val expectedForOverlap = word[i]
                        if (charAtLocation != expectedForOverlap) {
                            return false
                        }
                    }
                }
            } else {
                for (i in 0 until word.length) {
                    if (row - i >= grid[row].size) {
                        return false
                    } else if (grid[row - i][col] != '.') {
                        val charAtLocation = grid[row - i][col]
                        val expectedForOverlap = word[i]
                        if (charAtLocation != expectedForOverlap) {
                            return false
                        }
                    }
                }
            }
        } catch (e: Exception) {
            return false;
        }
        return true
    }

    private fun testPlaceHorizontally(
        grid: Array<CharArray>,
        word: String,
        loc: Location,
        reverseIt: Boolean = false
    ): Boolean {
        val row = loc.row
        val col = loc.col
        if (reverseIt == false) {
            try {
                for (i in 0 until word.length) {
                    if (col + i >= grid[col].size) {
                        return false
                    } else if (grid[row][col + i] != '.') {
                        val charAtLocation = grid[row][col + i]
                        val expectedForOverlap = word[i]
                        if (charAtLocation != expectedForOverlap) {
                            return false
                        }
                    }
                }
            } catch (e: Exception) {
                return false;
            }
        } else {
            try {
                for (i in 0 until word.length) {
                    if (col - i >= grid[col].size) {
                        return false
                    } else if (grid[row][col - i] != '.') {
                        val charAtLocation = grid[row][col - i]
                        val expectedForOverlap = word[i]
                        if (charAtLocation != expectedForOverlap) {
                            return false
                        }
                    }
                }
            } catch (e: Exception) {
                return false;
            }
        }
        return true
    }

    private fun placeWordInSouth(grid: Array<CharArray>, word: String, reverseIt: Boolean = false) {
        val loc = randomLocationInGrid
        val row = loc.row
        val col = loc.col
        if (testPlaceVertically(grid, word, loc, reverseIt) == false) {
            placeWordInSouth(grid, word, reverseIt)
            return
        }
        if (reverseIt == false) {
            for (i in 0 until word.length) {
                grid[row + i][col] = word[i]
                //printMatrix(grid);
            }
        } else {
            for (i in 0 until word.length) {
                grid[row - i][col] = word[i]
                //printMatrix(grid);
            }
        }
    }

    private val randomLocationInGrid: Location
        private get() {
            val rand = Random()
            val row = rand.nextInt(rows)
            val col = rand.nextInt(cols)
            val location = Location()
            location.row = row
            location.col = col
            return location
        }

    private fun populateBlank(grid: Array<CharArray>) {
        for (r in grid.indices) {
            for (c in 0 until grid[r].size) {
                grid[r][c] = '.'
            }
        }
    }

    private fun populateForPuzzle(grid: Array<CharArray>) {
        for (r in grid.indices) {
            for (c in 0 until grid[r].size) {
                if (grid[r][c] == '.' || grid[r][c] == ' ') {
                    grid[r][c] = getRandomCharForPuzzle()
                }
            }
        }
    }

    fun printMatrix(): String {
        var builder: StringBuilder = java.lang.StringBuilder();
        for (r in grid.indices) {
            for (c in 0 until grid[r].size) {
                builder.append(grid[r][c].toString() + " ")
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    internal class Location {
        var row = 0
        var col = 0
    }


}





