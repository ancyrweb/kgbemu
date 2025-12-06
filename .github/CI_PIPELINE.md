# CI/CD Pipeline

## Overview
This project uses GitHub Actions for continuous integration and deployment. The pipeline automatically runs on every push to the `main` branch and on pull requests targeting `main`.

## Workflow Configuration

**File**: `.github/workflows/ci.yml`

### What it does:
1. **Checkout Code** - Retrieves the latest code from the repository
2. **Set up JDK 25** - Configures OpenJDK 25.0.1 with Temurin distribution
3. **Grant Permissions** - Makes the Gradle wrapper executable
4. **Run Tests** - Executes all unit tests using `./gradlew test`
5. **Build Project** - Builds the JAR artifact using `./gradlew build`
6. **Upload Artifacts** - Stores test results, reports, and build artifacts for 30 days

### Artifacts Available:
- **test-results**: Raw test results XML files
- **test-reports**: HTML test reports
- **gameboyemulator-jar**: Built JAR file(s)

## Viewing Results

After pushing to GitHub:
1. Go to the **Actions** tab in your repository
2. Click on the latest workflow run
3. View test results and download artifacts

## Local Testing

To verify the build works locally before pushing:
```bash
./gradlew clean test build
```

## Requirements
- GitHub repository with Actions enabled
- OpenJDK 25 (automatically provided by GitHub Actions)
- Gradle wrapper (already included)

