/************************************************************
 *  Name:         
 *  Date:         5/17/20
 *  Assignment:
 *  Class Number: 
 *  Description:
 ***********************************************************/



fun main() {
   val randomValue = IntArray(200) { (1..100).random() }
   println("%s%9s%6s%12s".format("Range", "#", "Found", "Chart"))
   println("%-13s%-14s%s".format("-".repeat(8), "-".repeat(10), "-".repeat(43)))
   for (i in 1..91 step 10) {
       val amount = randomValue.count { it in i..i + 9 }
       println(
           "%2d%2s".format(i, "-") + "%4d%7s".format(i + 9, "|") + "%4d%3s%-5s".format(amount, "|", "") + "*".repeat(
               amount
           )
       )
   }
}



fun main() {

    val randomValue = IntArray(200) { (1..100).random() }
    println(
        "Range        # Found       Chart\n" +
                "--------     ----------    -------------------------------------------"
    )
    for (i in 1..91 step 10) {
        val amount = randomValue.count { it in i..i + 9 }

        println(
            "${i.toString().padStart(2)} -${(i + 9).toString().padStart(4)}      |  ${
                "%1d".format(amount).padStart(2)
            }  |     ${"*".repeat(amount)}"
        )
    }




   var number = 0
   var number1 = 0
   var number2 = 0
   var number3 = 0
   var number4 = 0
   var number5 = 0
   var number6 = 0
   var number7 = 0
   var number8 = 0
   var number9 = 0

   val randomValue =  IntArray(200){(0..99).random()}

   for (i in randomValue){

       if(i < 10){
           number++
       }
       else if(i < 20){
           number1++
       }
       else if(i < 30){
           number2++
       }
       else  if(i < 40){
           number3++
       }
       else if(i < 50){
           number4++
       }
       else if(i < 60){
           number5++
       }
       else if(i < 70){
           number6++
       }
       else  if(i < 80){
           number7++
       }
       else if(i < 90){
           number8++
       }
       else if(i < 100){
           number9++
       }



   }
   println("Range        # Found       Chart\n" +
           "--------     ----------    -------------------------------------------")
   println(" 1 -  10      |  ${"%1d".format(number)}  |     ${"*".repeat(number)}")
   println("11 -  20      |  ${"%1d".format(number1)}  |     ${"*".repeat(number1)}")
   println("21 -  30      |  ${"%1d".format(number2)}  |     ${"*".repeat(number2)}")
   println("31 -  40      |  ${"%1d".format(number3)}  |     ${"*".repeat(number3)}")
   println("41 -  50      |  ${"%1d".format(number4)}  |     ${"*".repeat(number4)}")
   println("51 -  60      |  ${"%1d".format(number5)}  |     ${"*".repeat(number5)}")
   println("61 -  70      |  ${"%1d".format(number6)}  |     ${"*".repeat(number6)}")
   println("71 -  80      |  ${"%1d".format(number7)}  |     ${"*".repeat(number7)}")
   println("81 -  90      |  ${"%1d".format(number8)}  |     ${"*".repeat(number8)}")
   println("91 -  100     |  ${"%1d".format(number9)}  |     ${"*".repeat(number9)}")

}


