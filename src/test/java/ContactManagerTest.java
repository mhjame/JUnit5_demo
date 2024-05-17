import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import java.util.*;

class ContactManagerTest {

    ContactManager contactManager;

    @BeforeAll
    public static void setupAll() {
        System.out.println("Should Print Before All Tests");
    }

    @BeforeEach
    public void setup() {
        contactManager = new ContactManager();
        System.out.println("Should print before each Tests");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Should Execute After Each Test");
    }

    @AfterAll
    public static void tearDownALl() {
        System.out.println("Should Execute After All Tests");
    }

    @Test
    @DisplayName("Should Create Contact")
    @Disabled
    public void shouldCreateContact() {
        contactManager.addContact("John", "Doe", "0123456789");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
        assertTrue(contactManager.getAllContacts().stream()
                .filter(contact -> contact.getFirstName().equals("John") &&
                        contact.getLastName().equals("Doe") &&
                        contact.getPhoneNumber().equals("0123456789"))
                .findAny()
                .isPresent());
    }

    @Test
    @DisplayName("Should Not Create Contact When First Name is Null")
    public void shouldThrowRuntimeExceptionWhenFirstNameIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("John", null, "0123456789");
        });
    }

    @Test
    @DisplayName("Should Not Create Contact When Last Name is Null")
    public void shouldThrowRuntimeExceptionWhenLastNameIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("John", null, "0123456789");
        });
    }

    @Test
    @DisplayName("Should Not Create Contact When Phone Number is Null")
    public void shouldThrowRuntimeExceptionWhenPhoneNumberIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("John", "Doe", null);
        });
    }

    @Test
    @DisplayName("Should Create Contact Only on MACOS")
    @EnabledOnOs(value = OS.MAC, disabledReason = "Should Run only on MAC")
    public void shouldCreateContactOnMAC() {
        contactManager.addContact("John", "Doe", "0123456789");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
        assertTrue(contactManager.getAllContacts().stream()
                .filter(contact -> contact.getFirstName().equals("John") &&
                        contact.getLastName().equals("Doe") &&
                        contact.getPhoneNumber().equals("0123456789"))
                .findAny()
                .isPresent());
    }

    @Test
    @DisplayName("Should NOT Create Contact on WindowsOS")
    @DisabledOnOs(value = OS.WINDOWS, disabledReason = "Disabled on Windows OS")
    public void shouldNotCreateOnlyOnWindows() {
        contactManager.addContact("John", "Doe", "0123456789");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
        assertTrue(contactManager.getAllContacts().stream()
                .filter(contact -> contact.getFirstName().equals("John") &&
                        contact.getLastName().equals("Doe") &&
                        contact.getPhoneNumber().equals("0123456789"))
                .findAny()
                .isPresent());
    }

//    @BeforeAll
//    public void tryThis()
//    {
//        System.out.print("This is system env");
//        System.out.println(System.getProperty("ENV"));
//    }

    @Test
    @DisplayName("Test Contact Creation on Developer Machine")
    public void shouldTestContactCreationOnDEV() {
        Assumptions.assumeTrue("DEV".equals(System.getProperty("ENV")));
        contactManager.addContact("John", "Doe", "0123456789");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
        assertTrue(contactManager.getAllContacts().stream()
                .filter(contact -> contact.getFirstName().equals("John") &&
                        contact.getLastName().equals("Doe") &&
                        contact.getPhoneNumber().equals("0123456789"))
                .findAny()
                .isPresent());
    }

    @Nested
    class RepeatedNestedTest_ {
        @DisplayName("Repeat Contact Creation Test 5 Times")
        @RepeatedTest(value = 5,
                name = "Repeating Contact Creation Test {currentRepetition} of {totalRepetitions}")
        public void shouldTestContactCreattionRepeatedly() {
            contactManager.addContact("John", "Doe", "0123456789");
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1, contactManager.getAllContacts().size());
            assertTrue(contactManager.getAllContacts().stream()
                    .filter(contact -> contact.getFirstName().equals("John") &&
                            contact.getLastName().equals("Doe") &&
                            contact.getPhoneNumber().equals("0123456789"))
                    .findAny()
                    .isPresent());
        }
    }

    @Nested
    class ParameterizedNestedTest {
        @DisplayName("Contact Creation Test 3 time (Parameterize Test)")
        @ParameterizedTest
        @ValueSource(strings = {"0123456789", "1234567890", "2345678901"})
        public void shouldTestContactCreationUsingValueSource(String phoneNumber) {
            contactManager.addContact("John", "Doe", phoneNumber);
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1, contactManager.getAllContacts().size());
            assertTrue(contactManager.getAllContacts().stream()
                    .filter(contact -> contact.getFirstName().equals("John") &&
                            contact.getLastName().equals("Doe") &&
                            contact.getPhoneNumber().equals(phoneNumber))
                    .findAny()
                    .isPresent());
        }


        @DisplayName("CSV Source Case - Phone Number should match the required format")
        @ParameterizedTest
        @CsvSource({"0123456789", "0123456798", "0123465789"})
        public void shouldTestPhoneNumberFormatUsingCSVSource(String phoneNumber) {
            contactManager.addContact("John", "Doe", phoneNumber);
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1, contactManager.getAllContacts().size());
            assertTrue(contactManager.getAllContacts().stream()
                    .filter(contact -> contact.getFirstName().equals("John") &&
                            contact.getLastName().equals("Doe") &&
                            contact.getPhoneNumber().equals(phoneNumber))
                    .findAny()
                    .isPresent());
        }

        @DisplayName("Contact Creation Test 3 times (Parameterized Test)")
        @ParameterizedTest(name = "{index} => FirstName={0}, LastName={1}, PhoneNumber={2}")
        @CsvSource({
                "John, Doe, 0123456789",
                "Jane, Smith, 0234567890",
                "Alice, Johnson, 2345678901"
        })
        public void shouldTestContactCreationUsingCsvSource(String firstName, String lastName, String phoneNumber) {
            // Assume contactManager is properly initialized
            contactManager.addContact(firstName, lastName, phoneNumber);
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1, contactManager.getAllContacts().size());
            assertTrue(contactManager.getAllContacts().stream()
                    .filter(contact -> contact.getFirstName().equals(firstName) &&
                            contact.getLastName().equals(lastName) &&
                            contact.getPhoneNumber().equals(phoneNumber))
                    .findAny()
                    .isPresent());
        }

        @DisplayName("CSV File Source Case - Phone Number should match the required format")
        @ParameterizedTest
        @CsvFileSource(resources = "./data.csv")
        public void shouldTestPhoneNumberFormatUsingCSVFileSource(String phoneNumber) {
            contactManager.addContact("John", "Doe", phoneNumber);
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1, contactManager.getAllContacts().size());
            assertTrue(contactManager.getAllContacts().stream()
                    .filter(contact -> contact.getFirstName().equals("John") &&
                            contact.getLastName().equals("Doe") &&
                            contact.getPhoneNumber().equals(phoneNumber))
                    .findAny()
                    .isPresent());
        }
    }

    @DisplayName("Method Source Case - Phone Number should match the required Format")
    @ParameterizedTest
    @MethodSource("phoneNumberList")
    public void shouldTestPhoneNumberFormatUsingMethodSource(String phoneNumber) {
        contactManager.addContact("John", "Doe", phoneNumber);
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
        assertTrue(contactManager.getAllContacts().stream()
                .filter(contact -> contact.getFirstName().equals("John") &&
                        contact.getLastName().equals("Doe") &&
                        contact.getPhoneNumber().equals(phoneNumber))
                .findAny()
                .isPresent());
    }

    private static List<String> phoneNumberList() {
        return Arrays.asList("0123456789", "0123456798", "0123456897");
    }

    //DynamicTest: má»™t lá»›p trong JUnit 5 Ä‘Æ°á»£c sá»­ dá»¥ng Ä‘á»ƒ táº¡o ra cÃ¡c test Ä‘á»™ng
    //DynamicTest bao gá»“m má»™t tÃªn (tÃªn cá»§a test) vÃ  má»™t Executable (má»™t khá»‘i mÃ£ thá»±c thi test)
    //DynamicTest.dynamicTest: ÄÃ¢y lÃ  má»™t phÆ°Æ¡ng thá»©c táº¡o ra má»™t DynamicTest má»›i. NÃ³ nháº­n vÃ o hai tham sá»‘: tÃªn cá»§a test vÃ  má»™t Executable chá»©a mÃ£ Ä‘á»ƒ thá»±c thi test.
    // Dá»¯ liá»‡u Ä‘áº§u vÃ o Ä‘a dáº¡ng, Logic kiá»ƒm tra phá»©c táº¡p, Táº¡o Test tá»± Ä‘á»™ng, Kiá»ƒm tra Ä‘a dáº¡ng trong mÃ´i trÆ°á»ng runtime

    @DisplayName("Dynamic Test With Collection")
    @TestFactory
        //@Test
    Collection<DynamicTest> dynamicTestsWithCollection() {
        return Arrays.asList(
                DynamicTest.dynamicTest("Add test",
                        () -> assertEquals(2, Math.addExact(1, 1))),
                DynamicTest.dynamicTest("Multiply Test",
                        () -> assertEquals(4, Math.multiplyExact(2, 2))));
    }
//
//    @DisplayName("Dynamic Test With Stream")
//    @TestFactory
//    Stream<DynamicTest> dynamicTestsFromIntStream() {
//        // Generates tests for the first 10 even integers.
//        return IntStream.iterate(0, n -> n + 1).limit(10)
//                .mapToObj(n -> DynamicTest.dynamicTest("test" + n, () -> assertTrue(n % 2 == 0)));
//    }


    //tagging and filtering
    //mvn test -Dgroups=fast

//    @Test
//    @Tag("fast")
//    void fastTest() {
//        assertEquals(2, Math.addExact(1, 1));
//    }
//
//    @Test
//    @Tag("slow")
//    void slowTest() {
//        assertEquals(4, Math.multiplyExact(2, 2));
//    }
//
//    @Test
//    @Tag("fast")
//    @Tag("important")
//    void importantFastTest() {
//        assertEquals(3, Math.addExact(1, 2));
//    }
}