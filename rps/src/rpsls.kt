/************************************************************
 *  Name:         
 *  Date:         5/4/20
 *  Assignment:
 *  Class Number: 
 *  Description:
 ***********************************************************/
fun main() {

    var menuItems = arrayOf(
        " Rock",
        " Paper",
        " Scissors",
        " Lizard",
        " Spock",
        " Quit"
    )
    val quitOption = menuItems.size
    var captionChoice = 0
    var botChoice = 0
    var starfleetWins = 0
    var botWins = 0
    var tie = 0
    var randNum = 0


    println("Welcome to Rock-Paper-Scissors-LIZARD-SPOCK!!!")
    println("This is a simple game of choosing one of either Rock, Paper, Scissors... Lizard or Spock! against the computer's choice")
    println("The rules are: ")
    println(" Scissors cuts paper\n" +
             " Paper covers rock\n" +
             " Rock crushes lizard\n" +
             " Lizard poisons Spock\n" +
             " Spock smashes scissors\n" +
             " Scissors decapitates lizard\n" +
             " Lizard eats paper\n" +
             " Paper disproves Spock\n" +
             " Spock vaporizes Rock\n" +
             " Rock crushes scissors")
    println()
    print("Would you like to play? (y or n) ")
    var option = readLine()!!.toString()




    do {
        if (option == "y") {

            captionChoice = menu1(menuItems, "Choose wisely: ")



            val randNum = (1..5).random()


            if (option == "y" && captionChoice == quitOption) {
                println()
            }else   if (captionChoice != 1 && captionChoice != 2 && captionChoice != 3  && captionChoice != 4 && captionChoice != 5 && captionChoice != 6) {
                println()
            } else {




                when (randNum) {

                    1 -> println("Computer chose: Rock")
                    2 -> println("Computer chose: Paper")
                    3 -> println("Computer chose: Scissors")
                    4 -> println("Computer chose: Lizard!")
                    5 -> println("Computer chose: Spock!")



                }


            }
            when (captionChoice) {
                1 -> println("You chose: Rock")
                2 -> println("You chose: Paper")
                3 -> println("You chose: Scissors")
                4 -> println("You chose: Lizard!")
                5 -> println("You chose: Spock!")
                else -> {
                    if (captionChoice != quitOption) {
                        println("Please select a valid menu option!")
                    }else if (captionChoice == quitOption) {
                        println("Live long and prosper... \uD83D\uDD96")
                        println()
                    }

                }
            }

            if (captionChoice != botChoice) {

                if (captionChoice == 1) {
                    if (randNum == 2) {
                        println("Paper beats Rock! You've been covered!")
                        println("Defeat!")
                        println("----STATS----")
                        println("Victories: $starfleetWins")
                        println("Defeats: ${botWins++ + 1}")
                        println("Draws: $tie")
                        println()

                    } else if (captionChoice == randNum) {
                        println("It's a draw!")
                        println("----STATS----")
                        println("Victories: $starfleetWins")
                        println("Defeats: ${botWins}")
                        println("Draws: ${tie++ + 1}")
                        println()

                    } else if (randNum == 3) {
                        println("Rock beats Scissors! You've crushed Scissors!")
                        println("VICTORY!")
                        println("----STATS----")
                        println("Victories: ${starfleetWins++ + 1}")
                        println("Defeats: $botWins")
                        println("Draws: $tie")
                        println()
                    }else if (randNum == 4) {
                        println("Rock crushed the Lizard! and made a mess...")
                        println("VICTORY!")
                        println("----STATS----")
                        println("Victories: ${starfleetWins++ + 1}")
                        println("Defeats: $botWins")
                        println("Draws: $tie")
                        println()
                     } else if (randNum == 5) {
                        println("Spock beats Rock! *\uD835\uDC4D\uD835\uDC34\uD835\uDC43!* You've been vaporized!")
                        println("Defeat!")
                        println("----STATS----")
                        println("Victories: $starfleetWins")
                        println("Defeats: ${botWins ++ + 1}")
                        println("Draws: $tie")
                        println()
                    }
                    }
                //quit option
                else if (captionChoice == quitOption){
                        println("----STATS----")
                        println("Victories: $starfleetWins")
                        println("Defeats: $botWins")
                        println("Draws: $tie")
                        println()
                }
                else if (captionChoice == 3) {
                    if (randNum == 1) {
                        println("Rock beats Scissors! You've been crushed!")
                        println("Defeat!")
                        println("----STATS----")
                        println("Victories: $starfleetWins")
                        println("Defeats: ${botWins++ + 1}")
                        println("Draws: $tie")
                        println()
                    } else if (randNum == 2) {
                        println("Scissors beats Paper! You cut Paper!")
                        println("VICTORY!")
                        println("----STATS----")
                        println("Victories: ${starfleetWins++ + 1}")
                        println("Defeats: $botWins")
                        println("Draws: $tie")
                        println()
                    } else if (captionChoice == randNum) {
                        println("It's a draw!")
                        println("----STATS----")
                        println("Victories: $starfleetWins")
                        println("Defeats: $botWins")
                        println("Draws: ${tie++ + 1}")
                        println()

                    }else if (randNum == 4) {
                        println("You decapitated the Lizard!!!")
                        println("VICTORY!")
                        println("----STATS----")
                        println("Victories: ${starfleetWins++ + 1}")
                        println("Defeats: $botWins")
                        println("Draws: $tie")
                        println()

                    } else if (randNum == 5) {
                        println("Spock smashes Scissors! You've been crushed!")
                        println("Defeat!")
                        println("----STATS----")
                        println("Victories: $starfleetWins")
                        println("Defeats: ${botWins ++ + 1}")
                        println("Draws: $tie")
                        println()
                    }
                } else if (captionChoice == 2) {
                    if (randNum == 3) {
                        println("Scissors beats Paper! You've been cut!")
                        println("Defeat!")
                        println("----STATS----")
                        println("Victories: $starfleetWins")
                        println("Defeats: ${botWins++ + 1}")
                        println("Draws: $tie")
                        println()
                    } else if (randNum == 1) {
                        println("Paper beats Rock! Rock has been covered!'")
                        println("VICTORY!")
                        println("----STATS----")
                        println("Victories: ${starfleetWins++ + 1}")
                        println("Defeats: $botWins")
                        println("Draws: $tie")
                        println()

                    } else if (captionChoice == randNum) {
                        println("It's a draw!")
                        println("----STATS----")
                        println("Victories: $starfleetWins")
                        println("Defeats: $botWins")
                        println("Draws: ${tie++ + 1}")
                        println()

                    }else if (randNum == 5) {
                        println("Paper disproves Spock!")
                        println("VICTORY!")
                        println("----STATS----")
                        println("Victories: ${starfleetWins++ + 1}")
                        println("Defeats: $botWins")
                        println("Draws: $tie")
                        println()

                    }else if (randNum == 4) {
                        println("Lizard eats Paper! You've been eaten!!!")
                        println("Defeat!")
                        println("----STATS----")
                        println("Victories: $starfleetWins")
                        println("Defeats: ${botWins++ + 1}")
                        println("Draws: $tie")
                        println()
                    }
                    //LIZARD
                }else if (captionChoice == 4) {
                     if (randNum == 5) {
                         println("Lizard poisons Spock! You've poisoned Spock!")
                        println("VICTORY!")
                        println("----STATS----")
                        println("Victories: ${starfleetWins++ + 1}")
                        println("Defeats: $botWins")
                        println("Draws: $tie")
                        println()

                    }else if (randNum == 2) {
                         println("Lizard eats Paper! Yum!")
                        println("VICTORY!")
                        println("----STATS----")
                        println("Victories: ${starfleetWins++ + 1}")
                        println("Defeats: $botWins")
                        println("Draws: $tie")
                        println()

                    }else if (randNum == 1) {
                         println("Rock crushes Lizard! You've been crushed!")
                         println("Defeat!")
                         println("----STATS----")
                         println("Victories: $starfleetWins")
                         println("Defeats: ${botWins++ + 1}")
                         println("Draws: $tie")
                         println()

                     }
                     else if (randNum == 3) {
                         println("Scissors decapitates Lizard! Don't lose your head over it...")
                         println("Defeat!")
                         println("----STATS----")
                         println("Victories: $starfleetWins")
                         println("Defeats: ${botWins++ + 1}")
                         println("Draws: $tie")
                         println()

                     }else if (captionChoice == randNum) {
                         println("*\uD835\uDC3B\uD835\uDC46\uD835\uDC46\uD835\uDC46\uD835\uDC46\uD835\uDC46*")
                         println("It's a draw!")
                         println("----STATS----")
                         println("Victories: $starfleetWins")
                         println("Defeats: $botWins")
                         println("Draws: ${tie++ + 1}")
                         println()

                     }
                    //SPOCK
                }else if (captionChoice == 5) {
                    if (randNum == 3) {
                        println("Spock smashes Scissors!")
                        println("VICTORY!")
                        println("----STATS----")
                        println("Victories: ${starfleetWins++ + 1}")
                        println("Defeats: $botWins")
                        println("Draws: $tie")
                        println()

                    }else if (randNum == 1) {
                        println("Spock vaporizes Rock! *\uD835\uDC4D\uD835\uDC34\uD835\uDC43!*")
                        println("VICTORY!")
                        println("----STATS----")
                        println("Victories: ${starfleetWins++ + 1}")
                        println("Defeats: $botWins")
                        println("Draws: $tie")
                        println()

                    }else if (randNum == 4) {
                        println("Lizard poisons Spock! *\uD835\uDC38\uD835\uDC3A\uD835\uDC3B\uD835\uDC3E*")
                        println("Defeat!")
                        println("----STATS----")
                        println("Victories: $starfleetWins")
                        println("Defeats: ${botWins ++ + 1}")
                        println("Draws: $tie")
                        println()

                    }else if (randNum == 2) {
                        println("Paper disproves Spock! Impossible!")
                        println("Defeat!")
                        println("----STATS----")
                        println("Victories: $starfleetWins")
                        println("Defeats: ${botWins ++ + 1}")
                        println("Draws: $tie")
                        println()

                    }else if (captionChoice == randNum) {
                        println("How illogical!?")
                        println("It's a draw!")
                        println("----STATS----")
                        println("Victories: $starfleetWins")
                        println("Defeats: $botWins")
                        println("Draws: ${tie++ + 1}")
                        println()

                    }
                }
            }


        }else if (option == "n") {
            captionChoice = 6
            println("Maybe next time...")
        }else {
//            println("Please select a valid menu option!")

            println("Would you like to begin? (Y or N)")
            option = readLine().toString()
        }
    }while (captionChoice != quitOption)

//    else if (option == "N") {
//    userChoice == 4
//
//    }
}


fun menu1( items: Array<String>, prompt : String ) : Int {

    for ( (index, item) in items.withIndex()) {
        println("${index + 1}.${item}")
    }


    print(prompt)
    println()
    return readLine()!!.toInt()

}