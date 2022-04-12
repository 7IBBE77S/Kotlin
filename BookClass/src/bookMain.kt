import java.io.File

/************************************************************
 *  Name:         
 *  Date:         10/12/20
 *  Assignment:
 *  Class Number: 
 *  Description:
 ***********************************************************/

fun main() {
    Start()
}

class Start {
    private val bookDb = File("src/books.txt")
    private var books: MutableList<Book>

    init {
        books = loadBooks()
        run()
    }

    private fun loadBooks(): MutableList<Book> {
        val books = mutableListOf<Book>()
        bookDb.forEachLine { line ->
            val pieces = line.trim().split("\t")
            books.add(Book(pieces[0], pieces[1], pieces[2].toInt(), pieces[3].toInt(), pieces[4].toLong()))
        }
        return books
    }

    private fun saveBooks() {
        val sb = StringBuilder()
        for (book in books) {
            sb.append(book.toTab()).append("\n")
        }
        bookDb.writeText(sb.toString().trim())
    }

    enum class BookVariables(val id: Int) {
        TITLE(1), AUTHOR(2), PUBLICATION_YEAR(3), NUMBER_OF_PAGES(4), ISBN_NUMBER(5)
    }

    private fun printBookVariables() {
        val sb = StringBuilder()
        for (enum in enumValues<BookVariables>()) {
            sb.append(enum.id).append('.').append(' ').append(enum.name).append('\n')
        }
        println(sb.toString().trimEnd())
    }

    private fun run() {
        while (true) {
            /*Prints menu*/
            println("\n1. View all books\n2. Add book\n3. Update book\n4. Delete book\n5. View book with most pages\n6. View book with least pages\n7. View books with pages greater than or equal to 200\n8. View books with pages less than 200\n9. View books with pages between 50-300 inclusive\n10. Exit")
            println("\nInput selection: ")
            val input = try {
                readLine()!!.toInt()
            } catch (e: NumberFormatException) {
                println("Invalid number!")
                return
            }
            println()
            when (input) {
                1 -> viewBooks(true)
                2 -> addBook()
                3 -> updateBook()
                4 -> deleteBook()
                5 -> viewBookWithMostPages()
                6 -> viewBookWithLeastPages()
                7 -> viewBooksPagesAboveOrEqual(200)
                8 -> viewBooksPagesBelow(200)
                9 -> viewBooksPagesBetweenInclusive(50, 300)
                10 -> {
                    saveBooks()
                    break
                }
                else -> println("Invalid selection!")
            }
            println("\nReturn? yes/no")
            if (readLine()!!.toLowerCase() == "no") {
                saveBooks()
                break
            }
        }
    }

    private fun viewBooks(sortAlphabetical: Boolean, books: List<Book>? = null) {
        var count = 0
        if (sortAlphabetical) {
            println("Geeks Publishing, Inc.")
            println(String.format("%-40s%-30s%-15s%-15s%-20s%-30s", "Name", "Author", "Pub Yr", "Pages", "ISBN", "URL"))
            println(String.format("%-40s%-30s%-15s%-15s%-20s%-30s", "---------------------------------", "----------------", "-----", "-----", "-----------", "----------------"))
            this.books.sortedWith(compareBy { it.title }).forEach { book ->
                count++
                println(String.format("%-40s%-30s%-15s%-15s%-20s%-30s", "$count: ${book.title.take(30)}", book.author, book.publicationYear, book.numberOfPages, book.isbn, "https://biblio.com/${book.isbn}"))
            }
            return
        }

        if (books == null) {
            this.books.forEach { book ->
                count++
                println(String.format("%-40s%-30s%-15s%-15s%-20s%-30s", "$count: ${book.title.take(30)}", book.author, book.publicationYear, book.numberOfPages, book.isbn, "https://biblio.com/${book.isbn}"))
            }
            return
        }

        books.forEach { book ->
            count++
            println(String.format("%-40s%-30s%-15s%-15s%-20s%-30s", "$count: ${book.title.take(30)}", book.author, book.publicationYear, book.numberOfPages, book.isbn, "https://biblio.com/${book.isbn}"))
        }
    }

    private fun addBook() {
        println("Add a new book with this format:\n${enumValues<BookVariables>().joinToString { it.name }}")
        val args = readLine()!!.replace(" ", "").trim().split(",")
        args.forEach { arg ->
            if (arg.isBlank()) {
                println("Not enough arguments! Aborting..")
                return
            }
        }

        try {
            if (args[2].toInt() <= 1600) {
                println("Publication year is too old! Aborting..")
                return
            }
            if (args[3].toInt() <= 0) {
                println("There must be at least one page! Aborting..")
                return
            }
            books.add(Book(args[0], args[1], args[2].toInt(), args[3].toInt(), args[4].toLong()))
        } catch (e: Exception) {
            println("Invalid format! Aborting..")
            return
        }
        println("Added book!")
    }

    private fun updateBook() {
        println("Pick a book by its number to update:")
        viewBooks(false)
        println("\nSelection: ")
        val bookSelection = try {
            books[readLine()!!.toInt() - 1]
        } catch (e: Exception) {
            println("Invalid selection! Aborting..")
            return
        }
        println("\nSelected:\n$bookSelection.\n\nChoose a variable to update:")
        printBookVariables()
        println("\nSelection: ")
        val enumSelection = try {
            readLine()!!.toInt()
        } catch (e: Exception) {
            println("Invalid selection! Aborting..")
            return
        }

        val selected = BookVariables.values().associateBy(BookVariables::id)[enumSelection]
        println("\nSelected modifier:\n$selected\n\nType a new ${selected.toString().toLowerCase()}:")
        try {
            val newValue = readLine()!!
            if (newValue.isBlank()) {
                println("Modification can't be empty! Aborting..")
                return
            }
            when (enumSelection) {
                BookVariables.TITLE.id -> bookSelection.title = newValue
                BookVariables.AUTHOR.id -> bookSelection.author = newValue
                BookVariables.PUBLICATION_YEAR.id -> {
                    if (newValue.toInt() <= 1600) {
                        println("Publication year is too old! Aborting..")
                        return
                    }
                    bookSelection.publicationYear = newValue.toInt()
                }
                BookVariables.NUMBER_OF_PAGES.id -> {
                    if (newValue.toInt() <= 0) {
                        println("There must be at least one page! Aborting..")
                        return
                    }
                    bookSelection.numberOfPages = newValue.toInt()
                }
                BookVariables.ISBN_NUMBER.id -> bookSelection.isbn = newValue.toLong()
            }
        } catch (e: NumberFormatException) {
            println("Invalid selection! Aborting..")
            return
        }
        println("\nUpdated book info:\n$bookSelection")
    }

    private fun deleteBook() {
        println("Pick a book by its number to delete:")
        viewBooks(false)
        println("\nSelection: ")
        val bookSelection = try {
            books[readLine()!!.toInt() - 1]
        } catch (e: Exception) {
            println("Invalid selection! Aborting..")
            return
        }
        books.remove(bookSelection)
        println("Book deleted!")
    }

    private fun viewBookWithMostPages() {
        val find = books.maxByOrNull { it.numberOfPages }
        if (find == null) {
            println("No books found! Aborting..")
            return
        }
        println("Book with most pages:\n${find.title} (Pages: ${find.numberOfPages})")
    }

    private fun viewBookWithLeastPages() {
        val find = books.minByOrNull { it.numberOfPages }
        if (find == null) {
            println("No books found! Aborting..")
            return
        }
        println("Book with least pages:\n${find.title} (Pages: ${find.numberOfPages})")
    }

    private fun viewBooksPagesAboveOrEqual(number: Int) {
        println("Books with pages above or equal to $number:")
        val find = books.filter { it.numberOfPages >= number }
        viewBooks(false, find)
    }

    private fun viewBooksPagesBelow(number: Int) {
        println("Books with pages below $number:")
        val find = books.filter { it.numberOfPages < number }
        viewBooks(false, find)
    }

    private fun viewBooksPagesBetweenInclusive(min: Int, max: Int) {
        println("Books with pages between $min and $max:")
        val find = books.filter { it.numberOfPages in min..max }
        viewBooks(false, find)
    }

//    fun viewBooksWithPagesInRange(books: MutableList<Book>, range: IntRange = 0..Int.MAX_VALUE) {
//        val tempBooksList = mutableListOf<Book>()
//        println("Books with pages between ${range.first} and ${range.last}: ")
//        for (book in books) {
//            if(book.numberOfPages in range) {
//                tempBooksList.add(book)
//            }
//        }
//        printBooks(tempBooksList, false)
//    }
}

class Book(
    var title: String,
    var author: String,
    var publicationYear: Int,
    var numberOfPages: Int,
    var isbn: Long
) {
    override fun toString(): String {
        return "$title, $author, $publicationYear, $numberOfPages, https://biblio.com/$isbn"
    }

    fun toTab(): String {
        return "$title\t$author\t$publicationYear\t$numberOfPages\t$isbn"
    }
}
