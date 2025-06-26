@echo off
echo Running JUnit/Mockito Tests...
echo.

echo Running EtudiantServiceImplTest...
call mvnw.cmd test -Dtest=EtudiantServiceImplTest -q
echo.

echo Running AdvancedMockitoExamplesTest...
call mvnw.cmd test -Dtest=AdvancedMockitoExamplesTest -q
echo.

echo Running EtudiantRestControllerTest...
call mvnw.cmd test -Dtest=EtudiantRestControllerTest -q
echo.

echo Running EtudiantRepositoryTest...
call mvnw.cmd test -Dtest=EtudiantRepositoryTest -q
echo.

echo All tests completed!
pause
