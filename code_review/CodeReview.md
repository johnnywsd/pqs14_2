Please enter your code review in this file as plain text
and make sure to include it when you commit your project.

#### AddressBook.java

1. It has newInstance() method, but we still also create new instance by AddressBook addressBook = new AddressBook(). It should contain private constructor.
If we create a new instance by it default constructor, the entries will be null by default. And getEntries will not return an empyt ArrayList. It return null.

2. Line 98. Name and address are required. It is unrelistic in Address Book in our real life.

3. Line 379. It use "~##~" as separator and use regular text to save the address book and its entries which means that we cannot have "~##~" in our cntries' name, address, note, etc.

4. Line 121. We cannot remove an entry unless we have it. We have search and get the entry or entries first.

5. Line 96. Same Entry instance can be added to the Addressbook more than once. 

For example,

    Entry entry = <A new Entry Instance>;
    addressbook.addEntry(entry);
    addressbook.addEntry(entry);  //add twice

If we change that Entry object

    entry.setName("NEW_NAME");

Both of the two entries in addressbook will be change, Since addressbook has two references of the same entry.

#### Entry.java

1. Line 50, 176. Name and address are required which should not be.

2. Line 261, It use "~#~" as separator, and use toString() method for serialization. So we cannot have "~#~" in entry's name, address, note, etc.

3. Line 64, 77, 90. It is not necessary to return a reference of the builder itself, since it update its members in place.

#### LoggerWrapper.java

1. LoggerWrapper is not a good idea. It use SimpleFormatter. The log will looks like this:

>    <date> <time> <Level> <Logger_Name> : <#line> - <Message>

Because all the class use the same LoggerWrapper, all log have the same <Logger_Name> which is "AddressBook". Then the #line become meaningless, since we cannot find which line of code print this log.
    
