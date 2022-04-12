import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.nio.file.Files
import java.util.*
import java.util.Map
import java.util.stream.Collectors
import kotlin.collections.LinkedHashMap
import kotlin.system.exitProcess
/************************************************************
 *  Name:         
 *  Date:         5/26/20
 *  Assignment:
 *  Class Number: 
 *  Description:
 ***********************************************************/






fun main() {

    ProductMenu().run()
}

class ProductMenu {

    companion object {

        lateinit var productsFile: File

        lateinit var products: HashMap<Int, Pair<String, Double>>
    }

    init {
        productsFile = File("src/products.txt")
        products = hashMapOf()
    }

    fun run() {

        checkProductFile(productsFile)

        displayMenu()
    }

    private fun checkProductFile(productsFile: File, error: Boolean = true) {
        if (!productsFile.exists()) {
            println("Products file doesn't exist! Please add product information and restart.")
            productsFile.createNewFile()
            exitProcess(0)
        }
        products.clear()
        productsFile.forEachLine { line ->
            if (line.isNotEmpty()) {
                val args = line.split(",")
                if (error) {
                    try {
                        products[args[0].toInt()] = Pair(args[1], args[2].toDouble())
                    } catch (ne: NumberFormatException) {
                        println("There's something wrong with the file! Wipe it or fix the data.")
                        exitProcess(0)
                    }
                } else products[args[0].toInt()] = Pair(args[1], args[2].toDouble())
            }
        }
    }

    private fun displayMenu() {
        println("1. View all products\n2. Add a new product\n3. Delete a product\n4. Update a product\n5. View highest priced product\n6. View lowest priced product\n7. View sum of all product prices\n8. Exit\n\nPlease enter your selection: ")
        // makes sure user input is an integer and uses it once valid
        when (getUserInput(8)) {
            1 -> displayProducts()
            2 -> addProduct()
            3 -> removeProduct()
            4 -> updateProduct()
            5 -> displayMostExpensiveProduct()
            6 -> displayLeastExpensiveProduct()
            7 -> displaySumOfProducts()
            8 -> exitProcess(0)
        }
    }

    private fun displayProducts() {
        if (products.isEmpty()) {
            println("Looks like there isn't any products. You'll need to add some.")
        }else {
            println(String.format("%-15s%-15s%-15s", "Item #", "Description", "Price"))
            println(String.format("%-15s%-15s%-15s", "------", "-----------", "-----"))

        }
        val sortedProducts = LinkedHashMap<Int, Pair<String, Double>>()
        products.entries
            .stream()
            .sorted(Map.Entry.comparingByKey())
            .forEachOrdered { x -> sortedProducts[x.key] = x.value }
        sortedProducts.forEach { p ->
            println(String.format("%-15s%-15s%-15s", p.key, p.value.first, "$${p.value.second}"))
        }

        displayClosePrompt()
    }

    private fun getUserInput(maxInt: Int? = null): Int {
        var input: String
        while (true) {
            input = readLine()!!
            try {
                val intCheck = input.toInt()
                if (maxInt != null && intCheck > maxInt) throw NumberFormatException()
                break
            } catch (ne: NumberFormatException) {
                println("Invalid selection, try again.")
            }
        }
        return input.toInt()
    }

    private fun addProduct() {
        println("Add a new item to the products list using the following format:\nItem number, Product, Price")
        productsFile.appendText("\n" + readLine()!!.replace(" ", "").replace("$", ""))
        while (true) {
            try {
                checkProductFile(productsFile, false)
                break
            } catch (e: Exception) {
                println("Invalid item syntax, try again.")
                displayClosePrompt()
            }
        }
        println("Product added!")
        displayClosePrompt()
    }

    private fun removeProduct() {
        println("Remove a product by ID number: ")
        val input = readLine()!!
        val out = Files.lines(productsFile.toPath())
            .filter { line: String -> line.startsWith(input) }
            .collect(Collectors.toList())
        var args = listOf<String>()
        while (true) {
            if (args.isEmpty()) {
                args = out.toString().replace("[", "").replace("]", "").split(",")
                if (args.size < 3) {
                    println("ID number '$input' not found, it may have already been deleted.")
                    displayClosePrompt()
                    return
                } else break
            }
        }
        val temp = File("_temp_")
        temp.createNewFile()
        val out2 = PrintWriter(FileWriter(temp))
        Files.lines(productsFile.toPath())
            .filter { line: String -> !line.startsWith(input) }
            .forEach { x: String -> out2.println(x) }
        out2.flush()
        out2.close()
        productsFile.delete()
        temp.renameTo(productsFile)
        checkProductFile(productsFile)
        println("Product removed!")
        displayClosePrompt()
    }

    private val sb = StringBuilder()
    private fun updateProduct() {
        println("Modify a product by ID number: ")
        val input = readLine()!!
        val out = Files.lines(productsFile.toPath())
            .filter { line: String -> line.startsWith(input) }
            .collect(Collectors.toList())
        var args = listOf<String>()
        while (true) {
            if (args.isEmpty()) {
                args = out.toString().replace("[", "").replace("]", "").split(",")
                if (args.size < 3) {
                    println("ID number '$input' not found, try again.")
                    displayClosePrompt()
                    return
                } else break
            }
        }
        val temp = File("_temp_")
        temp.createNewFile()
        val out2 = PrintWriter(FileWriter(temp))
        Files.lines(productsFile.toPath())
            .filter { line: String -> !line.startsWith(input) }
            .forEach { x: String -> out2.println(x) }
        out2.flush()
        out2.close()

        println("What would you like to change about ${args[1]}?\n1. Item number\n2. Description\n3. Price\n4. Quit")
        when (getUserInput(3)) {
            1 -> updateItemNumber(args)
            2 -> updateDescription(args)
            3 -> updatePrice(args)
            4 -> {
                displayClosePrompt()
                return
            }
        }

        productsFile.delete()
        temp.renameTo(productsFile)
        productsFile.appendText(sb.toString())
        sb.clear()
        checkProductFile(productsFile)
        println("Product updated!")
        displayClosePrompt()
    }

    private fun updateItemNumber(args: List<String>) {
        println("New item number: ")
        sb.append("${getUserInput()},${args[1]},${args[2]}")
    }

    private fun updateDescription(args: List<String>) {
        println("New description: ")
        sb.append("${args[0]},${readLine()},${args[2]}")
    }

    private fun updatePrice(args: List<String>) {
        println("New price: ")
        sb.append("${args[0]},${args[1]},${readLine()}")
    }

    private fun displayMostExpensiveProduct() {
        println("Most expensive product:")
        val p = products.maxBy { it.value.second }
        println("${p!!.value.first.capitalize()} with ID '${p.key}' are the most expensive and cost $${p.value.second}.")
        displayClosePrompt()
    }

    private fun displayLeastExpensiveProduct() {
        println("Least expensive product:")
        val p = products.minBy { it.value.second }
        println("${p!!.value.first.capitalize()} with ID '${p.key}' are the least expensive and cost $${p.value.second}.")
        displayClosePrompt()
    }

    private fun displaySumOfProducts() {
        println("Total retail price of merchandise:")
        val p = "%.2f".format(products.map { it.value.second }.sum())
        println("$$p")
        displayClosePrompt()
    }

    private fun displayClosePrompt() {
        println("Return to menu? [Y/N]: ")
        if (readLine()!!.toLowerCase() == "y") displayMenu()
        else exitProcess(0)
    }
}