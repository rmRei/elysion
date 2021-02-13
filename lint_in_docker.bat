::コードの整形と構文チェックの実行
cd %~dp0
cmd /k docker-compose exec front yarn lint
