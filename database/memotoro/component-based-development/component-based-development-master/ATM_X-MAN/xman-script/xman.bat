set CODE="%LOCALAPPDATA%\X-MAN Tool\Exported"
set REPO="%LOCALAPPDATA%\X-MAN Tool\Repository"

mkdir %CODE%
mkdir %REPO%
copy repos.db %REPO%

setx XMAN_CODE_DIR %CODE%
setx XMAN_REPOSITORY %REPO%