# Simple Flashcards

## Project Description

My project is a lightweight and convenient flashcard software
that users can use to quickly design small memorization tests
for themselves on any subject. The application will consist
of a user-defined **set** of **cards,** each of which contains
a question and an answer. After the user is done designing their
flashcard set, they can choose to test themselves, and at the
end of the test the program will display how well they did
along with the questions that they got wrong.

This project can be used by a variety of users, for example,
students in the field of environmental or geographical studies
may use it to help remember and solidify their knowledge of
key concepts. A person learning a new language can use it to
test themselves and practice their vocabulary. Or perhaps
friends in Math classes can design little problem sets for
each other to practice before a test. The versatility and
completely customizable questions and answers allow for a
wide variety of applications.

I designed this program due to its potential usefulness to
myself for studying concepts and vocabulary involved in my
geography class. There are obviously other similar programs
online, but many require paid subscriptions or arguably
intrusive information to sign up, and have somewhat complex
UIs, require an internet connection, and are overall not very
lightweight. I decided there must be other people who would 
find such a simple and accessible flashcard designer 
helpful in certain situations, and therefore I decided to 
fill that niche with this project.

## User Stories

**Phase 1**
- As a user, I want to be able to add an arbitrary amount of
**cards** to a **set** of flashcards.
- As a user, I want to be able to select an existing flashcard
and edit its answer.
- As a user, I want to be able to clear the entire flashcard
set, as removing each card individually can get very tedious
for large decks of cards.
- As a user, I want to be able to view my results/score
after completing a test with my flashcard set,
along with displaying all the questions I got wrong.

**Phase 2**
- As a user, I want to be able to save the **entire** state of
my flashcard set at any given point to a file.
- As a user, I wish to be able to load a flashcard set save
from a file, and resume the application with exactly the same 
state that it was in at the time that it was saved.


### Phase 4: Task 2
Wed Mar 30 14:57:20 PDT 2022
New flashcard added to set.

Wed Mar 30 14:57:24 PDT 2022
New flashcard added to set.

Wed Mar 30 14:57:25 PDT 2022
Flashcard removed from set.

### Phase 4: Task 3

I believe my program is designed quite compactly and
efficiently judging by my UML diagram. I call my main GUI
from the main function, I have clearly split up the GUI
and models/mechanics parts of my program, my JSON saving is
implemented cleanly. And the event logs
are a distinct part of the program that are seperated enough
from everything else to be very unlikely to break from
the UI's or model's potential bugs.

However, there is something I would improve upon given I
had the time, I see that my test class associates with both
my FlashcardSet class and a list of my Card class, this is
not good because FlashcardSet is created to literally
represent a set/deck/list of Cards, with some potential
refactoring I could make the wrong answers in my test class
be also represented as a FlashcardSet and therefore decrease
unnecessary complexity in my code.

**Credits:**
JSON setup structure was partially modeled off of:
  https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo