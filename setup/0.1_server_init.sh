#!/bin/bash
# curl -sfL https://raw.githubusercontent.com/yangwudong/kubernetes-quick-start/master/setup/0.1_server_init | sh -

# 禁用Swap分区
sudo swapoff -a
# 修改/etc/fstab，注释掉swap那行，持久化生效
# sudo vi /etc/fstab

# 设置IPv4转发和网络基础设置
sudo modprobe overlay
sudo modprobe br_netfilter
cat <<EOF | sudo tee /etc/sysctl.d/k8s.conf
net.bridge.bridge-nf-call-iptables  = 1
net.ipv4.ip_forward                 = 1
net.bridge.bridge-nf-call-ip6tables = 1
EOF
sudo sysctl --system

# 设置时区、日志时间设置
sudo timedatectl set-timezone Asia/Shanghai
sudo systemctl restart rsyslog
timedatectl
