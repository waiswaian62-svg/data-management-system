# Question One Answer

## UML Class Diagram

```mermaid
classDiagram
    class Book {
        -String isbn
        -String title
        -String author
        -boolean available
        +Book(String isbn, String title)
        +Book(String isbn, String title, String author)
        +getIsbn() String
        +setIsbn(String isbn) void
        +getTitle() String
        +setTitle(String title) void
        +getAuthor() String
        +setAuthor(String author) void
        +isAvailable() boolean
        +setAvailable(boolean available) void
        +toString() String
    }

    class Member {
        -String memberId
        -String name
        -List~Loan~ loans
        +Member(String memberId, String name)
        +getMemberId() String
        +setMemberId(String memberId) void
        +getName() String
        +setName(String name) void
        +getLoans() List~Loan~
        +setLoans(List~Loan~ loans) void
        +addLoan(Loan loan) void
        +removeLoan(Loan loan) void
        +toString() String
    }

    class Loan {
        -Member member
        -Book book
        -LocalDate borrowDate
        -LocalDate dueDate
        +Loan(Member member, Book book, LocalDate borrowDate, LocalDate dueDate)
        +getMember() Member
        +setMember(Member member) void
        +getBook() Book
        +setBook(Book book) void
        +getBorrowDate() LocalDate
        +setBorrowDate(LocalDate borrowDate) void
        +getDueDate() LocalDate
        +setDueDate(LocalDate dueDate) void
        +toString() String
    }

    class Library {
        -List~Book~ books
        -List~Member~ members
        +Library()
        +getBooks() List~Book~
        +setBooks(List~Book~ books) void
        +getMembers() List~Member~
        +setMembers(List~Member~ members) void
        +addBook(Book book) void
        +registerMember(Member member) void
        +lendBook(String isbn, String memberId) Loan
        +returnBook(String isbn, String memberId) boolean
        +searchBookByTitle(String title) Book
        -findBookByIsbn(String isbn) Book
        -findMemberById(String memberId) Member
        +toString() String
    }

    Library "1" o-- "0..*" Book : stores
    Library "1" o-- "0..*" Member : registers
    Member "1" *-- "0..*" Loan : holds
    Loan "1" --> "1" Book : for
    Loan "1" --> "1" Member : borrowed by
```

## Relationship Explanation

An association is a general link between classes, such as a `Loan` being linked to exactly one `Book` and exactly one `Member`.

Aggregation is a weak whole-part relationship. The relationship between `Library` and `Book`, and between `Library` and `Member`, is aggregation because the library stores books and members, but the books and members can still exist outside the library system.

Composition is a strong whole-part relationship. The relationship between `Member` and `Loan` is composition because the active loan belongs to the member's current borrowing record and should be removed when the member's active loan record is removed.

The multiplicity `1..*` means one or more. For example, if a library were required to have at least one book, the relationship from `Library` to `Book` could be shown as `1..*`, meaning one library has one or more books.

## Java Files Submitted

- `Book.java`
- `Member.java`
- `Loan.java`
- `Library.java`
- `LibraryDemo.java`

The program enforces the rule that a book may be on at most one active loan at a time. If a second member tries to borrow a book that is already on loan, the operation is rejected gracefully.
