#!/bin/bash
# curl -sfL https://raw.githubusercontent.com/yangwudong/kubernetes-quick-start/master/setup/0.2_install_docker_ubuntu.sh | sh -
sudo apt -y update

# 安装系统库依赖
sudo apt -y install \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg-agent \
    software-properties-common

# 配置Docker源
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo add-apt-repository \
    "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
   $(lsb_release -cs) \
   stable"

# 配置阿里云Docker安装源 - 如果网络连接不畅，可以注释上面官方源，选择阿里镜像
# curl -fsSL https://mirrors.aliyun.com/docker-ce/linux/ubuntu/gpg | sudo apt-key add -
# sudo add-apt-repository "deb [arch=amd64] https://mirrors.aliyun.com/docker-ce/linux/ubuntu $(lsb_release -cs) stable"

# 安装 Docker
sudo apt -y update && sudo apt -y install docker-ce docker-ce-cli containerd.io

# 当前用户添加到 Docker组权限
sudo usermod -aG docker $USER

# 修改 Docker配置
# 其他国内Docker镜像
# "registry-mirrors": ["https://<YOUR_ALICLOUD_CODE>.mirror.aliyuncs.com"]
# "registry-mirrors": ["https://hub-mirror.c.163.com"]
# "registry-mirrors": ["https://reg-mirror.qiniu.com"]
sudo bash -c 'cat > /etc/docker/daemon.json <<EOF
{
    "exec-opts": ["native.cgroupdriver=systemd"],
    "log-driver": "json-file",
    "log-opts": {
        "max-size": "100m"
    },
    "storage-driver": "overlay2",
    "storage-opts": [
        "overlay2.override_kernel_check=true"
    ],
    "registry-mirrors": ["https://mirror.ccs.tencentyun.com"]
}
EOF'

# 重启 Docker
sudo service docker restart