mvn clean compile jib:dockerBuild
docker rm -f string-calculator && \
docker rmi -f registry.cn-hangzhou.aliyuncs.com/jackyang/string_calculator:0.0.1 && \
docker pull registry.cn-hangzhou.aliyuncs.com/jackyang/string_calculator:0.0.1 && \
docker run -dit --name string-calculator -e 'ENV=local' -p 9080:9080 -v /Users/jack/workspaces/k8s/kubernetes-quick-start/app/spring_boot/logs:/app/logs registry.cn-hangzhou.aliyuncs.com/jackyang/string_calculator:0.0.1
