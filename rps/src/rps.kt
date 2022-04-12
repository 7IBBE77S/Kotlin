/************************************************************
 *  Name:         
 *  Date:         5/2/20
 *  Assignment:
 *  Class Number: 
 *  Description:
 ***********************************************************/

fun main() {

var menuItems = arrayOf(
         " Rock",
         " Paper",
         " Scissors",
         " Quit"
)
    val quitOption = menuItems.size
    var userChoice = 0
    var botChoice = 0
    var playerWins: Int = 0
    var botWins: Int = 0
    var tie: Int = 0
   var randNum = 0

    println("Welcome to Rock-Paper-Scissors!")
    println("This is a simple game of choosing one of either Rock, Paper, or " +
            "Scissors against the computer's choice")
    println("The rules are: ")
    println("  Rock beats Scissors")
    println("  Scissors beats paper")
    println("  Paper beats Rock")
    println()
    print("Would you like to play? (y or n) ")
   var option = readLine()!!.toString()



    while (userChoice != quitOption) {
        if (option == "y") {

        userChoice = menu(menuItems, "Enter your choice: ")

        val randNum = (1..3).random()


            if (option == "y" && userChoice == quitOption) {
                println()
            }else   if (userChoice != 1 && userChoice != 2 && userChoice != 3  && userChoice != 4) {
                println()
            } else {




                        when (randNum) {

                            1 -> println("Computer chose: Rock")
                            2 -> println("Computer chose: Paper")
                            3 -> println("Computer chose: Scissors")


                        }


            }
            when (userChoice) {
                1 -> println("You chose: Rock")
                2 -> println("You chose: Paper")
                3 -> println("You chose: Scissors")
                else -> {
                    if (userChoice != quitOption) {
                        println("Please select a valid menu option!")
                    } else if (userChoice == quitOption) {
                        println("Thank you for playing! Here are your results: ")
                        println()
                    }

                }
            }

        if (userChoice != botChoice) {

            if (userChoice == 1) {
                if (randNum == 2) {

                    println("You lose")
                    println("----STATS----")
                    println("Wins: $playerWins")
                    println("Losses: ${botWins++ + 1}")
                    println("Ties: $tie")
                    println()

                } else if (userChoice == randNum) {
                    println("It is a tie")
                    println("----STATS----")
                    println("Wins: $playerWins")
                    println("Losses: ${botWins}")
                    println("Ties: ${tie++ + 1}")
                    println()

                } else if (randNum == 3) {
                    println("You win")
                    println("----STATS----")
                    println("Wins: ${playerWins++ + 1}")
                    println("Losses: $botWins")
                    println("Ties: $tie")
                    println()
                }
            }
            //quit option
            else if (userChoice == quitOption){
                println("----STATS----")
                println("Wins: $playerWins")
                println("Losses: $botWins")
                println("Ties: $tie")
                println()
            }
            else if (userChoice == 3) {
                if (randNum == 1) {
                    println("You lose")
                    println("----STATS----")
                    println("Wins: $playerWins")
                    println("Losses: ${botWins++ + 1}")
                    println("Ties: $tie")
                    println()
                } else if (randNum == 2) {
                    println("You win")
                    println("----STATS----")
                    println("Wins: ${playerWins++ + 1}")
                    println("Losses: $botWins")
                    println("Ties: $tie")
                    println()
                } else if (userChoice == randNum) {
                    println("It is a tie")
                    println("----STATS----")
                    println("Wins: $playerWins")
                    println("Losses: $botWins")
                    println("Ties: ${tie++ + 1}")
                    println()

                }
            } else if (userChoice == 2) {
                if (randNum == 3) {
                    println("You lose")
                    println("----STATS----")
                    println("Wins: $playerWins")
                    println("Losses: ${botWins++ + 1}")
                    println("Ties: $tie")
                    println()
                } else if (randNum == 1) {
                    println("You win")
                    println("----STATS----")
                    println("Wins: ${playerWins++ + 1}")
                    println("Losses: $botWins")
                    println("Ties: $tie")
                    println()

                } else if (userChoice == randNum) {
                    println("It is a tie")
                    println("----STATS----")
                    println("Wins: $playerWins")
                    println("Losses: $botWins")
                    println("Ties: ${tie++ + 1}")
                    println()

                }

            }
        }


    }else if (option == "n") {
          userChoice = 4
            println("Sorry you didn't want to play with me!")
        }else {


            println("Would you like to play? (Y or N)")
             option = readLine().toString()
        }
}


}


fun menu( items: Array<String>, prompt : String ) : Int {

    for ( (index, item) in items.withIndex()) {
        println("${index + 1}.${item}")
    }


    print(prompt)
    println()
    return readLine()!!.toInt()

}