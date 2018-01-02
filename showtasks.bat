call runcrud.bat
if "%ERRORLEVEL%" == "0" goto openbrowser
echo.
echo RUN CRUD script is not working - please check runcrud error messages
goto fail

:openbrowser
start chrome "http://localhost:8080/crud/v1/task/getTasks"
goto end

:fail
echo.
echo There were errors

:end
echo.
echo Work is finished
