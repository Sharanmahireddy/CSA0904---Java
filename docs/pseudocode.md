# Pseudocode – Smart Library Management System

## Main Workflow
```text
START
Load sample data
WHILE user has not selected Exit
    Display menu
    Read choice
    CASE choice
        1: Register member
        2: Add book
        3: Display inventory
        4: Search books
        5: Update book
        6: Issue book
        7: Return book and calculate fine
        8: Reserve unavailable book
        9: Cancel reservation
        10: Display members
        11: Display circulation report
        12: Generate due/overdue notifications
        13: Run multithreading issue demo
        14: Generate inventory utilization report
        15: Display reservation queue
        0: Exit
        otherwise: Display invalid-choice message
    END CASE
    Catch invalid input and custom exceptions
END WHILE
STOP
```

## Issue Book
```text
Validate member
Validate book
IF member already borrowed book
    Reject
IF borrowing limit reached
    Reject
Synchronize inventory
IF no available copy
    Reject with BookUnavailableException
Decrease available copies
Create loan with 14-day due date
Add loan and borrowed-book record
Remove matching reservation if present
Return loan
```

## Return Book and Fine
```text
Validate member and active loan
Find due date
Calculate overdue days
IF overdue days > 0
    fine = overdue days * membership fine rate
ELSE
    fine = 0
Mark loan returned
Remove book from member borrowed list
Increase available copies
Call notifyAll()
Return fine
```

## Reservation
```text
Validate member and book
IF available copies > 0
    Reject because reservation is unnecessary
FOR every existing reservation
    IF same member and book
        Reject duplicate reservation
Add reservation to waitlist
```

## Concurrent Issue
```text
Create two issue threads with different priorities
Start both threads
Join both threads
Start availability waiting thread
Wait briefly
Find active loan
Return the active book
notifyAll waiting thread
Join waiting thread
Verify inventory consistency
```
