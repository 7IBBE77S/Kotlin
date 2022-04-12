/************************************************************
 *  Name:         
 *  Date:         6/10/20
 *  Assignment:
 *  Class Number: 
 *  Description:
 ***********************************************************/


import java.util.*
import kotlin.system.exitProcess


/*
fun main() {
    val rows = 8
    val cols = 8
    val board = Array(rows) { IntArray(cols) }

    // when not 0, red (1), blue (2), or stalemate (3) has won
    var winner = 0

    var p1IsBot = false
    var p2IsBot = false

    // bot vs bot only
    var slowedGameplay = false

    fun checkInput(): Int {
        while (true) {
            try {
                return readLine()!!.toInt()
            } catch (ne: NumberFormatException) {
                println("Invalid integer, try again!")
            }
        }
    }

    fun resetBoard() {
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                board[r][c] = 0
            }
        }
        winner = 0
    }

    fun updateBoard() {
        val border = StringBuilder().append(27.toChar() + "[36m ")
        for (c in 1 until cols + 1) border.append(c).append(" ")
        border.append(27.toChar() + "[0m\n╔")
        for (c in 0 until cols * 2 - 1) border.append("═")
        border.append("╗")
        println(border.toString() + 27.toChar() + "[0m")
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                when (board[r][c]) {
                    0 -> print(27.toChar() + "[90m ." + 27.toChar() + "[0m")
                    1 -> print(27.toChar() + "[31m X" + 27.toChar() + "[0m")
                    2 -> print(27.toChar() + "[34m ●" + 27.toChar() + "[0m")
                }
            }
            if (r == rows - 1) break
            println()
        }
        border.clear()
        border.append("\n╚")
        for (c in 0 until cols * 2 - 1) border.append("═")
        border.append("╝")
        println(border.toString())
    }

    fun askColumn(num: Int) {
        while (true) {
            if (num == 1) print("Choose a column Red Team: ")
            else print("Choose a column Blue Team: ")
            val c = checkInput() - 1
            if (c in 0 until cols) {
                for (r in rows - 1 downTo 0) {
                    if (board[r][c] == 0) {
                        board[r][c] = num
                        return
                    }
                }
                println("Column is full, try again!")
            } else {
                println("Column is outside range, try again!")
            }
        }
    }

    fun checkForWinner(): Int {
        var red = 0
        var blue = 0
        var tied = 0

        // search rows
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                when (board[r][c]) {
                    0 -> {
                        red = 0
                        blue = 0
                    }
                    1 -> {
                        ++red
                        blue = 0
                        ++tied
                    }
                    2 -> {
                        red = 0
                        ++blue
                        ++tied
                    }
                }
                if (red == 4) return 1
                else if (blue == 4) return 2
            }
            red = 0
            blue = 0
        }
        if (tied == rows * cols) return 3

        // search columns
        for (c in 0 until cols) {
            for (r in 0 until rows) {
                when (board[r][c]) {
                    0 -> {
                        red = 0
                        blue = 0
                    }
                    1 -> {
                        ++red
                        blue = 0
                    }
                    2 -> {
                        red = 0
                        ++blue
                    }
                }
                if (red == 4) return 1
                else if (blue == 4) return 2
            }
            red = 0
            blue = 0
        }

        // search diagonals left-right
        for (c in 0 until cols - 3) {
            for (r in 0 until rows - 3) {
                if (board[r][c] == board[r + 1][c + 1] && board[r][c] == board[r + 2][c + 2] && board[r][c] == board[r + 3][c + 3]) {
                    if (board[r][c] == 1) return 1
                    else if (board[r][c] == 2) return 2
                }
            }
        }

        // search diagonals right-left
        for (c in 0 until cols - 3) {
            for (r in rows - 1 downTo rows - 1) {
                if (board[r][c] == board[r - 1][c + 1] && board[r][c] == board[r - 2][c + 2] && board[r][c] == board[r - 3][c + 3]) {
                    if (board[r][c] == 1) return 1
                    else if (board[r][c] == 2) return 2
                }
            }
        }
        return 0
    }

    fun botTurn(num: Int) {
        var col: Int
        val rand = Random()
        while (true) {
            col = rand.nextInt(cols)
            for (r in rows - 1 downTo 0) {
                if (board[r][col] == 0) {
                    board[r][col] = num
                    return
                }
            }
        }
    }

    fun displayClosePrompt() {
        Thread.sleep(500)
        println("Return to menu? [y/N]: ")
        if (readLine()!!.toLowerCase() == "y") {
            p1IsBot = false
            p2IsBot = false
            loop@ while (true) {
                println("Select a game option below: " +
                        "\n1 - Play against computer  " +
                        "\n2 - Play against player  " +
                        "\n3 - Watch computer against computer")

                when (checkInput()) {
                    1 -> {
                        p2IsBot = true
                        break@loop
                    }
                    2 -> break@loop
                    3 -> {
                        p1IsBot = true
                        p2IsBot = true
                        slowedGameplay = true
                        break@loop
                    }
                    else -> println("Invalid selection, try again!")
                }
            }
            resetBoard()
            updateBoard()
            var p1Turn = true
            while (winner == 0) {
                when (p1Turn) {
                    true -> {
                        if (!p1IsBot) askColumn(1)
                        else botTurn(1)
                    }
                    false -> {
                        if (!p2IsBot) askColumn(2)
                        else botTurn(2)
                    }
                }
                winner = checkForWinner()
                p1Turn = !p1Turn
                updateBoard()
                if (slowedGameplay) Thread.sleep(500)
            }
            when (winner) {
                1 -> println("Red Team is the winner!")
                3 -> println("It's the cat's game! Nobody won.")
                else -> println("Blue Team is the winner!")
            }
            displayClosePrompt()
        } else exitProcess(0)
    }

    fun playGame() {
        var p1Turn = true
        while (winner == 0) {
            when (p1Turn) {
                true -> {
                    if (!p1IsBot) askColumn(1)
                    else botTurn(1)
                }
                false -> {
                    if (!p2IsBot) askColumn(2)
                    else botTurn(2)
                }
            }
            winner = checkForWinner()
            p1Turn = !p1Turn
            updateBoard()
            if (slowedGameplay) Thread.sleep(500)
        }
        when (winner) {
            1 -> println("Red Team is the winner!")
            3 -> println("It's the cat's game! Nobody won.")
            else -> println("Blue Team is the winner!")
        }
        displayClosePrompt()
    }

    fun startGame() {
        loop@ while (true) {
            println("Select a game option below:\n1 - Play against computer  2 - Play against player  3 - Watch computer against computer")
            when (checkInput()) {
                1 -> {
                    p2IsBot = true
                    break@loop
                }
                2 -> break@loop
                3 -> {
                    p1IsBot = true
                    p2IsBot = true
                    slowedGameplay = true
                    break@loop
                }
                else -> println("Invalid selection, try again!")
            }
        }
        resetBoard()
        updateBoard()
        playGame()
    }
    startGame()
}


 */


fun main() {

    ConnectFour().run()
}
//reason for using  a class: read-ability, separation of concerns, don't-repeat-yourself.
class ConnectFour {

    companion object {
        const val rows = 8
        const val cols = 8
        val board = Array(rows) { IntArray(cols) }

        // when not 0, red (1), blue (2), or stalemate (3) has won
        var winner = 0

        var p1IsBot = false
        var p2IsBot = false

        // bot vs bot only
        var slowedGameplay = false
    }

    enum class Code(val num: Int) {
        RED(1), BLUE(2), STALEMATE(3)
    }

    fun run() {
        startGame()
    }

    private fun startGame() {
        loop@ while (true) {
            println("Select a game option below:\n1 - Play against computer  2 - Play against player  3 - Watch computer against computer")
            when (checkInput()) {
                1 -> {
                    p2IsBot = true
                    break@loop
                }
                2 -> break@loop
                3 -> {
                    p1IsBot = true
                    p2IsBot = true
                    slowedGameplay = true
                    break@loop
                }
                else -> println("Invalid selection, try again!")
            }
        }
        resetBoard()
        updateBoard()
        playGame()
    }

    private fun playGame() {
        var p1Turn = true
        while (winner == 0) {
            when (p1Turn) {
                true -> {
                    if (!p1IsBot) askColumn(Code.RED.num)
                    else botTurn(Code.RED.num)
                }
                false -> {
                    if (!p2IsBot) askColumn(Code.BLUE.num)
                    else botTurn(Code.BLUE.num)
                }
            }
            winner = checkForWinner()
            p1Turn = !p1Turn
            updateBoard()
            if (slowedGameplay) Thread.sleep(500)
        }
        when (winner) {
            Code.RED.num -> println("Red Team is the winner!")
            Code.STALEMATE.num -> println("It's the cat's game! Nobody won.")
            else -> println("Blue Team is the winner!")
        }
        displayClosePrompt()
    }

    private fun checkInput(): Int {
        while (true) {
            try {
                return readLine()!!.toInt()
            } catch (ne: NumberFormatException) {
                println("Invalid integer, try again!")
            }
        }
    }

    private fun resetBoard() {
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                board[r][c] = 0
            }
        }
        winner = 0
    }

    private fun updateBoard() {
        val border = StringBuilder().append(27.toChar() + "[36m ")
        for (c in 1 until cols + 1) border.append(c).append(" ")
        border.append(27.toChar() + "[0m\n╔")
        for (c in 0 until cols * 2 - 1) border.append("═")
        border.append("╗")
        println(border.toString() + 27.toChar() + "[0m")
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                when (board[r][c]) {
                    0 -> print(27.toChar() + "[90m ." + 27.toChar() + "[0m")
                    1 -> print(27.toChar() + "[31m X" + 27.toChar() + "[0m")
                    2 -> print(27.toChar() + "[34m ●" + 27.toChar() + "[0m")
                }
            }
            if (r == rows - 1) break
            println()
        }
        border.clear()
        border.append("\n╚")
        for (c in 0 until cols * 2 - 1) border.append("═")
        border.append("╝")
        println(border.toString())
    }

    private fun askColumn(num: Int) {
        while (true) {
            if (num == Code.RED.num) print("Choose a column Red Team: ")
            else print("Choose a column Blue Team: ")
            val c = checkInput() - 1
            if (c in 0 until cols) {
                for (r in rows - 1 downTo 0) {
                    if (board[r][c] == 0) {
                        board[r][c] = num
                        return
                    }
                }
                println("Column is full, try again!")
            } else {
                println("Column is outside range, try again!")
            }
        }
    }

    private fun checkForWinner(): Int {
        var red = 0
        var blue = 0
        var tied = 0

        // search rows
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                when (board[r][c]) {
                    0 -> {
                        red = 0
                        blue = 0
                    }
                    1 -> {
                        ++red
                        blue = 0
                        ++tied
                    }
                    2 -> {
                        red = 0
                        ++blue
                        ++tied
                    }
                }
                if (red == 4) return 1
                else if (blue == 4) return 2
            }
            red = 0
            blue = 0
        }
        if (tied == rows * cols) return 3

        // search columns
        for (c in 0 until cols) {
            for (r in 0 until rows) {
                when (board[r][c]) {
                    0 -> {
                        red = 0
                        blue = 0
                    }
                    1 -> {
                        ++red
                        blue = 0
                    }
                    2 -> {
                        red = 0
                        ++blue
                    }
                }
                if (red == 4) return 1
                else if (blue == 4) return 2
            }
            red = 0
            blue = 0
        }

        // search diagonals left-right
        for (c in 0 until cols - 3) {
            for (r in 0 until rows - 3) {
                if (board[r][c] == board[r + 1][c + 1] && board[r][c] == board[r + 2][c + 2] && board[r][c] == board[r + 3][c + 3]) {
                    if (board[r][c] == 1) return 1
                    else if (board[r][c] == 2)return 2
                }
            }
        }

        // search diagonals right-left
        for (c in 0 until cols - 3) {
            for (r in rows - 1 downTo rows - 1) {
                if (board[r][c] == board[r - 1][c + 1] && board[r][c] == board[r - 2][c + 2] && board[r][c] == board[r - 3][c + 3]) {
                    if (board[r][c] == 1) return 1
                    else if (board[r][c] == 2) return 2
                }
            }
        }
        return 0
    }

    private fun botTurn(num: Int) {
        var col: Int
        val rand = Random()
        while (true) {
            col = rand.nextInt(cols)
            for (r in rows - 1 downTo 0) {
                if (board[r][col] == 0) {
                    board[r][col] = num
                    return
                }
            }
        }
    }

    private fun displayClosePrompt() {
        Thread.sleep(2000)
        println("Return to menu? [y/N]: ")
        if (readLine()!!.toLowerCase() == "y") {
            p1IsBot = false
            p2IsBot = false
            startGame()
        } else exitProcess(0)
    }
}
