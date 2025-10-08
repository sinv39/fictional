@echo off
REM 科幻界社团管理系统 - Windows一键Docker部署脚本
chcp 65001 >nul

echo ==========================================
echo 科幻界社团管理系统 - Docker一键部署
echo ==========================================
echo.

REM 步骤1：检查Docker
echo [1/6] 检查Docker环境...
docker --version >nul 2>&1
if errorlevel 1 (
    echo 错误：未安装Docker，请先安装Docker Desktop
    pause
    exit /b 1
)

docker-compose --version >nul 2>&1
if errorlevel 1 (
    docker compose version >nul 2>&1
    if errorlevel 1 (
        echo 错误：未安装Docker Compose
        pause
        exit /b 1
    )
)
echo ✓ Docker环境检查通过
echo.

REM 步骤2：停止旧容器
echo [2/6] 停止并删除旧容器...
docker-compose down 2>nul
echo ✓ 旧容器已清理
echo.

REM 步骤3：检查编译产物
echo [3/6] 检查编译产物...

REM 检查后端jar包
if not exist "..\backend\target\fictional-1.0-SNAPSHOT.jar" (
    echo 错误：后端jar包不存在，请先在本地编译
    echo 执行：cd backend ^&^& mvn clean package -DskipTests
    pause
    exit /b 1
)
echo ✓ 后端jar包已就绪

REM 检查前端dist目录
if not exist "..\frontend\dist" (
    echo 错误：前端dist目录不存在，请先在本地构建
    echo 执行：cd frontend ^&^& npm run build
    pause
    exit /b 1
)
echo ✓ 前端dist目录已就绪

echo ✓ 编译产物检查完成
echo.

REM 步骤4：构建Docker镜像
echo [4/6] 构建Docker镜像...
echo 构建后端镜像...
docker-compose build fictional-backend
if errorlevel 1 (
    echo 错误：后端镜像构建失败
    pause
    exit /b 1
)

echo 构建前端镜像...
docker-compose build fictional-frontend
if errorlevel 1 (
    echo 错误：前端镜像构建失败
    pause
    exit /b 1
)
echo ✓ Docker镜像构建完成
echo.

REM 步骤5：启动容器
echo [5/6] 启动容器...
docker-compose up -d
if errorlevel 1 (
    echo 错误：容器启动失败
    pause
    exit /b 1
)
echo ✓ 容器启动成功
echo.

echo [5.5/6] 等待服务就绪...
timeout /t 20 /nobreak >nul
echo ✓ 服务已就绪
echo.

REM 步骤6：等待服务启动
echo [6/6] 等待服务启动...
echo 等待后端服务启动（最多60秒）...
timeout /t 10 /nobreak >nul
echo ✓ 后端服务已启动
echo.

echo 等待前端服务启动（最多30秒）...
timeout /t 5 /nobreak >nul
echo ✓ 前端服务已启动
echo.

REM 显示状态
echo ==========================================
echo 部署完成！
echo ==========================================
echo.

echo 容器状态：
docker-compose ps
echo.

echo 访问地址：
echo   前端: http://localhost:10100
echo   后端API: http://localhost:10104
echo.

echo 管理员账号：
echo   学号: admin
echo   密码: 12345678
echo.

echo 常用命令：
echo   查看日志: docker-compose logs -f
echo   查看后端日志: docker-compose logs -f fictional-backend
echo   查看前端日志: docker-compose logs -f fictional-frontend
echo   停止服务: docker-compose stop
echo   启动服务: docker-compose start
echo   重启服务: docker-compose restart
echo   删除容器: docker-compose down
echo.

echo 🎉 部署成功！祝使用愉快！
echo.

pause

