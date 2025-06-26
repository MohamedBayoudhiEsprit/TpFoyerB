@echo off
echo ======================================
echo Testing Individual Test Classes
echo ======================================
echo.

echo [1/5] Testing MinimalEtudiantServiceTest...
call mvnw.cmd test -Dtest=MinimalEtudiantServiceTest -q > temp_result.txt 2>&1
if %ERRORLEVEL% equ 0 (
    echo [PASS] MinimalEtudiantServiceTest
) else (
    echo [FAIL] MinimalEtudiantServiceTest
    type temp_result.txt
)
echo.

echo [2/5] Testing EtudiantServiceImplTest...
call mvnw.cmd test -Dtest=EtudiantServiceImplTest -q > temp_result.txt 2>&1
if %ERRORLEVEL% equ 0 (
    echo [PASS] EtudiantServiceImplTest
) else (
    echo [FAIL] EtudiantServiceImplTest
    type temp_result.txt
)
echo.

echo [3/5] Testing AdvancedMockitoExamplesTest...
call mvnw.cmd test -Dtest=AdvancedMockitoExamplesTest -q > temp_result.txt 2>&1
if %ERRORLEVEL% equ 0 (
    echo [PASS] AdvancedMockitoExamplesTest
) else (
    echo [FAIL] AdvancedMockitoExamplesTest
    type temp_result.txt
)
echo.

echo [4/5] Testing EtudiantRestControllerTest...
call mvnw.cmd test -Dtest=EtudiantRestControllerTest -q > temp_result.txt 2>&1
if %ERRORLEVEL% equ 0 (
    echo [PASS] EtudiantRestControllerTest
) else (
    echo [FAIL] EtudiantRestControllerTest
    type temp_result.txt
)
echo.

echo [5/5] Testing EtudiantRepositoryTest...
call mvnw.cmd test -Dtest=EtudiantRepositoryTest -q > temp_result.txt 2>&1
if %ERRORLEVEL% equ 0 (
    echo [PASS] EtudiantRepositoryTest
) else (
    echo [FAIL] EtudiantRepositoryTest
    type temp_result.txt
)
echo.

del temp_result.txt >nul 2>&1

echo ======================================
echo Test Summary Completed
echo ======================================
pause
