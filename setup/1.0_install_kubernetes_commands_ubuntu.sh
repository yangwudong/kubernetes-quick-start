#!/bin/bash
# curl -sfL https://raw.githubusercontent.com/yangwudong/kubernetes-quick-start/master/setup/1.0_install_kubernetes_commands_ubuntu.sh | sh -
sudo apt -y update

# 配置阿里Kubernetes镜像源
curl https://mirrors.aliyun.com/kubernetes/apt/doc/apt-key.gpg | sudo apt-key add - 
sudo bash -c 'cat <<EOF >/etc/apt/sources.list.d/kubernetes.list
deb https://mirrors.aliyun.com/kubernetes/apt/ kubernetes-xenial main
EOF'

# 安装 Kubernetes commands
sudo apt -y update && sudo apt -y install -y kubelet kubeadm kubectl
