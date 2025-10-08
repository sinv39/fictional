#!/bin/bash
# 科幻界社团管理系统 - 一键Docker部署脚本

set -e

echo "=========================================="
echo "科幻界社团管理系统 - Docker一键部署"
echo "=========================================="
echo ""

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 步骤1：检查Docker和Docker Compose
echo "${YELLOW}[1/6] 检查Docker环境...${NC}"
if ! which docker > /dev/null 2>&1; then
    echo "${RED}错误：未安装Docker，请先安装Docker${NC}"
    exit 1
fi

if ! which docker-compose > /dev/null 2>&1 && ! docker compose version > /dev/null 2>&1; then
    echo "${RED}错误：未安装Docker Compose，请先安装Docker Compose${NC}"
    exit 1
fi
echo "${GREEN}✓ Docker环境检查通过${NC}"
echo ""

# 步骤2：停止并删除旧容器
echo "${YELLOW}[2/6] 停止并删除旧容器...${NC}"
docker-compose down 2>/dev/null || true
echo "${GREEN}✓ 旧容器已清理${NC}"
echo ""

# 步骤3：检查编译产物
echo "${YELLOW}[3/6] 检查编译产物...${NC}"

# 检查后端jar包
if [ ! -f "../backend/target/fictional-1.0-SNAPSHOT.jar" ]; then
    echo "${RED}错误：后端jar包不存在，请先在本地编译${NC}"
    echo "执行：cd backend && mvn clean package -DskipTests"
    exit 1
fi
echo "✓ 后端jar包已就绪"

# 检查前端dist目录
if [ ! -d "../frontend/dist" ]; then
    echo "${RED}错误：前端dist目录不存在，请先在本地构建${NC}"
    echo "执行：cd frontend && npm run build"
    exit 1
fi
echo "✓ 前端dist目录已就绪"

echo "${GREEN}✓ 编译产物检查完成${NC}"
echo ""

# 步骤4：构建Docker镜像
echo "${YELLOW}[4/6] 构建Docker镜像...${NC}"
echo "构建后端镜像..."
docker-compose build fictional-backend
echo "构建前端镜像..."
docker-compose build fictional-frontend
echo "${GREEN}✓ Docker镜像构建完成${NC}"
echo ""

# 步骤5：启动容器
echo "${YELLOW}[5/6] 启动容器...${NC}"
docker-compose up -d
echo "${GREEN}✓ 容器启动成功${NC}"
echo ""

# 步骤6：等待服务启动
echo "${YELLOW}[6/6] 等待服务启动...${NC}"
echo "等待后端服务启动（最多60秒）..."
i=1
while [ $i -le 60 ]; do
    if curl -sf http://localhost:10104/api/cover/home > /dev/null 2>&1; then
        echo "${GREEN}✓ 后端服务启动成功${NC}"
        break
    fi
    if [ $i -eq 60 ]; then
        echo "${RED}警告：后端服务启动超时，请检查日志${NC}"
    fi
    sleep 1
    i=$((i + 1))
done

echo "等待前端服务启动（最多30秒）..."
i=1
while [ $i -le 30 ]; do
    if curl -sf http://localhost:10100 > /dev/null 2>&1; then
        echo "${GREEN}✓ 前端服务启动成功${NC}"
        break
    fi
    if [ $i -eq 30 ]; then
        echo "${RED}警告：前端服务启动超时，请检查日志${NC}"
    fi
    sleep 1
    i=$((i + 1))
done
echo ""

# 显示容器状态
echo "=========================================="
echo "${GREEN}部署完成！${NC}"
echo "=========================================="
echo ""
echo "容器状态："
docker-compose ps
echo ""

echo "访问地址："
SERVER_IP=$(hostname -I 2>/dev/null | awk '{print $1}')
if [ -z "$SERVER_IP" ]; then
    SERVER_IP=$(ip addr show | grep 'inet ' | grep -v '127.0.0.1' | head -1 | awk '{print $2}' | cut -d/ -f1)
fi
echo "  前端: ${GREEN}http://localhost:10100${NC} 或 ${GREEN}http://${SERVER_IP}:10100${NC}"
echo "  后端API: ${GREEN}http://localhost:10104${NC}"
echo "  MinIO: ${GREEN}http://123.60.40.72:10102${NC} (外部服务)"
echo "  MySQL: ${GREEN}123.60.40.72:10101${NC} (外部服务)"
echo ""

echo "管理员账号："
echo "  学号: ${GREEN}admin${NC}"
echo "  密码: ${GREEN}12345678${NC}"
echo ""

echo "常用命令："
echo "  查看日志: docker-compose logs -f"
echo "  查看后端日志: docker-compose logs -f fictional-backend"
echo "  查看前端日志: docker-compose logs -f fictional-frontend"
echo "  停止服务: docker-compose stop"
echo "  启动服务: docker-compose start"
echo "  重启服务: docker-compose restart"
echo "  删除容器: docker-compose down"
echo ""

echo "${GREEN}🎉 部署成功！祝使用愉快！${NC}"

