import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LibraryDemo {
    public static void main(String[] args) {
        Library library = new Library();

        Book bookOne = new Book("978-0134685991", "Effective Java", "Joshua Bloch");
        Book bookTwo = new Book("978-1492078005", "Head First Java", "Kathy Sierra");

        Member memberOne = new Member("M001", "Amina");
        Member memberTwo = new Member("M002", "Brian");

        library.addBook(bookOne);
        library.addBook(bookTwo);
        library.registerMember(memberOne);
        library.registerMember(memberTwo);

        System.out.println("Before lending:");
        System.out.println(library);

        library.lendBook("978-0134685991", "M001");
        library.lendBook("978-1492078005", "M002");
        library.lendBook("978-0134685991", "M002");

        System.out.println("\nAfter lending attempts:");
        System.out.println(library);

        library.returnBook("978-0134685991", "M001");

        System.out.println("\nAfter returning one book:");
        System.out.println(library);

        Book foundBook = library.searchBookByTitle("Effective Java");
        System.out.println("Search result: " + foundBook);
    }
}

class Book {
    private String isbn;
    private String title;
    private String author;
    private boolean available;

    public Book(String isbn, String title) {
        this(isbn, title, "Unknown");
    }

    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", available=" + available +
                '}';
    }
}

class Member {
    private String memberId;
    private String name;
    private List<Loan> loans;

    public Member(String memberId, String name) {
        this.memberId = memberId;
        this.name = name;
        this.loans = new ArrayList<>();
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    public void removeLoan(Loan loan) {
        loans.remove(loan);
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId='" + memberId + '\'' +
                ", name='" + name + '\'' +
                ", activeLoans=" + loans.size() +
                '}';
    }
}

class Loan {
    private Member member;
    private Book book;
    private LocalDate borrowDate;
    private LocalDate dueDate;

    public Loan(Member member, Book book, LocalDate borrowDate, LocalDate dueDate) {
        this.member = member;
        this.book = book;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "member=" + member.getName() +
                ", book=" + book.getTitle() +
                ", borrowDate=" + borrowDate +
                ", dueDate=" + dueDate +
                '}';
    }
}

class Library {
    private List<Book> books;
    private List<Member> members;

    public Library() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void registerMember(Member member) {
        members.add(member);
    }

    public Loan lendBook(String isbn, String memberId) {
        Book book = findBookByIsbn(isbn);
        Member member = findMemberById(memberId);

        if (book == null) {
            System.out.println("Loan rejected: book with ISBN " + isbn + " was not found.");
            return null;
        }

        if (member == null) {
            System.out.println("Loan rejected: member with ID " + memberId + " was not found.");
            return null;
        }

        if (!book.isAvailable()) {
            System.out.println("Loan rejected: " + book.getTitle() + " is already on loan.");
            return null;
        }

        LocalDate borrowDate = LocalDate.now();
        LocalDate dueDate = borrowDate.plusDays(14);
        Loan loan = new Loan(member, book, borrowDate, dueDate);

        book.setAvailable(false);
        member.addLoan(loan);
        System.out.println("Loan successful: " + member.getName() + " borrowed " + book.getTitle() + ".");
        return loan;
    }

    public boolean returnBook(String isbn, String memberId) {
        Book book = findBookByIsbn(isbn);
        Member member = findMemberById(memberId);

        if (book == null || member == null) {
            System.out.println("Return rejected: book or member was not found.");
            return false;
        }

        Loan loanToReturn = null;
        for (Loan loan : member.getLoans()) {
            if (loan.getBook().getIsbn().equals(isbn)) {
                loanToReturn = loan;
                break;
            }
        }

        if (loanToReturn == null) {
            System.out.println("Return rejected: no active loan connects this member to this book.");
            return false;
        }

        member.removeLoan(loanToReturn);
        book.setAvailable(true);
        System.out.println("Return successful: " + book.getTitle() + " is now available.");
        return true;
    }

    public Book searchBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    private Book findBookByIsbn(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    private Member findMemberById(String memberId) {
        for (Member member : members) {
            if (member.getMemberId().equals(memberId)) {
                return member;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Library State\n");
        result.append("Books:\n");
        for (Book book : books) {
            result.append("  ").append(book).append('\n');
        }
        result.append("Members:\n");
        for (Member member : members) {
            result.append("  ").append(member).append('\n');
            for (Loan loan : member.getLoans()) {
                result.append("    ").append(loan).append('\n');
            }
        }
        return result.toString();
    }
}
