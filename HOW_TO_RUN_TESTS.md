# 🏃‍♂️ How to Run Tests - Complete Guide

## 🚀 Quick Start

### 1. PowerShell/Terminal Commands

#### Run ALL Tests
```powershell
./mvnw.cmd test
```

#### Run Specific Test Class
```powershell
./mvnw.cmd test -Dtest=EtudiantServiceImplTest
```

#### Run Single Test Method
```powershell
./mvnw.cmd test -Dtest=EtudiantServiceImplTest#testAddEtudiant_WithArgumentCaptor_ShouldCaptureCorrectArgument
```

#### Run Multiple Test Classes
```powershell
./mvnw.cmd test -Dtest=EtudiantServiceImplTest,AdvancedMockitoExamplesTest
```

#### Run Tests with Pattern
```powershell
./mvnw.cmd test -Dtest="*ServiceImplTest"
```

## 🎯 VS Code Integration

### Using VS Code Tasks (Ctrl+Shift+P → "Tasks: Run Task")

1. **Run All Tests** - Runs entire test suite
2. **Run Etudiant Service Tests** - Runs only service layer tests
3. **Run Advanced Mockito Examples** - Runs advanced Mockito examples
4. **Run Repository Tests** - Runs JPA repository tests
5. **Run Controller Tests** - Runs REST controller tests
6. **Run Tests with Coverage** - Runs tests and generates coverage report

### Using VS Code Test Explorer

1. Install **Test Runner for Java** extension
2. Open Test Explorer panel (Testing icon in sidebar)
3. Click ▶️ next to any test to run it
4. Right-click for more options (Debug, Run All, etc.)

## 📊 Test Output Understanding

### ✅ Successful Test Output
```
[INFO] Tests run: 15, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

### ❌ Failed Test Output
```
[ERROR] Tests run: 15, Failures: 1, Errors: 0, Skipped: 0
[ERROR] Failures:
[ERROR]   EtudiantServiceImplTest.testMethodName:42 Expected: <value> but was: <other>
```

### ⚠️ Test with Errors
```
[ERROR] Tests run: 15, Failures: 0, Errors: 1, Skipped: 0
[ERROR] Errors:
[ERROR]   EtudiantServiceImplTest.testMethodName:25 NullPointerException
```

## 🔧 Advanced Test Commands

### Run Tests with Detailed Output
```powershell
./mvnw.cmd test -Dtest=EtudiantServiceImplTest -X
```

### Run Tests and Ignore Failures (Continue Testing)
```powershell
./mvnw.cmd test -Dmaven.test.failure.ignore=true
```

### Run Tests with Specific Profile
```powershell
./mvnw.cmd test -Ptest
```

### Run Tests with System Properties
```powershell
./mvnw.cmd test -Dspring.profiles.active=test
```

### Skip Tests (for building without testing)
```powershell
./mvnw.cmd package -DskipTests
```

## 📈 Test Coverage

### Generate Coverage Report
```powershell
./mvnw.cmd test jacoco:report
```

### View Coverage Report
Open `target/site/jacoco/index.html` in browser

### Coverage with Specific Test
```powershell
./mvnw.cmd test -Dtest=EtudiantServiceImplTest jacoco:report
```

## 🐛 Debugging Tests

### Debug in VS Code
1. Set breakpoints in test code
2. Right-click test method → "Debug Test"
3. Use Debug Console to inspect variables

### Debug with Maven
```powershell
./mvnw.cmd test -Dtest=EtudiantServiceImplTest -Dmaven.surefire.debug
```
Then connect debugger to port 5005

### Print Debug Information
```powershell
./mvnw.cmd test -Dtest=EtudiantServiceImplTest -Dmaven.surefire.debug.args="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000"
```

## 📁 Test File Locations

Our current test structure:
```
src/test/java/tn/esprit/tpfoyer/
├── service/
│   ├── EtudiantServiceImplTest.java           ← Main service tests
│   └── AdvancedMockitoExamplesTest.java       ← Advanced Mockito examples
├── control/
│   └── EtudiantRestControllerTest.java        ← REST API tests
└── repository/
    └── EtudiantRepositoryTest.java            ← Database tests
```

## 🎨 Test Categories by Type

### Unit Tests (Fast)
```powershell
./mvnw.cmd test -Dtest="*ServiceImplTest"
```

### Integration Tests (Slower)
```powershell
./mvnw.cmd test -Dtest="*RestControllerTest"
```

### Repository Tests (Medium)
```powershell
./mvnw.cmd test -Dtest="*RepositoryTest"
```

## 🔍 Filtering Tests

### Run Only Failed Tests
```powershell
./mvnw.cmd test -Dsurefire.rerunFailingTestsCount=2
```

### Run Tests by Tag/Group (if using @Tag)
```powershell
./mvnw.cmd test -Dgroups="unit"
```

### Exclude Specific Tests
```powershell
./mvnw.cmd test -Dtest="!AdvancedMockitoExamplesTest"
```

## 📝 Test Reports

### Surefire Reports Location
- `target/surefire-reports/` - XML and TXT reports
- `target/surefire-reports/TEST-*.xml` - JUnit XML format
- `target/surefire-reports/*.txt` - Text format

### View HTML Report
1. Run tests with: `./mvnw.cmd surefire-report:report`
2. Open: `target/site/surefire-report.html`

## 🚨 Troubleshooting

### Common Issues & Solutions

#### "JAVA_HOME not found"
```powershell
$env:JAVA_HOME = "C:\Program Files\Java\jdk-17"
```

#### "Tests compilation failure"
```powershell
./mvnw.cmd clean compile test-compile
```

#### "Out of memory"
```powershell
$env:MAVEN_OPTS = "-Xmx512m"
./mvnw.cmd test
```

#### "Port already in use" (for integration tests)
```powershell
./mvnw.cmd test -Dserver.port=0
```

## 🎯 Best Practices

### 1. Run Tests Frequently
- Run specific test while developing: `./mvnw.cmd test -Dtest=YourTest`
- Run full suite before committing: `./mvnw.cmd test`

### 2. Use Descriptive Test Names
- Good: `testAddEtudiant_ShouldReturnSavedEtudiant_WhenValidInput`
- Bad: `testAdd`

### 3. Keep Tests Fast
- Unit tests should run in milliseconds
- Use `@MockBean` instead of real databases when possible

### 4. Test in Isolation
- Each test should be independent
- Use `@DirtiesContext` if needed for integration tests

### 5. Use Proper Assertions
```java
// Good
assertEquals("Expected message", actual.getMessage());
assertThat(actual).isNotNull().hasSize(2);

// Avoid
assertTrue(actual.getMessage().equals("Expected"));
```

## 📚 Quick Reference

| Command | Description |
|---------|-------------|
| `./mvnw.cmd test` | Run all tests |
| `./mvnw.cmd test -Dtest=ClassName` | Run specific class |
| `./mvnw.cmd test -Dtest=Class#method` | Run specific method |
| `./mvnw.cmd clean test` | Clean and run tests |
| `./mvnw.cmd test jacoco:report` | Run with coverage |
| `./mvnw.cmd test -X` | Verbose output |
| `./mvnw.cmd test -DskipTests` | Skip tests |

Now you're ready to run your Mockito tests! 🚀
