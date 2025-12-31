# Code Review: Console Hangman Game

## Overview
This is a console-based Hangman game implementation in Java. The game supports both user-provided words and random words from a dictionary file. The code is written in Russian (comments, variable names, UI text).

---

## 1. Architecture and Design

### ✅ Strengths
- **Good separation of concerns**: The code is organized into distinct classes with clear responsibilities
  - `Main` - Entry point
  - `Game` - Core game logic
  - `CLIGameProvider` - Console UI and user interaction
  - `HangmanAsciiPrinter` - ASCII art rendering
  - `IGameProvider` - Interface for game providers
- **Interface usage**: `IGameProvider` interface allows for potential future implementations (e.g., GUI version)
- **Clean package structure**: Code is organized under `ru.trukhmanov.core`

### ⚠️ Areas for Improvement
- **Game class visibility**: Methods in `Game` class are `protected` but should be `public` or package-private. Since these methods are accessed from a different class (`CLIGameProvider`), they need appropriate visibility
- **Missing abstraction**: No interface or abstract class for `Game`, which could be useful for different game variations
- **Hard-coded constants**: Magic numbers like `maxErrorCount = 6` should be class constants

---

## 2. Code Quality

### ✅ Strengths
- **Clear method names**: Methods like `letterCheckAndReplace`, `inputIsValid`, `printCurrentState` are self-descriptive
- **Good use of modern Java features**: Uses text blocks for ASCII art (Java 15+), enhanced switch expressions
- **Proper encoding handling**: Uses `StandardCharsets.UTF_8` when reading files

### ⚠️ Issues and Improvements Needed

#### Critical Issues
1. **String concatenation in loops** (Performance issue in `Game.java`, line 29-32):
   ```java
   String maskHiddenWordString = "";
   for (var letter : maskHiddenWord) {
       maskHiddenWordString += letter.toString();
   }
   ```
   **Problem**: Creates new String objects in each iteration, leading to O(n²) complexity
   **Fix**: Use `StringBuilder` or `String.join()`
   ```java
   return String.join("", maskHiddenWord.stream()
       .map(String::valueOf).toArray(String[]::new));
   ```

2. **Similar string concatenation issue** (line 72 in `Game.java`):
   ```java
   misspelledLetters += letter + " ";
   ```
   **Fix**: Use `StringBuilder` or `List<Character>` with joining

3. **Resource leak** (`CLIGameProvider.java`, lines 92-105):
   ```java
   InputStream inputStream = classloader.getResourceAsStream("words.txt");
   InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
   BufferedReader reader = new BufferedReader(streamReader);
   ```
   **Problem**: Resources are not closed properly
   **Fix**: Use try-with-resources:
   ```java
   try (InputStream inputStream = classloader.getResourceAsStream("words.txt");
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader)) {
       // ... code
   }
   ```

4. **Potential NullPointerException** (`CLIGameProvider.java`, line 95):
   ```java
   InputStream inputStream = classloader.getResourceAsStream("words.txt");
   ```
   **Problem**: If `words.txt` is not found, `inputStream` will be null, causing NPE on line 96
   **Fix**: Add null check and proper error handling

5. **Scanner not closed** (`CLIGameProvider.java`, line 15):
   ```java
   private final Scanner scanner = new Scanner(System.in);
   ```
   **Problem**: Scanner is never closed. While closing `System.in` can cause issues, consider documenting this or using try-with-resources in methods

#### Code Style Issues
1. **Inconsistent spacing** (line 18 in `Game.java`):
   ```java
   protected String getMisspelledLetters() {return misspelledLetters;}
   ```
   Should have space after opening brace

2. **Inconsistent variable naming**:
   - Some variables use camelCase: `errorCounter`, `hiddenWord`
   - Some use descriptive names: `maskHiddenWord`
   - Generally good, but could be more consistent

3. **Magic numbers**:
   - Line 8: `new ArrayList<>(15)` - why 15?
   - Line 73: `maxErrorCount = 6` - should be a class constant
   
4. **Typo in comment** (line 79):
   ```java
   else {      //проверка выйгрыша
   ```
   Should be "выигрыша" (с одной 'й')

---

## 3. Functionality and Logic

### ✅ Strengths
- **Game state management**: Properly tracks game end conditions
- **Input validation**: Checks for valid single letter input
- **Multiple game modes**: Supports both custom words and random words

### ⚠️ Issues

1. **Case sensitivity handling inconsistency**:
   - Game converts input to uppercase (line 37 and 54)
   - But the word list contains lowercase Russian words
   - Works because both are converted to uppercase, but could be clearer

2. **Return value confusion** (`Game.play()` method):
   Returns: -2, -1, 0, 1
   - Would be better with an enum:
   ```java
   enum GameResult {
       REPEATED_MISTAKE, GAME_LOST, CONTINUE, GAME_WON
   }
   ```

3. **No validation for empty word**:
   In `playWithYourWord()`, doesn't check if word is empty or contains invalid characters

4. **Repeated error handling is incomplete**:
   - Line 50 checks for repeated misspelled letters
   - But doesn't check for repeated correct letters (user can enter same correct letter multiple times)

5. **ArrayList size initialization**:
   - `new ArrayList<>(15)` suggests expected capacity, but words can be any length
   - This is a minor optimization issue

---

## 4. Security Considerations

### ⚠️ Issues

1. **No input sanitization for file paths**: 
   - If extended to allow custom word files, could be vulnerable to path traversal

2. **Resource file exposure**:
   - `words.txt` is in resources, which is fine, but no validation of file contents

3. **No input length limits**:
   - User can input extremely long words in "playWithYourWord", potentially causing memory issues

### ✅ Good Practices
- Uses classpath resource loading (not direct file paths)
- Validates input format with regex

---

## 5. Testing

### ❌ Critical Gap
- **No unit tests**: Project has JUnit 5 dependency but no test files
- **No integration tests**: Game flow is not tested

### Recommended Tests
1. **Game class tests**:
   - Test correct letter guessing
   - Test incorrect letter guessing
   - Test repeated letters
   - Test win condition
   - Test lose condition
   - Test case insensitivity

2. **CLIGameProvider tests**:
   - Test input validation
   - Test random word selection
   - Mock user input for game flow testing

3. **HangmanAsciiPrinter tests**:
   - Test each error count produces correct ASCII art

---

## 6. Documentation

### ⚠️ Areas for Improvement

1. **No README file**: 
   - Should include:
     - Project description
     - How to build and run
     - How to play
     - Requirements (Java version)

2. **Comments are in Russian**:
   - Consider using English for broader audience
   - Or provide bilingual documentation

3. **Javadoc missing**:
   - Public/protected methods should have Javadoc comments
   - Especially for the `play()` method return values

4. **Build instructions missing**:
   - No documentation on how to build with Gradle
   - No main class specification in build file for easy running

---

## 7. Performance

### ⚠️ Issues

1. **String concatenation in loops** (mentioned above)
   - O(n²) complexity for building masked word string
   - O(n²) for building misspelled letters string

2. **Repeated file reading**:
   - Loads words.txt on every random game
   - Should load once and cache

3. **ArrayList iteration**:
   - Lines 52-56: Could use `indexOf` or `contains` more efficiently

### Potential Optimizations
```java
// Cache words list as static field
private static List<String> cachedWords;

private static synchronized List<String> loadWords() {
    if (cachedWords == null) {
        // load words
    }
    return cachedWords;
}
```

---

## 8. Error Handling

### ⚠️ Issues

1. **Generic exception handling**:
   ```java
   catch (IOException e) {
       throw new RuntimeException(e);
   }
   ```
   - Should provide user-friendly error message
   - Could recover more gracefully (e.g., fall back to manual word input)

2. **No handling for missing words.txt**:
   - Game crashes if file is missing

3. **No validation for word file content**:
   - Empty file would cause issues
   - File with invalid characters not handled

### Recommended Approach
```java
private Optional<Game> createRandomWordGame() {
    try {
        List<String> words = loadWords();
        if (words.isEmpty()) {
            System.out.println("Файл слов пуст!");
            return Optional.empty();
        }
        return Optional.of(new Game(words.get(random.nextInt(words.size()))));
    } catch (IOException e) {
        System.out.println("Ошибка загрузки файла слов: " + e.getMessage());
        return Optional.empty();
    }
}
```

---

## 9. Maintainability

### ✅ Strengths
- Clean class structure
- Each class has single responsibility
- Good use of private methods for internal logic

### ⚠️ Improvements Needed

1. **Extract constants**:
   ```java
   public class Game {
       private static final int MAX_ERRORS = 6;
       private static final int INITIAL_CAPACITY = 15;
       private static final char MASK_CHARACTER = '_';
   }
   ```

2. **Extract game configuration**:
   - Max errors, mask character, etc. could be configurable

3. **Better method organization**:
   - Group related methods together
   - Add section comments

---

## 10. Build Configuration

### ⚠️ Issues

1. **No main class specified in Gradle**:
   ```kotlin
   application {
       mainClass.set("ru.trukhmanov.Main")
   }
   ```

2. **No run task configuration**:
   - Should add application plugin for easy running

3. **No manifest configuration**:
   - For building executable JAR

### Recommended Addition to build.gradle.kts:
```kotlin
plugins {
    id("java")
    id("application")
}

application {
    mainClass.set("ru.trukhmanov.Main")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "ru.trukhmanov.Main"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
```

---

## Summary of Priority Issues

### 🔴 High Priority (Must Fix)
1. Resource leak in file reading (use try-with-resources)
2. Potential NullPointerException for missing words.txt
3. String concatenation performance issues
4. Add unit tests (0% coverage currently)

### 🟡 Medium Priority (Should Fix)
1. Add README with build/run instructions
2. Use enums instead of magic return values
3. Extract constants (MAX_ERRORS, etc.)
4. Add Javadoc comments
5. Configure Gradle for easy running
6. Add input validation for custom words

### 🟢 Low Priority (Nice to Have)
1. Cache word list for better performance
2. Add more descriptive error messages
3. Consider English comments for wider audience
4. Add configuration file for game settings
5. Improve code formatting consistency

---

## Overall Assessment

**Grade: B- (75/100)**

**Strengths:**
- Clean architecture with good separation of concerns
- Functional implementation of the game logic
- Good use of modern Java features
- Proper Unicode/encoding handling

**Weaknesses:**
- No tests (critical gap)
- Resource management issues
- Performance concerns with string concatenation
- Lack of documentation
- Missing error handling

**Recommendation:**
This is a solid foundation for a console game, but needs work before being production-ready:
1. Add comprehensive tests
2. Fix resource leaks and null pointer issues
3. Add proper documentation
4. Improve error handling
5. Optimize string operations

With these improvements, this would be a well-structured, maintainable console application suitable for educational purposes or as a foundation for more advanced features.
