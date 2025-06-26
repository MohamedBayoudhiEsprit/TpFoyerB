# 🔧 Advanced Mockito Testing Guide

## Overview
This guide demonstrates advanced Mockito features used in our Spring Boot test suite.

## 📋 Key Mockito Annotations

### `@Mock`
- Creates a mock object
- All methods return default values (null, 0, false, empty collections)
- Must be used with `@ExtendWith(MockitoExtension.class)`

```java
@Mock
private EtudiantRepository etudiantRepository;
```

### `@InjectMocks`
- Injects mocks into the tested object
- Automatically finds and injects @Mock objects as dependencies

```java
@InjectMocks
private EtudiantServiceImpl etudiantService;
```

### `@Spy`
- Creates a spy (partial mock)
- Calls real methods unless explicitly stubbed
- Useful for testing legacy code or when you need real behavior

```java
@Spy
private List<Etudiant> spyList = new ArrayList<>();
```

### `@Captor`
- Captures method arguments for verification
- Useful for complex argument verification

```java
@Captor
private ArgumentCaptor<Etudiant> etudiantCaptor;
```

## 🎯 Stubbing Techniques

### Basic Stubbing
```java
when(repository.findById(1L)).thenReturn(Optional.of(etudiant));
```

### Multiple Return Values
```java
when(repository.count())
    .thenReturn(1L)
    .thenReturn(2L)
    .thenReturn(3L);
```

### Exception Throwing
```java
when(repository.findById(999L))
    .thenThrow(new RuntimeException("Not found"));
```

### Custom Answers
```java
when(repository.save(any(Etudiant.class))).thenAnswer(invocation -> {
    Etudiant arg = invocation.getArgument(0);
    arg.setIdEtudiant(100L);
    return arg;
});
```

## 🔍 Argument Matchers

### Basic Matchers
- `any()` - matches any object
- `anyLong()` - matches any long
- `anyString()` - matches any string
- `eq(value)` - matches exact value

### Custom Matchers
```java
argThat(student -> student.getNomEtudiant().startsWith("Test"))
```

## ✅ Verification Techniques

### Basic Verification
```java
verify(repository).findById(1L);
verify(repository, times(2)).save(any());
verify(repository, never()).deleteAll();
verify(repository, atLeastOnce()).findAll();
```

### Timeout Verification
```java
verify(repository, timeout(1000)).save(any());
```

### Order Verification
```java
InOrder inOrder = inOrder(repository);
inOrder.verify(repository).findById(1L);
inOrder.verify(repository).save(any());
```

### No Interactions Verification
```java
verifyNoInteractions(repository);
verifyNoMoreInteractions(repository);
```

## 📦 Argument Captors

### Capturing Single Arguments
```java
@Captor
private ArgumentCaptor<Etudiant> etudiantCaptor;

// In test
verify(repository).save(etudiantCaptor.capture());
Etudiant captured = etudiantCaptor.getValue();
assertEquals("ExpectedName", captured.getNomEtudiant());
```

### Capturing Multiple Arguments
```java
verify(repository, times(3)).save(etudiantCaptor.capture());
List<Etudiant> allCaptured = etudiantCaptor.getAllValues();
assertEquals(3, allCaptured.size());
```

## 🕵️ Spy Objects

### Creating Spies
```java
List<String> spyList = spy(new ArrayList<>());
EtudiantRepository spyRepo = spy(etudiantRepository);
```

### Stubbing Spies
```java
doReturn("mocked").when(spyList).get(0);
// or
when(spyList.get(0)).thenReturn("mocked"); // Be careful with this
```

## 🚨 Exception Testing

### Testing Exceptions
```java
@Test
void testMethodThrowsException() {
    when(repository.save(any())).thenThrow(RuntimeException.class);
    
    assertThrows(RuntimeException.class, () -> {
        service.addEtudiant(etudiant);
    });
}
```

## 🎨 Advanced Features

### Deep Stubs
```java
EtudiantRepository mock = mock(EtudiantRepository.class, RETURNS_DEEP_STUBS);
```

### Custom Mock Settings
```java
EtudiantRepository mock = mock(EtudiantRepository.class, 
    withSettings()
        .name("CustomRepo")
        .verboseLogging()
        .defaultAnswer(RETURNS_SMART_NULLS));
```

### Mock Reset
```java
reset(repository); // Clear all interactions and stubbing
```

## 📊 Best Practices

### 1. Test Structure (AAA Pattern)
```java
@Test
void testMethod_ShouldExpectedBehavior_WhenCondition() {
    // Arrange (Given)
    when(repository.method()).thenReturn(value);
    
    // Act (When)
    Result result = service.method();
    
    // Assert (Then)
    assertEquals(expected, result);
    verify(repository).method();
}
```

### 2. Use Descriptive Test Names
- `testAddEtudiant_ShouldReturnSavedEtudiant_WhenValidInput`
- `testRetrieveEtudiant_ShouldThrowException_WhenIdNotFound`

### 3. Verify Important Interactions
```java
verify(repository, times(1)).save(any());
verifyNoMoreInteractions(repository);
```

### 4. Use Argument Captors for Complex Verification
```java
verify(repository).save(etudiantCaptor.capture());
Etudiant captured = etudiantCaptor.getValue();
// Assert on captured object properties
```

### 5. Keep Tests Simple and Focused
- One concept per test
- Clear Given-When-Then structure
- Meaningful assertions

## 🏃‍♂️ Running Tests

### Run All Tests
```bash
./mvnw.cmd test
```

### Run Specific Test Class
```bash
./mvnw.cmd test -Dtest=EtudiantServiceImplTest
```

### Run Single Test Method
```bash
./mvnw.cmd test -Dtest=EtudiantServiceImplTest#testAddEtudiant_ShouldReturnSavedEtudiant
```

### Run Tests with Coverage
```bash
./mvnw.cmd test jacoco:report
```

## 📚 Additional Resources

- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Spring Boot Testing Guide](https://spring.io/guides/gs/testing-web/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
